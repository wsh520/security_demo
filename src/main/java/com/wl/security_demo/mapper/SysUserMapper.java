package com.wl.security_demo.mapper;

import com.wl.security_demo.domain.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wangl
 * @since 2026-02-03
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    List<String> queryUserRoleAndPremiss(@Param("userId") Integer userId);
}
