CREATE DATABASE interfacetestdemo;
use interfacetestdemo;

CREATE TABLE `ps_user` (
  `user_id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_name` VARCHAR(255) NOT NULL COMMENT '用户名',
  `password` VARCHAR(255) NOT NULL COMMENT '密码',
  `email` VARCHAR(64) NOT NULL COMMENT '邮箱',
  `status` INT(2) NOT NULL COMMENT '用户的状态，0为可用，1为不可用',
  `user_authority` INT(2) NOT NULL COMMENT '用户权限',
  `latest_login_time` DATETIME DEFAULT NULL COMMENT '最近一次登录时间',
  `create_time` DATETIME NOT NULL COMMENT '创建时间',
  `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`user_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE TABLE `ps_dept` (
  `dept_id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `dept_name` VARCHAR(255) NOT NULL COMMENT '部门名称',
  `dept_code` VARCHAR(255) NOT NULL COMMENT '部门编码',
  `dept_type` INT(11) NOT NULL DEFAULT 0 COMMENT '部门类型',
  `create_time` DATETIME NOT NULL COMMENT '创建时间',
  `update_time` TIMESTAMP  NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`dept_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE TABLE `ps_dept_user` (
  `dept_user_id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `dept_id` INT(11) NOT NULL COMMENT '部门id',
  `user_id` INT(11) NOT NULL COMMENT '用户id',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`dept_user_id`),
  KEY `fk_du_did` (`dept_id`),
  KEY `fk_du_uid` (`user_id`),
  CONSTRAINT `fk_du_pid` FOREIGN KEY (`dept_id`) REFERENCES `ps_dept` (`dept_id`) ON DELETE CASCADE,
  CONSTRAINT `fk_du_uid` FOREIGN KEY (`user_id`) REFERENCES `ps_user` (`user_id`) ON DELETE CASCADE
) ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE TABLE `ps_project` (
  `project_id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `project_name` VARCHAR(255) NOT NULL COMMENT '项目名',
  `create_user_id` INT(11) NOT NULL COMMENT '创建人id',
  `dept_id` INT(11) NOT NULL COMMENT '部门id',
  `create_time` DATETIME NOT NULL COMMENT '创建时间',
  `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`project_id`),
  KEY `fk_pd_did` (`dept_id`),
  KEY `fk_pu_uid` (`create_user_id`),
  CONSTRAINT `fk_pd_did` FOREIGN KEY (`dept_id`) REFERENCES `ps_dept` (`dept_id`) ON DELETE CASCADE,
  CONSTRAINT `fk_pu_uid` FOREIGN KEY (`create_user_id`) REFERENCES `ps_user` (`user_id`) ON DELETE CASCADE
) ENGINE=INNODB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `ps_module`;
CREATE TABLE `ps_module` (
  `module_id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `module_name` VARCHAR(255) NOT NULL COMMENT '模块名',
  `project_id` INT(11) NOT NULL COMMENT '项目id',
  `is_run` INT(2) NOT NULL DEFAULT 0 COMMENT '是否执行，0 表示执行，1表示不执行',
  `create_time` DATETIME NOT NULL COMMENT '创建时间',
  `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`module_id`),
  KEY `fk_md_pid` (`project_id`),
  CONSTRAINT `fk_md_pid` FOREIGN KEY (`project_id`) REFERENCES `ps_project` (`project_id`) ON DELETE CASCADE
) ENGINE=INNODB DEFAULT CHARSET=utf8;

-- 接口
DROP TABLE IF EXISTS `ps_interface`;
CREATE TABLE `ps_interface` (
  `interface_id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `module_id` INT(11) NOT NULL COMMENT '模块id',
  `interface_name` VARCHAR(255) NOT NULL COMMENT '接口名',
  `interface_url` VARCHAR(255) NOT NULL COMMENT '接口url',
  `interface_type` VARCHAR(64) NOT NULL COMMENT '接口类型',
  `request_param` TEXT COMMENT '接口参数',
  `response_result` TEXT COMMENT '接口返回结果',
  `is_run` INT(2) NOT NULL COMMENT '是否执行该用例，0表示执行，1表示不执行',  
  `create_time` DATETIME NOT NULL COMMENT '创建时间',
  `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`interface_id`),
  KEY `fk_interface_mid` (`module_id`),
  CONSTRAINT `fk_interface_mid` FOREIGN KEY (`module_id`) REFERENCES `ps_module` (`module_id`) ON DELETE CASCADE
) ENGINE=INNODB DEFAULT CHARSET=utf8;

-- 接口用例
DROP TABLE IF EXISTS `ps_interface_testcase`;
CREATE TABLE `ps_interface_testcase` (
  `interface_testcase_id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `interface_id` INT(11) NOT NULL COMMENT '接口id',
  `test_case_name` varchar(255) COMMENT '测试用例名称',
  `param_case` TEXT COMMENT '接口入参',
  `expect_result` TEXT COMMENT '期望结果',
  `expect_status` INT(11) NOT NULL COMMENT '期望状态',
  `is_run` INT(2) NOT NULL COMMENT '是否执行该用例，0表示执行，1表示不执行',  
  `create_time` DATETIME NOT NULL COMMENT '创建时间',
  `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`interface_testcase_id`),
  KEY `fk_interface_id` (`interface_id`),
  CONSTRAINT `fk_interface_id` FOREIGN KEY (`interface_id`) REFERENCES `ps_interface` (`interface_id`) ON DELETE CASCADE
) ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE TABLE `ps_request_param`(  
  `request_param_id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `request_param_name` VARCHAR(125) NOT NULL COMMENT '参数名',
  `request_param_type` VARCHAR(64) NOT NULL COMMENT '参数类型',
  `request_param_description` VARCHAR(256) NOT NULL COMMENT '请求描述',
  `interface_id` INT(11) NOT NULL COMMENT '接口Id',
  `create_time` DATETIME NOT NULL COMMENT '创建时间',
  `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`request_param_id`),
  CONSTRAINT `fk_requestparam_iid` FOREIGN KEY (`interface_id`) REFERENCES `interfacetestdemo`.`ps_interface`(`interface_id`) ON DELETE CASCADE
) ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE TABLE `ps_response_param`(  
  `response_param_id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `response_param_name` VARCHAR(125) NOT NULL COMMENT '响应参数名',
  `response_param_type` VARCHAR(64) NOT NULL COMMENT '响应类型',
  `response_param_description` VARCHAR(256) NOT NULL COMMENT '响应描述',
  `interface_id` INT(11) NOT NULL COMMENT '接口Id',
  `create_time` DATETIME NOT NULL COMMENT '创建时间',
  `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`response_param_id`),
  CONSTRAINT `fk_responseparam_iid` FOREIGN KEY (`interface_id`) REFERENCES `interfacetestdemo`.`ps_interface`(`interface_id`) ON DELETE CASCADE
) ENGINE=INNODB DEFAULT CHARSET=utf8;

-- admin/admin
INSERT INTO `ps_user` (`user_id`, `user_name`, `password`, `email`, `status`, `user_authority`, `latest_login_time`, `create_time`, `update_time`) VALUES('1','admin','$2a$10$P7pwwuhkAHPH/omhVd46GuYATgaX7CExUrf0HRrRaf42CU.F22WGq','admin','0','0','2017-08-23 09:04:00','2017-08-18 09:32:24','2017-08-22 16:55:08');
INSERT INTO `ps_dept` (`dept_id`, `dept_name`, `dept_code`, `dept_type`, `create_time`, `update_time`) VALUES('1','全部项目','QBXM',1,'2017-08-22 14:28:04','2017-08-22 14:28:04');
INSERT INTO `ps_dept_user` (`dept_user_id`, `dept_id`, `user_id`, `create_time`, `update_time`) VALUES('1','1','1','2017-08-22 15:44:21','2017-08-22 15:44:25');