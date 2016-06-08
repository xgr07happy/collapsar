package com.devpt.collapsar.dao.impl;

import com.devpt.collapsar.dao.ConfigDao;
import com.devpt.collapsar.dao.mapper.ConfigGlobalMapper;
import com.devpt.collapsar.dao.mapper.ConfigOuterChannelMapper;
import com.devpt.collapsar.model.ConfigGlobal;
import com.devpt.collapsar.model.ConfigGlobalExample;
import com.devpt.collapsar.model.ConfigOuterChannel;
import com.devpt.collapsar.model.ConfigOuterChannelExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by chenyong on 2016/6/7.
 */
@Repository
public class ConfigDaoImpl implements ConfigDao{
    @Autowired
    private ConfigGlobalMapper configGlobalMapper;
    @Autowired
    private ConfigOuterChannelMapper configOuterChannelMapper;

    @Override
    public ConfigGlobal getConfigGlobal(String key){
        ConfigGlobalExample example = new ConfigGlobalExample();
        example.or()
                .andIsValidEqualTo(true)
                .andGlobalKeyEqualTo(key);
        List<ConfigGlobal> configGlobalList = this.configGlobalMapper.selectByExample(example);
        return CollectionUtils.isEmpty(configGlobalList) ? null : configGlobalList.get(0);
    }

    @Override
    public ConfigOuterChannel getConfigOuterChannel(int channelCode) {
        ConfigOuterChannelExample example = new ConfigOuterChannelExample();
        example.or()
                .andIsValidEqualTo(true)
                .andChannelCodeEqualTo(channelCode);
        List<ConfigOuterChannel> configOuterChannelList = this.configOuterChannelMapper.selectByExample(example);
        return CollectionUtils.isEmpty(configOuterChannelList) ? null : configOuterChannelList.get(0);
    }
}
