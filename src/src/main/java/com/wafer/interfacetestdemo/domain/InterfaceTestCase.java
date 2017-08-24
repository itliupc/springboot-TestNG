package com.wafer.interfacetestdemo.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.wafer.interfacetestdemo.config.Constants;
import com.wafer.interfacetestdemo.vo.TestCaseView;

@Entity
@Table(name = "ps_interface_testcase")
public class InterfaceTestCase {

  @Id
  @GeneratedValue(generator = "generator")
  @GenericGenerator(name = "generator", strategy = "native")
  @Column(name = "interface_testcase_id", unique = true, nullable = false)
  private long interfaceTestCaseId;
  private long interfaceId;
  private String paramCase;
  private String expectResult;
  private int expectStatus;
  private int isRun;
  private Date createTime;
  private Date updateTime;

  public long getInterfaceTestCaseId() {
    return interfaceTestCaseId;
  }

  public void setInterfaceTestCaseId(long interfaceTestCaseId) {
    this.interfaceTestCaseId = interfaceTestCaseId;
  }

  public long getInterfaceId() {
    return interfaceId;
  }

  public void setInterfaceId(long interfaceId) {
    this.interfaceId = interfaceId;
  }

  public String getParamCase() {
    return paramCase;
  }

  public void setParamCase(String paramCase) {
    this.paramCase = paramCase;
  }

  public String getExpectResult() {
    return expectResult;
  }

  public void setExpectResult(String expectResult) {
    this.expectResult = expectResult;
  }

  public int getExpectStatus() {
    return expectStatus;
  }

  public void setExpectStatus(int expectStatus) {
    this.expectStatus = expectStatus;
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

  public static InterfaceTestCase transformTestCaseToView(TestCaseView testCaseView) {
    if (null == testCaseView) {
      return null;
    }
    InterfaceTestCase testCase = new InterfaceTestCase();
    testCase.setExpectResult(testCaseView.getExpectResult());
    testCase.setExpectStatus(testCaseView.getExpectStatus());
    testCase.setInterfaceId(testCaseView.getInterfaceId());
    testCase.setInterfaceTestCaseId(testCaseView.getInterfaceTestCaseId());
    testCase.setParamCase(testCaseView.getParamCase());
    testCase.setIsRun(testCaseView.isRun() ? Constants.RUNNING : Constants.NOT_RUNNING);
    return testCase;
  }
}
