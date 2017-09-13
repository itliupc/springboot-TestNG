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
import com.wafer.interfacetestdemo.domain.RequestParam;
import com.wafer.interfacetestdemo.service.RequestParamService;
import com.wafer.interfacetestdemo.vo.ResponseResult;

@RestController
@Transactional
@RequestMapping(Constant.CONTROLLER_PATH)
public class RequestParamController {
	
	@Autowired
	RequestParamService requestParamService;

	@RequestMapping(value = "interface/requestParam/{interfaceId}", method = RequestMethod.GET)
	public ResponseResult requestParamList(@PathVariable long interfaceId){
	  List<RequestParam> requestParamList= requestParamService.getRequestParamByInterfaceId(interfaceId);
	  return ResponseResult.success(requestParamList);
	}
	
//	@RequestMapping(value = "interface/requestParam/{requestParamId}/{interfaceId}", method = RequestMethod.DELETE)
//	public ResponseResult requestParamDelete(@PathVariable long requestParamId, @PathVariable long interfaceId){
//	  requestParamService.deleteRequestParamByRequestParamId(requestParamId);
//	  
//	  List<RequestParam> requestParamList= requestParamService.getRequestParamByInterfaceId(interfaceId);
//	  return ResponseResult.success(requestParamList);
//	}
//	
//	@RequestMapping(value = "interface/requestParam", method = RequestMethod.PUT)
//	public ResponseResult requestParamByRequestParamModify(@RequestBody RequestParam requestParam){
//	  RequestParam RP= requestParamService.findRequestParamById(requestParam.getRequestParamId());
//		
//	  RP.setRequestParamName(requestParam.getRequestParamName());
//	  RP.setRequestParamType(requestParam.getRequestParamType());
//	  RP.setRequestParamDescription(requestParam.getRequestParamDescription());
//	  
//	  RequestParam currentRequestParam = requestParamService.saveRequestParam(RP);
//	  return ResponseResult.success(currentRequestParam);
//	}
	
//	@RequestMapping(value = "interface/requestParam", method = RequestMethod.POST)
//	public ResponseResult requestParamCreate(@RequestBody RequestParam requestParam){
//	  RequestParam RP= new RequestParam();
//		
//	  RP.setRequestParamName(requestParam.getRequestParamName());
//	  RP.setRequestParamType(requestParam.getRequestParamType());
//	  RP.setRequestParamDescription(requestParam.getRequestParamDescription());
//	  RP.setInterfaceId(requestParam.getInterfaceId());
//	  RP.setCreateTime(new Date());
//	  
//	  RequestParam currentRequestParam = requestParamService.saveRequestParam(RP);
//	  return ResponseResult.success(currentRequestParam);
//	}
}
