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

import com.wafer.interfacetestdemo.config.Constant;
import com.wafer.interfacetestdemo.domain.Interface;
import com.wafer.interfacetestdemo.domain.InterfaceTestCase;
import com.wafer.interfacetestdemo.domain.RequestParam;
import com.wafer.interfacetestdemo.domain.ResponseParam;
import com.wafer.interfacetestdemo.service.InterfaceService;
import com.wafer.interfacetestdemo.service.InterfaceTestCaseService;
import com.wafer.interfacetestdemo.service.RequestParamService;
import com.wafer.interfacetestdemo.service.ResponseParamService;
import com.wafer.interfacetestdemo.vo.InterfaceView;
import com.wafer.interfacetestdemo.vo.ResponseResult;
import com.wafer.interfacetestdemo.vo.TestCaseView;

@RestController
@Transactional
@RequestMapping(Constant.CONTROLLER_PATH)
public class InterfaceController {

  Logger logger = LoggerFactory.getLogger(InterfaceController.class);
 
  @Autowired
  InterfaceService interfaceService;
  
  @Autowired
  InterfaceTestCaseService testCaseService;
  
  @Autowired
  RequestParamService requestParamService;
  
  @Autowired
  ResponseParamService responseParamService;
  
  /**
   * 新增一个【接口】
   * @param moduleId
   * @return
   */
  @RequestMapping(value = "interface", method = RequestMethod.POST)
  public ResponseResult saveInterface(@RequestBody InterfaceView faceView){
    Interface face = Interface.transformInterfaceToView(faceView);
    face.setCreateTime(new Date());
    face = interfaceService.saveInterface(face);
    return ResponseResult.success(InterfaceView.transformInterfaceToView(face));
  }
  
  /**
   * 删除一个【接口】
   * @param moduleId
   * @return
   */
  @RequestMapping(value = "interface/{interfaceId}", method = RequestMethod.DELETE)
  public ResponseResult deleteInterface(@PathVariable long interfaceId){
    if(0 == interfaceId){
      return ResponseResult.failure();
    }
    interfaceService.deleteInterface(interfaceId);
    return ResponseResult.success();
  }
  
  /**
   * 修改一个【接口】
   * @param moduleId
   * @return
   */
  @RequestMapping(value = "interface", method = RequestMethod.PUT)
  public ResponseResult updateInterface(@RequestBody InterfaceView faceView){
    Interface face = interfaceService.findInterfaceById(faceView.getInterfaceId());
    // 可以修改的字段
    face.setInterfaceName(faceView.getInterfaceName());
    face.setInterfaceType(faceView.getInterfaceType());
    face.setInterfaceUrl(faceView.getInterfaceUrl());
    face.setIsRun(faceView.isRun() ? Constant.RUNNING : Constant.NOT_RUNNING);
    face.setRequestParam(faceView.getRequestParam());
    face.setResponseResult(faceView.getResponseResult());
    
    face = interfaceService.saveInterface(face);
    
    InterfaceView faceViews = InterfaceView.transformInterfaceToView(face);
    List<InterfaceTestCase> testCases = testCaseService.findInterfaceTestCaseByFace(face.getInterfaceId());
    List<RequestParam> requestParamList= requestParamService.getRequestParamByInterfaceId(face.getInterfaceId());
    List<ResponseParam> responseParamList= responseParamService.getResponseParamByInterfaceId(face.getInterfaceId());
    List<TestCaseView> testCaseViews = new ArrayList<>();
    testCases.parallelStream().forEach(testCase -> testCaseViews.add(TestCaseView.transformViewToTestCase(testCase)));
    faceViews.setTestCaseViews(testCaseViews);
    faceViews.setRequestParams(requestParamList);
    faceViews.setResponseParams(responseParamList);
    return ResponseResult.success(faceViews);
  }
  
  /**
   * 查询一个module下的所有【接口】
   * @param moduleId
   * @return
   */
  @RequestMapping(value = "interface/module/{moduleId}", method = RequestMethod.GET)
  public ResponseResult getInterfaceByModule(@PathVariable long moduleId){
    List<Interface> interfaces = interfaceService.findInterfaceByModuleOrderBy(moduleId);
//    List<InterfaceView> faceViews = new ArrayList<>();
//    interfaces.parallelStream().forEach((face) -> faceViews.add(InterfaceView.transformInterfaceToView(face)));
    return ResponseResult.success(interfaces);
  }
  
