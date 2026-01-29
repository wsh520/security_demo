package com.wl.security_demo.controller;

import com.wl.security_demo.exceptions.BusinessException;
import com.wl.security_demo.utils.JwtUtils;
import com.wl.security_demo.vo.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class TestController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public static Map<String,String> TOKEN_STORE = new HashMap<>();


    /**
     * 模拟数据库中的数据
     */
    public static Map<String, SysUser> MOCK_DB = new HashMap<>();

    static {
        MOCK_DB.put("admin", new SysUser(1L,
                "admin",
                new BCryptPasswordEncoder().encode("123456"),
                Arrays.asList("system:user:query", "system:user:delete","ROLE_ADMIN")));
        MOCK_DB.put("zhangsan", new SysUser(2L,
                "zhangsan",
                new BCryptPasswordEncoder().encode("123456"),
                List.of("system:user:query","system:user:delete")));
    }

    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody Map<String, String> user) {
        String username = user.get("username");
        String password = user.get("password");
        String strRoles = user.get("roles");

        // 1. 校验用户是否已存在
        if (MOCK_DB.containsKey(username)) {
            return Map.of("code", 400, "msg", "用户名已存在");
        }

        // 2. 【核心】加密密码，绝对不能明文存储
        String encodePassword = passwordEncoder.encode(password);

        // 3. 存入数据库 (模拟)
        MOCK_DB.put(username, new SysUser(1L, username, encodePassword, Arrays.stream(strRoles.split(",")).toList()));

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
        TOKEN_STORE.put(body.get("username"), token);
        return result;
    }

    /**
     * 2. 权限验证接口：查询权限
     * admin 和 zhangsan 都能访问
     */
    @GetMapping("/user/query")
    @PreAuthorize("hasAuthority('system:user:query')")
    public String queryUser() {

        throw new BusinessException("查询出错了！");
//        return "查询用户成功！";
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
