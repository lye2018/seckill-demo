package com.chongdu.seckill.account.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chongdu.seckill.account.entity.LoginLog;
import com.chongdu.seckill.account.entity.User;
import com.chongdu.seckill.account.mapper.LoginLogMapper;
import com.chongdu.seckill.account.mapper.UserMapper;
import com.chongdu.seckill.account.service.UserService;
import com.chongdu.seckill.common.dto.Constants;
import com.chongdu.seckill.common.exception.ServiceException;
import com.chongdu.seckill.common.utils.IpAddress;
import com.chongdu.seckill.common.utils.StringUtil;
import com.chongdu.seckill.common.vo.CodeMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author lye
 * @since 2019-11-07
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    LoginLogMapper loginLogMapper;

    @Autowired
    HttpServletRequest request;

    @Transactional(rollbackFor = Exception.class)
    public User loginByWxUnionId(String unionid) throws ServiceException {
        try {


            // 查看三方登录用户是否登录过 ()
            User userInfo = userMapper.selectOne(new QueryWrapper<User>().eq("regeistType", Constants.REGISTE_WX).eq("openId_wx", unionid));
            String t = String.valueOf(System.currentTimeMillis());
            User user = null;
            if (null == userInfo) {

                // 新增记录
                user = new User();
                user.setId(UUID.randomUUID().toString());
                user.setD(0);
                user.setStatus(0);
//        		user.setIcon(req.getHeadImg());
                user.setOpenidWx(unionid);
//                user.setOpenidQq(unionid);
                user.setRegeistType(Constants.REGISTE_WX); // 微信登录
                String nickName = "cd_" + StringUtil.generateString(6);
                user.setNickname(nickName);
                user.setUsername("cd_" + StringUtil.generateString(6));
                user.setCt(t);
                userMapper.insert(user);

            } else {

                user = userInfo;
            }

            // 登录日志信息
            LoginLog loginLog = new LoginLog();
            loginLog.setUserId(user.getId());
            String ip = IpAddress.getIpAddr(request);
            loginLog.setIp(ip);
            loginLog.setLoginTime(t);
            loginLog.setType(Constants.REGISTE_WX);
//            loginLog.setMac(req.getMacAddress());

            // 保存登录日志信息
            loginLogMapper.insert(loginLog);

            // 返回结果
//            String nickName = user.getNickname();
//            user.setNickname(EmojiUtil.parseToUnicode(nickName));
            return user;
        } catch (Exception e) {
            throw new ServiceException(CodeMsg.SERVER_ERROR);
        }
    }
}
