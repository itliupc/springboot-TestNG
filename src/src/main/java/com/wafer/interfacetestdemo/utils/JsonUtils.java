package com.wafer.interfacetestdemo.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONArray;

public class JsonUtils {

  static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);

  private static final String DEFAULT_EXPORT_FILE_NAME = "TestAPI.xls";
  
  private static final String EXPORT_FILE_NAME_JSON = "TestAPI.json";
  
  public static final String DOWNLOAD_FILE_PATH = "/download/";
  
  /**
   * 创建JSON格式的文件
   * @param data
   * @return
   */
  public static String createJson(List<HashMap<String, String>> data) {

    JSONArray json = JSONArray.fromObject(data);
    // 获取文件的路径
    String filePath = getFilePath(EXPORT_FILE_NAME_JSON);
    logger.debug("Export Excel and filePath = {}.", filePath);

    if (!writeData(json, filePath)) {
      return null;
    }
    return filePath;
  }

  public static boolean writeData(JSONArray json, String filePath) {
    FileOutputStream fileOut = null;
    try {
      fileOut = new FileOutputStream(filePath);
      fileOut.write(json.toString().getBytes());
    } catch (FileNotFoundException e) {
      logger.error(e.getMessage(), e);
      return false;
    } catch (IOException e) {
      logger.error(e.getMessage(), e);
      return false;
    }finally {
      try {
        fileOut.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return true;
  }


  public static String getFilePath(String... name) {
    String fileName = DEFAULT_EXPORT_FILE_NAME;
    if (null != name && name.length > 0) {
      fileName = name[0];
    }
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
    String dateTime = dateFormat.format(new Date());
    String path = DOWNLOAD_FILE_PATH + dateTime + "/" + UUID.randomUUID().toString().replaceAll("-", "") + "/";
    File file = new File(path);
    if (!file.exists()) {
      file.mkdirs();
    }
    String filePath = path + fileName;
    return filePath;
  }

}
