package com.devpt.collapsar.dao.impl;

import com.devpt.collapsar.dao.AsyncTaskDao;
import com.devpt.collapsar.dao.mapper.UtilAsyncTaskMapper;
import com.devpt.collapsar.model.UtilAsyncTask;
import com.devpt.collapsar.model.UtilAsyncTaskExample;
import com.devpt.collapsar.model.common.AsyncTask;
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
public class AsyncTaskDaoImpl implements AsyncTaskDao {
    @Autowired
    private UtilAsyncTaskMapper utilAsyncTaskMapper;

    @Override
    public List<AsyncTask> getTimeoutTasks() {
        UtilAsyncTaskExample example = new UtilAsyncTaskExample();
        example.or()
                .andStatusIn(Arrays.asList(AsyncTask.STATUS_UNKNOWN, AsyncTask.STATUS_SERVICE_UNAVAILABLE))
                .andNextTimeLessThan(MyUtils.getCurrentTimeStamp());
        List<UtilAsyncTask> utilAsyncTaskList = this.utilAsyncTaskMapper.selectByExample(example);

        List<AsyncTask> asyncTaskList = new ArrayList<>();
        if(null != utilAsyncTaskList && utilAsyncTaskList.size()>0){
            for(UtilAsyncTask utilAsyncTask : utilAsyncTaskList){
                asyncTaskList.add(this.convert(utilAsyncTask));
            }
        }
        return asyncTaskList;
    }

    @Override
    public AsyncTask getTaskById(int id) {
        UtilAsyncTask utilAsyncTask = this.utilAsyncTaskMapper.selectByPrimaryKey(id);
        return this.convert(utilAsyncTask);
    }

    @Override
    public int updateTask(AsyncTask task) {
        UtilAsyncTask utilAsyncTask = this.convert(task);
        UtilAsyncTaskExample example = new UtilAsyncTaskExample();
        example.or()
                .andIdEqualTo(utilAsyncTask.getId());
        return this.utilAsyncTaskMapper.updateByExampleSelective(utilAsyncTask, example);
    }


    private AsyncTask convert(UtilAsyncTask utilAsyncTask){
        AsyncTask task = new AsyncTask();
        task.setId(utilAsyncTask.getId());
        task.setGid(utilAsyncTask.getGid());
        task.setType(utilAsyncTask.getType());
        task.setContext(utilAsyncTask.getContext());
        task.setStatus(utilAsyncTask.getStatus());
        task.setCreateTime(utilAsyncTask.getCreateTime());
        task.setUpdateTime(utilAsyncTask.getUpdateTime());
        task.setNextTime(utilAsyncTask.getNextTime());
        task.setRetryNum(utilAsyncTask.getRetryNum());
        return task;
    }

    private UtilAsyncTask convert(AsyncTask asyncTask){
        UtilAsyncTask utilAsyncTask = new UtilAsyncTask();
        utilAsyncTask.setId(asyncTask.getId());
        utilAsyncTask.setGid(asyncTask.getGid());
        utilAsyncTask.setType(asyncTask.getType());
        utilAsyncTask.setContext(asyncTask.getContext());
        utilAsyncTask.setStatus(asyncTask.getStatus());
        utilAsyncTask.setCreateTime(asyncTask.getCreateTime());
        utilAsyncTask.setUpdateTime(asyncTask.getUpdateTime());
        utilAsyncTask.setNextTime(asyncTask.getNextTime());
        utilAsyncTask.setRetryNum(asyncTask.getRetryNum());
        return utilAsyncTask;
    }
}
