package com.wafer.interfacetestdemo.vo;

import com.wafer.interfacetestdemo.config.Constants;
import com.wafer.interfacetestdemo.domain.InterfaceTestCase;
import com.wafer.interfacetestdemo.utils.DateUtils;

public class TestCaseView {

  private long interfaceTestCaseId;
  private long interfaceId;
  private String paramCase;
  private String expectResult;
  private int expectStatus;
  private boolean isRun;
  private String createTime;
  private String updateTime;

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

  public static TestCaseView transformViewToTestCase(InterfaceTestCase testCase) {
    if (null == testCase) {
      return null;
    }
    TestCaseView tcv = new TestCaseView();
    tcv.setExpectResult(testCase.getExpectResult());
    tcv.setExpectStatus(testCase.getExpectStatus());
    tcv.setInterfaceId(testCase.getInterfaceId());
    tcv.setInterfaceTestCaseId(testCase.getInterfaceTestCaseId());
    tcv.setParamCase(testCase.getParamCase());
    tcv.setRun(Constants.RUNNING == testCase.getIsRun() ? true : false);
    tcv.setCreateTime(DateUtils.formatDateTime(testCase.getCreateTime()));
    tcv.setUpdateTime(DateUtils.formatDateTime(testCase.getUpdateTime()));
    return tcv;
  }

}
