package com.wl.security_demo.cache;

import com.wl.security_demo.vo.SysUser;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataCache {

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


}
