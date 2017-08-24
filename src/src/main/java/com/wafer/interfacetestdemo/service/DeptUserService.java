package com.wafer.interfacetestdemo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wafer.interfacetestdemo.domain.DeptUser;
import com.wafer.interfacetestdemo.repository.DeptUserRepository;

@Service
public class DeptUserService {
  
  @Autowired
  private DeptUserRepository deptUserReposistory;

  public DeptUser getDeptUserByUserId(long userId) {
    return deptUserReposistory.getDeptUserByUserId(userId);
  }

  public void deptUserSave(DeptUser deptUser) {
    deptUserReposistory.save(deptUser);
  }

  public List<DeptUser> getDeptUserByDeptId(long deptId) {
    return deptUserReposistory.getDeptUserByDeptId(deptId);
  }

  public void removeDeptUserByUserId(long userId) {
    deptUserReposistory.removeDeptUserByUserId(userId);
  }
}