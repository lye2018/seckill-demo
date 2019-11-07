package com.chongdu.seckill.common.dto;

/**
 * 分页模板
 */
public class Pagination {

    private Integer current = 0;

    private Integer limit = 10;

    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
