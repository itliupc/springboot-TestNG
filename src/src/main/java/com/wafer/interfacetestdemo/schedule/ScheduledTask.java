package com.wafer.interfacetestdemo.schedule;

import java.io.File;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.wafer.interfacetestdemo.utils.DateUtils;
import com.wafer.interfacetestdemo.utils.ExcelUtils;

@Configuration
@EnableScheduling
public class ScheduledTask {

  Logger logger = LoggerFactory.getLogger(ScheduledTask.class);

  public static final long DAY_TIME_OF_7 = 7 * 24 * 60 * 60 * 1000;

  /**
   * 每天删除上一天产生的所有文件
   */
  @Scheduled(cron = "0 0 0 0/1 * ?")
  public void scheduledJobTest() {
    logger.debug("时间到了，{}", new Date());
    String filePath = ExcelUtils.DOWNLOAD_FILE_PATH;
    File file = new File(filePath);
    File[] files = file.listFiles();
    for (File fi : files) {
      if (fi.isDirectory()) {
        String fileName = fi.getName();
        Date fileDate = DateUtils.formatToDate(fileName, "yyyyMMdd");
        if (null != fileDate && fileDate.getTime() > new Date().getTime() - DAY_TIME_OF_7) {
          deleteFile(fi);
        }
      }
    }
  }

  /**
   * 递归删除文件夹，已经文件夹内所有内容
   * 
   * @param file
   */
  public void deleteFile(File file) {
    if (file.isFile()) {
      file.delete();
    } else if (file.isDirectory()) {
      File[] files = file.listFiles();
      for (File fi : files) {
        deleteFile(fi);
      }
      file.delete();
    }
  }
}
