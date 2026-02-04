package com.wl.security_demo.converts;

import com.wl.security_demo.domain.entity.BizOrder;
import com.wl.security_demo.vo.BizOrderVO;
import jdk.dynalink.linker.LinkerServices;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BizOrderConvert {

    BizOrderConvert BIZ_ORDER_INSTANCE = Mappers.getMapper(BizOrderConvert.class);

    BizOrderVO toVo(BizOrder bizOrder);

    List<BizOrderVO> toVOList(List<BizOrder> bizOrders);

}
