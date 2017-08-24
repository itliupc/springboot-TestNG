package com.wafer.interfacetestdemo.vo;

import java.util.List;

import com.wafer.interfacetestdemo.config.Constants;
import com.wafer.interfacetestdemo.domain.Interface;
import com.wafer.interfacetestdemo.utils.DateUtils;

public class InterfaceView {

  private long interfaceId;
  private long moduleId;
  private String interfaceName;
  private String interfaceUrl;
  private String interfaceType;
  private String requestParam;
  private String responseResult;
  private boolean isRun;
  private String createTime;
  private String updateTime;
  
  private List<TestCaseView> testCases;

  public long getInterfaceId() {
    return interfaceId;
  }

  public void setInterfaceId(long interfaceId) {
    this.interfaceId = interfaceId;
  }

  public long getModuleId() {
    return moduleId;
  }

  public void setModuleId(long moduleId) {
    this.moduleId = moduleId;
  }

  public String getInterfaceName() {
    return interfaceName;
  }

  public void setInterfaceName(String interfaceName) {
    this.interfaceName = interfaceName;
  }

  public String getInterfaceUrl() {
    return interfaceUrl;
  }

  public void setInterfaceUrl(String interfaceUrl) {
    this.interfaceUrl = interfaceUrl;
  }

  public String getInterfaceType() {
    return interfaceType;
  }

  public void setInterfaceType(String interfaceType) {
    this.interfaceType = interfaceType;
  }

  public String getRequestParam() {
    return requestParam;
  }

  public void setRequestParam(String requestParam) {
    this.requestParam = requestParam;
  }

  public String getResponseResult() {
    return responseResult;
  }

  public void setResponseResult(String responseResult) {
    this.responseResult = responseResult;
  }

  public boolean isRun() {
    return isRun;
  }

  public void setRun(boolean isRun) {
    this.isRun = isRun;
  }

  public String getCreateTime() {
    return createTime;
  }

  public void setCreateTime(String createTime) {
    this.createTime = createTime;
  }

  public String getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(String updateTime) {
    this.updateTime = updateTime;
  }

  public static InterfaceView transformInterfaceToView(Interface face) {
    if (null == face) {
      return null;
    }
    InterfaceView faceView = new InterfaceView();
    faceView.setInterfaceId(face.getInterfaceId());
    faceView.setInterfaceName(face.getInterfaceName());
    faceView.setInterfaceType(face.getInterfaceType());
    faceView.setInterfaceUrl(face.getInterfaceUrl());
    faceView.setModuleId(face.getModuleId());
    faceView.setRequestParam(face.getRequestParam());
    faceView.setResponseResult(face.getResponseResult());
    faceView.setRun(Constants.RUNNING == face.getIsRun());
    faceView.setUpdateTime(DateUtils.formatDateTime(face.getUpdateTime()));
    faceView.setCreateTime(DateUtils.formatDateTime(face.getCreateTime()));
    return faceView;
  }

  public List<TestCaseView> getTestCases() {
    return testCases;
  }

  public void setTestCases(List<TestCaseView> testCases) {
    this.testCases = testCases;
  }
}
