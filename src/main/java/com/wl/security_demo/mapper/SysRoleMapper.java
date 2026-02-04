package com.wl.security_demo.mapper;

import com.wl.security_demo.domain.entity.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wangl
 * @since 2026-02-04
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

    Set<String> queryUserRoleKeys(@Param("userId") Integer userId);

}
