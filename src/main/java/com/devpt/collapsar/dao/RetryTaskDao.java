package com.devpt.collapsar.dao;

import com.devpt.collapsar.model.common.RetryTask;

import java.util.List;

/**
 * Created by chenyong on 2016/7/6.
 */
public interface RetryTaskDao {
    List<RetryTask> getTimeoutTasks();
    RetryTask getTaskById(int id);
    int updateTask(RetryTask task);
}
