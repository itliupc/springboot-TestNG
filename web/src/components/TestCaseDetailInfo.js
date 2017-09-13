/**
 * Created by Administrator on 2017/8/25 0025.
 */
import React from "react";
import {Form, Table, Input, Popconfirm, Button, message } from "antd";
import {routerRedux} from "dva/router";
import {FormattedMessage, injectIntl} from 'react-intl';
import style from "./TestCaseParamForm.less";
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
export default injectIntl(({form, intl, dispatch, interfaceInfo, testCaseDetailInfo, jsonEditModal, testCaseParamFrom}) => {
    var testCaseParamCase = [];
    var testCaseExpectResult = [];
    if(null != testCaseDetailInfo.paramCase && ""!= testCaseDetailInfo.paramCase){
        testCaseParamCase = "";
          try{
            testCaseParamCase = JSON.parse(testCaseDetailInfo.paramCase);
          }catch (e){
            console.log(e);
            message.warn(intl.formatMessage({id: "testCase.params.formatWrong"}));
          }
    }
    if(null != testCaseDetailInfo.expectResult && ""!= testCaseDetailInfo.expectResult){
        testCaseExpectResult = "";
      try{
          testCaseExpectResult = JSON.parse(testCaseDetailInfo.expectResult);
      }catch (e){
        console.log(e);
        message.warn(intl.formatMessage({id: "testCase.params.formatWrong"}));
      }
    }
    const {paramCase, expectResult} = testCaseDetailInfo;
    const {getFieldDecorator, validateFields} = form;
    var paramCaseObj = [];
    var ParamDiv = [];

    const columns = [
        {
            title: "paramName",
            dataIndex: 'paramName',
            width:'20%'
        },
        {
            title: "paramType",
            dataIndex: 'paramType',
            width:'20%'
        },
        {
            title: "paramValue",
            dataIndex: 'paramValue',
            width:'60%'
        },
    ];
    if("paramCase" == testCaseParamFrom){
        if(null != interfaceInfo.requestParams && ""!= interfaceInfo.requestParams){
            var requestParamObject = interfaceInfo.requestParams;
            var paramNameObj;
            var paramTypeObj;
            for(var i=0; i< requestParamObject.length;i++){
                paramNameObj =  requestParamObject[i].requestParamName;
                paramTypeObj =  requestParamObject[i].requestParamType;
                if(0 == testCaseParamCase[paramNameObj]){
                    testCaseParamCase[paramNameObj] = "0";
                }else if(null == testCaseParamCase[paramNameObj]){
                    testCaseParamCase[paramNameObj] = "";
                }
                if("object" ==  typeof testCaseParamCase[paramNameObj]){
                    testCaseParamCase[paramNameObj] = format(JSON.stringify(testCaseParamCase[paramNameObj]), false);
                }
                var newJson={"paramName": paramNameObj, "paramType": paramTypeObj,  "paramValue": (<div className={style.testCaseParamEditText}><FormItem>
                    {getFieldDecorator(paramNameObj, {
                        initialValue: testCaseParamCase[paramNameObj]
                    })(
                        <TextArea autosize={{ minRows: 3}} />
                    )}
                </FormItem></div>)};
                paramCaseObj.push(newJson);
            }
        }
    }else if("expectResult" == testCaseParamFrom){
        if(null != interfaceInfo.responseParams && ""!= interfaceInfo.responseParams){
            var responseParamObject = interfaceInfo.responseParams;
            var paramNameObj;
            var paramTypeObj;
            for(var i=0; i< responseParamObject.length;i++){
                paramNameObj =  responseParamObject[i].responseParamName;
                paramTypeObj =  responseParamObject[i].responseParamType;
                if(0 == testCaseExpectResult[paramNameObj]){
                    testCaseExpectResult[paramNameObj] = "0";
                }else if(null == testCaseExpectResult[paramNameObj]){
                    testCaseExpectResult[paramNameObj] = "";
                }
                if("object" ==  typeof testCaseExpectResult[paramNameObj]){
                    testCaseExpectResult[paramNameObj] = format(JSON.stringify(testCaseExpectResult[paramNameObj]), false);
                }
                var newJson={"paramName": paramNameObj, "paramType": paramTypeObj,  "paramValue": (<div className={style.testCaseParamEditText}><FormItem>
                    {getFieldDecorator(paramNameObj, {
                        initialValue: testCaseExpectResult[paramNameObj]
                    })(
                        <TextArea autosize={{ minRows: 3}} />
                    )}
                </FormItem></div>)};
                paramCaseObj.push(newJson);
            }
        }
    }

    const handleSubmit=(e)=> {
        e.preventDefault();
        validateFields((err, values) => {
            console.log("values:", values);
            if(null != values){
                if(jsonEditModal){
                    var testCaseParamStr = values.testCaseParam;
                    testCaseParamStr= testCaseParamStr.trim();
                    testCaseParamStr = testCaseParamStr.replace(/[\r\n]/g, "");
                    if(testCaseParamStr.length==0 || ((testCaseParamStr.indexOf("{") == 0 || testCaseParamStr.indexOf("[{") ==0) && testCaseParamStr.length!=0)) {
                        try {
                            if(testCaseParamStr.length!=0){
                                JSON.parse(testCaseParamStr);
                            }
                            if ("paramCase" == testCaseParamFrom) {
                                testCaseDetailInfo.paramCase = testCaseParamStr;
                            } else if ("expectResult" == testCaseParamFrom) {
                                testCaseDetailInfo.expectResult = testCaseParamStr;
                            }
                            dispatch({type: "interfaces/editTestCase", testCase: testCaseDetailInfo});
                            dispatch({type:"interfaces/cancelDialog"});
                        } catch (e) {
                            console.log(e);
                            message.warn(intl.formatMessage({id: "testCase.params.formatWrong"}));
                            return;
                        }
                    }else{
                        message.warn(intl.formatMessage({id: "testCase.params.formatWrong"}));
                        return;
                    }
                }else{
                    var resultValues = {};
                    for(var obj in values){
                        if(undefined != values[obj] ){
                            if(values[obj].indexOf("{")>=0 || values[obj].indexOf("[{")>=0){
                                try{
                                    resultValues[obj] = JSON.parse(values[obj]);
                                }catch (e){
                                    console.log(e);
                                    message.warn(obj+intl.formatMessage({id: "testCase.params.formatWrong"}));
                                    return;
                                }
                            }else{
                                values[obj]= (values[obj]).trim();
                                values[obj] = (values[obj]).replace(/[\r\n]/g, "");
                                if(values[obj].length!=0){
                                    resultValues[obj] = values[obj];
                                }
                            }
                        }
                    }
                    if(JSON.stringify(resultValues).length == 2){
                        resultValues = "";
                    }else{
                        resultValues = JSON.stringify(resultValues);
                    }
                    if("paramCase" == testCaseParamFrom){
                        testCaseDetailInfo.paramCase = resultValues;
                    }else if("expectResult" == testCaseParamFrom){
                        testCaseDetailInfo.expectResult = resultValues;
                    }
                    dispatch({type: "interfaces/editTestCase", testCase: testCaseDetailInfo});
                    dispatch({type:"interfaces/cancelDialog"});
                }
            }
        });
    };

    const changeEditDiv=()=> {
        dispatch({type: "interfaces/changeEditWay", interfaceInfo: interfaceInfo});
    };

    if(jsonEditModal){
        var jsonValue = "";
        if("paramCase" == testCaseParamFrom){
            jsonValue = paramCase
        }else if("expectResult" == testCaseParamFrom){
            jsonValue = expectResult;
        }

        ParamDiv =  (<FormItem label={intl.formatMessage({id: "testCase.paramCase"}) + ":"} {...formLayout}>
            {getFieldDecorator("testCaseParam", {
                initialValue: jsonValue?format(jsonValue, false):""
            })(
                <TextArea autosize={{ minRows: 4}}/>
            )}
        </FormItem>);
    }else{
        ParamDiv =  (<Table columns={columns} dataSource={paramCaseObj} pagination={false} bordered/>);
    }
    return (
        <div className={style.showParamsContent}>
            <Form>
                {ParamDiv}
                <FormItem>
                    <Button className={style.testCaseParamEditButton} onClick={changeEditDiv.bind(this)}>{intl.formatMessage({id: "testCase.changeMode.btn"})}</Button>
                    <Button className={style.testCaseParamButton} onClick={handleSubmit.bind(this)}>{intl.formatMessage({id: "module.save"})}</Button>
                </FormItem>
            </Form>
        </div>
    );
});
