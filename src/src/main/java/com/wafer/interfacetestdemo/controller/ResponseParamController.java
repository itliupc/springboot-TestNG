package com.wafer.interfacetestdemo.controller;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wafer.interfacetestdemo.config.Constant;
import com.wafer.interfacetestdemo.domain.ResponseParam;
import com.wafer.interfacetestdemo.service.ResponseParamService;
import com.wafer.interfacetestdemo.vo.ResponseResult;

@RestController
@Transactional
@RequestMapping(Constant.CONTROLLER_PATH)
public class ResponseParamController {
	@Autowired
	ResponseParamService responseParamService;

	@RequestMapping(value = "interface/responseParam/{interfaceId}", method = RequestMethod.GET)
	public ResponseResult responseParamList(@PathVariable long interfaceId){
	  List<ResponseParam> responseParamList= responseParamService.getResponseParamByInterfaceId(interfaceId);
	  return ResponseResult.success(responseParamList);
	}
	
//	@RequestMapping(value = "interface/responseParam/{responseParamId}", method = RequestMethod.DELETE)
//	public ResponseResult responseParamDelete(@PathVariable long responseParamId){
//		responseParamService.deleteResponseParamByResponseParamId(responseParamId);
//	  return ResponseResult.success();
//	}
//	
//	@RequestMapping(value = "interface/responseParam", method = RequestMethod.PUT)
//	public ResponseResult responseParamByResponseParamModify(@RequestBody ResponseParam responseParam){
//	  ResponseParam RP= responseParamService.findResponseParamById(responseParam.getResponseParamId());
//		
//	  RP.setResponseParamName(responseParam.getResponseParamName());
//	  RP.setResponseParamType(responseParam.getResponseParamType());
//	  RP.setResponseParamDescription(responseParam.getResponseParamDescription());
//	  
//	  ResponseParam currentResponseParam = responseParamService.saveResponseParam(RP);
//	  return ResponseResult.success(currentResponseParam);
//	}
//	
//	@RequestMapping(value = "interface/responseParam", method = RequestMethod.POST)
//	public ResponseResult responseParamCreate(@RequestBody ResponseParam responseParam){
//	  ResponseParam RP= new ResponseParam();
//		
//	  RP.setResponseParamName(responseParam.getResponseParamName());
//	  RP.setResponseParamType(responseParam.getResponseParamType());
//	  RP.setResponseParamDescription(responseParam.getResponseParamDescription());
//	  RP.setInterfaceId(responseParam.getInterfaceId());
//	  RP.setCreateTime(new Date());
//	  
//	  ResponseParam currentResponseParam = responseParamService.saveResponseParam(RP);
//	  return ResponseResult.success(currentResponseParam);
//	}
}
