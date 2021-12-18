package com.harry.mall.service.impl;

import com.harry.mall.common.CommonException;
import com.harry.mall.common.CommonResult;
import com.harry.mall.common.ResultCode;
import com.harry.mall.service.RedisService;
import com.harry.mall.service.UmsMemberService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;


@Service
public class UmsMemberServiceImpl implements UmsMemberService {

    private RedisService redisService;

    @Value("${redis.key.prefix.authCode}")
    private String REDIS_KEY_PREFIX_AUTH_CODE;

    @Value("${redis.key.expire.authCode}")
    private Long AUTH_CODE_EXPIRE_SECONDS;

    public UmsMemberServiceImpl(RedisService redisService) {
        this.redisService = redisService;
    }

    @Override
    public CommonResult generateAuthCode(String telephone) {
        Integer randomNumber = (int) ((Math.random() * 9 + 1) * 100000);
        redisService.set(REDIS_KEY_PREFIX_AUTH_CODE + telephone, randomNumber.toString());
        redisService.expire(REDIS_KEY_PREFIX_AUTH_CODE + telephone, AUTH_CODE_EXPIRE_SECONDS);
        return CommonResult.success("verification code generated", randomNumber.toString());
    }

    @Override
    public CommonResult verifyAuthCode(String telephone, String authCode) {
        if (!StringUtils.hasLength(authCode)) {
            throw new CommonException(ResultCode.VALIDATE_FAILED.getCode(), "Verification code is empty");
        }
        String storedAuthCode = redisService.get(REDIS_KEY_PREFIX_AUTH_CODE + telephone);
        if (storedAuthCode.equals(authCode)) {
            return CommonResult.success("verification succeeded", null);
        } else {
            throw new CommonException(ResultCode.VALIDATE_FAILED.getCode(), "verification code is not correct");
        }
    }
}
