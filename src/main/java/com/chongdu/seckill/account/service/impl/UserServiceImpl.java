package com.chongdu.seckill.account.service.impl;

import com.chongdu.seckill.account.entity.User;
import com.chongdu.seckill.account.mapper.UserMapper;
import com.chongdu.seckill.account.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lye
 * @since 2019-11-07
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
