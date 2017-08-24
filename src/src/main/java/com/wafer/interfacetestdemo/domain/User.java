package com.wafer.interfacetestdemo.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;



/**
 * Created by codea on 2017/4/10.
 */

@Entity
@Table(name = "ps_user")
public class User {

  @Id
  @GeneratedValue(generator = "generator")
  @GenericGenerator(name = "generator", strategy = "native")
  @Column(name = "user_id", unique = true, nullable = false)
  private long userId;

  @Column(name = "user_name")
  private String userName;

  @Column(name = "password")
  private String password;

  @Column(name = "email")
  private String email;

  @Column(name = "status")
  private int status;  
  
  @Column(name = "user_authority")
  private int userAuthority;

  @Column(name = "latest_login_time")
  private Date latestLoginTime;

  @Column(name = "create_time")
  private Date createTime;

  @Column(name = "update_time")
  private Date updateTime;


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

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public Date getLatestLoginTime() {
    return latestLoginTime;
  }

  public void setLatestLoginTime(Date latestLoginTime) {
    this.latestLoginTime = latestLoginTime;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public Date getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }
  
  public int getUserAuthority() {
	return userAuthority;
  }

  public void setUserAuthority(int userAuthority) {
	this.userAuthority = userAuthority;
  }

  public User(){
    super();
  }

  public User(long userId, String userName, String password, String email, int status, int userAuthority,
		Date latestLoginTime, Date createTime, Date updateTime) {
	super();
	this.userId = userId;
	this.userName = userName;
	this.password = password;
	this.email = email;
	this.status = status;
	this.userAuthority = userAuthority;
	this.latestLoginTime = latestLoginTime;
	this.createTime = createTime;
	this.updateTime = updateTime;
	}
  
}
