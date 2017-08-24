package com.wafer.wtp.base;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wafer.interfacetestdemo.InterfaceTestDemo;
import com.wafer.wtp.annotation.Field;

/**
 * 接口测试公共类
 * 
 * @author wafer
 */
@Transactional
@SpringBootTest(classes = InterfaceTestDemo.class)
public class TestAPI extends AbstractTestNGTest {

  /**
   * 根据Excel中提供的接口数据测试接口
   * 
   * @param caseName
   * @param url
   * @param type
   * @param paramters
   * @param expectResult
   * @param expectStatus
   * @throws JsonProcessingException
   * @throws Exception
   */
  @Test(dataProvider = DP_API_DATA)
  public void testAPI(@Field("caseName") String caseName, @Field("url") String url,
      @Field("type") String type, @Field("paramters") String paramters,
      @Field("expectResult") String expectResult, @Field("expectStatus") String expectStatus)
      throws JsonProcessingException, Exception {
    // 获取requestBuilder对象
    MockHttpServletRequestBuilder requestBuilder = getRequestBuilder(url, type);
    // 添加请求参数
    requestBuilder = addParamters(requestBuilder, paramters);

    ResultActions resultActions = getMockMvc().perform(requestBuilder);

    resultActions.andDo(MockMvcResultHandlers.print());

    // 校验请求状态
    if (null != expectStatus && !expectStatus.isEmpty()) {
      resultActions.andExpect(status().is(Integer.parseInt(expectStatus)));
    }
    // 校验请求结果
    if (null != expectResult && !expectResult.isEmpty()) {
      String responseBody = resultActions.andReturn().getResponse().getContentAsString();
      // 日志数据返回结果
      Reporter.log(caseName + " : " + responseBody);
      JSONAssert.assertEquals(expectResult, responseBody, false);
    }
  }
}
