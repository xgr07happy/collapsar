package com.devpt.collapsar.dao;

import com.devpt.collapsar.model.RbacUser;
import com.devpt.collapsar.model.common.DataGrid;
import com.devpt.collapsar.model.rbac.QueryUsersReq;

import java.util.List;

/**
 * Created by chenyong on 2016/5/17.
 */
public interface RbacDao {

    List<RbacUser> getUsers();

    DataGrid getUsersByPage(int pageIndex, int pageSize);
}