  /**
   * 通过interfaceId查询一个【接口】的详情
   * @param interfaceId
   * @return
   */
  @RequestMapping(value = "interface/{interfaceId}", method = RequestMethod.GET)
  public ResponseResult getInterfaceById(@PathVariable long interfaceId){
    Interface face= interfaceService.findInterfaceById(interfaceId);
    return ResponseResult.success(InterfaceView.transformInterfaceToView(face));
  }
  
  /**
   * 通过interfaceId查询一个【接口】的详情
   * @param interfaceId
   * @return
   */
  @RequestMapping(value = "interface/testcase/{interfaceId}", method = RequestMethod.GET)
  public ResponseResult getInterfaceAndTestCasesById(@PathVariable long interfaceId){
	InterfaceView faceView = getInterfaceInfo(interfaceId);
	return ResponseResult.success(faceView);
  }
  
  @RequestMapping(value = "interface/requestParam/{requestParamId}/{interfaceId}", method = RequestMethod.DELETE)
	public ResponseResult requestParamDelete(@PathVariable long requestParamId, @PathVariable long interfaceId){
	  requestParamService.deleteRequestParamByRequestParamId(requestParamId);
	  
	  InterfaceView faceView = getInterfaceInfo(interfaceId);
	  return ResponseResult.success(faceView);
	}
  
  @RequestMapping(value = "interface/requestParam", method = RequestMethod.PUT)
	public ResponseResult requestParamByRequestParamModify(@RequestBody RequestParam requestParam){
	  RequestParam RP= requestParamService.findRequestParamById(requestParam.getRequestParamId());
		
	  RP.setRequestParamName(requestParam.getRequestParamName());
	  RP.setRequestParamType(requestParam.getRequestParamType());
	  RP.setRequestParamDescription(requestParam.getRequestParamDescription());
	  
	  requestParamService.saveRequestParam(RP);
	  
	  InterfaceView faceView = getInterfaceInfo(requestParam.getInterfaceId());
	  return ResponseResult.success(faceView);
	}
  
	@RequestMapping(value = "interface/requestParam", method = RequestMethod.POST)
	public ResponseResult requestParamCreate(@RequestBody RequestParam requestParam){
	  RequestParam RP= new RequestParam();
		
	  RP.setRequestParamName(requestParam.getRequestParamName());
	  RP.setRequestParamType(requestParam.getRequestParamType());
	  RP.setRequestParamDescription(requestParam.getRequestParamDescription());
	  RP.setInterfaceId(requestParam.getInterfaceId());
	  RP.setCreateTime(new Date());
	  
	  requestParamService.saveRequestParam(RP);
	  InterfaceView faceView = getInterfaceInfo(requestParam.getInterfaceId());
	  return ResponseResult.success(faceView);
	}
	
	@RequestMapping(value = "interface/responseParam/{responseParamId}/{interfaceId}", method = RequestMethod.DELETE)
	public ResponseResult responseParamDelete(@PathVariable long responseParamId, @PathVariable long interfaceId){
		responseParamService.deleteResponseParamByResponseParamId(responseParamId);
		InterfaceView faceView = getInterfaceInfo(interfaceId);
		return ResponseResult.success(faceView);
	}
	
	@RequestMapping(value = "interface/responseParam", method = RequestMethod.PUT)
	public ResponseResult responseParamByResponseParamModify(@RequestBody ResponseParam responseParam){
	  ResponseParam RP= responseParamService.findResponseParamById(responseParam.getResponseParamId());
		
	  RP.setResponseParamName(responseParam.getResponseParamName());
	  RP.setResponseParamType(responseParam.getResponseParamType());
	  RP.setResponseParamDescription(responseParam.getResponseParamDescription());
	  
	  responseParamService.saveResponseParam(RP);
	  InterfaceView faceView = getInterfaceInfo(responseParam.getInterfaceId());
	  return ResponseResult.success(faceView);
	}
	
