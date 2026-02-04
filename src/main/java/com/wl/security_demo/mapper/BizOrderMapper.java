package com.wl.security_demo.mapper;

import com.wl.security_demo.domain.entity.BizOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 订单业务表 Mapper 接口
 * </p>
 *
 * @author wangl
 * @since 2026-02-04
 */
@Mapper
public interface BizOrderMapper extends BaseMapper<BizOrder> {

}
