package com.wl.security_demo.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
 * @since 2026-02-03
 */
@Getter
@Setter
@TableName("sys_permission")
public class SysPermission implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 父ID
     */
    @TableField("pid")
    private Integer pid;

    /**
     * 资源类型（1：菜单，2：按钮，3：操作）
     */
    @TableField("type")
    private Byte type;

    /**
     * 资源名称
     */
    @TableField("name")
    private String name;

    /**
     * 资源标识（或者叫权限字符串）
     */
    @TableField("code")
    private String code;

    /**
     * 资源URI
     */
    @TableField("uri")
    private String uri;

    /**
     * 序号
     */
    @TableField("seq")
    private Integer seq;

    @TableField("create_user")
    private String createUser;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("update_user")
    private String updateUser;

    @TableField("update_time")
    private LocalDateTime updateTime;
}
