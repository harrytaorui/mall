package com.harry.mall.service.impl;

import com.harry.mall.common.CommonLog;
import com.harry.mall.common.utils.JwtTokenUtil;
import com.harry.mall.dao.UmsAdminRoleRelationDao;
import com.harry.mall.mbg.mapper.UmsAdminMapper;
import com.harry.mall.mbg.model.UmsAdmin;
import com.harry.mall.mbg.model.UmsAdminExample;
import com.harry.mall.mbg.model.UmsPermission;
import com.harry.mall.service.UmsAdminService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UmsAdminServiceImpl implements UmsAdminService {

  private static final Logger logger = LogManager.getLogger(UmsAdminServiceImpl.class);

  private final JwtTokenUtil jwtTokenUtil;

  private final PasswordEncoder passwordEncoder;

  private final UmsAdminRoleRelationDao adminRoleRelationDao;

  private final UmsAdminMapper adminMapper;

  private final UserDetailsService userDetailsService;

  @Value("${jwt.tokenHead}")
  private String tokenHead;

  public UmsAdminServiceImpl(
      JwtTokenUtil jwtTokenUtil,
      PasswordEncoder passwordEncoder,
      UmsAdminRoleRelationDao adminRoleRelationDao,
      UmsAdminMapper adminMapper,
      UserDetailsService userDetailsService) {
    this.jwtTokenUtil = jwtTokenUtil;
    this.passwordEncoder = passwordEncoder;
    this.adminRoleRelationDao = adminRoleRelationDao;
    this.adminMapper = adminMapper;
    this.userDetailsService = userDetailsService;
  }

  @Override
  public UmsAdmin getAdminByUserName(String username) {
    UmsAdminExample umsAdminExample = new UmsAdminExample();
    umsAdminExample.createCriteria().andUsernameEqualTo(username);
    List<UmsAdmin> adminList = adminMapper.selectByExample(umsAdminExample);
    if (adminList != null && adminList.size() > 0) {
      return adminList.get(0);
    }
    return null;
  }

  @Override
  public UmsAdmin register(UmsAdmin umsAdminParams) {
    UmsAdmin newAdmin = new UmsAdmin();
    BeanUtils.copyProperties(newAdmin, umsAdminParams);
    newAdmin.setCreateTime(new Date());
    newAdmin.setStatus(1);
    UmsAdmin existedAdmin = getAdminByUserName(newAdmin.getUsername());
    if (existedAdmin != null) {
      logger.debug(
          CommonLog.builder().event("admin username existed").data(newAdmin.getUsername()));
      return null;
    }
    String encodedPassword = passwordEncoder.encode(newAdmin.getPassword());
    newAdmin.setPassword(encodedPassword);
    adminMapper.insert(newAdmin);
    return newAdmin;
  }

  @Override
  public String login(String username, String password) {
    String token = null;
    try {
      UserDetails userdetails = userDetailsService.loadUserByUsername(username);
      if (!passwordEncoder.matches(password, userdetails.getPassword())) {
        logger.debug(CommonLog.builder().event("admin password incorrect").data(password));
        throw new BadCredentialsException("password incorrect");
      }
      UsernamePasswordAuthenticationToken authenticationToken =
          new UsernamePasswordAuthenticationToken(userdetails, null, userdetails.getAuthorities());
      SecurityContextHolder.getContext().setAuthentication(authenticationToken);
      token = jwtTokenUtil.generateToken(userdetails);
    } catch (AuthenticationException ex) {
      logger.debug(CommonLog.builder().event("admin login failed"));
    }
    return token;
  }

  @Override
  public List<UmsPermission> getPermissionList(Long adminId) {
    return adminRoleRelationDao.getPermissionList(adminId);
  }
}
