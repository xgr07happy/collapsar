package com.devpt.collapsar.dao.mapper;

import com.devpt.collapsar.model.UtilRetryTask;
import com.devpt.collapsar.model.UtilRetryTaskExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UtilRetryTaskMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table util_retry_task
     *
     * @mbggenerated
     */
    int countByExample(UtilRetryTaskExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table util_retry_task
     *
     * @mbggenerated
     */
    int deleteByExample(UtilRetryTaskExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table util_retry_task
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table util_retry_task
     *
     * @mbggenerated
     */
    int insert(UtilRetryTask record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table util_retry_task
     *
     * @mbggenerated
     */
    int insertSelective(UtilRetryTask record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table util_retry_task
     *
     * @mbggenerated
     */
    List<UtilRetryTask> selectByExample(UtilRetryTaskExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table util_retry_task
     *
     * @mbggenerated
     */
    UtilRetryTask selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table util_retry_task
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") UtilRetryTask record, @Param("example") UtilRetryTaskExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table util_retry_task
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") UtilRetryTask record, @Param("example") UtilRetryTaskExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table util_retry_task
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(UtilRetryTask record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table util_retry_task
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(UtilRetryTask record);
}