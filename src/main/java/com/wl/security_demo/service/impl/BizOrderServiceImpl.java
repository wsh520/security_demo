package com.wl.security_demo.service.impl;

import com.wl.security_demo.domain.entity.BizOrder;
import com.wl.security_demo.mapper.BizOrderMapper;
import com.wl.security_demo.service.BizOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单业务表 服务实现类
 * </p>
 *
 * @author wangl
 * @since 2026-02-04
 */
@Service
public class BizOrderServiceImpl extends ServiceImpl<BizOrderMapper, BizOrder> implements BizOrderService {

}
