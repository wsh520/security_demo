package com.wl.security_demo.service.impl;

import com.wl.security_demo.domain.entity.RoleDO;
import com.wl.security_demo.mapper.RoleMapper;
import com.wl.security_demo.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色信息表 服务实现类
 * </p>
 *
 * @author wangl
 * @since 2026-02-02
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, RoleDO> implements RoleService {

}
