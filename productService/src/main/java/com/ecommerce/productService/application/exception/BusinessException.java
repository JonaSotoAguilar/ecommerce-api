package com.ecommerce.productservice.application.exception;

import com.ecommerce.productservice.application.constant.ErrorCode;

public class BusinessException extends RuntimeException {
    private final ErrorCode code;

    public BusinessException(ErrorCode code) {
        super(code.defaultMessage);
        this.code = code;
    }

    public BusinessException(ErrorCode code, String overrideMsg) {
        super(overrideMsg);
        this.code = code;
    }

    public ErrorCode getCode() {
        return code;
    }
}