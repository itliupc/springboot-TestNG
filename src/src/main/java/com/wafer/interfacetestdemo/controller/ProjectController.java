package com.wafer.interfacetestdemo.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wafer.interfacetestdemo.config.Constant;
import com.wafer.interfacetestdemo.domain.DeptUser;
import com.wafer.interfacetestdemo.domain.Project;
import com.wafer.interfacetestdemo.domain.User;
import com.wafer.interfacetestdemo.service.DeptUserService;
import com.wafer.interfacetestdemo.service.ProjectService;
import com.wafer.interfacetestdemo.service.UserService;
import com.wafer.interfacetestdemo.vo.ProjectVo;
import com.wafer.interfacetestdemo.vo.ResponseResult;

@RestController
@RequestMapping(Constant.CONTROLLER_PATH)
@Transactional
public class ProjectController {
  
  @Autowired
  UserService userService;
  
  @Autowired
  DeptUserService deptUserService;
  
  @Autowired
  ProjectService projectService;
  
  /**
   * 查询project信息
   * @return 封装的project list信息
   */
  @RequestMapping(value = Constant.PROJECTS, method = RequestMethod.GET)
  @Transactional(readOnly = true)
  public ResponseResult projectList(){
    
    List<ProjectVo> projectList = projectService.getProjectList();
    return ResponseResult.success(projectList);
  }
  
  /**
   * 查询当前部门下的project信息
   * @return 封装的project list信息
   */
  @RequestMapping(value = Constant.PROJECTS_DEPT, method = RequestMethod.GET)
  @Transactional(readOnly = true)
  public ResponseResult projectList(@PathVariable long deptId){
    
    List<ProjectVo> projectList = projectService.getProjectByDeptId(deptId);
    return ResponseResult.success(projectList);
  }
  
  /**
   * 新增项目
   * @param projectVo
   * @return
   */
  @RequestMapping(value = Constant.PROJECT, method = RequestMethod.POST)
  public ResponseResult projectCreate(@RequestBody ProjectVo projectVo){
    Project project = new Project();
    project.setProjectName(projectVo.getProjectName());
    project.setCreateUserId(projectVo.getCreateUserId());
    project.setDeptId(projectVo.getDeptId());
    project.setCreateTime(new Date());
    
    projectService.projectSave(project);
    
    User user = userService.getUserbyUserId(projectVo.getCreateUserId());
    List<ProjectVo> projectList = null;
    if(0 == user.getUserAuthority()){
      projectList = projectService.getProjectList();
    }else{
      projectList = projectService.getProjectByDeptId(projectVo.getDeptId());
    }
    return ResponseResult.success(projectList);
  }
  
  /**
   * 编辑项目
   * @param projectVo
   * @return
   */
  @RequestMapping(value = Constant.PROJECT, method = RequestMethod.PUT)
  public ResponseResult projectModify(@RequestBody ProjectVo projectVo){
    Project project = projectService.getProjectByProjectId(projectVo.getProjectId());
    
    if(null != project.getProjectName()){
      project.setProjectName(projectVo.getProjectName());
    }
    
    projectService.projectSave(project);
    
    User user = userService.getUserbyUserId(projectVo.getCreateUserId());
    List<ProjectVo> projectList = null;
    if(0 == user.getUserAuthority()){
      projectList = projectService.getProjectList();
    }else{
      projectList = projectService.getProjectByDeptId(projectVo.getDeptId());
    }
    return ResponseResult.success(projectList);
  }
  
  /**
   * 删除项目
   * @param userId
   * @param projectId
   * @return
   */
  @RequestMapping(value = Constant.PROJECT_DELETE, method = RequestMethod.DELETE)
  public ResponseResult projectDelete(@PathVariable long userId, @PathVariable long projectId){
    
    projectService.deleteProjectByProjectId(projectId);
    
    User user = userService.getUserbyUserId(userId);
    List<ProjectVo> projectList = null;
    if(0 == user.getUserAuthority()){
      projectList = projectService.getProjectList();
    }else{
      DeptUser deptUser = deptUserService.getDeptUserByUserId(userId);
      projectList = projectService.getProjectByDeptId(deptUser.getDeptId());
    }
    return ResponseResult.success(projectList);
  }
  
}
