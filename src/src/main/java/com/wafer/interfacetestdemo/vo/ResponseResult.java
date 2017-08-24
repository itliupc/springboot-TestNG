package com.wafer.interfacetestdemo.vo;

public class ResponseResult {

  private boolean result;

  private String message;

  private Object data;

  private ResponseResult(boolean result) {
    this.result = result;
  }

  private ResponseResult(boolean result, String message) {
    this.result = result;
    this.message = message;
  }

  private ResponseResult(boolean result, Object data) {
    this.result = result;
    this.data = data;
  }

  private ResponseResult(boolean result, String message, Object data) {
    this.result = result;
    this.message = message;
    this.data = data;
  }

  public static ResponseResult success() {
    return new ResponseResult(true);
  }

  public static ResponseResult success(Object data) {
    return new ResponseResult(true, data);
  }

  public static ResponseResult success(String message, Object data) {
    return new ResponseResult(true, message, data);
  }


  public static ResponseResult failure() {
    return new ResponseResult(false);
  }

  public static ResponseResult failure(String message) {
    return new ResponseResult(false, message);
  }

  public static ResponseResult failure(String message, Object data) {
    return new ResponseResult(false, message, data);
  }



  public boolean isResult() {
    return result;
  }

  public void setResult(boolean result) {
    this.result = result;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Object getData() {
    return data;
  }

  public void setData(Object data) {
    this.data = data;
  }



}
