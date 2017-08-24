package com.wafer.interfacetestdemo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wafer.interfacetestdemo.config.Constants;
import com.wafer.interfacetestdemo.domain.InterfaceTestCase;
import com.wafer.interfacetestdemo.repository.InterfaceTestCaseRepository;

@Service
public class InterfaceTestCaseService {

  @Autowired
  InterfaceTestCaseRepository interfaceCaseRepository;

  /**
   * 新增 / 修改 一个test case
   * 
   * @param testCase
   * @return
   */
  public InterfaceTestCase saveInterfaceCase(InterfaceTestCase testCase) {
    return interfaceCaseRepository.save(testCase);
  }

  /**
   * 删除 一个test case
   * 
   * @param testCaseId
   * @return
   */
  public void deleteInterfaceCase(long testCaseId) {
    interfaceCaseRepository.delete(testCaseId);
  }

  /**
   * 通过interfaceId 查询一个接口下的所有TestCase
   * 
   * @param testCaseId
   * @return
   */
  public InterfaceTestCase findInterfaceTestCaseById(long testCaseId) {
    return interfaceCaseRepository.findOne(testCaseId);
  }

  /**
   * 通过interfaceId 查询一个接口下的所有TestCase
   * 
   * @param faceId
   * @return
   */
  public List<InterfaceTestCase> findInterfaceTestCaseByFace(long faceId) {
    return interfaceCaseRepository.findByInterfaceId(faceId);
  }

  /**
   * 通过interfaceId 查询一个接口下的所有 run 或者 not run的TestCase
   * 
   * @param faceId
   * @param isRun
   * @return
   */
  public List<InterfaceTestCase> findInterfaceTestCaseByFaceAndIsRun(long faceId, boolean isRun) {
    int running = isRun ? Constants.RUNNING : Constants.NOT_RUNNING;
    return interfaceCaseRepository.findByInterfaceIdAndIsRun(faceId, running);
  }
}
