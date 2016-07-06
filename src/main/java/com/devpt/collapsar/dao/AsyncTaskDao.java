package com.devpt.collapsar.dao;

import com.devpt.collapsar.model.UtilAsyncTask;
import com.devpt.collapsar.model.common.AsyncTask;

import java.util.List;

/**
 * Created by chenyong on 2016/7/6.
 */
public interface AsyncTaskDao {
    List<AsyncTask> getTimeoutTasks();
    AsyncTask getTaskById(int id);
    int updateTask(AsyncTask task);
}
