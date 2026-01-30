package com.wl.security_demo.controller;

import com.wl.security_demo.cache.DataCache;
import com.wl.security_demo.exceptions.BusinessException;
import com.wl.security_demo.utils.JwtUtils;
import com.wl.security_demo.vo.SysUser;
import jakarta.annotation.Resource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class TestController {

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody Map<String, String> user) {
        String username = user.get("username");
        String password = user.get("password");
        String strRoles = user.get("roles");

        // 1. 校验用户是否已存在
        if (DataCache.MOCK_DB.containsKey(username)) {
            return Map.of("code", 400, "msg", "用户名已存在");
        }

        // 2. 【核心】加密密码，绝对不能明文存储
        String encodePassword = passwordEncoder.encode(password);

        // 3. 存入数据库 (模拟)
        DataCache.MOCK_DB.put(username, new SysUser(1L, username, encodePassword, Arrays.stream(strRoles.split(",")).toList()));

        return Map.of("code", 200, "msg", "注册成功");
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> body) {
        // 1. 验证账号密码
        var authRequest = new UsernamePasswordAuthenticationToken(body.get("username"), body.get("password"));
        Authentication auth = authenticationManager.authenticate(authRequest);

        // 2. 获取该用户的权限列表
        List<String> authorities = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        // 3. 生成 JWT
        String token = JwtUtils.createToken(auth.getName(), authorities);

        Map<String, Object> result = new HashMap<>();
        result.put("token", "Bearer " + token); // 加上 Bearer 前缀规范
        DataCache.TOKEN_STORE.put(body.get("username"), token);
        return result;
    }

    /**
     * 2. 权限验证接口：查询权限
     * admin 和 zhangsan 都能访问
     */
    @GetMapping("/user/query")
    @PreAuthorize("hasAuthority('system:user:query')")
    public String queryUser() {

        throw new BusinessException("查询出错了！"); // 错误模拟，依靠全局异常处理器处理，正确抛出异常

    }

    /**
     * 3. 权限验证接口：删除权限
     * 只有 admin 能访问
     */
    @DeleteMapping("/user/delete")
    @PreAuthorize("hasRole('ADMIN')  and hasAuthority('system:user:delete')")
    public String deleteUser() {
        return "删除用户成功！";
    }
}
