package com.wl.security_demo.service.impl;

import com.wl.security_demo.domain.entity.UserDO;
import com.wl.security_demo.mapper.UserMapper;
import com.wl.security_demo.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author wangl
 * @since 2026-02-02
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {

}
