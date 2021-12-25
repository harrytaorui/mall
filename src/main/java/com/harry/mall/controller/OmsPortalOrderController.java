package com.harry.mall.controller;

import com.harry.mall.common.CommonResult;
import com.harry.mall.dto.OrderParam;
import com.harry.mall.service.OmsPortalOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "OmsPortalOrderController", description = "order management")
@RestController
@RequestMapping(value ="/orders")
@RequiredArgsConstructor
public class OmsPortalOrderController {

    private final OmsPortalOrderService portalOrderService;

    @Operation(description = "create order based on cart information")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommonResult generateOrder(@RequestBody OrderParam orderParam) {
        return portalOrderService.generateOrder(orderParam);
    }
}
