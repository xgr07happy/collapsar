package com.devpt.collapsar.service.impl;

import com.devpt.collapsar.dao.ConfigDao;
import com.devpt.collapsar.model.ConfigGlobal;
import com.devpt.collapsar.model.ConfigOuterChannel;
import com.devpt.collapsar.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by chenyong on 2016/6/7.
 */
@Service
public class ConfigServiceImpl implements ConfigService{
    @Autowired
    private ConfigDao configDao;

    @Override
    public ConfigGlobal getConfigGlobal(String key) {
        return this.configDao.getConfigGlobal(key);
    }

    @Override
    public ConfigOuterChannel getConfigOuterChannel(int channelCode) {
        return this.configDao.getConfigOuterChannel(channelCode);
    }
}
