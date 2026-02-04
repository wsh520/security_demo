package com.wl.security_demo.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BizOrderVO {

    private String orderNo;

    /**
     * 金额
     */
    private BigDecimal amount;


    /**
     * 创建者ID
     */
    private Integer userId;

    /**
     * 创建者
     */
    private Integer userName;

}
