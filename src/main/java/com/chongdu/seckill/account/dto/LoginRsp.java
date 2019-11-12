package com.chongdu.seckill.account.dto;

public class LoginRsp {

    private String token;

    private UserRsp items;

    private String openid;

    private String sessionKey;

    public LoginRsp(UserRsp items, String token) {
        this.items = items;
        this.token = token;
    }

    /**
     * 小程序登录返回结构体
     * @param items
     * @param token
     * @param openid
     * @param sessionKey
     */
    public LoginRsp(UserRsp items, String token, String openid, String sessionKey) {
        this.items = items;
        this.token = token;
        this.openid = openid;
        this.sessionKey = sessionKey;
    }
}
