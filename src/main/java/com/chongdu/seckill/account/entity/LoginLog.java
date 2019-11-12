package com.chongdu.seckill.account.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2019-11-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("chongdu_login_log")
public class LoginLog implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户类型
     */
    private Integer type;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 管理员ID
     */
    private String adminId;

    /**
     * 登录时间
     */
    private String loginTime;

    /**
     * ip地址
     */
    private String ip;

    /**
     * mac地址
     */
    private String mac;


}
