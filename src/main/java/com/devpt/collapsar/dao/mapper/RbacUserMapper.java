package com.devpt.collapsar.dao.mapper;

import com.devpt.collapsar.model.RbacUser;
import com.devpt.collapsar.model.RbacUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RbacUserMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rbac_user
     *
     * @mbggenerated
     */
    int countByExample(RbacUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rbac_user
     *
     * @mbggenerated
     */
    int deleteByExample(RbacUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rbac_user
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rbac_user
     *
     * @mbggenerated
     */
    int insert(RbacUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rbac_user
     *
     * @mbggenerated
     */
    int insertSelective(RbacUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rbac_user
     *
     * @mbggenerated
     */
    List<RbacUser> selectByExample(RbacUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rbac_user
     *
     * @mbggenerated
     */
    RbacUser selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rbac_user
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") RbacUser record, @Param("example") RbacUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rbac_user
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") RbacUser record, @Param("example") RbacUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rbac_user
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(RbacUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rbac_user
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(RbacUser record);
}