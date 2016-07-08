package com.devpt.collapsar.dao.impl;

import com.devpt.collapsar.dao.RetryTaskDao;
import com.devpt.collapsar.dao.mapper.UtilRetryTaskMapper;
import com.devpt.collapsar.model.UtilRetryTask;
import com.devpt.collapsar.model.UtilRetryTaskExample;
import com.devpt.collapsar.model.common.RetryTask;
import com.devpt.collapsar.util.MyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by chenyong on 2016/7/6.
 */
@Repository
public class RetryTaskDaoImpl implements RetryTaskDao {
    @Autowired
    private UtilRetryTaskMapper utilRetryTaskMapper;

    @Override
    public List<RetryTask> getTimeoutTasks() {
        UtilRetryTaskExample example = new UtilRetryTaskExample();
        example.or()
                .andStatusIn(Arrays.asList(RetryTask.STATUS_UNKNOWN, RetryTask.STATUS_SERVICE_UNAVAILABLE))
                .andNextTimeLessThan(MyUtils.getCurrentTimeStamp());
        List<UtilRetryTask> utilRetryTaskList = this.utilRetryTaskMapper.selectByExample(example);

        List<RetryTask> retryTaskList = new ArrayList<>();
        if(null != utilRetryTaskList && utilRetryTaskList.size()>0){
            for(UtilRetryTask utilRetryTask : utilRetryTaskList){
                retryTaskList.add(this.convert(utilRetryTask));
            }
        }
        return retryTaskList;
    }

    @Override
    public RetryTask getTaskById(int id) {
        UtilRetryTask utilRetryTask = this.utilRetryTaskMapper.selectByPrimaryKey(id);
        return this.convert(utilRetryTask);
    }

    @Override
    public int updateTask(RetryTask task) {
        UtilRetryTask utilRetryTask = this.convert(task);
        UtilRetryTaskExample example = new UtilRetryTaskExample();
        example.or()
                .andIdEqualTo(utilRetryTask.getId());
        return this.utilRetryTaskMapper.updateByExampleSelective(utilRetryTask, example);
    }


    private RetryTask convert(UtilRetryTask utilRetryTask){
        RetryTask task = new RetryTask();
        task.setId(utilRetryTask.getId());
        task.setGid(utilRetryTask.getGid());
        task.setType(utilRetryTask.getType());
        task.setContext(utilRetryTask.getContext());
        task.setStatus(utilRetryTask.getStatus());
        task.setCreateTime(utilRetryTask.getCreateTime());
        task.setUpdateTime(utilRetryTask.getUpdateTime());
        task.setNextTime(utilRetryTask.getNextTime());
        task.setRetryNum(utilRetryTask.getRetryNum());
        return task;
    }

    private UtilRetryTask convert(RetryTask asyncTask){
        UtilRetryTask utilRetryTask = new UtilRetryTask();
        utilRetryTask.setId(asyncTask.getId());
        utilRetryTask.setGid(asyncTask.getGid());
        utilRetryTask.setType(asyncTask.getType());
        utilRetryTask.setContext(asyncTask.getContext());
        utilRetryTask.setStatus(asyncTask.getStatus());
        utilRetryTask.setCreateTime(asyncTask.getCreateTime());
        utilRetryTask.setUpdateTime(asyncTask.getUpdateTime());
        utilRetryTask.setNextTime(asyncTask.getNextTime());
        utilRetryTask.setRetryNum(asyncTask.getRetryNum());
        return utilRetryTask;
    }
}
