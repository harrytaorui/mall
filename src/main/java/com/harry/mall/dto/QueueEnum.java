package com.harry.mall.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum QueueEnum {

    QUEUE_ORDER_CANCEL("mall.order.direct", "mall.order.cancel", "mall.order.cancel"),
    QUEUE_TTL_ORDER_CANCEL("mall.order.direct.ttl","mall.order.direct.ttl","mall.order.cancel");

    private String exchange;
    private String name;
    private String routeKey;
}
