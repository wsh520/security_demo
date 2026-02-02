package com.wl.security_demo.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {

    @Value("${spring.data.redis.host}")
    private String host;

    @Value("${spring.data.redis.port}")
    private String port;

    @Value("${spring.data.redis.password}")
    private String password;

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        // 单机模式
        // 注意：Redisson 的地址必须带 redis:// 前缀
        String address = "redis://" + host + ":" + port;
        config.useSingleServer()
                .setAddress(address)
                .setPassword(password != null && !password.isEmpty() ? password : null)
                .setDatabase(0);

        // 核心：设置编码为 JSON，这样存进去的数据在 RedisDesktopManager 里能看懂
        config.setCodec(new JsonJacksonCodec());

        return Redisson.create(config);
    }
}