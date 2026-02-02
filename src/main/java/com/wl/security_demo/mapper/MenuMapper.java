package com.wl.security_demo.mapper;

import com.wl.security_demo.domain.entity.MenuDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 菜单权限表 Mapper 接口
 * </p>
 *
 * @author wangl
 * @since 2026-02-02
 */
@Mapper
public interface MenuMapper extends BaseMapper<MenuDO> {

}
