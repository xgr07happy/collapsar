package com.devpt.collapsar.dao.mapper;

import com.devpt.collapsar.model.RbacUserRole;
import com.devpt.collapsar.model.RbacUserRoleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RbacUserRoleMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rbac_user_role
     *
     * @mbggenerated
     */
    int countByExample(RbacUserRoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rbac_user_role
     *
     * @mbggenerated
     */
    int deleteByExample(RbacUserRoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rbac_user_role
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rbac_user_role
     *
     * @mbggenerated
     */
    int insert(RbacUserRole record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rbac_user_role
     *
     * @mbggenerated
     */
    int insertSelective(RbacUserRole record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rbac_user_role
     *
     * @mbggenerated
     */
    List<RbacUserRole> selectByExample(RbacUserRoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rbac_user_role
     *
     * @mbggenerated
     */
    RbacUserRole selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rbac_user_role
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") RbacUserRole record, @Param("example") RbacUserRoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rbac_user_role
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") RbacUserRole record, @Param("example") RbacUserRoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rbac_user_role
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(RbacUserRole record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rbac_user_role
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(RbacUserRole record);
}