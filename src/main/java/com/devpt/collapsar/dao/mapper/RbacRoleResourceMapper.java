package com.devpt.collapsar.dao.mapper;

import com.devpt.collapsar.model.RbacRoleResource;
import com.devpt.collapsar.model.RbacRoleResourceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RbacRoleResourceMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rbac_role_resource
     *
     * @mbggenerated
     */
    int countByExample(RbacRoleResourceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rbac_role_resource
     *
     * @mbggenerated
     */
    int deleteByExample(RbacRoleResourceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rbac_role_resource
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rbac_role_resource
     *
     * @mbggenerated
     */
    int insert(RbacRoleResource record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rbac_role_resource
     *
     * @mbggenerated
     */
    int insertSelective(RbacRoleResource record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rbac_role_resource
     *
     * @mbggenerated
     */
    List<RbacRoleResource> selectByExample(RbacRoleResourceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rbac_role_resource
     *
     * @mbggenerated
     */
    RbacRoleResource selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rbac_role_resource
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") RbacRoleResource record, @Param("example") RbacRoleResourceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rbac_role_resource
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") RbacRoleResource record, @Param("example") RbacRoleResourceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rbac_role_resource
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(RbacRoleResource record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rbac_role_resource
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(RbacRoleResource record);
}