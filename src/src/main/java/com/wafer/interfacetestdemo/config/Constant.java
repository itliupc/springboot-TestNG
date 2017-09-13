package com.wafer.interfacetestdemo.config;


public interface Constant {

  String CONTROLLER_PATH = "/api/v1/";
  
  String LOGIN = "login";
  
  String LOGOUT = "logout";
  
  String REGISTER = "register";
  
  String USERS = "users";
  
  String USER = "user";
  
  String USER_DELETE = "user/{userId}";
  
  String DEPTS = "depts";
  
  String DEPT = "dept";
  
  String DEPT_DELETE = "dept/{deptId}";
  
  String PROJECTS = "projects";
  
  String PROJECTS_DEPT = "projects/{deptId}";
  
  String PROJECT = "project";
  
  String PROJECT_DELETE = "project/{userId}/{projectId}";
  
  String DATA_SERVICE = "dss/{projectId}";
  
  String DATA_SERVICE_TESTCASE = "dss/{projectId}/{moduleName}/{interfaceName}";
  
  
  
  
  String APPLICATION_JSON = "json";
  
  /** 用户角色  admin */
  String USER_ROLE_ADMIN = "ADMIN";
  
  /** 用户角色  user */
  String USER_ROLE_USER = "USER";
  
  /** 运行 */
  int RUNNING = 0;
  
  /** 不运行 */
  int NOT_RUNNING = 1;
  
  
  
  String EMAIL_DUPLICATE = "100004";
  
  String USER_NAME_DUPLICATE = "100005";
  
  String USER_EXIST = "200001";
  
  String PROJECT_EXIST = "200002";
  
  String DEPT_NAME_EXIST = "300001";
  String DEPT_CODE_EXIST = "300002";
  
  /**
   * 下载的文件类型
   */
  String DOWNLOAD_FILE_TYPE_EXCEL = "excel";
  String DOWNLOAD_FILE_TYPE_JSON = "json";
  
  
}
