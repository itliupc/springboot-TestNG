package com.wafer.interfacetestdemo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.wafer.interfacetestdemo.domain.Dept;
import com.wafer.interfacetestdemo.repository.base.BaseRepository;
import com.wafer.interfacetestdemo.vo.DeptVo;

public interface DeptRepository extends BaseRepository<Dept, Long> {
  
  @Query(value = "Select new com.wafer.interfacetestdemo.vo.DeptVo (d.deptId, d.deptName, d.deptCode, d.createTime) from Dept d where d.deptType = 0")
  List<DeptVo> getDeptVoList();

  @Query(value = "Select d from Dept d, DeptUser du where d.deptId = du.deptId and du.userId=:userId")
  Dept getDeptByUserId(@Param("userId") long userId);
}
