package com.wafer.interfacetestdemo.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExcelImportUtil {

  final static Logger logger = LoggerFactory.getLogger(ExcelImportUtil.class);

  /**
   * 读取Excel中数据
   * @param fis
   * @return
   */
  public static List<Map<String, String>> parseExcel(InputStream fis) {

    List<Map<String, String>> data = new ArrayList<Map<String, String>>();
    HSSFWorkbook book = null;

    try {
      book = new HSSFWorkbook(fis);
      HSSFSheet sheet = book.getSheetAt(0);
      int firstRow = sheet.getFirstRowNum();
      int lastRow = sheet.getLastRowNum();

      // 除去表头和第一行
      for (int i = firstRow + 1; i < lastRow; i++) {
        Map<String, String> map = new HashMap<>();
        HSSFRow row = sheet.getRow(i);

        int firstCell = row.getFirstCellNum();
        int lastCell = row.getLastCellNum();

        for (int j = firstCell; j < lastCell; j++) {
          
          HSSFCell cell2 = sheet.getRow(firstRow + 1).getCell(j);
          if (cell2.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
            cell2.setCellType(HSSFCell.CELL_TYPE_STRING);
          }
          
          String key = cell2.getStringCellValue();

          HSSFCell cell = row.getCell(j);
          if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
          }

          String val = cell.getStringCellValue();
          map.put(key, val);
        }
        data.add(map);
      }
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    } finally {
      try {
        book.close();
      } catch (IOException e) {
        logger.error(e.getMessage(), e);
      }
    }
    return data;
  }

}
