package com.wl.security_demo.service.impl;

import com.wl.security_demo.domain.entity.SysPermission;
import com.wl.security_demo.mapper.SysPermissionMapper;
import com.wl.security_demo.service.SysPermissionService;
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
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements SysPermissionService {

    @Resource
    private  SysPermissionMapper sysPermissionMapper;

    @Override
    public Set<String> getPermsByUserId(Integer userId) {


        return sysPermissionMapper.getPermsByUserId(userId);
    }
}
