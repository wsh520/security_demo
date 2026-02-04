package com.wl.security_demo.service.impl;

import com.wl.security_demo.domain.entity.SysRole;
import com.wl.security_demo.mapper.SysRoleMapper;
import com.wl.security_demo.service.SysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wangl
 * @since 2026-02-04
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Resource
    private SysRoleMapper sysRoleMapper;

    @Override
    public Set<String> queryUserRoleKeys(Integer userId) {

        return sysRoleMapper.queryUserRoleKeys(userId);
    }
}
