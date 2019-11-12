package com.chongdu.seckill.account.service;

import com.chongdu.seckill.account.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chongdu.seckill.common.exception.ServiceException;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lye
 * @since 2019-11-07
 */
public interface UserService extends IService<User> {

    /**
     * 小程序登录使用
     * @param unionid
     * @return
     * @throws ServiceException
     */
    public User loginByWxUnionId(String unionid) throws ServiceException;
}
