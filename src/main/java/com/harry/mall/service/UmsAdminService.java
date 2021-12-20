package com.harry.mall.service;

import com.harry.mall.mbg.model.UmsAdmin;
import com.harry.mall.mbg.model.UmsPermission;

import java.util.List;

public interface UmsAdminService {
  UmsAdmin getAdminByUserName(String username);

  UmsAdmin register(UmsAdmin umsAdmin);

  String login(String username, String password);

  List<UmsPermission> getPermissionList(Long adminId);
}
