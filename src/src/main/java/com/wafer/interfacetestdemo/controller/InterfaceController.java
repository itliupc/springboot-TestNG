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
import com.wafer.interfacetestdemo.domain.Interface;
import com.wafer.interfacetestdemo.service.InterfaceService;
import com.wafer.interfacetestdemo.vo.InterfaceView;
import com.wafer.interfacetestdemo.vo.ResponseResult;

@RestController
@Transactional
@RequestMapping(Constants.CONTROLLER_PATH)
public class InterfaceController {

  Logger logger = LoggerFactory.getLogger(InterfaceController.class);
 
  @Autowired
  InterfaceService interfaceService;
  
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
    face.setIsRun(faceView.isRun() ? Constants.RUNNING : Constants.NOT_RUNNING);
    face.setRequestParam(faceView.getRequestParam());
    face.setResponseResult(faceView.getResponseResult());
    
    face = interfaceService.saveInterface(face);
    return ResponseResult.success(InterfaceView.transformInterfaceToView(face));
  }
  
  /**
   * 查询一个module下的所有【接口】
   * @param moduleId
   * @return
   */
  @RequestMapping(value = "interface/module/{moduleId}", method = RequestMethod.GET)
  public ResponseResult getInterfaceByModule(@PathVariable long moduleId){
    List<Interface> interfaces = interfaceService.findInterfaceByModule(moduleId);
    List<InterfaceView> faceViews = new ArrayList<>();
    interfaces.parallelStream().forEach((face) -> faceViews.add(InterfaceView.transformInterfaceToView(face)));
    return ResponseResult.success(faceViews);
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
  
}
