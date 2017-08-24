package com.wafer.interfacetestdemo.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wafer.interfacetestdemo.config.Constants;
import com.wafer.interfacetestdemo.domain.InterfaceTestCase;
import com.wafer.interfacetestdemo.service.InterfaceTestCaseService;
import com.wafer.interfacetestdemo.vo.ResponseResult;
import com.wafer.interfacetestdemo.vo.TestCaseView;

@RestController
@Transactional
@RequestMapping(Constants.CONTROLLER_PATH)
public class InterfaceTestCaseController {

  Logger logger = LoggerFactory.getLogger(InterfaceTestCaseController.class);

  @Autowired
  InterfaceTestCaseService testCaseService;

  /**
   * 新增一个TestCase
   * 
   * @return
   */
  @RequestMapping(value = "interfacecase", method = RequestMethod.POST)
  public ResponseResult saveInterfaceCase(@RequestBody TestCaseView testCaseView) {
    InterfaceTestCase testCase = InterfaceTestCase.transformTestCaseToView(testCaseView);
    testCase.setCreateTime(new Date());
    testCase = testCaseService.saveInterfaceCase(testCase);
    return ResponseResult.success(TestCaseView.transformViewToTestCase(testCase));
  }

  /**
   * 删除指定的test Case
   * 
   * @param testCaseId
   * @return
   */
  @RequestMapping(value = "interfacecase/{testCaseId}", method = RequestMethod.DELETE)
  public ResponseResult deleteInterfaceCase(@PathVariable long testCaseId) {
    if (0 == testCaseId) {
      return ResponseResult.failure();
    }
    testCaseService.deleteInterfaceCase(testCaseId);
    return ResponseResult.success();
  }

  /**
   * 修改指定的TestCase
   * 
   * @param testCaseView
   * @return
   */
  @RequestMapping(value = "interfacecase", method = RequestMethod.PUT)
  public ResponseResult updatelInterfaceCase(@RequestBody TestCaseView testCaseView) {
    InterfaceTestCase testCase =
        testCaseService.findInterfaceTestCaseById(testCaseView.getInterfaceTestCaseId());
    if (null == testCase) {
      return ResponseResult.failure();
    }
    if (null != testCaseView.getExpectResult()) {
      testCase.setExpectResult(testCaseView.getExpectResult());
    }
    if (0 != testCaseView.getExpectStatus()) {
      testCase.setExpectStatus(testCaseView.getExpectStatus());
    }
    if (null != testCaseView.getParamCase()) {
      testCase.setParamCase(testCaseView.getParamCase());
    }
    testCase.setIsRun(testCaseView.isRun() ? Constants.RUNNING : Constants.NOT_RUNNING);
    testCase = testCaseService.saveInterfaceCase(testCase);
    return ResponseResult.success(TestCaseView.transformViewToTestCase(testCase));
  }

  /**
   * 通过TestCaseId查询一条记录的详情
   * 
   * @param interfacecaseId
   * @return
   */
  @RequestMapping(value = "interfacecase/{interfacecaseId}", method = RequestMethod.GET)
  public ResponseResult getInterfaceCaseById(@PathVariable long interfacecaseId) {
    InterfaceTestCase testCase = testCaseService.findInterfaceTestCaseById(interfacecaseId);
    return ResponseResult.success(TestCaseView.transformViewToTestCase(testCase));
  }

  /**
   * 查询一个接口下的所有用例
   * 
   * @param faceId
   * @return
   */
  @RequestMapping(value = "interfacecase/interface/{faceId}", method = RequestMethod.GET)
  public ResponseResult getInterfaceCaseByFace(@PathVariable long faceId) {
    List<InterfaceTestCase> testCases = testCaseService.findInterfaceTestCaseByFace(faceId);
    List<TestCaseView> testCaseViews = new ArrayList<>();
    testCases.forEach((testCase -> {
      testCaseViews.add(TestCaseView.transformViewToTestCase(testCase));
    }));
    return ResponseResult.success(testCaseViews);
  }

  /**
   * 查询一个接口下的所有run 或 not run的用例
   * 
   * @param faceId
   * @param isRun
   * @return
   */
  @RequestMapping(value = "interfacecase/interface/{faceId}/running/{isRun}",
      method = RequestMethod.GET)
  public ResponseResult getInterfaceCaseByFaceAndRun(@PathVariable long faceId, @PathVariable boolean isRun) {
    List<InterfaceTestCase> testCases =
        testCaseService.findInterfaceTestCaseByFaceAndIsRun(faceId, isRun);
    List<TestCaseView> testCaseViews = new ArrayList<>();
    testCases.forEach((testCase -> {
      testCaseViews.add(TestCaseView.transformViewToTestCase(testCase));
    }));
    return ResponseResult.success(testCaseViews);
  }
}
