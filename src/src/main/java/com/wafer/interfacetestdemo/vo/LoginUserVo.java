package com.wafer.interfacetestdemo.vo;

import com.wafer.interfacetestdemo.domain.Dept;
import com.wafer.interfacetestdemo.domain.User;

public class LoginUserVo {
  private String token;
  private User user;
  private Dept dept;
  
  public String getToken() {
    return token;
  }
  
  public void setToken(String token) {
    this.token = token;
  }
  
  public User getUser() {
    return user;
  }
  
  public void setUser(User user) {
    this.user = user;
  }
  
  public Dept getDept() {
    return dept;
  }

  public void setDept(Dept dept) {
    this.dept = dept;
  }

  public LoginUserVo() {
    super();
  } 
  
  public LoginUserVo(String token, User user, Dept dept) {
    super();
    this.token = token;
    this.user = user;
    this.dept = dept;
  }  
  
}
