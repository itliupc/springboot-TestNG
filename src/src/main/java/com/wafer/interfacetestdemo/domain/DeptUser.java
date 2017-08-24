package com.wafer.interfacetestdemo.domain;

import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name  =  "ps_dept_user")
public class DeptUser { 
  @Id
  @GeneratedValue(generator = "generator")
  @GenericGenerator(name = "generator", strategy = "native")
  @Column(name = "dept_user_id", unique = true, nullable = false)
  private long deptUserId;
  
  @Column(name = "dept_id")
  private long deptId;
  
  @Column(name = "user_id")
  private long userId;
 
  @Column(name = "create_time")
  private Date createTime;
  
  @Column(name = "update_time")
  private Date updateTime;

  public long getDeptUserId() {
    return deptUserId;
  }

  public void setDeptUserId(long deptUserId) {
    this.deptUserId = deptUserId;
  }

  public long getDeptId() {
    return deptId;
  }

  public void setDeptId(long deptId) {
    this.deptId = deptId;
  }

  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
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

  public DeptUser() {
    super();
  }

  public DeptUser(long deptUserId, long deptId, long userId, Date createTime, Date updateTime) {
    super();
    this.deptUserId = deptUserId;
    this.deptId = deptId;
    this.userId = userId;
    this.createTime = createTime;
    this.updateTime = updateTime;
  }
}
