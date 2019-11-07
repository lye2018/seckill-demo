package com.chongdu.seckill.common.exception;

import com.chongdu.seckill.common.vo.CodeMsg;

public class ServiceException extends Exception {

    protected CodeMsg codeMsg;

    public ServiceException(CodeMsg codeMsg) {
        super(codeMsg.getMsg());
        this.codeMsg = codeMsg;
        
    }

    public CodeMsg getCodeMsg() {
        return codeMsg;
    }

    
    
}
