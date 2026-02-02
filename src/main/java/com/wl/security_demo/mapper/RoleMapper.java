package com.wl.security_demo.mapper;

import com.wl.security_demo.domain.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 角色信息表 Mapper 接口
 * </p>
 *
 * @author wangl
 * @since 2026-02-02
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

}
