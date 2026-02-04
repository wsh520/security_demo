package com.wl.security_demo.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 订单业务表
 * </p>
 *
 * @author wangl
 * @since 2026-02-04
 */
@Getter
@Setter
@TableName("biz_order")
public class BizOrder implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 订单号
     */
    @TableField("order_no")
    private String orderNo;

    /**
     * 金额
     */
    @TableField("amount")
    private BigDecimal amount;

    /**
     * 所属部门ID
     */
    @TableField("dept_id")
    private Integer deptId;

    /**
     * 创建者ID
     */
    @TableField("user_id")
    private Integer userId;
}
