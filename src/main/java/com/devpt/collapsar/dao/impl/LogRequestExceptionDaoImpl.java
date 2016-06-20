package com.devpt.collapsar.dao.impl;

import com.devpt.collapsar.dao.LogRequestExceptionDao;
import com.devpt.collapsar.dao.mapper.LogRequestExceptionMapper;
import com.devpt.collapsar.model.LogRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by chenyong on 2016/6/13.
 */
@Repository
public class LogRequestExceptionDaoImpl implements LogRequestExceptionDao{
    @Autowired
    private LogRequestExceptionMapper logRequestExceptionMapper;

    @Override
    public void insertLogRequestException(LogRequestException log) {
        this.logRequestExceptionMapper.insertSelective(log);
    }
}
