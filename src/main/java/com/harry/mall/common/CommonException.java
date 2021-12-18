package com.harry.mall.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class CommonException extends RuntimeException {

    private String code;
    private String message;
}
