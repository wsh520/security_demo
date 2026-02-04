package com.wl.security_demo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    SUCCESS(200, "成功"),
    FAILED(500, "失败");

    private static final long serialVersionUID = 1L;

    private final int code;

    private final String msg;
}
