package com.wl.security_demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wl.security_demo.domain.entity.SysUser;
import com.wl.security_demo.mapper.SysPermissionMapper;
import com.wl.security_demo.mapper.SysRoleMapper;
import com.wl.security_demo.service.SysPermissionService;
import com.wl.security_demo.service.SysRoleService;
import com.wl.security_demo.service.SysUserService;
import com.wl.security_demo.vo.LoginUser;
import jakarta.annotation.Resource;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static net.sf.jsqlparser.util.validation.metadata.NamedObject.user;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private SysUserService sysUserService;

    @Resource
    private SysRoleService sysRoleService;

    @Resource
    private SysPermissionService sysPermissionService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. 从数据库查询用户
        LambdaQueryWrapper<SysUser> userQueryWrapper = new LambdaQueryWrapper<>();
        userQueryWrapper.eq(SysUser::getUsername, username);
        SysUser sysUser = sysUserService.getOne(userQueryWrapper);

        // 2. 如果查不到用户，必须抛出这个异常
        if (sysUser == null) {
            throw new UsernameNotFoundException("用户 " + username + " 不存在");
        }

        // 2. 查该用户的角色标识集合 (如: ["ADMIN", "MANAGER"])
        Set<String> roles = sysRoleService.queryUserRoleKeys(sysUser.getId());

        // 3. 查该用户的权限标识集合 (如: ["system:user:list", "system:user:delete"])
        Set<String> perms = sysPermissionService.getPermsByUserId(sysUser.getId());

        // 4. 构建 LoginUser
        LoginUser loginUser = new LoginUser();
        loginUser.setUser(sysUser);
        loginUser.setUserId(sysUser.getId());
        loginUser.setDeptId(sysUser.getDeptId());
        loginUser.setRoles(roles);
        loginUser.setPermissions(perms);

        return loginUser;

    }
}
