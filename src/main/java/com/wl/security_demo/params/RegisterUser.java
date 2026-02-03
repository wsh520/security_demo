package com.wl.security_demo.params;

import lombok.Data;

import java.util.List;

@Data
public class RegisterUser {

    private String userName;

    private String nickName;

    private String password;

    private String email;

}
