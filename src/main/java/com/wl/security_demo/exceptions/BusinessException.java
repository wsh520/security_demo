package com.wl.security_demo.exceptions;

import lombok.Getter;

import java.io.Serial;

@Getter
public class BusinessException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 业务错误码，默认为 400（可根据项目约定调整）
     */
    private final int code;

    public BusinessException(String message) {
        this(400, message, null);
    }

    public BusinessException(int code, String message) {
        this(code, message, null);
    }

    public BusinessException(String message, Throwable cause) {
        this(400, message, cause);
    }

    public BusinessException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public static BusinessException of(String message) {
        return new BusinessException(message);
    }

    public static BusinessException of(int code, String message) {
        return new BusinessException(code, message);
    }

    @Override
    public String toString() {
        return "BusinessException{code=" + code + ", message=" + getMessage() + "}";
    }
}
