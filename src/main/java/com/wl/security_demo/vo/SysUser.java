package com.wl.security_demo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class SysUser {

    private Long id;

    private String username;

    private String password;

    private List<String> permissions; // 权限列表


}
