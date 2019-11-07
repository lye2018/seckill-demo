package com.chongdu.seckill.common.vo;

public class ServiceResult<T> {

    private CodeMsg codeMsg;
    private T data;

    /**
     * Restful接口调用成功
     * @param data
     * @param <T>
     * @return RestResponse
     */
    public static <T> ServiceResult<T> success(T data) {
        return new ServiceResult<>(data);
    }

    /**
     * Restful接口调用成功（无返回值）
     * @param <T>
     * @return
     */
    public static <T> ServiceResult<T> success() {
        return new ServiceResult<>();
    }

    /**
     * Restful接口调用失败
     * @param <T>
     * @return
     */
    public static <T> ServiceResult<T> error(CodeMsg codeMsg) {
        return new ServiceResult<>(codeMsg);
    }

    public ServiceResult() {
        this.codeMsg = CodeMsg.SUCCESS;
        this.data = null;
    }

    public ServiceResult(T data) {
        this.codeMsg = CodeMsg.SUCCESS;
        this.data = data;
    }

    public ServiceResult(T data, CodeMsg codeMsg) {
        this.codeMsg = codeMsg;
        this.data = data;
    }

    public ServiceResult(CodeMsg codeMsg) {
        this.codeMsg = codeMsg;
    }

    /**
     * 返回是否成功
     * @return
     */
    public boolean isSuccess() {
        return this.codeMsg.getCode() == CodeMsg.SUCCESS.getCode();
    }

    public int getCode() { return this.codeMsg.getCode(); }

    public String getMsg() {
        return this.codeMsg.getMsg();
    }

    public T getData() {
        return data;
    }

    public CodeMsg getCodeMsg() { return codeMsg; }

    @Override
    public String toString() {
        return "ServiceResult{" +
                "code=" + this.codeMsg.getCode() +
                ", msg='" + this.codeMsg.getMsg() + "\'" +
                ", data=" + data + "}";
    }
}
