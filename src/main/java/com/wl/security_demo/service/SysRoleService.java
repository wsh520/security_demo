package com.wl.security_demo.service;

import com.wl.security_demo.domain.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Set;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wangl
 * @since 2026-02-04
 */
public interface SysRoleService extends IService<SysRole> {

    Set<String> queryUserRoleKeys(Integer userId    );

}
