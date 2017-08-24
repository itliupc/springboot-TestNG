package com.wafer.interfacetestdemo.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.wafer.interfacetestdemo.config.Constants;
import com.wafer.interfacetestdemo.vo.InterfaceView;

@Entity
@Table(name = "ps_interface")
public class Interface {

  @Id
  @GeneratedValue(generator = "generator")
  @GenericGenerator(name = "generator", strategy = "native")
  @Column(name = "interface_id", unique = true, nullable = false)
  private long interfaceId;
  private long moduleId;
  private String interfaceName;
  private String interfaceUrl;
  private String interfaceType;
  private String requestParam;
  private String responseResult;
  private int isRun;
  private Date createTime;
  private Date updateTime;

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

  public int getIsRun() {
    return isRun;
  }

  public void setIsRun(int isRun) {
    this.isRun = isRun;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public Date getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }
  
  public static Interface transformInterfaceToView(InterfaceView faceView){
    if(null == faceView){
      return null;
    }
    Interface face = new Interface();
    face.setInterfaceId(faceView.getInterfaceId());
    face.setInterfaceName(faceView.getInterfaceName());
    face.setInterfaceType(faceView.getInterfaceType());
    face.setInterfaceUrl(faceView.getInterfaceUrl());
    face.setModuleId(faceView.getModuleId());
    face.setRequestParam(faceView.getRequestParam());
    face.setResponseResult(faceView.getResponseResult());
    face.setIsRun(faceView.isRun() ? Constants.RUNNING : Constants.NOT_RUNNING);
    return face;
  }

}
