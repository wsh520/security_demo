package com.wl.security_demo.utils;

import com.wl.security_demo.vo.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 安全服务工具类
 */
public class SecurityUtils {

    /**
     * 获取当前登录用户信息 (LoginUser 是你自定义的 UserDetails 实现类)
     */
    public static LoginUser getLoginUser() {
        try {
            return (LoginUser) getAuthentication().getPrincipal();
        } catch (Exception e) {
            throw new RuntimeException("获取用户信息异常", e);
        }
    }

    /**
     * 获取 Authentication
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 获取用户ID
     */
    public static Integer getUserId() {
        return getLoginUser().getUserId();
    }

    /**
     * 获取部门ID (用于数据权限)
     */
    public static Integer getDeptId() {
        return getLoginUser().getDeptId();
    }

    /**
     * 获取用户账号
     */
    public static String getUsername() {
        return getLoginUser().getUsername();
    }

    /**
     * 是否为管理员 (基于角色 code 判断)
     */
    public static boolean isAdmin() {
        Integer userId = getUserId();
        return userId != null && userId == 1L; // 或者根据角色标识：getLoginUser().getRoles().contains("ADMIN")
    }

    /**
     * 生成 BCryptPasswordEncoder 密码
     * @param password 密码
     * @return 加密字符串
     */
    public static String encryptPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    /**
     * 判断密码是否相同
     * @param rawPassword 真实密码
     * @param encodedPassword 加密后密码
     * @return 结果
     */
    public static boolean matchesPassword(String rawPassword, String encodedPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
