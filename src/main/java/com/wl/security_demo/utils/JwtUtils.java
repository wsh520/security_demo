package com.wl.security_demo.utils;

import io.jsonwebtoken.*;
import java.util.Date;
import java.util.List;

public class JwtUtils {
    private static final String SECRET = "MyMagicKey"; // 签名密钥
    private static final long EXPIRATION = 3600 * 1000; // 过期时间 1小时

    // 生成 Token
    public static String createToken(String username, List<String> authorities) {
        return Jwts.builder()
                .setSubject(username)
                .claim("authorities", authorities) // 将权限存入载荷
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    // 解析 Token
    public static Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();
    }

    public static Boolean tokenExpired(Claims claims) {
        Date expiration = claims.getExpiration();
        return expiration.before(new Date());
    }
}
