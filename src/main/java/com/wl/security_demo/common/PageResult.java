package com.wl.security_demo.common;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PageResult<T> {

    private List<T> list;      // 数据列表

    private long total;        // 总条数
}
