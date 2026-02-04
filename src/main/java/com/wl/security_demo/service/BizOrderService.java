package com.wl.security_demo.service;

import com.wl.security_demo.common.PageResult;
import com.wl.security_demo.domain.entity.BizOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wl.security_demo.params.OrderParam;
import com.wl.security_demo.vo.BizOrderVO;
/**
 * <p>
 * 订单业务表 服务类
 * </p>
 *
 * @author wangl
 * @since 2026-02-04
 */
public interface BizOrderService extends IService<BizOrder> {

    PageResult<BizOrderVO> queryOrder(OrderParam orderParam);

}
