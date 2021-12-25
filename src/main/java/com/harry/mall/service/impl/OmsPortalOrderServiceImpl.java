package com.harry.mall.service.impl;

import com.harry.mall.common.CommonLog;
import com.harry.mall.common.CommonResult;
import com.harry.mall.component.CancelOrderSender;
import com.harry.mall.dto.OrderParam;
import com.harry.mall.service.OmsPortalOrderService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OmsPortalOrderServiceImpl implements OmsPortalOrderService {

    private static final Logger logger = LogManager.getLogger(OmsPortalOrderServiceImpl.class);

    private final CancelOrderSender cancelOrderSender;

    @Override
    public CommonResult generateOrder(OrderParam orderParam) {
        logger.info(CommonLog.builder().event("processing generateOrder").build());

        sendDelayMessageCancelOrder(11L);
        return CommonResult.success(null, "order created");
    }

    private void sendDelayMessageCancelOrder(long orderId) {
        long delayTimes = 30 * 1000;

        cancelOrderSender.sendMessage(orderId, delayTimes);
    }

    @Override
    public void cancelOrder(Long orderId) {
        logger.info(CommonLog.builder().event("canceling order").data(orderId).build());
    }
}
