import React from "react";
import {Form, Input, InputNumber, Select, Button, message} from "antd";
import {routerRedux} from "dva/router";
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

export default injectIntl(({form, intl, type, dispatch, userInfo={}, deptList = []}) => {
  const {getFieldDecorator, validateFields} = form;
  const {userName, email, deptId} = userInfo;
  let pwItem;
  if(type === "add"){
    // 如果是新增User才能输入密码
    pwItem =
      <FormItem label={intl.formatMessage({id: "user.userPw"}) + ":"} {...formLayout}>
      {getFieldDecorator("password", {
        rules: [
          {
            required: true,
            message: intl.formatMessage({id: "user.warn.emptyPw"})
          },
          {
            pattern: /^(\w){6,20}$/,
            message: intl.formatMessage({id: "user.warn.passwordFormat"})
          }
        ],
        initialValue: ""
      })(
        <Input type="password" autoComplete="new-password"/>
      )}
     </FormItem>;
  }

  const submitHandle = (e) => {
    e.preventDefault();
    validateFields((err, values) => {
      if (!err) {
        if(type === "add"){
          dispatch({type: "users/add", payload: values});
        }else {
          dispatch({type: "users/edit", payload: {...userInfo, ...values}});
        }
        dispatch({type: 'reload'});
      } else {
        // message.warn(JSON.stringify(err));
      }
    });
  };

  return (
    <div style={{width: "400px"}}>
      <Form onSubmit={submitHandle}>
        <FormItem label={intl.formatMessage({id: "user.userName"}) + ":"} {...formLayout}>
          {getFieldDecorator("userName", {
            rules: [
              {
                required: true,
                message: intl.formatMessage({id: "user.warn.emptyUserName"})
              },
              {
                pattern: /^.{1,20}$/,
                message: intl.formatMessage({id: "user.warn.tooManyChar20"})
              }
            ],
            initialValue: userName
          })(
            <Input type="text"/>
          )}
        </FormItem>
        {pwItem}
        <FormItem label={intl.formatMessage({id: "user.emailAddr"}) + ":"} {...formLayout}>
          {getFieldDecorator("email", {
            rules: [
              {
                required: true,
                message: intl.formatMessage({id: "user.warn.emptyEmail"})
              },
              {
                pattern: /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/,
                message: intl.formatMessage({id: "user.warn.invalidEmail"})
              }
            ],
            initialValue: email
          })(
            <Input type="text"/>
          )}
        </FormItem>
        <FormItem label={intl.formatMessage({id: "user.dept"}) + ":"} {...formLayout}>
          {getFieldDecorator("deptId", {
            rules: [
              {
                required: true,
                message: intl.formatMessage({id: "user.warn.emptyDept"})
              }
            ],
            initialValue: String(deptId||"")
          })( <Select>
            {deptList.map((dept) => {
              return (
                <Select.Option key={dept.deptId} value={String(dept.deptId)}>{dept.deptName}</Select.Option>
              );
            })}
          </Select>)}
        </FormItem>
        <br/>
        <FormItem wrapperCol={{...formLayout.wrapperCol, offset: 21}}>
          <Button type="primary" htmlType="submit"><FormattedMessage id="user.submit"/></Button>
        </FormItem>
      </Form>
    </div>
  );
});
