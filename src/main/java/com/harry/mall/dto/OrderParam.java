package com.harry.mall.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderParam {

    private Long memberReceiveAddressId;

    private Long couponId;

    private Integer useIntegration;

    private Integer payType;
}
