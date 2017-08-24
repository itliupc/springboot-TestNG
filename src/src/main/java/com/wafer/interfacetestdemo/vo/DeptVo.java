package com.wafer.interfacetestdemo.vo;

import java.util.Date;

public class DeptVo {

  private long deptId;
  
  private String deptName;
  
  private String deptCode;
  
  private Date createTime;
  
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
  
  public Date getCreateTime() {
    return createTime;
  }
  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }
  
  public DeptVo() {
    super();
  }
  
  public DeptVo(long deptId, String deptName, String deptCode, Date createTime) {
    super();
    this.deptId = deptId;
    this.deptName = deptName;
    this.deptCode = deptCode;
    this.createTime = createTime;
  }
}
