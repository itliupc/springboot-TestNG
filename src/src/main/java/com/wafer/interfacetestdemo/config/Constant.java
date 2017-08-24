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
  
  
  /** 用户角色  admin */
  String USER_ROLE_ADMIN = "ADMIN";
  
  /** 用户角色  user */
  String USER_ROLE_USER = "USER";
  
  String EMAIL_DUPLICATE = "100004";
  
  String USER_EXIST = "200001";
  
  String PROJECT_EXIST = "200002";
  
  String APPLICATION_JSON = "json";
  
}
