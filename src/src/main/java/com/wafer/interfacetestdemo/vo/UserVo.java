package com.wafer.interfacetestdemo.vo;

import java.util.Date;

public class UserVo {

  private long userId;

  private String userName;
  
  private String password;

  private String email;

  private Date latestLoginTime;

  private long deptId;

  private String deptName;

  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Date getLatestLoginTime() {
    return latestLoginTime;
  }

  public void setLatestLoginTime(Date latestLoginTime) {
    this.latestLoginTime = latestLoginTime;
  }

  public long getDeptId() {
    return deptId;
  }

  public void setDeptId(long deptId) {
    this.deptId = deptId;
  }

  public String getDeptName() {
    return deptName;
  }

  public void setDeptName(String deptName) {
    this.deptName = deptName;
  }

  public UserVo() {
    super();
  }

  public UserVo(long userId, String userName, String email, Date latestLoginTime, long deptId,
      String deptName) {
    super();
    this.userId = userId;
    this.userName = userName;
    this.email = email;
    this.latestLoginTime = latestLoginTime;
    this.deptId = deptId;
    this.deptName = deptName;
  }

  public UserVo(long userId, String userName, String password, String email, Date latestLoginTime,
      long deptId, String deptName) {
    super();
    this.userId = userId;
    this.userName = userName;
    this.password = password;
    this.email = email;
    this.latestLoginTime = latestLoginTime;
    this.deptId = deptId;
    this.deptName = deptName;
  }
}
