package com.devpt.collapsar.dao.impl;

import com.devpt.collapsar.dao.RbacDao;
import com.devpt.collapsar.dao.mapper.*;
import com.devpt.collapsar.model.RbacUser;
import com.devpt.collapsar.model.RbacUserExample;
import com.devpt.collapsar.model.common.BasicModel;
import com.devpt.collapsar.model.common.DataGrid;
import com.devpt.collapsar.model.rbac.QueryUsersReq;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import java.util.List;

/**
 * Created by chenyong on 2016/5/17.
 */
@Repository
public class RbacDaoImpl implements RbacDao{
    @Autowired
    private RbacUserMapper rbacUserMapper;
    @Autowired
    private RbacRoleMapper rbacRoleMapper;
    @Autowired
    private RbacGroupMapper rbacGroupMapper;
    @Autowired
    private RbacResourceMapper rbacResourceMapper;
    @Autowired
    private RbacUserRoleMapper rbacUserRoleMapper;
    @Autowired
    private RbacUserGroupMapper rbacUserGroupMapper;
    @Autowired
    private RbacGroupRoleMapper rbacGroupRoleMapper;



    @Override
    public List<RbacUser> getUsers() {
        RbacUserExample example = new RbacUserExample();
        example.or()
                .andIsValidEqualTo(BasicModel.ROW_IS_VALID);

        List<RbacUser> rbacUsers = this.rbacUserMapper.selectByExample(example);
        return rbacUsers;
    }

    @Override
    public DataGrid getUsersByPage(int pageIndex, int pageSize) {
        RbacUserExample example = new RbacUserExample();
        example.or()
                .andIsValidEqualTo(BasicModel.ROW_IS_VALID);
        Page page = PageHelper.startPage(pageIndex, pageSize);
        this.rbacUserMapper.selectByExample(example);
        DataGrid dataGrid = new DataGrid(page.getTotal(), page.getResult());
        return dataGrid;
    }
}
