package com.wl.security_demo.common;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AjaxResult {
    private int code;
    private String msg;
    private Object data;

    public static AjaxResult error(int code, String msg) {
        return new AjaxResult(code, msg, null);
    }
}