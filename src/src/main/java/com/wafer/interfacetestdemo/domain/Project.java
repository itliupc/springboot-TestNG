package com.wafer.interfacetestdemo.domain;

import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name  =  "ps_project")
public class Project { 
  @Id
  @GeneratedValue(generator = "generator")
  @GenericGenerator(name = "generator", strategy = "native")
  @Column(name = "project_id", unique = true, nullable = false)
  private long projectId;
  
  @Column(name = "project_name")
  private String projectName;
  
  @Column(name = "create_user_id")
  private long createUserId;
  
  @Column(name = "dept_id")
  private long deptId;
  
  @Column(name = "create_time")
  private Date createTime;
  
  @Column(name = "update_time")
  private Date updateTime;

  public long getProjectId() {
    return projectId;
  }

  public void setProjectId(long projectId) {
    this.projectId = projectId;
  }

  public String getProjectName() {
    return projectName;
  }

  public void setProjectName(String projectName) {
    this.projectName = projectName;
  }

  public long getCreateUserId() {
    return createUserId;
  }

  public void setCreateUserId(long createUserId) {
    this.createUserId = createUserId;
  }

  public long getDeptId() {
    return deptId;
  }

  public void setDeptId(long deptId) {
    this.deptId = deptId;
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

  public Project() {
    super();
  }

  public Project(long projectId, String projectName, long createUserId, long deptId,
      Date createTime, Date updateTime) {
    this.projectId = projectId;
    this.projectName = projectName;
    this.createUserId = createUserId;
    this.deptId = deptId;
    this.createTime = createTime;
    this.updateTime = updateTime;
  }
}
