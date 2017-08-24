package com.wafer.interfacetestdemo.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wafer.interfacetestdemo.config.Constants;
import com.wafer.interfacetestdemo.domain.Interface;
import com.wafer.interfacetestdemo.domain.InterfaceTestCase;
import com.wafer.interfacetestdemo.domain.Module;
import com.wafer.interfacetestdemo.service.InterfaceService;
import com.wafer.interfacetestdemo.service.InterfaceTestCaseService;
import com.wafer.interfacetestdemo.service.ModuleService;
import com.wafer.interfacetestdemo.vo.InterfaceView;
import com.wafer.interfacetestdemo.vo.ModuleView;
import com.wafer.interfacetestdemo.vo.ResponseResult;
import com.wafer.interfacetestdemo.vo.TestCaseView;

@RestController
@Transactional
@RequestMapping(Constants.CONTROLLER_PATH)
public class ModuleController {

  Logger logger = LoggerFactory.getLogger(ModuleController.class);

  @Autowired
  ModuleService moduleService;
  
  @Autowired
  InterfaceService interfaceService;
  
  @Autowired
  InterfaceTestCaseService testCaseService;

  /**
   * 查询所有的module
   */
  @RequestMapping(value = "modules", method = RequestMethod.GET)
  public ResponseResult getAllModule() {
    List<Module> modules = moduleService.findAllModule();
    List<ModuleView> moduleViews = new ArrayList<>();
    modules.forEach(mo -> moduleViews.add(ModuleView.transformViewToModule(mo)));
    return ResponseResult.success(moduleViews);
  }

  /**
   * 通过project ID查询该项目下的所有module
   * 
   * @param moduleId
   */
  @RequestMapping(value = "module/project/{projectId}", method = RequestMethod.GET)
  public ResponseResult getModuleByProject(@PathVariable long projectId) {
    List<Module> modules = moduleService.findModuleByProjectId(projectId);
    List<ModuleView> moduleViews = new ArrayList<>();
    modules.forEach(mo -> moduleViews.add(ModuleView.transformViewToModule(mo)));
    return ResponseResult.success(moduleViews);
  }

  /**
   * 通过project ID查询该项目下 与Name模糊匹配 的所有module
   * 
   * @param moduleId
   */
  @RequestMapping(value = "module/project/{projectId}/moname/{moduleName}",
      method = RequestMethod.GET)
  public ResponseResult getModuleByProjectAndName(@PathVariable @Min(0) long projectId,
      @PathVariable @NotNull String moduleName) {
    List<Module> modules = moduleService.findModuleByProjectIdAndName(projectId, moduleName);
    List<ModuleView> moduleViews = new ArrayList<>();
    modules.forEach(mo -> moduleViews.add(ModuleView.transformViewToModule(mo)));
    return ResponseResult.success(moduleViews);
  }

  /**
   * 在一个项目下 找到 需要run或者不需要run的的module
   * 
   * @param projectId
   * @param isRun
   * @return
   */
  @RequestMapping(value = "module/project/{projectId}/run/{isRun}", method = RequestMethod.GET)
  public ResponseResult getModuleByProjectAndRun(@PathVariable @Min(0) long projectId,
      @PathVariable @NotNull boolean isRun) {
    List<Module> modules = moduleService.findModuleByProjectIdAndRun(projectId, isRun);
    List<ModuleView> moduleViews = new ArrayList<>();
    modules.forEach(mo -> moduleViews.add(ModuleView.transformViewToModule(mo)));
    return ResponseResult.success(moduleViews);
  }

  /**
   * 通过ID查询单个module
   * 
   * @param moduleId
   */
  @RequestMapping(value = "module/{moduleId}", method = RequestMethod.GET)
  public ResponseResult getModule(@PathVariable long moduleId) {

    Module module = moduleService.findModuleById(moduleId);
    return ResponseResult.success(ModuleView.transformViewToModule(module));
  }

  /**
   * 添加一个module
   * 
   * @param mv
   * @return
   */
  @RequestMapping(value = "module", method = RequestMethod.POST)
  public ResponseResult addModule(@RequestBody ModuleView mv) {
    Module module = Module.transformViewToModule(mv);
    module.setCreateTime(new Date());
    module = moduleService.saveModule(module);
    return ResponseResult.success(ModuleView.transformViewToModule(module));
  }

  /**
   * 删除Module
   * 
   * @param moduleId
   * @return
   */
  @RequestMapping(value = "module/{moduleId}", method = RequestMethod.DELETE)
  public ResponseResult deleteModule(@PathVariable long moduleId) {
    if (0 == moduleId) {
      return ResponseResult.failure();
    }
    moduleService.deleteModule(moduleId);
    return ResponseResult.success();
  }

  /**
   * 修改module的信息
   * 
   * @param mv
   * @return
   */
  @RequestMapping(value = "module", method = RequestMethod.PUT)
  public ResponseResult modifyModule(@RequestBody ModuleView mv) {
    // Module module = Module.transformViewToModule(mv);
    Module module = moduleService.findModuleById(mv.getModuleId());
    // 可以修改以下内容
    module.setModuleName(mv.getModuleName());
    module.setIsRun(mv.isRun() ? Constants.RUNNING : Constants.NOT_RUNNING);
    module = moduleService.saveModule(module);
    return ResponseResult.success(ModuleView.transformViewToModule(module));
  }
  
  @GetMapping("modules/interfaces/{projectId}")
  public ResponseResult getModulesAndInterfaces(@PathVariable long projectId){
    // 1.查询一个项目下所有的module
    List<Module> modules = moduleService.findModuleByProjectId(projectId);
    List<ModuleView> mvs = new ArrayList<>();
    modules.forEach((module -> {
      
      ModuleView mv = ModuleView.transformViewToModule(module);
      // 2.查询module下的所有interface
      List<Interface> interfaces = interfaceService.findInterfaceByModule(module.getModuleId());
      List<InterfaceView> interfaceViews = new ArrayList<>();
      
      interfaces.forEach(face -> {
        InterfaceView faceView = InterfaceView.transformInterfaceToView(face);
        List<InterfaceTestCase> testCases = testCaseService.findInterfaceTestCaseByFace(face.getInterfaceId());
        
        List<TestCaseView> testCaseViews = new ArrayList<>(); 
        testCases.forEach(testCase -> {
          testCaseViews.add(TestCaseView.transformViewToTestCase(testCase));
        });
        
        faceView.setTestCases(testCaseViews);
        interfaceViews.add(faceView);
        
      });
      
      mv.setInterfaceViews(interfaceViews);
      mvs.add(mv);
    }));
    return ResponseResult.success(mvs);
  }

}
