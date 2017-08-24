package com.wafer.wtp.util;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.Resource;

import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class FileReader {

  /**
   * 读取JSON数据
   * 
   * @param resource
   * @param method
   * @param methodName
   * @return
   */
  protected abstract List<Object[]> getRunDataObject(URL resource, Method method,
      String methodName);

  /**
   * 转换参数类型
   * 
   * @param cellStrings
   * @param classes
   * @return
   */
  protected Object[] convertParam(String[] cellStrings, List<Class<?>> classes) {
    Object[] result = new Object[cellStrings.length];
    ObjectMapper objectMapper = new ObjectMapper();
    for (int i = 0; i < cellStrings.length; i++) {
      String cellString = cellStrings[i];
      Class<?> classType = classes.get(i);
      if (null == cellString || cellString.isEmpty()) {
        result[i] = null;
      } else if (classType == String.class) {
        result[i] = cellString;
      } else {
        try {
          result[i] = objectMapper.readValue(cellString, classType);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }

    return result;
  }

  private static FileReader buildFactory(Class<?> classes) {
    FileReader fileReader = null;
    if (classes == JSONUtil.class) {
      fileReader = new JSONUtil();
    } else if (classes == ExcelUtil.class) {
      fileReader = new ExcelUtil();
    }
    return fileReader;
  }

  /**
   * 读取单个文件
   * 
   * @param resource
   * @param method
   * @param methodName
   * @return
   * @throws IOException
   */
  public static Object[][] readDataObject(URL resource, Method method, String methodName)
      throws IOException {
    List<URL> urls = new ArrayList<URL>();
    urls.add(resource);
    return readDataByURL(urls, method, methodName);
  }

  /**
   * 读取多个文件
   * 
   * @param resources
   * @param method
   * @param methodName
   * @return
   * @throws IOException
   */
  public static Object[][] readAllDataObject(Resource[] resources, Method method, String methodName)
      throws IOException {
    List<URL> urls = new ArrayList<URL>();
    for (Resource resource : resources) {
      urls.add(resource.getURL());
    }
    return readDataByURL(urls, method, methodName);
  }

  /**
   * 读取所有文件数据
   * 
   * @param urls
   * @param method
   * @param methodName
   * @return
   */
  public static Object[][] readDataByURL(List<URL> urls, Method method, String methodName) {
    FileReader fileReader = null;
    if (urls.get(0).getFile().toUpperCase().endsWith("JSON")) {
      fileReader = FileReader.buildFactory(JSONUtil.class);
    } else if (urls.get(0).getFile().toUpperCase().endsWith("XLS")) {
      fileReader = FileReader.buildFactory(ExcelUtil.class);
    }
    List<Object[]> results = new ArrayList<Object[]>();

    if (null != fileReader) {
      for (URL url : urls) {
        results.addAll(fileReader.getRunDataObject(url, method, methodName));
      }
    }

    Object[][] returnArray = new Object[results.size()][];
    for (int i = 0; i < returnArray.length; i++) {
      returnArray[i] = results.get(i);
    }
    return returnArray;
  }
}
