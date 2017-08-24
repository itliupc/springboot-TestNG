package com.wafer.interfacetestdemo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wafer.interfacetestdemo.config.Constants;
import com.wafer.interfacetestdemo.domain.Module;
import com.wafer.interfacetestdemo.repository.ModuleRepository;

@Service
public class ModuleService {

  @Autowired
  ModuleRepository moduleRepository;

  /**
   * 添加一个module
   * 
   * @param module
   * @return
   */
  public Module saveModule(Module module) {
    return moduleRepository.save(module);
  }

  /**
   * 通过module ID 删除module
   * 
   * @param moduleId
   */
  public void deleteModule(long moduleId) {
    moduleRepository.delete(moduleId);
  }

  /**
   * 查询所有的module
   * 
   * @return
   */
  public List<Module> findAllModule() {
    return moduleRepository.findAll();
  }

  /**
   * 通过ID查询Module
   * 
   * @param id
   * @return
   */
  public Module findModuleById(long id) {
    return moduleRepository.findOne(id);
  }

  /**
   * 通过moduleName查询与之匹配的module
   * 
   * @param moduleName
   * @return
   */
  public List<Module> findModuleLikeModuleName(String moduleName) {
    return moduleRepository.findByModuleNameLike(moduleName);
  }

  /**
   * 通过项目ID查询所有module
   * 
   * @param projectId
   * @return
   */
  public List<Module> findModuleByProjectId(long projectId) {
    return moduleRepository.findByProjectId(projectId);
  }

  /**
   * 在一个项目下 模糊匹配到的module
   * 
   * @param projectId
   * @return
   */
  public List<Module> findModuleByProjectIdAndName(long projectId, String moduleName) {
    return moduleRepository.findByProjectIdAndModuleNameLike(projectId, moduleName);
  }

  /**
   * 在一个项目下 找到 需要run或者不需要run的的module
   * 
   * @param projectId
   * @return
   */
  public List<Module> findModuleByProjectIdAndRun(long projectId, boolean isRun) {
    int running = isRun ? Constants.RUNNING : Constants.NOT_RUNNING;
    return moduleRepository.findModuleByProjectIdAndIsRun(projectId, running);
  }

  /**
   * 在一个项目下 找到 需要run或者不需要run的的module
   * 
   * @param projectId
   * @return
   */
  public List<Module> findModuleByProjectIdAndRun2(long projectId, boolean isRun) {
    List<Module> modules = moduleRepository.findByProjectId(projectId);
    int running = isRun ? Constants.RUNNING : Constants.NOT_RUNNING;
    return modules.parallelStream().filter((mo) -> mo.getIsRun() == running)
        .collect(Collectors.toList());
  }
}
