import React from "react";
import {Form, Input, InputNumber, Select, Button, message, Switch} from "antd";
import {routerRedux} from "dva/router";
import {FormattedMessage, injectIntl} from 'react-intl';

const FormItem = Form.Item;
const formLayout = {
  labelCol: {
    span: 5
  },
  wrapperCol: {
    span: 16
  }
};

export default injectIntl(({form, intl, dispatch, moduleInfo, projectId}) => {
  const {getFieldDecorator, validateFields} = form;
  const {moduleName, run, moduleId} = moduleInfo;
  const type = moduleId ? "update" : "add";

  const submitHandle = (e) => {
    e.preventDefault();
    validateFields((err, values) => {
      if (!err) {
        if("update" == type){
          dispatch({type: "modules/edit", payload: {...moduleInfo, ...values}});
        }else{
          dispatch({type: "modules/add", payload: {...values, ...{projectId:projectId}}});
        }
      } else {
        // message.warn(JSON.stringify(err));
      }
    });
  };

  return (
    <div style={{width: "100%"}}>
      <Form onSubmit={submitHandle}>
        <FormItem label={intl.formatMessage({id: "module.moduleName"}) + ":"} {...formLayout}>
          {getFieldDecorator("moduleName", {
            rules: [
              {
                required: true,
                message: intl.formatMessage({id: "module.moduleName.emptyUserName"})
              }
            ],
            initialValue: moduleName
          })(
            <Input type="text"/>
          )}
        </FormItem>

        <FormItem
          {...formLayout}
          label={intl.formatMessage({id: "module.running"}) + ":"}
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
