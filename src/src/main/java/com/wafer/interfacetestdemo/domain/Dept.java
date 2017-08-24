package com.wafer.interfacetestdemo.domain;

import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name  =  "ps_dept")
public class Dept { 
  @Id
  @GeneratedValue(generator = "generator")
  @GenericGenerator(name = "generator", strategy = "native")
  @Column(name = "dept_id", unique = true, nullable = false)
  private long deptId;
  
  @Column(name = "dept_name")
  private String deptName;
  
  @Column(name = "dept_code")
  private String deptCode;
  
  @Column(name = "dept_type")
  private int deptType;
 
  @Column(name = "create_time")
  private Date createTime;
  
  @Column(name = "update_time")
  private Date updateTime;
  
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

  public String getDeptCode() {
    return deptCode;
  }

  public void setDeptCode(String deptCode) {
    this.deptCode = deptCode;
  }
  
  public int getDeptType() {
    return deptType;
  }

  public void setDeptType(int deptType) {
    this.deptType = deptType;
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

  public Dept(){
    super();
  }

  public Dept(long deptId, String deptName, String deptCode, Date createTime, Date updateTime) {
    super();
    this.deptId = deptId;
    this.deptName = deptName;
    this.deptCode = deptCode;
    this.createTime = createTime;
    this.updateTime = updateTime;
  }
}
