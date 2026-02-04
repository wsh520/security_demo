package com.wl.security_demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wl.security_demo.common.PageResult;
import com.wl.security_demo.converts.BizOrderConvert;
import com.wl.security_demo.domain.entity.BizOrder;
import com.wl.security_demo.mapper.BizOrderMapper;
import com.wl.security_demo.params.OrderParam;
import com.wl.security_demo.service.BizOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wl.security_demo.vo.BizOrderVO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.util.List;

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

    @Resource
    private  BizOrderMapper bizOrderMapper;


    /**
     * 优化后的订单查询方法
     */
    public PageResult<BizOrderVO> queryOrder(OrderParam orderParam) {
        // 1. 构建分页参数（MyBatis-Plus 的 Page 对象）
        Page<BizOrder> page = new Page<>(orderParam.getPageNum(), orderParam.getPageSize());

        // 2. 使用 LambdaQueryChainWrapper 简化链式调用
        // .condition() 可以在参数不为空时才拼接 SQL，去掉繁琐的 if
        IPage<BizOrder> bizOrderPage = bizOrderMapper.selectPage(page,
                new LambdaQueryWrapper<BizOrder>()
                        .eq(StringUtils.hasText(orderParam.getOrderNo()), BizOrder::getOrderNo, orderParam.getOrderNo())
        );

        // 3. 利用 MapStruct 批量转换 DO -> VO
        List<BizOrderVO> voRecords = BizOrderConvert.BIZ_ORDER_INSTANCE.toVOList(bizOrderPage.getRecords());

        // 4. 返回自定义的统一分页响应对象
        return new PageResult<>(voRecords, bizOrderPage.getTotal());
    }
}
