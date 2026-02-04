package com.wl.security_demo.params;

import lombok.Data;

@Data
public class BasePageParam {

    private Integer pageNum = 1;

    private  Integer pageSize = 10;

}
