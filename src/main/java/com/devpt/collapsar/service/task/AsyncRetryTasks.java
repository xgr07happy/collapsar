package com.devpt.collapsar.service.task;

import com.devpt.collapsar.dao.RetryTaskDao;
import com.devpt.collapsar.model.common.RetryTask;
import com.devpt.collapsar.util.DistributedLocker;
import com.devpt.collapsar.util.MyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Enumeration;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by chenyong on 2016/7/6.
 */
@Component
public class AsyncRetryTasks {
    private static final Logger logger = LoggerFactory.getLogger(AsyncRetryTasks.class);
    private static final String RETRY_TASK_LOCKER_PREFIX = "retry_task_locker:";
    private static final int RETRY_TASK_LOCKER_TIMEOUT = 60;
    private RunningTask runningTask = new RunningTask();
    private ExecutorService executorService = Executors.newCachedThreadPool();
    @Autowired
    private DistributedLocker distributedLocker;

    @Autowired
    private RetryTaskDao retryTaskDao;



    @Scheduled(cron = "*/5 * * * * *")
    public void scanAndLaunch(){

        logger.debug("scanAndLaunch: start.");
        List<RetryTask> timeoutTasks = this.retryTaskDao.getTimeoutTasks();
        if(null == timeoutTasks || timeoutTasks.size() <= 0){
            logger.info("scanAndLaunch: timeoutTasks is empty.");
            return;
        }
        logger.info("scanAndLaunch: timeoutTasks.size={}", timeoutTasks.size());
        for(RetryTask task : timeoutTasks){
            if(runningTask.isRunning(task)){
                logger.debug("scanAndLaunch: task is still running at local. taskId={}", task.getId());
            }else if(this.distributedLocker.isLocked(this.getLockerName(task))){
                logger.debug("scanAndLaunch: task is running in other host. taskId={}", task.getId());
            }else{
                runningTask.addTask(task);
                try{
                    this.executorService.execute(new TaskRunner(task));
                }catch (Exception ex){
                    runningTask.removeTask(task);
                    logger.error("scanAndLaunch: exception happened while execute task.", ex);
                }
            }
        }
        logger.debug("=======================");
    }






    private void executeTask(RetryTask task){
        logger.debug("executeTask: start, task={}", task);
        String lockerName = this.getLockerName(task);
        String lockerId = this.distributedLocker.lock(lockerName, RETRY_TASK_LOCKER_TIMEOUT);
        if(null == lockerId){
            logger.debug("executeTask: the task is not running since the locking fail.");
            this.runningTask.removeTask(task);
            return;
        }

        try{
            //confirm if the task has been executed
            RetryTask t = this.retryTaskDao.getTaskById(task.getId());
            if(null != t && t.equals(task)){
                task.process();
            }

            if(task.canRetry()){
                task.getNextRetryTime();
                logger.debug("executeTask: schedule task(id={}) at({}).", task.getId(), MyUtils.formatTimeStamp(task.getNextTime()));
            }
            task.setUpdateTime(MyUtils.getCurrentTimeStamp());
            this.retryTaskDao.updateTask(task);

        } finally {
            this.runningTask.removeTask(task);
            this.distributedLocker.unlock(lockerName, lockerId);
        }
    }




    private String getLockerName(RetryTask task){
        return RETRY_TASK_LOCKER_PREFIX + task.getId();
    }





    private class TaskRunner implements Runnable{
        private RetryTask task;

        public TaskRunner(RetryTask retryTask){
            task = retryTask;
        }

        @Override
        public void run() {
            AsyncRetryTasks.this.executeTask(task);
        }
    }


    private class RunningTask{
        private ConcurrentHashMap<Integer, Integer> tasks = new ConcurrentHashMap<>();

        public void addTask(RetryTask task){
            this.tasks.put(task.getId(), task.getId());
        }

        void removeTask(RetryTask task){
            this.tasks.remove(task.getId());
        }

        boolean isRunning(RetryTask task){
            return tasks.contains(task.getId());
        }

        public String dump(){
            StringBuffer sb = new StringBuffer();
            Enumeration<Integer> keys = tasks.keys();
            while(keys.hasMoreElements()){
                sb.append(keys.nextElement()).append(",");
            }
            return sb.toString();
        }
    }
}
