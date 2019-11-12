package com.chongdu.seckill.account.controller;


import com.alibaba.fastjson.JSONObject;
import com.chongdu.seckill.account.dto.LoginRsp;
import com.chongdu.seckill.account.dto.UserRsp;
import com.chongdu.seckill.account.entity.User;
import com.chongdu.seckill.account.service.AuthService;
import com.chongdu.seckill.account.service.UserService;
import com.chongdu.seckill.common.exception.ServiceException;
import com.chongdu.seckill.common.utils.WeChatUtil;
import com.chongdu.seckill.common.vo.RestResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lye
 * @since 2019-11-07
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    /**  微信小程序ID **/
    private static final String appid = "appid";

    /**  微信小程序秘钥 **/
    private static final String secret = "secret";

    @Autowired
    UserService userService;

    @Autowired
    AuthService authService;

    @ApiOperation(value = "微信小程序登录服务端接口", notes = "to do", response = UserRsp.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "登录成功"),
            @ApiResponse(code = 500, message = "系统异常")
    })
    @GetMapping("/login")
    public RestResponse login(@RequestParam(value = "code", required = true) String code,
                              @RequestParam(value = "encryptedData", required = false) String encryptedData,
                              @RequestParam(value = "iv", required = false) String iv) {

        try {

            String url = "https://api.weixin.qq.com/sns/jscode2session?appid="+ appid +"&secret="+ secret +"&js_code="+ code +"&grant_type=authorization_code";

            // 发送请求，返回Json字符串
            String str = WeChatUtil.httpRequest(url, "GET", null);
            logger.info("请求返回的值：{}", str);

            // 转成Json对象 获取openid
            JSONObject jsonObject = JSONObject.parseObject(str);

            // 取得open_id
            String openid = jsonObject.get("openid").toString();
            logger.info("openid：{}", openid);

            // 取得session_key
            String sessionKey = jsonObject.get("session_key").toString();
            logger.info("session_key：{}", sessionKey);

            // 取得union_id
            String unionid = jsonObject.get("unionid").toString();
            logger.info("unionid：{}", unionid);

            User user = userService.loginByWxUnionId(unionid);

            String token = authService.createToken(user, 1);

            UserRsp userRsp = new UserRsp(user);

            LoginRsp loginRsp = new LoginRsp(userRsp, token, openid, sessionKey);
            return RestResponse.success(loginRsp, "登录成功！");

        } catch (ServiceException e) {
            return RestResponse.error(e.getCodeMsg());
        }
    }
}

