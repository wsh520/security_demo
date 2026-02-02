package com.wl.security_demo.service.impl;

import com.wl.security_demo.domain.entity.Menu;
import com.wl.security_demo.mapper.MenuMapper;
import com.wl.security_demo.service.MenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 菜单权限表 服务实现类
 * </p>
 *
 * @author wangl
 * @since 2026-02-02
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

}
