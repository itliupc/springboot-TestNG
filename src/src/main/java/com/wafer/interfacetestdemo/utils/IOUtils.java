package com.wafer.interfacetestdemo.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

public class IOUtils {

  /**
   * 下载文件
   * @param response
   * @param filePath
   */
  public static void downloadFile(HttpServletResponse response, String filePath) {
   
    FileInputStream fis = null;
    OutputStream os = null;
    try {
      
      File file = new File(filePath);
      if(!file.exists()){
        throw new FileNotFoundException();
      }
      fis = new FileInputStream(file);
      
      byte[] buffer = new byte[fis.available()];
      fis.read(buffer);
      fis.close();
      
      // 清空response
      response.reset();
      
      response.addHeader("Content-Length", "" + file.length());
      response.addHeader("Content-Type", "application/json;charset=utf-8");
      
      os = new BufferedOutputStream(response.getOutputStream());
      os.write(buffer);
      os.flush();
      os.close();
    } catch (IOException e) {
      e.printStackTrace();
    }finally {
      if(null != fis){
        try {
          fis.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      if(null != os){
        try {
          os.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }
}
