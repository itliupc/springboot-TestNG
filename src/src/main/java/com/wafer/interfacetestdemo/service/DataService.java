package com.wafer.interfacetestdemo.service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wafer.interfacetestdemo.repository.DeptRepository;

import net.sf.json.JSONObject;

@Service
public class DataService {

  @Autowired
  private DeptRepository deptRepository;

  private static final Pattern pattern = Pattern.compile("\\{(.*?)\\}");
  private static Matcher matcher;

  @SuppressWarnings("unchecked")
  public List<Object[]> getTestCaseData(long projectId) {
    StringBuffer sql = new StringBuffer();
    sql.append(
        "SELECT IFNULL(pit.test_case_name,'') caseName, ")
        .append("       IFNULL(pin.interface_url,'') url, ")
        .append("       IFNULL(pin.interface_type,'') type, ")
        .append("       IFNULL(pit.param_case,'') paramters, ")
        .append("       IFNULL(pit.expect_result,'') expectResult, ")
        .append("       IFNULL(pit.expect_status,'') expectStatus ")
        .append("   FROM ps_interface_testcase pit ").append("   LEFT JOIN ps_interface pin ")
        .append("       ON pit.interface_id = pin.interface_id").append("   LEFT JOIN ps_module pm")
        .append("       ON pin.module_id = pm.module_id").append("   LEFT JOIN ps_project pjt")
        .append("       ON pm.project_id = pjt.project_id")
        .append("   WHERE pit.is_run = 0 AND pin.is_run = 0 AND pm.is_run = 0 ")
        .append("       AND pjt.project_id = ").append(projectId)
        .append("   ORDER BY pm.module_id,pin.interface_id ASC");
    List<Object[]> list = deptRepository.createQuery(sql.toString()).getResultList();
    return this.formatUrl(list);
  }

  @SuppressWarnings("unchecked")
  public List<Object[]> getSimpleTestCaseData(long projectId, String moduleName,
      String interfaceName) {
    StringBuffer sql = new StringBuffer();
    sql.append(
        "SELECT IFNULL(pit.test_case_name,'') caseName, ")
        .append("       IFNULL(pin.interface_url,'') url, ")
        .append("       IFNULL(pin.interface_type,'') type, ")
        .append("       IFNULL(pit.param_case,'') paramters, ")
        .append("       IFNULL(pit.expect_result,'') expectResult, ")
        .append("       IFNULL(pit.expect_status,'') expectStatus ")
        .append("   FROM ps_interface_testcase pit ").append("   LEFT JOIN ps_interface pin ")
        .append("       ON pit.interface_id = pin.interface_id").append("   LEFT JOIN ps_module pm")
        .append("       ON pin.module_id = pm.module_id").append("   LEFT JOIN ps_project pjt")
        .append("       ON pm.project_id = pjt.project_id")
        .append("   WHERE pit.is_run = 0 AND pin.is_run = 0 AND pm.is_run = 0 ")
        .append("       AND pjt.project_id = ").append(projectId)
        .append("       AND pm.module_name = '").append(moduleName).append("' ")
        .append("       AND pin.interface_name = '").append(interfaceName).append("' ")
        .append("   ORDER BY pm.module_id,pin.interface_id ASC");
    List<Object[]> list = deptRepository.createQuery(sql.toString()).getResultList();
    return this.formatUrl(list);
  }

  /**
   * 格式化Url中的PathVariable
   * @param list
   * @return
   */
  public List<Object[]> formatUrl(List<Object[]> list) {
    for (Object[] objects : list) {
      Object url = objects[1];// url
      Object param = objects[3];// paramters
      if (null == url || null == param) {
        continue;
      }

      String tagetStr = String.valueOf(url).trim();
      String paramStr = String.valueOf(param).trim();
      if (paramStr.isEmpty() || !paramStr.startsWith("{") || !paramStr.endsWith("}")) {
        continue;
      }

      try {
        JSONObject paramObj = JSONObject.fromObject(paramStr);
        matcher = pattern.matcher(tagetStr);
        while (matcher.find()) {
          String key = matcher.group();
          String keyclone = key.substring(1, key.length() - 1).trim();
          if (paramObj.has(keyclone)) {
            Object value = paramObj.get(keyclone);
            tagetStr = tagetStr.replace(key, value.toString());
            paramObj.remove(keyclone);
          }
        }
        objects[1] = tagetStr;
        objects[3] = paramObj.isEmpty() ? "" : paramObj.toString();
      } catch (Exception e) {
        continue;
      }
    }

    return list;
  }
}
