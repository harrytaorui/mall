package com.harry.mall.service;

import com.harry.mall.common.CommonResult;
import com.harry.mall.dto.OrderParam;
import org.springframework.transaction.annotation.Transactional;

public interface OmsPortalOrderService {

  @Transactional
  CommonResult generateOrder(OrderParam orderParam);

  @Transactional
  void cancelOrder(Long orderId);
}
