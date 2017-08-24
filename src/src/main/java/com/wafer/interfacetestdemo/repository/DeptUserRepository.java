package com.wafer.interfacetestdemo.repository;

import java.util.List;

import com.wafer.interfacetestdemo.domain.DeptUser;
import com.wafer.interfacetestdemo.repository.base.BaseRepository;

public interface DeptUserRepository extends BaseRepository<DeptUser, Long> {

  DeptUser getDeptUserByUserId(long userId);
  
  List<DeptUser> getDeptUserByDeptId(long userId);

  void removeDeptUserByUserId(long userId);
  
}
