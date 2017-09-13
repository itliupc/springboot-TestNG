package com.wafer.interfacetestdemo.vo;

import java.util.List;

import com.wafer.interfacetestdemo.config.Constant;
import com.wafer.interfacetestdemo.domain.Module;
import com.wafer.interfacetestdemo.utils.DateUtils;

public class ModuleView {

  private long moduleId;

  private long projectId;

  private String moduleName;

  private boolean isRun;

  private String createTime;

  private String updateTime;
  
  private List<InterfaceView> interfaceViews;

  public long getModuleId() {
    return moduleId;
  }

  public void setModuleId(long moduleId) {
    this.moduleId = moduleId;
  }

  public long getProjectId() {
    return projectId;
  }

  public void setProjectId(long projectId) {
    this.projectId = projectId;
  }

  public String getModuleName() {
    return moduleName;
  }

  public void setModuleName(String moduleName) {
    this.moduleName = moduleName;
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

  /**
   * 将Module转成ModuleView对象
   * 
   * @param mo
   * @return
   */
  public static ModuleView transformViewToModule(Module mo) {
    if (null == mo) {
      return null;
    }
    ModuleView mv = new ModuleView();
    mv.setModuleId(mo.getModuleId());
    mv.setModuleName(mo.getModuleName());
    mv.setProjectId(mo.getProjectId());
    mv.setRun(Constant.RUNNING == mo.getIsRun() ? true : false);
    mv.setCreateTime(DateUtils.formatDateTime(mo.getCreateTime()));
    mv.setUpdateTime(DateUtils.formatDateTime(mo.getUpdateTime()));
    return mv;
  }

  public List<InterfaceView> getInterfaceViews() {
    return interfaceViews;
  }

  public void setInterfaceViews(List<InterfaceView> interfaceViews) {
    this.interfaceViews = interfaceViews;
  }
}
