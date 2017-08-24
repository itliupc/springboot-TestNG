package com.wafer.wtp.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

/**
 * 读取Excel数据工具类
 * 
 * @author wafer
 */
public class ExcelUtil extends FileReader {

  private static HSSFWorkbook workBook;

  /**
   * 读取Excel数据
   * 
   * @param resource
   * @param method
   * @param sheetName
   * @return
   */
  @Override
  protected List<Object[]> getRunDataObject(URL resource, Method method, String sheetName) {
    List<Class<?>> paramTypes = new ArrayList<Class<?>>();
    for (Parameter parameter : method.getParameters()) {
      paramTypes.add(parameter.getType());
    }

    List<Object[]> results = new ArrayList<Object[]>();
    try {
      File file = new File(resource.getPath());
      FileInputStream fis = new FileInputStream(file);

      POIFSFileSystem poiStream = new POIFSFileSystem(fis);
      workBook = new HSSFWorkbook(poiStream);

      List<HSSFSheet> sheetList = new ArrayList<HSSFSheet>();
      if (null != sheetName && !sheetName.isEmpty()) {
        sheetList.add(workBook.getSheet(sheetName));
      } else {
        for (int i = 0; i < workBook.getNumberOfSheets(); i++) {
          sheetList.add(workBook.getSheetAt(i));
        }
      }

      for (HSSFSheet sheet : sheetList) {
        HSSFRow headRow = sheet.getRow(0);
        int rowSize = sheet.getLastRowNum();
        int colSize = headRow.getLastCellNum();
        int objectSize = paramTypes.size();

        for (int rowIndex = 1; rowIndex <= rowSize; rowIndex++) {
          HSSFRow row = sheet.getRow(rowIndex);
          if (null == row) {
            continue;
          }
          HSSFCell firstCell = row.getCell(0);
          if (null == firstCell || null == firstCell.getStringCellValue()
              || firstCell.getStringCellValue().isEmpty()) {
            continue;
          }

          HSSFCell lastcell = row.getCell(colSize - 1);
          if (null != lastcell && lastcell.getStringCellValue().equalsIgnoreCase("y")) {
            continue;
          }
          String[] objects = new String[objectSize];

          String currCellString = null;

          for (int columnIndex = 0; columnIndex < colSize - 1; columnIndex++) {
            currCellString = getValueByColunmIndex(row, columnIndex);
            objects[columnIndex] = currCellString;
          }
          results.add(convertParam(objects, paramTypes));
        }
        poiStream.close();
        fis.close();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return results;
  }


  /**
   * 获取Cell数据
   * 
   * @param row
   * @param cIndex
   * @return
   */
  private String getValueByColunmIndex(HSSFRow row, int cIndex) {
    String value = "";
    HSSFCell cell = row.getCell(cIndex);
    if (cell != null) {
      switch (cell.getCellType()) {
        case HSSFCell.CELL_TYPE_STRING:
          value = cell.getStringCellValue();
          break;
        case HSSFCell.CELL_TYPE_NUMERIC:
          if (HSSFDateUtil.isCellDateFormatted(cell)) {
            Date date = cell.getDateCellValue();
            if (date != null) {
              value = new SimpleDateFormat("yyyy-MM-dd").format(date);
            } else {
              value = "";
            }
          } else {
            value = new DecimalFormat("0").format(cell.getNumericCellValue());
          }
          break;
        case HSSFCell.CELL_TYPE_FORMULA:
          if (!cell.getStringCellValue().equals("")) {
            value = cell.getStringCellValue();
          } else {
            value = cell.getNumericCellValue() + "";
          }
          break;

        case HSSFCell.CELL_TYPE_BLANK:
          break;
        case HSSFCell.CELL_TYPE_ERROR:
          value = "";
          break;
        case HSSFCell.CELL_TYPE_BOOLEAN:
          value = (cell.getBooleanCellValue() == true ? "Y" : "N");
          break;
        default:
          value = "default";
      }
    }
    return value;
  }
}
