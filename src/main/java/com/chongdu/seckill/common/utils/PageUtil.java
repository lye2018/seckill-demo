package com.chongdu.seckill.common.utils;

import com.chongdu.seckill.common.dto.PageModel;

import java.util.List;

public class PageUtil {

    /**
     *
     * @param list List集合
     * @param current 当前页，从1开始
     * @param limit 每页几条数据
     * @return PageModel
     */
    public static <T> PageModel pageForList(List<T> list, Integer current, Integer limit) {

        PageModel page = new PageModel();

        // 设置起始页 必须从从1开始
        if (current == 0) {
            current = 1;
        }
        page.setCurrentPage(current -1);

        // 设置每页显示的条数
        page.setSize(limit);

        // 开始数据索引
        page.setFromIndex((current - 1) * limit);

        int count = list.size();
        page.setTotalRecord(count);
        page.setTotalPage(count % limit == 0 ? count / limit : count / limit +1);

        // 数据截取
        if (page.getFromIndex() > count) {
            page.setDataList(null);
        } else {
            page.setDataList(list.subList(page.getFromIndex(), count - page.getFromIndex() > page.getSize() ? page.getFromIndex() + page.getSize() : count));
        }
        return page;
    }
}
