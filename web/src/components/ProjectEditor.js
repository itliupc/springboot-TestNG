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

export default injectIntl(({form, intl, type, dispatch, projectInfo={}}) => {
  const {getFieldDecorator, validateFields} = form;
  const {projectName} = projectInfo;

  const submitHandle = (e) => {
    e.preventDefault();
    validateFields((err, values) => {
      if (!err) {
        if(type === "add"){
          dispatch({type: "projects/add", payload: values});
        }else {
          dispatch({type: "projects/edit", payload: {...projectInfo, ...values}});
        }
        dispatch({type: 'reload'});
      } else {
        message.warn(JSON.stringify(err));
      }
    });
  };

  return (
    <div style={{width: "400px"}}>
      <Form onSubmit={submitHandle}>
        <FormItem label={intl.formatMessage({id: "project.projectName"}) + ":"} {...formLayout}>
          {getFieldDecorator("projectName", {
            rules: [
              {
                required: true,
                message: intl.formatMessage({id: "project.warn.emptyProjectName"})
              },
              {
                pattern: /^.{1,20}$/,
                message: intl.formatMessage({id: "project.warn.tooManyChar20"})
              }
            ],
            initialValue: projectName
          })(
            <Input type="text"/>
          )}
        </FormItem>
        <br/>
        <FormItem wrapperCol={{...formLayout.wrapperCol, offset: 21}}>
          <Button type="primary" htmlType="submit"><FormattedMessage id="project.submit"/></Button>
        </FormItem>
      </Form>
    </div>
  );
});
