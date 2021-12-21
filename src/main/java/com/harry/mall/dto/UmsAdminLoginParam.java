package com.harry.mall.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UmsAdminLoginParam {
    @Schema(name = "username", required = true)
    @NotEmpty(message = "username can not be empty")
    private String username;

    @Schema(name = "password", required = true)
    @NotEmpty(message = "password can not be empty")
    private String password;

}
