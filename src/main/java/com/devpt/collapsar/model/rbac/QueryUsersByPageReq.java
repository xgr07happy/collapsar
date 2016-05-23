package com.devpt.collapsar.model.rbac;

/**
 * Created by chenyong on 2016/5/17.
 */
public class QueryUsersByPageReq {
    private int pageIndex;
    private int pageSize;

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }


    @Override
    public String toString() {
        return "QueryUsersByPageReq{" +
                "pageIndex=" + pageIndex +
                ", pageSize=" + pageSize +
                '}';
    }
}
