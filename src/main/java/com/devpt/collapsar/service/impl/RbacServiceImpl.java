package com.devpt.collapsar.service.impl;

import com.devpt.collapsar.dao.RbacDao;
import com.devpt.collapsar.exception.CollapsarException;
import com.devpt.collapsar.model.RbacUser;
import com.devpt.collapsar.model.common.DataGrid;
import com.devpt.collapsar.model.rbac.QueryUsersByPageReq;
import com.devpt.collapsar.model.rbac.QueryUsersReq;
import com.devpt.collapsar.service.RbacService;
import com.devpt.collapsar.util.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by chenyong on 2016/5/17.
 */
@Service
public class RbacServiceImpl implements RbacService{
    private static final Logger logger = LoggerFactory.getLogger(RbacService.class);
    @Autowired
    private RbacDao rbacDao;


    @Override
    public List<RbacUser> queryUsers(QueryUsersReq queryUserReq) {
        if(null == queryUserReq){
            logger.error("queryUsers: queryUserReq is null.");
            throw new CollapsarException(ErrorCode.SYS_PARAM_INVALID);
        }
        List<RbacUser> rbacUserList = this.rbacDao.getUsers();
        return rbacUserList;
    }


    @Override
    public DataGrid queryUsersByPage(QueryUsersByPageReq queryUsersByPageReq) {
        if(null == queryUsersByPageReq){
            logger.error("queryUsers: queryUsersByPageReq is null. queryUsersByPageReq={}", queryUsersByPageReq);
            throw new CollapsarException(ErrorCode.SYS_PARAM_INVALID);
        }

        int pageIndex = queryUsersByPageReq.getPageIndex();
        int pageSize = queryUsersByPageReq.getPageSize();
        if(pageIndex<=0 || pageSize<=0){
            logger.error("queryUsers: pageIndex or pageSize is invalid. pageIndex={}, pageSize={}", pageIndex, pageSize);
            throw new CollapsarException(ErrorCode.SYS_PARAM_INVALID);
        }

        DataGrid dataGrid = this.rbacDao.getUsersByPage(pageIndex, pageSize);
        return dataGrid;
    }


}
