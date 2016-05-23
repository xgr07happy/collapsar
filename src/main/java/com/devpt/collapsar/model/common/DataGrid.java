package com.devpt.collapsar.model.common;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by chenyong on 2015/12/4.
 */
public class DataGrid {
    private long total;
    private List rows;

    public DataGrid(){}

    public DataGrid(List rows){
        this.rows = rows;
        if(this.rows != null){
            this.total = this.rows.size();
        }
    }

    public DataGrid(long total, List rows){
        this.total = total;
        this.rows = rows;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "DataGrid{" +
                "total=" + total +
                ", rows=" + rows +
                '}';
    }
}
