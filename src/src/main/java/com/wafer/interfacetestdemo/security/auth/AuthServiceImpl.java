package com.wafer.interfacetestdemo.security.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.wafer.interfacetestdemo.domain.User;
import com.wafer.interfacetestdemo.security.utils.JwtTokenUtil;
import com.wafer.interfacetestdemo.service.UserService;

@Service
public class AuthServiceImpl implements AuthService<User> {

  private AuthenticationManager authenticationManager;
  private UserDetailsService userDetailsService;
  private JwtTokenUtil jwtTokenUtil;
  private UserService userService;
  
  @Autowired
  public AuthServiceImpl(
          AuthenticationManager authenticationManager,
          UserDetailsService userDetailsService,
          JwtTokenUtil jwtTokenUtil, 
          UserService userService) {
      this.authenticationManager = authenticationManager;
      this.userDetailsService = userDetailsService;
      this.jwtTokenUtil = jwtTokenUtil;
      this.userService = userService;
  }
  
  /**
   * 注册用户
   */
  @Override
  public User register(User user) {
    // 查询该username是否已经被注册过了
    User us = userService.findByUserName(user.getUserName());
    if(null != us){
      return null;
    }
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    final String rawPassword = user.getPassword();
    user.setPassword(encoder.encode(rawPassword));
    user.setUserAuthority(1);
    userService.userSave(user);
    return user;
  }

  /**
   * 用户登陆
   */
  @Override
  public String login(String username, String password) {
    
    UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);
    final Authentication authentication = authenticationManager.authenticate(upToken);
    
    SecurityContextHolder.getContext().setAuthentication(authentication);
    
    final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
    // 获取token
    String token  = jwtTokenUtil.generateToken(userDetails);
    
    return token;
  }

  @Override
  public String refresh(String oldToken) {
    
    return jwtTokenUtil.refreshToken(oldToken);
  }
  
  @Override
  public boolean logout(String username) {
    
    /*UserDetails userDetails = userDetailsService.loadUserByUsername(username);
    
    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
        userDetails, null, userDetails.getAuthorities());*/

    SecurityContextHolder.clearContext();
    
    return true;
  }
}
