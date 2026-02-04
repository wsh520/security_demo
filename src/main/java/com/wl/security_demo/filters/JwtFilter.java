package com.wl.security_demo.filters;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.wl.security_demo.service.impl.UserDetailsServiceImpl;
import com.wl.security_demo.utils.JwtUtils;
import com.wl.security_demo.utils.RedisCacheUtils;
import com.wl.security_demo.vo.LoginUser;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Resource
    private RedisCacheUtils redisCacheUtils;

    @Resource
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain chain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        // 1. 判断 Header 是否有 Token (格式: Bearer xxx)
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            try {
                // 2. 解析 Token
                var claims = JwtUtils.parseToken(token);
                String username = claims.getSubject();

                String effectToken = redisCacheUtils.getCacheObject(username);
                if (!token.equals(effectToken) || JwtUtils.tokenExpired(claims)) {
                    throw new RuntimeException("Token 已被注销");
                }
                List<String> auths = (List<String>) claims.get("authorities");

                if (username != null) {
                    // 3. 将权限字符串转为 Security 识别的对象
                    var authorities = auths.stream()
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());
                    LoginUser loginUser = JSONObject.parseObject(redisCacheUtils.getCacheObject("login:" + username), LoginUser.class);
                    // 4. 构建并存入上下文
                    var authentication = new UsernamePasswordAuthenticationToken(loginUser, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (Exception e) {
                // Token 过期或非法，直接清除上下文
                SecurityContextHolder.clearContext();
            }
        }
        chain.doFilter(request, response);
    }
}
