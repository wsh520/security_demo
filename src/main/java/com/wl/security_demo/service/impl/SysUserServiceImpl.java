package com.wl.security_demo.service.impl;

import com.wl.security_demo.domain.entity.SysUser;
import com.wl.security_demo.exceptions.BusinessException;
import com.wl.security_demo.mapper.SysUserMapper;
import com.wl.security_demo.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wangl
 * @since 2026-02-03
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Resource
    private SysUserMapper sysUserMapper;

    @Override
    public List<String> queryUserRoleAndPremiss(Integer userId) {

        List<String> userRoleAndPremiss = sysUserMapper.queryUserRoleAndPremiss(userId);

        if (CollectionUtils.isEmpty(userRoleAndPremiss)) {
            throw new BusinessException(500,"该用户未分配角色或权限，请联系管理员");
        }

        return userRoleAndPremiss;
    }
}
