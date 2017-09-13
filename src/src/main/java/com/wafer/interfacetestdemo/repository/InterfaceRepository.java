package com.wafer.interfacetestdemo.repository;

import java.util.List;

import com.wafer.interfacetestdemo.domain.Interface;
import com.wafer.interfacetestdemo.repository.base.BaseRepository;

public interface InterfaceRepository extends BaseRepository<Interface, Long> {
  
  List<Interface> findByModuleId(long moduleId);
  
  List<Interface> findByModuleIdOrderByInterfaceIdDesc(long moduleId);
  
  List<Interface> findByModuleIdIn(List<Long> moduleIds);
  
  List<Interface> findByModuleIdAndInterfaceNameLike(long moduleId, String interfaceName);
  
  List<Interface> findByModuleIdAndInterfaceType(long moduleId, String interfaceType);
  
}
