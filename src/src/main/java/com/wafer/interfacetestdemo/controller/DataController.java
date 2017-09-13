package com.wafer.interfacetestdemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wafer.interfacetestdemo.config.Constant;
import com.wafer.interfacetestdemo.service.DataService;

@RestController
@RequestMapping(Constant.CONTROLLER_PATH)
@Transactional
public class DataController {

  @Autowired
  DataService dataService;

  /**
   * 根据ProjectId提供测试数据
   * 
   * @param projectId
   * @return
   */
  @RequestMapping(value = Constant.DATA_SERVICE, method = RequestMethod.GET)
  @Transactional(readOnly = true)
  public List<Object[]> testCaseDataList(@PathVariable long projectId) {
    return dataService.getTestCaseData(projectId);
  }

  /**
   * 根据projectId,moduleName,interfaceName获取测试数据
   * 
   * @param projectId
   * @param moduleName
   * @param interfaceName
   * @return
   */
  @RequestMapping(value = Constant.DATA_SERVICE_TESTCASE, method = RequestMethod.GET)
  @Transactional(readOnly = true)
  public List<Object[]> simpleTestCaseData(@PathVariable long projectId,
      @PathVariable String moduleName, @PathVariable String interfaceName) {
    return dataService.getSimpleTestCaseData(projectId, moduleName, interfaceName);
  }
}
