package com.wl.security_demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wl.security_demo.domain.entity.SysUser;
import com.wl.security_demo.service.SysUserService;
import jakarta.annotation.Resource;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private SysUserService sysUserService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. 模拟从数据库查询用户
        LambdaQueryWrapper<SysUser> userQueryWrapper = new LambdaQueryWrapper<>();
        userQueryWrapper.eq(SysUser::getUsername, username);
        SysUser sysUser = sysUserService.getOne(userQueryWrapper);

        // 2. 如果查不到用户，必须抛出这个异常
        if (sysUser == null) {
            throw new UsernameNotFoundException("用户 " + username + " 不存在");
        }

        List<String> userRoleAndPremiss = sysUserService.queryUserRoleAndPremiss(sysUser.getId());
        // 3. 将数据库中的权限字符串转换为 Spring Security 的 GrantedAuthority 对象
        List<SimpleGrantedAuthority> authorities = userRoleAndPremiss.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        // 4. 返回 Spring Security 预置的 User 对象（这是 UserDetails 的实现类）
        // 注意：实际开发中数据库存的是加密后的密码，这里我们手动加密模拟
        return new User(
                sysUser.getUsername(),
                sysUser.getPassword(),
                authorities
        );

    }
}
