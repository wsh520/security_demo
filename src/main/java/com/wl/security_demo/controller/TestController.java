package com.wl.security_demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wl.security_demo.common.AjaxResult;
import com.wl.security_demo.domain.entity.UserDO;
import com.wl.security_demo.exceptions.BusinessException;
import com.wl.security_demo.params.LoginUser;
import com.wl.security_demo.params.RegisterUser;
import com.wl.security_demo.service.UserService;
import com.wl.security_demo.utils.JwtUtils;
import com.wl.security_demo.utils.RedisCacheUtils;
import jakarta.annotation.Resource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TestController {

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private RedisCacheUtils redisCacheUtils;

    @Resource
    private UserService userService;

    @PostMapping("/register")
    public AjaxResult register(@RequestBody RegisterUser registerUser) {
        String username = registerUser.getUserName();
        String password = registerUser.getPassword();

        // 1. 校验用户是否已存在
        LambdaQueryWrapper<UserDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserDO::getUserName, username);

        if (userService.exists(wrapper)) {
            return AjaxResult.error( 400,  "用户名已存在");
        }

        // 2. 【核心】加密密码，绝对不能明文存储
        String encodePassword = passwordEncoder.encode(password);

        // 3. 存入数据库 (模拟)
        UserDO user = new UserDO();
        user.setUserName(username);
        user.setPassword(encodePassword);
        user.setNickName(registerUser.getNickName());
        user.setDeptId(registerUser.getDeptId());
        userService.save(user);

        return AjaxResult.success("注册成功", null);
    }

    @PostMapping("/login")
    public AjaxResult login(@RequestBody LoginUser loginParam) {
        // 1. 验证账号密码
        var authRequest = new UsernamePasswordAuthenticationToken(loginParam.getUsername(), loginParam.getPassword());
        Authentication auth = authenticationManager.authenticate(authRequest);

        // 2. 获取该用户的权限列表
        List<String> authorities = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        // 3. 生成 JWT
        String token = JwtUtils.createToken(auth.getName(), authorities);

        redisCacheUtils.setCacheObjectDefaultExpire(loginParam.getUsername(), token);
        return AjaxResult.success("登录成功", token);
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
