package com.wj.exception;

import com.wj.result.CodeMsg;

/**
 * @创建人 wj
 * @创建时间 2018/11/13
 * @描述
 */
public class GlobalException extends RuntimeException {
    private CodeMsg codeMsg;

    public GlobalException(CodeMsg codeMsg) {
        super(codeMsg.getMsg());
        this.codeMsg = codeMsg;
    }

    public CodeMsg getCodeMsg() {
        return codeMsg;
    }
}
