package com.devpt.collapsar.dao.mapper;

import com.devpt.collapsar.model.ConfigOuterChannel;
import com.devpt.collapsar.model.ConfigOuterChannelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ConfigOuterChannelMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table config_outer_channel
     *
     * @mbggenerated
     */
    int countByExample(ConfigOuterChannelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table config_outer_channel
     *
     * @mbggenerated
     */
    int deleteByExample(ConfigOuterChannelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table config_outer_channel
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table config_outer_channel
     *
     * @mbggenerated
     */
    int insert(ConfigOuterChannel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table config_outer_channel
     *
     * @mbggenerated
     */
    int insertSelective(ConfigOuterChannel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table config_outer_channel
     *
     * @mbggenerated
     */
    List<ConfigOuterChannel> selectByExample(ConfigOuterChannelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table config_outer_channel
     *
     * @mbggenerated
     */
    ConfigOuterChannel selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table config_outer_channel
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") ConfigOuterChannel record, @Param("example") ConfigOuterChannelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table config_outer_channel
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") ConfigOuterChannel record, @Param("example") ConfigOuterChannelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table config_outer_channel
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(ConfigOuterChannel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table config_outer_channel
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(ConfigOuterChannel record);
}