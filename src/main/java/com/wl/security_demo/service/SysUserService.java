package com.wl.security_demo.service;

import com.wl.security_demo.domain.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wangl
 * @since 2026-02-04
 */
public interface SysUserService extends IService<SysUser> {

    List<String> queryUserRoleAndPremiss(Integer userId);

}
