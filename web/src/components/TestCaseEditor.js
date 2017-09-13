import React from "react";
import {Form, Input, InputNumber, Select, Button, message, Switch} from "antd";
import {routerRedux} from "dva/router";
import {FormattedMessage, injectIntl} from 'react-intl';
import {format} from '../utils/JSONFormat';

const FormItem = Form.Item;
const {TextArea} = Input;
const formLayout = {
  labelCol: {
    span: 5
  },
  wrapperCol: {
    span: 16
  }
};

export default injectIntl(({form, intl, dispatch, currentTestCase, interfaceId}) => {
  const {getFieldDecorator, validateFields} = form;
  const {interfaceTestCaseId, testCaseName, paramCase, expectResult,expectStatus,run} = currentTestCase;
  const type = interfaceTestCaseId ? "update" : "add";

  const submitHandle = (e) => {
    e.preventDefault();
    validateFields((err, values) => {
      if (!err) {
        if("update" == type){
          dispatch({type: "interfaces/editTestCase", testCase: {...currentTestCase, ...values}});
        }else{
          dispatch({type: "interfaces/addTestCase", testCase: {...values, ...{interfaceId:interfaceId}}});
        }
        dispatch({type:"interfaces/cancelDialog"});
      } else {
        // message.warn(JSON.stringify(err));
      }
    });
  };

  return (
    <div style={{width: "100%"}}>
      <Form onSubmit={submitHandle}>
        <FormItem label={intl.formatMessage({id: "testCase.testCaseName"}) + ":"} {...formLayout}>
          {getFieldDecorator("testCaseName", {
            rules: [
              {
                required: true,
                message: intl.formatMessage({id: "testCase.testCaseName.emptyTestCase"})
              }
            ],
            initialValue: testCaseName
          })(
            <Input type="text"/>
          )}
        </FormItem>

        {/*<FormItem label={intl.formatMessage({id: "testCase.paramCase"}) + ":"} {...formLayout}>*/}
          {/*{getFieldDecorator("paramCase", {*/}
            {/*initialValue: paramCase?format(paramCase, false):""*/}
          {/*})(*/}
            {/*<TextArea rows={4} autosize={true}/>*/}
          {/*)}*/}
        {/*</FormItem>*/}

        {/*<FormItem label={intl.formatMessage({id: "testCase.expectResult"}) + ":"} {...formLayout}>*/}
          {/*{getFieldDecorator("expectResult", {*/}
            {/*initialValue: expectResult?format(expectResult, false):""*/}
          {/*})(*/}
            {/*<TextArea rows={4} autosize={true} minRows={4}/>*/}
          {/*)}*/}
        {/*</FormItem>*/}

        <FormItem label={intl.formatMessage({id: "testCase.expectStatus"}) + ":"} {...formLayout}>
          {getFieldDecorator("expectStatus", {
            initialValue: expectStatus
          })(
            <Select style={{ width: 120 }} >
              <Select.OptGroup label="1xx">
                <Select.Option value="100">100</Select.Option>
                <Select.Option value="101">101</Select.Option>
              </Select.OptGroup>
              <Select.OptGroup label="2xx">
                <Select.Option value="200">200</Select.Option>
                <Select.Option value="201">201</Select.Option>
                <Select.Option value="202">202</Select.Option>
                <Select.Option value="203">203</Select.Option>
                <Select.Option value="204">204</Select.Option>
                <Select.Option value="205">205</Select.Option>
                <Select.Option value="206">206</Select.Option>
              </Select.OptGroup>
              <Select.OptGroup label="3xx">
                <Select.Option value="300">300</Select.Option>
                <Select.Option value="301">301</Select.Option>
                <Select.Option value="302">302</Select.Option>
                <Select.Option value="303">303</Select.Option>
                <Select.Option value="304">304</Select.Option>
                <Select.Option value="305">305</Select.Option>
                <Select.Option value="307">307</Select.Option>
              </Select.OptGroup>
              <Select.OptGroup label="4xx">
                <Select.Option value="400">400</Select.Option>
                <Select.Option value="401">401</Select.Option>
                <Select.Option value="403">403</Select.Option>
                <Select.Option value="404">404</Select.Option>
                <Select.Option value="405">405</Select.Option>
                <Select.Option value="406">406</Select.Option>
                <Select.Option value="407">407</Select.Option>
                <Select.Option value="408">408</Select.Option>
                <Select.Option value="409">409</Select.Option>
                <Select.Option value="410">410</Select.Option>
                <Select.Option value="411">411</Select.Option>
                <Select.Option value="412">412</Select.Option>
                <Select.Option value="413">413</Select.Option>
                <Select.Option value="414">414</Select.Option>
                <Select.Option value="415">415</Select.Option>
                <Select.Option value="416">416</Select.Option>
                <Select.Option value="417">417</Select.Option>
                <Select.Option value="423">423</Select.Option>
              </Select.OptGroup>
              <Select.OptGroup label="5xx">
                <Select.Option value="500">500</Select.Option>
                <Select.Option value="501">501</Select.Option>
                <Select.Option value="502">502</Select.Option>
                <Select.Option value="503">503</Select.Option>
                <Select.Option value="504">504</Select.Option>
                <Select.Option value="505">505</Select.Option>
              </Select.OptGroup>
            </Select>
          )}
        </FormItem>

        <FormItem
          {...formLayout}
          label={intl.formatMessage({id: "testCase.running"}) + ":"}
        >
          {getFieldDecorator('run', {
            valuePropName: 'checked',
            initialValue : (undefined != run? run: true)
          })(
            <Switch/>
          )}
        </FormItem>

        <br/>
        <FormItem wrapperCol={{...formLayout.wrapperCol, offset: 19}}>
          <Button type="primary" htmlType="submit"><FormattedMessage id="module.save"/></Button>
        </FormItem>
      </Form>
    </div>
  );
});
