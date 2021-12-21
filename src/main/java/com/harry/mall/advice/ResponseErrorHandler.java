package com.harry.mall.advice;

import com.harry.mall.common.CommonException;
import com.harry.mall.common.CommonResult;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ResponseErrorHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler({CommonException.class})
    public ResponseEntity<Object> handleCommonException(CommonException ex) {
        return new ResponseEntity<>(CommonResult.failed(ex.getCode(), ex.getMessage()), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleAll(Exception ex) {
        return new ResponseEntity<>(CommonResult.failed(ex.getMessage()), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}
