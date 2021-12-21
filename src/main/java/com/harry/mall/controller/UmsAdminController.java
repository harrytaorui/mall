package com.harry.mall.controller;

import com.harry.mall.common.CommonException;
import com.harry.mall.common.CommonResult;
import com.harry.mall.common.ResultCode;
import com.harry.mall.dto.UmsAdminLoginParam;
import com.harry.mall.mbg.model.UmsAdmin;
import com.harry.mall.mbg.model.UmsPermission;
import com.harry.mall.service.UmsAdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "UmsAdminController", description = "user management")
@RestController
@RequestMapping("/admin")
public class UmsAdminController {
    private final UmsAdminService umsAdminService;

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    public UmsAdminController(UmsAdminService umsAdminService) {
        this.umsAdminService = umsAdminService;
    }

    @Operation(description = "register user")
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public CommonResult<UmsAdmin> register(@RequestBody UmsAdmin umsAdminParams, BindingResult result) {
        UmsAdmin umsAdmin = umsAdminService.register(umsAdminParams);
        if (umsAdmin == null) {
            throw new CommonException(ResultCode.CREATE_FAILED.getCode(), "register user failed");
        }
        return CommonResult.success("user registered", umsAdmin);
    }

    @Operation(description = "login")
    @PostMapping("/login")
    public CommonResult login(@RequestBody UmsAdminLoginParam umsAdminLoginParam, BindingResult result) {
        String token = umsAdminService.login(umsAdminLoginParam.getUsername(), umsAdminLoginParam.getPassword());
        if (token == null) {
            throw new CommonException(ResultCode.FAILED.getCode(), "login failed");
        }
        Map<String,String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return CommonResult.success(tokenMap);
    }

    @Operation(description = "get all user author")
    @GetMapping("/permission/{adminId}")
    public CommonResult<List<UmsPermission>> getPermissionList(@PathVariable Long adminId) {
        List<UmsPermission> permissionList = umsAdminService.getPermissionList(adminId);
        return CommonResult.success(permissionList);
    }

}
