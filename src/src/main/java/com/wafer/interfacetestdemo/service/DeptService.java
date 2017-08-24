package com.wafer.interfacetestdemo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wafer.interfacetestdemo.domain.Dept;
import com.wafer.interfacetestdemo.repository.DeptRepository;
import com.wafer.interfacetestdemo.vo.DeptVo;

@Service
public class DeptService {
  
  @Autowired
  private DeptRepository deptReposistory;

  public List<Dept> getDeptList(){
    return deptReposistory.findAll();
  }
  
  public void deptSave(Dept dept){
    deptReposistory.save(dept);
  }
  
  public Dept getDeptByDeptId(long deptId){
    return deptReposistory.findOne(deptId);
  }

  public void deleteDeptByDeptId(long deptId) {
    deptReposistory.delete(deptId);
  }

  public List<DeptVo> getDeptVoList() {
    return deptReposistory.getDeptVoList();
  }

  public Dept getDeptByUserId(long userId) {
    return deptReposistory.getDeptByUserId(userId);
  }
}