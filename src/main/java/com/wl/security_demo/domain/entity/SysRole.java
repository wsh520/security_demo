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
 * 
 * </p>
 *
 * @author wangl
 * @since 2026-02-04
 */
@Getter
@Setter
@TableName("sys_role")
public class SysRole implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 角色名称
     */
    @TableField("role_name")
    private String roleName;

    @TableField("role_code")
    private String roleCode;

    /**
     * 角色描述
     */
    @TableField("role_description")
    private String roleDescription;

    @TableField("create_user")
    private String createUser;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("update_user")
    private String updateUser;

    @TableField("update_time")
    private LocalDateTime updateTime;

    /**
     * 1:全部, 2:自定义, 3:本部门, 4:本部门及以下, 5:仅本人
     */
    @TableField("data_scope")
    private Integer dataScope;
}
