package com.harry.mall.component;

import com.harry.mall.common.CommonLog;
import com.harry.mall.service.OmsPortalOrderService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "mall.order.cancel")
@RequiredArgsConstructor
public class CancelOrderReceiver {
    private static final Logger logger = LogManager.getLogger(CancelOrderReceiver.class);

    private final OmsPortalOrderService portalOrderService;

    @RabbitHandler
    public void handle(Long orderId) {
        logger.info(CommonLog.builder().event("received delay message with orderId").data(orderId));
        portalOrderService.cancelOrder(orderId);
    }
}
