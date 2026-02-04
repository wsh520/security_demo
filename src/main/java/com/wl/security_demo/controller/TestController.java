package com.wl.security_demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wl.security_demo.common.PageResult;
import com.wl.security_demo.common.Result;
import com.wl.security_demo.domain.entity.SysUser;
import com.wl.security_demo.exceptions.BusinessException;
import com.wl.security_demo.params.LoginUserParam;
import com.wl.security_demo.params.OrderParam;
import com.wl.security_demo.params.RegisterUser;
import com.wl.security_demo.service.BizOrderService;
import com.wl.security_demo.service.SysUserService;
import com.wl.security_demo.utils.JwtUtils;
import com.wl.security_demo.utils.RedisCacheUtils;
import com.wl.security_demo.vo.BizOrderVO;
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
    private SysUserService sysUserService;

    @Resource
    private BizOrderService bizOrderService;

    @PostMapping("/register")
    public Result<String> register(@RequestBody RegisterUser registerUser) {
        String username = registerUser.getUserName();
        String password = registerUser.getPassword();

        // 1. 校验用户是否已存在
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getUsername, username);

        if (sysUserService.exists(wrapper)) {
            return Result.error( 500,  "用户名已存在");
        }

        // 2. 【核心】加密密码，绝对不能明文存储
        String encodePassword = passwordEncoder.encode(password);

        // 3. 存入数据库 (模拟)
        SysUser user = new SysUser();
        user.setUsername(username);
        user.setPassword(encodePassword);
        user.setNickname(registerUser.getNickName());
        user.setEmail(registerUser.getEmail());
        sysUserService.save(user);

        return Result.success("注册成功");
    }

    @PostMapping("/login")
    public Result<String> login(@RequestBody LoginUserParam loginParam) {
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
        return Result.success(token);
    }

    /**
     * 2. 权限验证接口：查询权限
     * admin 和 zhangsan 都能访问
     */
    @GetMapping("/user/query")
    @PreAuthorize("hasAuthority('system:user:query') AND hasAnyRole('ADMIN','USER')")
    public Result<String> queryUser() {

        throw new BusinessException(500,"查询出错了！"); // 错误模拟，依靠全局异常处理器处理，正确抛出异常

    }

    /**
     * 3. 权限验证接口：删除权限
     * 只有 admin 能访问
     */
    @DeleteMapping("/user/delete")
    @PreAuthorize("hasRole('ADMIN')  and hasAuthority('system:user:delete')")
    public Result<String> deleteUser() {

        return Result.success("删除用户成功！");
    }

    @PostMapping("/query/order")
    public Result<PageResult<BizOrderVO>> queryOrder(@RequestBody OrderParam orderParam) {


        return Result.success( bizOrderService.queryOrder(orderParam));
    }
}
