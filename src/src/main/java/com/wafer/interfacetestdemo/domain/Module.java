package com.wafer.interfacetestdemo.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.wafer.interfacetestdemo.config.Constants;
import com.wafer.interfacetestdemo.vo.ModuleView;

@Entity
@Table(name = "ps_module")
public class Module {

  @Id
  @GeneratedValue(generator = "generator")
  @GenericGenerator(name = "generator", strategy = "native")
  @Column(name = "module_id", unique = true, nullable = false)
  private long moduleId;

  @Column(name = "project_id", nullable = false)
  private long projectId;

  @Column(name = "module_name")
  private String moduleName;

  @Column(name = "is_run")
  private int isRun;

  @Column(name = "create_time")
  private Date createTime;

  @Column(name = "update_time")
  private Date updateTime;

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

  /**
   * 将moduleView对象转成module对象
   * 
   * @param mv
   * @return
   */
  public static Module transformViewToModule(ModuleView mv) {
    if(null == mv){
      return null;
    }
    Module module = new Module();
    module.setModuleId(mv.getModuleId());
    module.setModuleName(mv.getModuleName());
    module.setProjectId(mv.getProjectId());
    module.setIsRun(mv.isRun() ? Constants.RUNNING : Constants.NOT_RUNNING);
    return module;
  }
}
