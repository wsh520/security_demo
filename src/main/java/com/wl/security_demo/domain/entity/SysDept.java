package com.wl.security_demo.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 部门表
 * </p>
 *
 * @author wangl
 * @since 2026-02-04
 */
@Getter
@Setter
@TableName("sys_dept")
public class SysDept implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 父部门ID
     */
    @TableField("pid")
    private Integer pid;

    /**
     * 部门名称
     */
    @TableField("name")
    private String name;

    @TableField("create_time")
    private LocalDateTime createTime;
}
