package com.harry.mall.component;

import com.harry.mall.common.CommonLog;
import com.harry.mall.dto.QueueEnum;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CancelOrderSender {
  private static final Logger logger = LogManager.getLogger(CancelOrderSender.class);

  private final AmqpTemplate AmqpTemplate;

  public void sendMessage(Long orderId, final Long delayTimes) {
    AmqpTemplate.convertAndSend(
            QueueEnum.QUEUE_TTL_ORDER_CANCEL.getExchange(),
            QueueEnum.QUEUE_TTL_ORDER_CANCEL.getRouteKey(),
            orderId, message -> {
                message.getMessageProperties().setExpiration(String.valueOf(delayTimes));
                return message;
            });
    logger.info(CommonLog.builder().event("send delay message with orderId").data(orderId).build());


  }
}
