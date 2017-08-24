package com.wafer.interfacetestdemo.repository;

import java.util.List;

import com.wafer.interfacetestdemo.domain.InterfaceTestCase;
import com.wafer.interfacetestdemo.repository.base.BaseRepository;

public interface InterfaceTestCaseRepository extends BaseRepository<InterfaceTestCase, Long> {
  
  public List<InterfaceTestCase> findByInterfaceId(long faceId);
  
  public List<InterfaceTestCase> findByInterfaceIdAndIsRun(long faceId, int isRun);
  
}
