package com.harry.mall.service;

import com.harry.mall.common.CommonResult;

public interface UmsMemberService {

  CommonResult generateAuthCode(String telephone);

  CommonResult verifyAuthCode(String telephone, String authCode);
}
