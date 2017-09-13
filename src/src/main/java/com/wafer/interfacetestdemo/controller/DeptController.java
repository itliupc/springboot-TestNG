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
import com.wafer.interfacetestdemo.domain.Dept;
import com.wafer.interfacetestdemo.domain.DeptUser;
import com.wafer.interfacetestdemo.service.DeptService;
import com.wafer.interfacetestdemo.service.DeptUserService;
import com.wafer.interfacetestdemo.service.ProjectService;
import com.wafer.interfacetestdemo.vo.DeptVo;
import com.wafer.interfacetestdemo.vo.ProjectVo;
import com.wafer.interfacetestdemo.vo.ResponseResult;

@RestController
@RequestMapping(Constant.CONTROLLER_PATH)
@Transactional
public class DeptController {
  @Autowired
  DeptService deptService;

  @Autowired
  DeptUserService deptUserService;

  @Autowired
  ProjectService projectService;

  /**
   * 查询dept信息
   * 
   * @return 封装的dept list信息
   */
  @RequestMapping(value = Constant.DEPTS, method = RequestMethod.GET)
  @Transactional(readOnly = true)
  public ResponseResult deptList() {

    List<DeptVo> deptList = deptService.getDeptVoList();
    return ResponseResult.success(deptList);
  }

  /**
   * 新建dept
   * 
   * @param dept基本信息
   * @return 封装的dept list信息
   */
  @RequestMapping(value = Constant.DEPT, method = RequestMethod.POST)
  public ResponseResult deptCreate(@RequestBody Dept dept) {
    List<Dept> nameDepts = deptService.getDeptByDeptName(dept.getDeptName());
    List<Dept> codeDepts = deptService.getDeptByDeptCode(dept.getDeptCode());
    if (nameDepts.size() > 0) {
      return ResponseResult.failure(Constant.DEPT_NAME_EXIST);
    } else if (codeDepts.size() > 0) {
      return ResponseResult.failure(Constant.DEPT_CODE_EXIST);
    } else {
      dept.setDeptType(0);
      dept.setCreateTime(new Date());

      deptService.deptSave(dept);

      List<DeptVo> deptList = deptService.getDeptVoList();
      return ResponseResult.success(deptList);
    }
  }

  /**
   * 通过deptId删除dept
   * 
   * @param deptId
   * @return 封装的dept list信息
   */
  @RequestMapping(value = Constant.DEPT_DELETE, method = RequestMethod.DELETE)
  public ResponseResult deptDelete(@PathVariable long deptId) {
    List<DeptUser> deptUserList = deptUserService.getDeptUserByDeptId(deptId);
    List<ProjectVo> projectList = projectService.getProjectByDeptId(deptId);
    if (null != deptUserList && deptUserList.size() > 0) {
      return ResponseResult.failure(Constant.USER_EXIST);
    } else if (null != projectList && projectList.size() > 0) {
      return ResponseResult.failure(Constant.PROJECT_EXIST);
    } else {
      deptService.deleteDeptByDeptId(deptId);
      List<DeptVo> deptList = deptService.getDeptVoList();
      return ResponseResult.success(deptList);
    }

  }

  /**
   * 更新dept信息
   * 
   * @param dept
   * @return 封装的dept list信息
   */
  @RequestMapping(value = Constant.DEPT, method = RequestMethod.PUT)
  public ResponseResult deptModify(@RequestBody Dept dept) {

    List<Dept> nameDepts = deptService.getDeptByDeptNameExceptOne(dept.getDeptName(),
        dept.getDeptId());
    List<Dept> codeDepts = deptService.getDeptByDeptCodeExceptOne(dept.getDeptCode(),
        dept.getDeptId());
    if (nameDepts.size() > 0) {
      return ResponseResult.failure(Constant.DEPT_NAME_EXIST);
    } else if (codeDepts.size() > 0) {
      return ResponseResult.failure(Constant.DEPT_CODE_EXIST);
    } else {
      Dept deptOriginal = deptService.getDeptByDeptId(dept.getDeptId());
      if (null != dept.getDeptName()) {
        deptOriginal.setDeptName(dept.getDeptName());
      }
      if (null != dept.getDeptCode()) {
        deptOriginal.setDeptCode(dept.getDeptCode());
      }
      deptOriginal.setUpdateTime(new Date());
      deptService.deptSave(deptOriginal);
      List<DeptVo> deptList = deptService.getDeptVoList();
      return ResponseResult.success(deptList);
    }
  }
}
