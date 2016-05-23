package com.devpt.collapsar.service;

import com.devpt.collapsar.model.RbacUser;
import com.devpt.collapsar.model.common.DataGrid;
import com.devpt.collapsar.model.rbac.QueryUsersByPageReq;
import com.devpt.collapsar.model.rbac.QueryUsersReq;

import java.util.List;

/**
 * Created by chenyong on 2016/5/17.
 */
public interface RbacService {

    List<RbacUser> queryUsers(QueryUsersReq queryUsersReq);

    DataGrid queryUsersByPage(QueryUsersByPageReq queryUsersByPageReq);
}