	@RequestMapping(value = "interface/responseParam", method = RequestMethod.POST)
	public ResponseResult responseParamCreate(@RequestBody ResponseParam responseParam){
	  ResponseParam RP= new ResponseParam();
		
	  RP.setResponseParamName(responseParam.getResponseParamName());
	  RP.setResponseParamType(responseParam.getResponseParamType());
	  RP.setResponseParamDescription(responseParam.getResponseParamDescription());
	  RP.setInterfaceId(responseParam.getInterfaceId());
	  RP.setCreateTime(new Date());
	  
	  responseParamService.saveResponseParam(RP);
	  InterfaceView faceView = getInterfaceInfo(responseParam.getInterfaceId());
	  return ResponseResult.success(faceView);
	}
	  /**
	   * 删除指定的test Case
	   * 
	   * @param testCaseId
	   * @return
	   */
	  @RequestMapping(value = "interfacecase/{testCaseId}/{interfaceId}", method = RequestMethod.DELETE)
	  public ResponseResult deleteInterfaceCase(@PathVariable long testCaseId,@PathVariable long interfaceId) {
	    if (0 == testCaseId) {
	      return ResponseResult.failure();
	    }
	    testCaseService.deleteInterfaceCase(testCaseId);
	    InterfaceView faceView = getInterfaceInfo(interfaceId);
		return ResponseResult.success(faceView);
	  }
  
  public InterfaceView getInterfaceInfo(long interfaceId){
	  Interface face= interfaceService.findInterfaceById(interfaceId);
	    InterfaceView faceView = InterfaceView.transformInterfaceToView(face);
	    if(null != face){
	      List<InterfaceTestCase> testCases = testCaseService.findInterfaceTestCaseByFace(face.getInterfaceId());
	      List<RequestParam> requestParamList= requestParamService.getRequestParamByInterfaceId(interfaceId);
	      List<ResponseParam> responseParamList= responseParamService.getResponseParamByInterfaceId(interfaceId);
	      List<TestCaseView> testCaseViews = new ArrayList<>();
	      testCases.parallelStream().forEach(testCase -> testCaseViews.add(TestCaseView.transformViewToTestCase(testCase)));
	      faceView.setTestCaseViews(testCaseViews);
	      faceView.setRequestParams(requestParamList);
	      faceView.setResponseParams(responseParamList);
	    }
	    return faceView;
  }
  
  /**
   * 通过interfaceId查询一个【接口】的详情
   * @param interfaceId
   * @return
   */
  @RequestMapping(value = "interface/{interfaceId}", method = RequestMethod.POST)
  public ResponseResult duplicateInterface(@PathVariable long interfaceId){
    Interface face= interfaceService.findInterfaceById(interfaceId);
    InterfaceView faceView = null;
    if(null != face){
      Interface newFace = new Interface();
      newFace.setInterfaceType(face.getInterfaceType());
      newFace.setInterfaceUrl(face.getInterfaceUrl());
      newFace.setIsRun(face.getIsRun());
      newFace.setModuleId(face.getModuleId());
      newFace.setRequestParam(face.getRequestParam());
      newFace.setResponseResult(face.getResponseResult());
      newFace.setInterfaceName("Copy of "+face.getInterfaceName());
      newFace.setCreateTime(new Date());
      newFace = interfaceService.saveInterface(newFace);
      
      final long newInterfaceId = newFace.getInterfaceId();
      // 复制TestCase
      List<InterfaceTestCase> testCases = testCaseService.findInterfaceTestCaseByFace(interfaceId);
      if(testCases.size() > 0){
        testCases.forEach(testCase -> {
          InterfaceTestCase fCase = new InterfaceTestCase();
          fCase.setExpectResult(testCase.getExpectResult());
          fCase.setExpectStatus(testCase.getExpectStatus());
          fCase.setInterfaceId(newInterfaceId);
          fCase.setIsRun(testCase.getIsRun());
          fCase.setParamCase(testCase.getParamCase());
          fCase.setTestCaseName("Copy Of "+testCase.getTestCaseName());
          fCase.setCreateTime(new Date());
          testCaseService.saveInterfaceCase(fCase);
        });
      }
      faceView = InterfaceView.transformInterfaceToView(newFace);
    }
    return ResponseResult.success(faceView);
  }
  
}
