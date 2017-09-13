package com.wafer.interfacetestdemo.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.wafer.interfacetestdemo.config.Constant;
import com.wafer.interfacetestdemo.domain.Interface;
import com.wafer.interfacetestdemo.domain.InterfaceTestCase;
import com.wafer.interfacetestdemo.domain.Module;
import com.wafer.interfacetestdemo.domain.Project;
import com.wafer.interfacetestdemo.service.InterfaceService;
import com.wafer.interfacetestdemo.service.InterfaceTestCaseService;
import com.wafer.interfacetestdemo.service.ModuleService;
import com.wafer.interfacetestdemo.service.ProjectService;
import com.wafer.interfacetestdemo.utils.ExcelUtils;
import com.wafer.interfacetestdemo.utils.IOUtils;
import com.wafer.interfacetestdemo.utils.JsonUtils;
import com.wafer.interfacetestdemo.vo.ResponseResult;

@RestController
@Transactional
@RequestMapping(Constant.CONTROLLER_PATH)
public class DataSourceController {

  Logger logger = LoggerFactory.getLogger(DataSourceController.class);

  @Autowired
  InterfaceService interfaceService;

  @Autowired
  InterfaceTestCaseService testCaseService;
  
  @Autowired
  ProjectService projectService;
  
  @Autowired
  ModuleService moduleService;

  @PostMapping("/dataimport/excel")
  public ResponseResult importDataFromExcel(@RequestParam("excelFile") MultipartFile excelFile,
      HttpServletRequest request) {
    if (null == excelFile) {
      return ResponseResult.failure("模板文件不存在");
    }
    String fileName = excelFile.getOriginalFilename(); // report.xls
    System.out.println(fileName);
    try {
      InputStream fis = excelFile.getInputStream();
      // List<Map<String, String>> data = ExcelImportUtil.parseExcel(fis);
      List<List<Map<String, String>>> data = ExcelUtils.parseExcel(fis, fileName);
      logger.debug("import data is : {}.", data.toString());
      for (int i = 0; i < data.size(); i++) {
        List<Map<String, String>> rows = data.get(i);
        for (int j = 0; j < rows.size(); j++) {
          Map<String, String> row = rows.get(j);
          dealRowData(row);
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    return null;
  }


  /**
   * 导出一个interface下的所有test Case
   * @param faceId
   * @param response
   * @return
   */
  @GetMapping("/dataexport/excel/face/{faceId}")
  public ResponseResult exportDataToExcelFromFace(@PathVariable long faceId, HttpServletResponse response) {
    // 1.查询获取需要导出的数据
    Interface face = interfaceService.findInterfaceById(faceId);

    List<InterfaceTestCase> testCases = testCaseService.findInterfaceTestCaseByFace(faceId);
    List<HashMap<String, String>> data = new ArrayList<>();
    packageCaseData(data, face, testCases);

    download(Constant.DOWNLOAD_FILE_TYPE_EXCEL, response, data);
    // 4.定时任务删除文件以及
    return ResponseResult.success();
  }
  
  /**
   * 到处一个项目下的所有test case
   * @param projectId
   * @param response
   * @return
   */
  @GetMapping("/dataexport/{fileType}/project/{projectId}")
  public void exportDataToExcelFromProject(@PathVariable String fileType, @PathVariable long projectId, HttpServletResponse response) {
    // 1.查询获取需要导出的数据
    
    Project project = projectService.getProjectByProjectId(projectId);
    
    List<HashMap<String, String>> data = new ArrayList<>();
    
    if(null != project){
      List<Module> modules = moduleService.findModuleByProjectId(project.getProjectId());
      for(Module module : modules){
        List<Interface> faces = interfaceService.findInterfaceByModule(module.getModuleId());
        for(Interface face : faces){
          List<InterfaceTestCase> testCases = testCaseService.findInterfaceTestCaseByFace(face.getInterfaceId());
          packageCaseData(data, face, testCases);
        }
      }
    }
    
    download(fileType, response, data);
    // 4.定时任务删除文件以及
//    return ResponseResult.success();
  }


  public List<HashMap<String, String>> packageCaseData(List<HashMap<String, String>> data,
      Interface face, List<InterfaceTestCase> testCases) {
    
    for(InterfaceTestCase testCase : testCases){
      HashMap<String, String> entry = new HashMap<String, String>();
      entry.put(ExcelUtils.COLUMN_NAME_01, testCase.getTestCaseName());
      entry.put(ExcelUtils.COLUMN_NAME_02, face.getInterfaceUrl());
      entry.put(ExcelUtils.COLUMN_NAME_03, face.getInterfaceType());
      entry.put(ExcelUtils.COLUMN_NAME_04, testCase.getParamCase());
      entry.put(ExcelUtils.COLUMN_NAME_05, testCase.getExpectResult());
      entry.put(ExcelUtils.COLUMN_NAME_06, String.valueOf(testCase.getExpectStatus()));
      entry.put(ExcelUtils.COLUMN_NAME_07, Constant.RUNNING == testCase.getIsRun() ? "N" : "Y");

      data.add(entry);
    }
    return data;
  }


  public void download(String fileType, HttpServletResponse response, List<HashMap<String, String>> data) {
    // 2.将数据生成excel文件保存在服务器上
    String filePath = null;
    if(Constant.DOWNLOAD_FILE_TYPE_EXCEL.equals(fileType)){
      filePath = ExcelUtils.createExcel(data);
    }else if(Constant.DOWNLOAD_FILE_TYPE_JSON.equals(fileType)){
      filePath = JsonUtils.createJson(data);
    }
    // 3.将服务器文件下载到本地
    if (null != filePath) {
      IOUtils.downloadFile(response, filePath);
    }
  }



  public void dealRowData(Map<String, String> row) {

    Interface face = faceDataToSave(row);

    testCaseDataToSave(row, face);
  }

  public Interface faceDataToSave(Map<String, String> row) {
    Interface face = new Interface();

    face.setInterfaceName(row.get("column_1"));
    face.setInterfaceType(row.get("column_2"));
    face.setInterfaceUrl(row.get("column_3"));
    face.setIsRun((null != row.get("column_7") && "Y".equals(row.get("column_7"))) ? 1 : 0);
    face.setModuleId(Long.valueOf(1));
    face.setRequestParam(row.get("column_4"));
    face.setResponseResult(row.get("column_5"));
    face.setCreateTime(new Date());

    face = interfaceService.saveInterface(face);
    return face;
  }


  public void testCaseDataToSave(Map<String, String> row, Interface face) {
    InterfaceTestCase testCase = new InterfaceTestCase();
    testCase.setExpectResult(row.get("column_5"));
    testCase.setExpectStatus(Integer.valueOf(row.get("column_6")));
    testCase.setInterfaceId(face.getInterfaceId());
    testCase.setIsRun((null != row.get("column_7") && "Y".equals(row.get("column_7"))) ? 1 : 0);
    testCase.setParamCase(row.get("column_4"));
    testCase.setCreateTime(new Date());

    testCaseService.saveInterfaceCase(testCase);
  }
}
