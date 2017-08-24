package com.wafer.interfacetestdemo.security.auth;

/**
 * 
 * @author wf
 * @see
 * @param <T>
 */
public interface AuthService<T> {

  /**
   * 注册用户
   * @param t 用户基本信息
   * @return 返回新用户全部信息
   */
  T register(T t);
  
  /**
   * 用户登录
   * @param username 用户名
   * @param password 密码
   * @return 登陆成功产生的Token
   */
  String login(String username, String password);
  
  /**
   * 刷新用户Token
   * @param oldToken 久Token
   * @return 新的Token
   */
  String refresh(String oldToken);
  
  
  /**
   * 用户登出
   * @return
   */
  boolean logout(String username);
}
