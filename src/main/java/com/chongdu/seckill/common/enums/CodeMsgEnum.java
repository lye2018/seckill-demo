package com.chongdu.seckill.common.enums;

/**
 * 服务端异常
 */
public enum CodeMsgEnum {

    SUCCESS(200, "处理成功"),
    INVALID_PARAM(400, "参数错误"),
    UNAUTHORIZED(401, "授权失败"),
    NOT_FOUND(404, "记录不存在"),
    CONFLICT(409, "状态不符合"),
    SERVER_ERROR(500, "内部错误"),
    SERVICE_UNAVAILABLE(503, "服务暂不可用"),
    INVALID_TOKEN(601, "token失效"),
    INVALID_SMSCODE(602, "验证码不正确"),
    INVALID_PASSWORD(603, "密码错误或账号错误"),
    EXCEED_LIMIT(604, "请求超过限制"),
    BATCH_FAILED(605, "处理失败"),
    RECORD_EXIST(606, "记录已存在"),
    PARTIAL_SUCCESS(607, "部分成功"),
    IN_USE(612, "正在使用");

    private Integer state;
    private String message;

    CodeMsgEnum(Integer state, String message) {
        this.state = state;
        this.message = message;
    }

    public Integer getState() {
        return state;
    }

    public String getMessage() {
        return message;
    }

    public static CodeMsgEnum resolve(Integer code) {
        for (CodeMsgEnum codeMsgEnum: values()) {
            if (codeMsgEnum.state.equals(code)) {
                return codeMsgEnum;
            }
        }
        return null;
    }
}
