package com.harry.mall.controller;

import com.harry.mall.common.CommonResult;
import com.harry.mall.service.UmsMemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "UmsMemberController", description = "user login/signup management")
@RestController
@RequestMapping("/users")
public class UmsMemberController {

    private final UmsMemberService umsMemberService;

    public UmsMemberController(UmsMemberService umsMemberService) {
        this.umsMemberService = umsMemberService;
    }

    @Operation(description = "get verification code")
    @GetMapping("/verifications")
    public CommonResult getAuthCode(@RequestParam(value = "phone") String telephone) {
        return umsMemberService.generateAuthCode(telephone);
    }

    @Operation(description = "verify verification code")
    @PostMapping("/verifications")
    public CommonResult verifyAuthCode(@RequestParam(value = "phone") String telephone, @RequestParam(value = "code") String authCode) {
        return umsMemberService.verifyAuthCode(telephone, authCode);
    }
}
