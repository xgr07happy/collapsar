package com.devpt.collapsar.service.task;

import com.devpt.collapsar.dao.AsyncTaskDao;
import com.devpt.collapsar.model.common.AsyncTask;
import com.devpt.collapsar.util.DistributedLocker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by chenyong on 2016/7/6.
 */
@Service
public class AsyncTaskService {
    private static final Logger logger = LoggerFactory.getLogger(AsyncTaskService.class);
    private static final String ASYNC_TASK_LOCKER_PREFIX = "async_task_locker:";
    private static final int ASYNC_TASK_LOCKER_TIMEOUT = 60;
    private RunningTask runningTask = new RunningTask();
    @Autowired
    private DistributedLocker distributedLocker;

    @Autowired
    private AsyncTaskDao asyncTaskDao;




    public void scanAndLaunch(){
        for(AsyncTask task : asyncTaskDao.getTimeoutTasks()){
            if(runningTask.isRunning(task)){
                logger.debug("scanAndLaunch: task is still running at local. taskId={}", task.getId());
            }else if(this.distributedLocker.isLocked(this.getLockerName(task))){
                logger.debug("scanAndLaunch: task is running in other host. taskId={}", task.getId());
            }else{
                runningTask.addTask(task);
                try{
                    this.executeTask(task);
                }catch (Exception ex){
                    ex.printStackTrace();
                    runningTask.removeTask(task);
                }
            }
        }
    }




    @Async
    private void executeTask(AsyncTask task){
        logger.debug("executeTask: execute task begin, task={}", task);
        String lockerName = this.getLockerName(task);
        String lockerId = this.distributedLocker.lock(lockerName, ASYNC_TASK_LOCKER_TIMEOUT);
        if(null == lockerId){
            runningTask.removeTask(task);
            logger.debug("executeTask: the task is not running since the locking fail.");
            return;
        }

        try{
            //confirm if the task has been executed
            AsyncTask t = this.asyncTaskDao.getTaskById(task.getId());
            if(null != t && t.equals(task)){
                int status = task.getStatus();
                switch (task.getType()){
                    case AsyncTask.TYPE_DEFAULT:
                        processDefaultTask(task);
                        break;
                    default:
                        logger.error("executeTask: invalid task, type={}", task.getType());
                }
            }

        } finally {
            this.runningTask.removeTask(task);
            this.distributedLocker.unlock(lockerName, lockerId);
        }
        logger.debug("executeTask: execute task finish, task={}", task);
    }


    private void processDefaultTask(AsyncTask task){
        /**
         * use context to process request and get response
         * chk response
         * if need retry then updat async task to retry status( if has next retry),
         * else update async task to a certain status and stop retry.
         * update the async task.
         */

    }


    private String getLockerName(AsyncTask task){
        return ASYNC_TASK_LOCKER_PREFIX + task.getId();
    }


    private class RunningTask{
        private ConcurrentHashMap<Integer, Integer> tasks = new ConcurrentHashMap<>();

        public void addTask(AsyncTask task){
            this.tasks.put(task.getId(), task.getId());
        }

        void removeTask(AsyncTask task){
            this.tasks.remove(task.getId());
        }

        boolean isRunning(AsyncTask task){
            return tasks.contains(task.getId());
        }

        String dumpTask(){
            StringBuffer sb = new StringBuffer();
            Enumeration<Integer> keys = tasks.keys();
            while(keys.hasMoreElements()){
                sb.append(keys.nextElement()).append(",");
            }
            return sb.toString();
        }
    }
}
