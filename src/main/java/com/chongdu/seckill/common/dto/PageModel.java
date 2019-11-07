package com.chongdu.seckill.common.dto;

import lombok.Data;

import java.util.List;

/**
 * 自定义分页数据模型类
 */

@Data
public class PageModel {

    private Integer currentPage;  // 当前页

    private Integer size; // 每页显示的记录数

    private Integer totalPage; // 总页数

    private Integer totalRecord; // 总记录数

    private Integer fromIndex;  // 开始数据索引

    private List<?> dataList;
}
