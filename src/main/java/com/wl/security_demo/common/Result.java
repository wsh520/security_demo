package com.wl.security_demo.common;

import com.wl.security_demo.enums.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder // 支持链式编程
public class Result<T> implements Serializable {

    private int code;       // 状态码
    private String msg;     // 提示消息
    private T data;         // 泛型数据负载
    private long timestamp; // 响应时间戳，方便前端排查缓存问题

    // --- 成功状态的静态工厂方法 ---

    public static <T> Result<T> success() {
        return success(null);
    }

    public static <T> Result<T> success(T data) {
        return Result.<T>builder()
                .code(200)
                .msg("操作成功")
                .data(data)
                .timestamp(System.currentTimeMillis())
                .build();
    }

    // --- 失败状态的静态工厂方法 ---

    public static <T> Result<T> error(String msg) {
        return error(500, msg);
    }

    public static <T> Result<T> error(int code, String msg) {
        return Result.<T>builder()
                .code(code)
                .msg(msg)
                .timestamp(System.currentTimeMillis())
                .build();
    }

    // 适配特定的异常或状态码枚举
    public static <T> Result<T> error(ErrorCode errorCode) {
        return error(errorCode.getCode(), errorCode.getMsg());
    }
}