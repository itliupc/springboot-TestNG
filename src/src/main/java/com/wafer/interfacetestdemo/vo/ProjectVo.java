package com.wafer.interfacetestdemo.vo;

public class ProjectVo {

  private long projectId;
  
  private String projectName;
  
  private long createUserId;
  
  private String createUserName;
  
  private long deptId;
  
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

  public String getCreateUserName() {
    return createUserName;
  }

  public void setCreateUserName(String createUserName) {
    this.createUserName = createUserName;
  }
  
  public long getDeptId() {
    return deptId;
  }

  public void setDeptId(long deptId) {
    this.deptId = deptId;
  }

  public ProjectVo() {
    super();
  }

  public ProjectVo(long projectId, String projectName, long createUserId, String createUserName,
      long deptId) {
    this.projectId = projectId;
    this.projectName = projectName;
    this.createUserId = createUserId;
    this.createUserName = createUserName;
    this.deptId = deptId;
  }
 
}
