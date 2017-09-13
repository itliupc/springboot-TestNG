package com.wafer.interfacetestdemo.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExcelUtils {

  static final Logger logger = LoggerFactory.getLogger(ExcelUtils.class);

  private static final String DEFAULT_EXPORT_SHEET_NAME = "InterfaceTestCase.xls";

  private static final String DEFAULT_EXPORT_FILE_NAME = "TestAPI.xls";
  
  public static final String DOWNLOAD_FILE_PATH = "../download/";
  
  public static String COLUMN_NAME_01 = "caseName";
  public static String COLUMN_NAME_02 = "url";
  public static String COLUMN_NAME_03 = "type";
  public static String COLUMN_NAME_04 = "paramters";
  public static String COLUMN_NAME_05 = "expectResult";
  public static String COLUMN_NAME_06 = "expectStatus";
  public static String COLUMN_NAME_07 = "isSkip";

  public static List<List<Map<String, String>>> parseExcel(InputStream is, String fileName)
      throws IOException {

    List<List<Map<String, String>>> dataValue = new ArrayList<>();

    try (Workbook workbook = getWorkBook(is, fileName)) {
      for (int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum++) {
        Sheet sheet = workbook.getSheetAt(sheetNum);
        int firstRow = sheet.getFirstRowNum();
        int lastRow = sheet.getLastRowNum();

        // 获取第一列表头信息
        // Row headRow = sheet.getRow(0);
        // List<String> columnHead = new ArrayList<>();
        // for(int hr = headRow.getFirstCellNum(); hr < headRow.getLastCellNum(); hr++){
        // Cell cell = headRow.getCell(hr);
        // String value = getCellValue(cell);
        // columnHead.add(value);
        // }

        List<Map<String, String>> rowValue = new ArrayList<>();
        for (int i = firstRow + 1; i < lastRow; i++) {
          Row row = sheet.getRow(i);
          Map<String, String> cellValue = new HashMap<>();
          // 一行数据
          for (int j = row.getFirstCellNum(); j < row.getLastCellNum(); j++) {
            Cell cell = row.getCell(j);
            String value = getCellValue(cell);
            cellValue.put("column_" + j, value);
          }

          rowValue.add(cellValue);
        }

        dataValue.add(rowValue);
      }
    }
    return dataValue;
  }

  /**
   * 导出TestCase 到Excel
   * @param face 
   * @param testCaseViews 
   * 
   * @return
   */
  public static String createExcel(List<HashMap<String, String>> data) {

    Workbook work = new HSSFWorkbook();
    Sheet sheet = work.createSheet(DEFAULT_EXPORT_SHEET_NAME);
    sheet.setDefaultColumnWidth(16);
    // 创建表头信息
    createTitle(work, sheet);
    // 创建excel的内容信息
    createExcelContent(sheet, data);
    // 获取文件的路径
    String filePath = getFilePath();
    logger.debug("Export Excel and filePath = {}.", filePath);

    if (!writeData(work, filePath)) {
      return null;
    }
    return filePath;
  }
  
  private static String getCellValue(Cell cell) {
    String cellValue = "";
    if (null == cell) {
      return cellValue;
    }
    // 将数字当作string读取
    if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
      cell.setCellType(Cell.CELL_TYPE_STRING);
    }
    // 判断数据类型
    switch (cell.getCellType()) {
      case Cell.CELL_TYPE_BOOLEAN:
        cellValue = String.valueOf(cell.getBooleanCellValue());
        break;
      case Cell.CELL_TYPE_STRING:
        cellValue = cell.getStringCellValue();
        break;
      case Cell.CELL_TYPE_NUMERIC:
        cellValue = String.valueOf(cell.getNumericCellValue());
        break;
      case Cell.CELL_TYPE_FORMULA: // 公式
        cellValue = String.valueOf(cell.getCellFormula());
        break;
      default:
        break;
    }
    return cellValue;
  }


  private static Workbook getWorkBook(InputStream is, String fileName) {
    Workbook workbood = null;

    try {
      if (fileName.endsWith(".xlsx")) {
        workbood = new XSSFWorkbook(is);
      } else if (fileName.endsWith(".xls")) {
        workbood = new HSSFWorkbook(is);
      }
    } catch (IOException e) {

    }
    return workbood;
  }

  public static void createTitle(Workbook work, Sheet sheet) {
    // 设置单元格居中加粗
    CellStyle style = work.createCellStyle();
    Font font = work.createFont();
    font.setBold(true);
    style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    style.setFont(font);

    // 创建表头
    Row titleRow = sheet.createRow(0);
    Cell cell = null;
    cell = titleRow.createCell(0);
    cell.setCellValue(COLUMN_NAME_01);
    cell.setCellStyle(style);

    cell = titleRow.createCell(1);
    cell.setCellValue(COLUMN_NAME_02);
    cell.setCellStyle(style);

    cell = titleRow.createCell(2);
    cell.setCellValue(COLUMN_NAME_03);
    cell.setCellStyle(style);

    cell = titleRow.createCell(3);
    cell.setCellValue(COLUMN_NAME_04);
    cell.setCellStyle(style);

    cell = titleRow.createCell(4);
    cell.setCellValue(COLUMN_NAME_05);
    cell.setCellStyle(style);

    cell = titleRow.createCell(5);
    cell.setCellValue(COLUMN_NAME_06);
    cell.setCellStyle(style);

    cell = titleRow.createCell(6);
    cell.setCellValue(COLUMN_NAME_07);
    cell.setCellStyle(style);

    /*cell = titleRow.createCell(7);
    cell.setCellValue("是否运行");
    cell.setCellStyle(style);*/
  }

  public static void createExcelContent(Sheet sheet, List<HashMap<String, String>> data) {
    int i = 1;
    for(HashMap<String, String> entry : data){
      Row row = sheet.createRow(i);

      row.createCell(0).setCellValue(entry.get(COLUMN_NAME_01));
      row.createCell(1).setCellValue(entry.get(COLUMN_NAME_02));
      row.createCell(2).setCellValue(entry.get(COLUMN_NAME_03));
      row.createCell(3).setCellValue(entry.get(COLUMN_NAME_04));
      row.createCell(4).setCellValue(entry.get(COLUMN_NAME_05));
      row.createCell(5).setCellValue(entry.get(COLUMN_NAME_06));
      row.createCell(6).setCellValue(entry.get(COLUMN_NAME_07));
      
      i++;
    }
  }


  public static boolean writeData(Workbook work, String filePath) {
    FileOutputStream fileOut = null;
    try {
      fileOut = new FileOutputStream(filePath);
      work.write(fileOut);
    } catch (FileNotFoundException e) {
      logger.error(e.getMessage(), e);
      return false;
    } catch (IOException e) {
      logger.error(e.getMessage(), e);
      return false;
    }finally {
      try {
        work.close();
      } catch (IOException e) {
        logger.error(e.getMessage(), e);
      }
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
    String dateTime = DateUtils.formatDateTime(new Date(), "yyyyMMdd");
    String path = DOWNLOAD_FILE_PATH + dateTime + "/" + UUID.randomUUID().toString().replaceAll("-", "") + "/";
    File file = new File(path);
    if (!file.exists()) {
      file.mkdirs();
    }
    String filePath = path + fileName;
    return filePath;
  }

}
