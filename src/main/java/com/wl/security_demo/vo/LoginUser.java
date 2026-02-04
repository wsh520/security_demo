package com.wl.security_demo.vo;

import com.wl.security_demo.domain.entity.SysUser;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Data
public class LoginUser implements UserDetails {
    private Integer userId;
    private Integer deptId;
    private SysUser user;

    // 权限字符串集合 (如: system:user:list)
    private Set<String> permissions;

    // 角色标识集合 (如: ADMIN, USER)
    private Set<String> roles;

    /**
     * 将角色和权限统一封装为 GrantedAuthority
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();

        // 1. 添加权限标识
        if (!CollectionUtils.isEmpty(permissions)) {
            permissions.forEach(p -> authorities.add(new SimpleGrantedAuthority(p)));
        }

        // 2. 添加角色标识 (Spring Security 默认角色需要加 ROLE_ 前缀才能被 hasRole 识别)
        if (!CollectionUtils.isEmpty(roles)) {
            roles.forEach(r -> {
                String roleKey = r.startsWith("ROLE_") ? r : "ROLE_" + r;
                authorities.add(new SimpleGrantedAuthority(roleKey));
            });
        }

        return authorities;
    }

    // --- 以下为 UserDetails 核心状态判断 ---

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // 与数据库状态挂钩：1-正常, 0-锁定
        return user.getStatus() == 1;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.getStatus() == 1;
    }
}