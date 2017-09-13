/**
 * Created by Administrator on 2017/8/31 0031.
 */
import React from "react";
import {Form, Table, Input, Popconfirm, Button, Icon, Select, Switch } from "antd";
import {routerRedux} from "dva/router";
import {FormattedMessage, injectIntl} from 'react-intl';
import style from "./InterfaceEditor.less"

const FormItem = Form.Item;
const Option = Select.Option;

const formLayout = {
  labelCol: {
    span: 5
  },
  wrapperCol: {
    span: 16
  }
};

export default injectIntl(({form, intl, dispatch, interfaceInfo, moduleId, from}) => {
    let {interfaceName = [],interfaceType = [], interfaceUrl =[], run =[]} = interfaceInfo;
    const {getFieldDecorator, validateFields} = form;
    if("interfaceCreate" == from){
        interfaceName = "";
        interfaceType = "POST";
        interfaceUrl = "";
        run = true;
    }

    const handleSubmit=(e)=> {
        e.preventDefault();
        validateFields((err, values) => {
            if (!err){
                console.log("values",values);
                if("interfaceEdit" == from && undefined != interfaceInfo.interfaceId){
                    values.moduleId = interfaceInfo.moduleId;
                    values.interfaceId = interfaceInfo.interfaceId;
                    dispatch({type:"interfaces/edit", payload:values});
                }else{
                    debugger;
                    values.moduleId = moduleId;
                    dispatch({type:"interfaces/add", payload:values});
                }
                dispatch({type:"interfaces/cancelDialog"});
                // dispatch({type: "modules/list", payload: moduleId});
            }
        });
    };

    return (
        <div>
            <Form>
                <FormItem  label={intl.formatMessage({id: "interface.interfaceName"})} {...formLayout}>
                    {getFieldDecorator("interfaceName", {
                        rules: [
                            {
                                required: true,
                                message: intl.formatMessage({id: "module.warn.emptyInterfaceName"})
                            }
                        ],
                        initialValue: interfaceName
                    })(
                        <Input type="text"/>
                    )}
                </FormItem>
                <FormItem  label={intl.formatMessage({id: "interface.interfaceType"})} {...formLayout}>
                    {getFieldDecorator("interfaceType", {
                        rules: [
                            {
                                required: true,
                            },
                        ],
                        initialValue: interfaceType
                    })(
                        <Select>
                            <Option value="POST">POST</Option>
                            <Option value="GET">GET</Option>
                            <Option value="DELETE">DELETE</Option>
                            <Option value="PUT">PUT</Option>
                        </Select>
                    )}
                </FormItem>
                <FormItem  label={intl.formatMessage({id: "interface.interfaceURL"})} {...formLayout}>
                    {getFieldDecorator("interfaceUrl", {
                        rules: [
                            {
                                required: true,
                                message: intl.formatMessage({id: "module.warn.emptyInterfaceURL"})
                            },

                        ],
                        initialValue: interfaceUrl
                    })(
                        <Input type="text"/>
                    )}
                </FormItem>
                <FormItem
                    label={intl.formatMessage({id: "module.running"}) + ":"} {...formLayout}
                >
                    {getFieldDecorator('run', {
                        valuePropName: 'checked',
                        initialValue : (undefined != run? run: true)
                    })(
                        <Switch/>
                    )}
                </FormItem>
                </Form>
            <FormItem wrapperCol={{...formLayout.wrapperCol, offset: 19}}>
                <Button className={style.testCaseParamButton} onClick={handleSubmit.bind(this)}><Icon type="save"/>{intl.formatMessage({id: "module.save"})}</Button>
            </FormItem>
        </div>
    );
});
