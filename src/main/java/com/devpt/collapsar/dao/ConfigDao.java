package com.devpt.collapsar.dao;

import com.devpt.collapsar.model.ConfigGlobal;
import com.devpt.collapsar.model.ConfigOuterChannel;

/**
 * Created by chenyong on 2016/6/7.
 */
public interface ConfigDao {
    ConfigGlobal getConfigGlobal(String key);
    ConfigOuterChannel getConfigOuterChannel(int channelCode);
}
