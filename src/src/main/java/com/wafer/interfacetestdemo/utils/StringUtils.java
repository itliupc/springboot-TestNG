package com.wafer.interfacetestdemo.utils;

import java.util.Map;
import java.util.Set;

public class StringUtils {

  /**
   * 将Map转成JSON形式的String
   * 
   * @param map
   * @return
   */
  public static String transformMapToString(Map<String, Object> map) {

    Set<String> keys = map.keySet();
    StringBuilder sb = new StringBuilder();
    sb.append("{");
    for (String key : keys) {
      Object value = map.get(key);
      sb.append("\"").append(key).append("\":\"").append(value).append("\",");
    }
    String result = sb.substring(0, sb.toString().length() - 1) + "}";
    return result;
  }
  
}
