package com.wafer.interfacetestdemo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wafer.interfacetestdemo.domain.Interface;
import com.wafer.interfacetestdemo.repository.InterfaceRepository;

@Service
public class InterfaceService {

  @Autowired
  InterfaceRepository interfaceRepository;

  /**
   * 添加一个Interface
   * 
   * @param interf
   * @return
   */
  public Interface saveInterface(Interface face) {
    return interfaceRepository.save(face);
  }

  /**
   * 删除一个【接口】
   * 
   * @param interfaceId
   */
  public void deleteInterface(long interfaceId) {
    interfaceRepository.delete(interfaceId);
  }

  /**
   * 获取所有的【接口】
   * 
   * @return
   */
  public List<Interface> findAllInterface() {
    return interfaceRepository.findAll();
  }

  /**
   * 获取指定的【接口】
   * 
   * @param interfaceId
   * @return
   */
  public Interface findInterfaceById(long interfaceId) {
    return interfaceRepository.findOne(interfaceId);
  }

  /**
   * 获取一个module下的所有【接口】
   * 
   * @param moduleId
   * @return
   */
  public List<Interface> findInterfaceByModule(long moduleId) {
    return interfaceRepository.findByModuleId(moduleId);
  }
  
  /**
   * 获取一个module下的所有【接口】 排序
   * 
   * @param moduleId
   * @return
   */
  public List<Interface> findInterfaceByModuleOrderBy(long moduleId) {
    return interfaceRepository.findByModuleIdOrderByInterfaceIdDesc(moduleId);
  }

  /**
   * 获取多个module下的所有【接口】
   * 
   * @param moduleId
   * @return
   */
  public List<Interface> findInterfaceByModules(List<Long> moduleIds) {
    return interfaceRepository.findByModuleIdIn(moduleIds);
  }

  /**
   * 通过【接口】名称模糊匹配 一个module下
   * 
   * @param interfaceName
   * @return
   */
  public List<Interface> findInterfaceByName(long moduleId, String interfaceName) {
    return interfaceRepository.findByModuleIdAndInterfaceNameLike(moduleId, interfaceName);
  }

  /**
   * 通过【接口】类型查询所有【接口】 一个module下
   * 
   * @param interfaceName
   * @return
   */
  public List<Interface> findInterfaceByInterfType(long moduleId, String interfaceType) {
    return interfaceRepository.findByModuleIdAndInterfaceType(moduleId, interfaceType);
  }
}
