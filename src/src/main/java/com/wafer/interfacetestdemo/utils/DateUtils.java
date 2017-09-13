package com.wafer.interfacetestdemo.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

  /**
   * date 默认显示形式
   */
  public static final String DEFAULT_DATE_FORMAT = "YYYY-MM-dd HH:mm:ss"; 
  /**
   * 将Date类型的数据转成默认格式的date String形式 方便显示
   * @see YYYY-MM-dd HH:mm:ss
   * @param dateTime
   * @return
   */
  public static String formatDateTime(Date dateTime){
    return formatDateTime(dateTime, DEFAULT_DATE_FORMAT);
  }
  
  /**
   * 将Date转成制定形式的格式
   * @param dateTime
   * @param format 格式类型
   * @return
   */
  public static String formatDateTime(Date dateTime, String format){
    if(null == dateTime){
      return null;
    }
    SimpleDateFormat sf = new SimpleDateFormat(format);
    return sf.format(dateTime);
  }
  
  /**
   * 将String 格式转成Date类型
   * @param dateTime
   * @param format 格式类型
   * @return
   */
  public static Date formatToDate(String dateString, String format){
    if(null == dateString){
      return null;
    }
    SimpleDateFormat sf = new SimpleDateFormat(format);
    try {
      return sf.parse(dateString);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return null;
  }
}
