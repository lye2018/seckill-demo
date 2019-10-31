package com.chongdu.seckill.mapper;

import com.chongdu.seckill.entity.ExpressCompany;

public interface ExpressCompanyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ExpressCompany record);

    int insertSelective(ExpressCompany record);

    ExpressCompany selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ExpressCompany record);

    int updateByPrimaryKey(ExpressCompany record);
}