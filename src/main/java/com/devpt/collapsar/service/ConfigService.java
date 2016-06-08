package com.devpt.collapsar.service;

import com.devpt.collapsar.model.ConfigGlobal;
import com.devpt.collapsar.model.ConfigOuterChannel;

/**
 * Created by chenyong on 2016/6/7.
 */
public interface ConfigService {
    ConfigGlobal getConfigGlobal(String key);
    ConfigOuterChannel getConfigOuterChannel(int channelCode);
}
