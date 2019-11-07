package com.chongdu.seckill.common.vo;

import com.chongdu.seckill.common.enums.CodeMsgEnum;

public class CodeMsg {


    /*服务端异常*/
    public static CodeMsg SUCCESS = new CodeMsg(200,"处理成功", CodeMsgEnum.SUCCESS);
    public static CodeMsg INVALID_PARAM = new CodeMsg(400,"参数错误", CodeMsgEnum.INVALID_PARAM);
    public static CodeMsg UNAUTHORIZED = new CodeMsg(401,"授权失败", CodeMsgEnum.UNAUTHORIZED); /*用占位符 传入一个参数*/
    public static CodeMsg NOT_FOUND = new CodeMsg(404,"请求资源不存在：%s", CodeMsgEnum.NOT_FOUND); /*用占位符 传入一个参数*/
    public static CodeMsg CONFLICT = new CodeMsg(409,"状态不符合：%s", CodeMsgEnum.CONFLICT); /*用占位符 传入一个参数*/
    public static CodeMsg SERVER_ERROR = new CodeMsg(500,"系统内部错误", CodeMsgEnum.SERVER_ERROR); /*用占位符 传入一个参数*/
    public static CodeMsg SERVICE_UNAVAILABLE = new CodeMsg(503, "服务暂不可用", CodeMsgEnum.SERVICE_UNAVAILABLE);
    public static CodeMsg INVALID_TOKEN = new CodeMsg(601, "token失效：%s", CodeMsgEnum.INVALID_TOKEN);
    public static CodeMsg INVALID_SMSCODE = new CodeMsg(602, "验证码不正确", CodeMsgEnum.INVALID_SMSCODE);
    public static CodeMsg INVALID_PASSWORD = new CodeMsg(603, "密码错误或账号错误", CodeMsgEnum.INVALID_PASSWORD);
    public static CodeMsg EXCEED_LIMIT = new CodeMsg(604, "请求超过限制", CodeMsgEnum.EXCEED_LIMIT);
    public static CodeMsg BATCH_FAILED = new CodeMsg(605, "处理失败: %s", CodeMsgEnum.BATCH_FAILED);
    public static CodeMsg RECORD_EXIST = new CodeMsg(606, "记录已存在: %s", CodeMsgEnum.RECORD_EXIST);
    public static CodeMsg PARTIAL_SUCCESS = new CodeMsg(607, "部分成功: %s", CodeMsgEnum.PARTIAL_SUCCESS);
    public static CodeMsg IN_USE = new CodeMsg(612, "正在使用中: %s", CodeMsgEnum.IN_USE);

    private int code;
    private String msg;
    private CodeMsgEnum codeMsgEnum;

    private CodeMsg() {
    }

    private CodeMsg(int code, String msg, CodeMsgEnum codeMsgEnum) {
        this.code = code;
        this.msg = msg;
        this.codeMsgEnum = codeMsgEnum;
    }

    private CodeMsg(CodeMsgEnum codeMsgEnum) {
        this.code = codeMsgEnum.getState();
        this.msg = codeMsgEnum.getMessage();
        this.codeMsgEnum = codeMsgEnum;
    }

    public CodeMsg fillArgs(Object... args) {
        int code = this.code;
        String message = String.format(this.msg, args);
        return new CodeMsg(code, message, this.codeMsgEnum);
//        return new CodeMsg(this.codeMsgEnum);
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public CodeMsgEnum getCodeMsgEnum() {
        return codeMsgEnum;
    }

    public void setCodeMsgEnum(CodeMsgEnum codeMsgEnum) {
        this.codeMsgEnum = codeMsgEnum;
    }

    @Override
    public String toString() {
        return "CodeMsg{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
