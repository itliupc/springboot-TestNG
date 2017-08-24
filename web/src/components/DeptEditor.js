/**
 * Created by admin on 2017/5/18.
 */
import React from "react";
import {Input, Form, Button, DatePicker, Select, message} from "antd";
import moment from 'moment'
import {FormattedMessage, injectIntl} from 'react-intl';

const FormItem = Form.Item;
const formLayout = {
  labelCol: {
    span: 7
  },
  wrapperCol: {
    span: 16
  }
};

export default injectIntl(({form, intl, deptInfo = {}, type, dispatch}) => {
  const {deptName, deptCode} = deptInfo;
  const {getFieldDecorator, validateFields} = form;

  const submitHandle = (e) => {
    e.preventDefault();
    validateFields((err, values) => {
      if (!err) {
        if(type === "add"){
          dispatch({type: "depts/add", payload: values});
        }else {
          dispatch({type: "depts/edit", payload: {...deptInfo, ...values}});
        }
        dispatch({type: 'reload'});
      } else {
        message.warn(JSON.stringify(err));
      }
    });
  };

  return (
    <Form onSubmit={submitHandle} style={{width: "400px"}}>
      <FormItem label={intl.formatMessage({id: "dept.deptName"}) + ":"} {...formLayout}>
        {getFieldDecorator("deptName", {
          rules: [
            {
              required: true,
              message: intl.formatMessage({id: "dept.warn.emptyDeptName"})
            }
          ],
          initialValue: deptName
        })(<Input type="text"/>)}
      </FormItem>
      <FormItem label={intl.formatMessage({id: "dept.deptCode"}) + ":"} {...formLayout}>
        {getFieldDecorator("deptCode", {
          rules: [
            {
              required: true,
              message: intl.formatMessage({id: "dept.warn.emptyDeptCode"})
            }
          ],
          initialValue: deptCode
        })(<Input type="text"/>)}
      </FormItem>
      <FormItem wrapperCol={{...formLayout.wrapperCol, offset: 21}}>
        <Button type="primary" htmlType="submit"><FormattedMessage id="dept.submit"/></Button>
      </FormItem>
    </Form >
  );
});
