package com.chongdu.seckill.account.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author lye
 * @since 2019-11-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("chongdu_user")
public class User implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * id
     */
    private String id;

    /**
     * 用户类型(0.普通用户，1.个体用户，2.公司用户)
     */
    private Integer type;

    /**
     * 用户名
     */
    private String username;

    /**
     * hash值
     */
    private String hash;

    /**
     * 加密盐
     */
    private String salt;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像
     */
    private String icon;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 注册类型(1:邮箱,2:手机号,3:微信,4:QQ,5:dy)
     */
    @TableField("regeistType")
    private Integer regeistType;

    /**
     * 邮箱
     */
    private String mail;

    /**
     * 注册时间
     */
    private String regtime;

    /**
     * 最后登录时间
     */
    private String lastlogintime;

    /**
     * 最后登录ip地址
     */
    private String ip;

    /**
     * VIP
     */
    private Integer vip;

    /**
     * 是否实名认证
     */
    private Integer isauthent;

    /**
     * 用户名是否允许修改(0：Y，1：N)
     */
    private Integer isaudit;

    /**
     * 最后修改者(可以是平台管理员ID)
     */
    private String updateBy;

    /**
     * qq登录的openId
     */
    private String openidQq;

    /**
     * 状态(0：正常，1：停用)
     */
    private Integer status;

    /**
     * 创建时间
     */
    private String ct;

    /**
     * 修改时间
     */
    private String mt;

    /**
     * 是否删除
     */
    private Integer d;

    /**
     * 微信登录的openId
     */
    private String openidWx;

    /**
     * 抖音登录的openId
     */
    private String openidDy;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 余额
     */
    private BigDecimal balance;


}
