/**
 * Created by admin on 2017/8/23.
 */
import React from "react";
import {Input, Form, Button, DatePicker, Select, message, Table, Icon, Popconfirm, Modal} from "antd";
import {FormattedMessage, injectIntl} from 'react-intl';
import style from "./InterfaceEditor.less"
import InterfaceParamEditor from "./InterfaceParamEditor";
import TestCaseDetailInfo from "./TestCaseDetailInfo";
import TestCaseEditor from './TestCaseEditor'
import InterfaceInfoEdit from './InterfaceInfoEdit'

const FormItem = Form.Item;
const confirm = Modal.confirm;
export default injectIntl(({dispatch, operatorType, interfaceInfo, moduleKey, displayInterParamDia, displayTestCaseDia,
  displayEditTestCaseModal, currentTestCase, currentReqParam, currentRespParam, testCaseDetailInfo, jsonEditModal, displayInterfaceInfoDia, testCaseParamFrom, interfaceEditFrom, reqOrResp, form, moduleId, intl}) => {
    const {getFieldDecorator, validateFields} = form;
    let requestTableData = [];
    let responseTableData = [];

    // requestParam的列定义
    const columns = [
        {
            title: intl.formatMessage({id: "interface.request.paramName"}),
            dataIndex: 'requestParamName',
            width:"30%"
        },
        {
            title: intl.formatMessage({id: "interface.request.paramType"}),
            dataIndex: 'requestParamType',
            width:"20%"
        },
        {
            title: intl.formatMessage({id: "interface.request.paramDesc"}),
            dataIndex: 'requestParamDescription',
            width:"42%"
        },
      {
        title: intl.formatMessage({id: "testCase.operation"}),
          width:"8%",
        render: (text, record) => {
          return (
            <Button.Group type="ghost">
              <Button title="edit"  size="small" onClick={showEditRequestParamModal.bind(this, record)}><Icon type="edit"/></Button>
              <Popconfirm title={intl.formatMessage({id: "interface.request.deleteParamConfirm"})}
                          onConfirm={
                            () => {
                                const {interfaceId} = interfaceInfo;
                                var flag = false;
                                if(null != interfaceInfo.testCaseViews){
                                    for(var i=0; i<interfaceInfo.testCaseViews.length; i++){
                                        if(flag){
                                            break;
                                        }
                                        var requestParam = interfaceInfo.testCaseViews[i].paramCase;
                                        if(""!= requestParam && null != requestParam){
                                            for(var obj in JSON.parse(requestParam)){
                                                if(obj == record.requestParamName){
                                                    flag = true;
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }
                                if(!flag){
                                    dispatch({type: "interfaces/deleteRequestParam", interfaceId: interfaceId, requestParam: record});
                                }else{
                                    Modal.info({
                                        title: intl.formatMessage({id: "interface.deleteParamInfo"}),
                                        content: intl.formatMessage({id: "interface.ParamInUseInfo"}),
                                        onOk() {},
                                    });
                                }
                            }
                          }>
                <Button title="delete" size="small"><Icon type="delete" /></Button>
              </Popconfirm>
            </Button.Group>
          );
        }
      }
    ];

  // responseParam 的列定义
  const responseResultColumns = [
    {
      title: intl.formatMessage({id: "interface.request.paramName"}),
      dataIndex: 'responseParamName',
      width:"30%"
    },
    {
      title: intl.formatMessage({id: "interface.request.paramType"}),
      dataIndex: 'responseParamType',
      width:"20%"
    },
    {
      title: intl.formatMessage({id: "interface.request.paramDesc"}),
      dataIndex: 'responseParamDescription',
      width:"42%"
    },
    {
      title: intl.formatMessage({id: "testCase.operation"}),
      width:"8%",
      render: (text, record) => {
        return (
          <Button.Group type="ghost">
            <Button title="edit"  size="small" onClick={showEditResponseParamModal.bind(this, record)}><Icon type="edit"/></Button>
            <Popconfirm title={intl.formatMessage({id: "interface.request.deleteResponseConfirm"})}
                        onConfirm={
                          () => {
                              const {interfaceId} = interfaceInfo;
                              var flag = false;
                              if(null != interfaceInfo.testCaseViews){
                                 for(var i=0; i<interfaceInfo.testCaseViews.length; i++){
                                     if(flag){
                                        break;
                                     }
                                     var responseParam = interfaceInfo.testCaseViews[i].expectResult;
                                     if(""!= responseParam && null != responseParam){
                                           for(var obj in JSON.parse(responseParam)){
                                               if(obj == record.responseParamName){
                                                   flag = true;
                                                   break;
                                               }
                                           }
                                     }
                                 }
                              }
                              if(!flag){
                                  dispatch({type: "interfaces/deleteResponseParam", interfaceId: interfaceId, responseParam: record});
                              }else{
                                  Modal.info({
                                      title: intl.formatMessage({id: "interface.deleteParamInfo"}),
                                      content: intl.formatMessage({id: "interface.ParamInUseInfo"}),
                                      onOk() {},
                                  });
                              }
                          }
                        }>
              <Button title="delete" size="small"><Icon type="delete" /></Button>
            </Popconfirm>
          </Button.Group>
        );
      }
    }
  ];

    const showEditRequestParamModal = (record) =>{
      const {interfaceId} = interfaceInfo;
        var flag = false;
        if(null != interfaceInfo.testCaseViews){
            for(var i=0; i<interfaceInfo.testCaseViews.length; i++){
                if(flag){
                    break;
                }
                var requestParam = interfaceInfo.testCaseViews[i].paramCase;
                if(""!= requestParam && null != requestParam){
                    for(var obj in JSON.parse(requestParam)){
                        if(obj == record.requestParamName){
                            flag = true;
                            break;
                        }
                    }
                }
            }
        }
        if(!flag){
            dispatch({type:"interfaces/showParam", interfaceId: interfaceId, currentParam: record, reqOrResp: "requestParam"});
        }else{
            Modal.info({
                title: intl.formatMessage({id: "interface.editParamInfo"}),
                content: intl.formatMessage({id: "interface.ParamInUseInfo"}),
                onOk() {},
            });
        }
    };

  const showEditResponseParamModal = (record) =>{
    const {interfaceId} = interfaceInfo;
      var flag = false;
      if(null != interfaceInfo.testCaseViews){
          for(var i=0; i<interfaceInfo.testCaseViews.length; i++){
              if(flag){
                  break;
              }
              var responseParam = interfaceInfo.testCaseViews[i].expectResult;
              if(""!= responseParam && null != responseParam){
                  for(var obj in JSON.parse(responseParam)){
                      if(obj == record.responseParamName){
                          flag = true;
                          break;
                      }
                  }
              }
          }
      }
      if(!flag){
          dispatch({type:"interfaces/showParam", interfaceId: interfaceId, currentParam: record, reqOrResp: "responseParam"});
      }else{
          Modal.info({
              title: intl.formatMessage({id: "interface.editParamInfo"}),
              content: intl.formatMessage({id: "interface.ParamInUseInfo"}),
              onOk() {},
          });
      }
  };

    const editTestCaseModalShow = () => {
      dispatch({type:"interfaces/showEditTestCaseModal", payload:""});
    };
    const showEditTestCaseModal = (testCase) =>{
      dispatch({type:"interfaces/showEditTestCaseModal", currentTestCase:testCase});
    };

    let TestCaseEditorForm = Form.create()(
      (props) => {
        return <TestCaseEditor form={props.form} dispatch={dispatch} currentTestCase={props.currentTestCase} interfaceId ={props.interfaceId}/>
      }
    );

    const moduleTitle = currentTestCase? intl.formatMessage({id: "testCase.editModalTitle"}):intl.formatMessage({id: "testCase.addModalTitle"});
    const editTestCaseModal = <Modal
          width="750px"
          title={moduleTitle}
          visible={displayEditTestCaseModal}
          onCancel={editTestCaseModalShow}
          maskClosable = {false}
          footer={null}
          key={moduleKey}>

      <TestCaseEditorForm currentTestCase={currentTestCase} interfaceId = {interfaceInfo?interfaceInfo.interfaceId:""}/>
    </Modal>;
    const testCaseColumns = [
        {
            title: intl.formatMessage({id: "testCase.testCaseName"}),
            dataIndex: 'testCaseName',
            width: '20%',
        },
        {
            title: intl.formatMessage({id: "testCase.expectStatus"}),
            dataIndex: 'expectStatus',
            width: '8%',
        },
        // {
        //     title: "",
        //     width: '4%',
        //     render: (text, record) => {
        //         return (
        //             <Button.Group type="ghost">
        //                 <Button title="Edit Param Case"  size="small" onClick={showTestCaseDetailInfoDialog.bind(this, record,"paramCase")}><Icon type="edit" /></Button>
        //             </Button.Group>
        //         );
        //     }
        // },
        {
            title: intl.formatMessage({id: "testCase.paramCase"}),
            dataIndex: 'paramCase',
            width: '32%',
            onCellClick: (record)=>{
                dispatch({type:"interfaces/showTestCase", record: record, testCaseParamFrom: "paramCase"});
            },
        },
        // {
        //     title: "",
        //     width: '4%',
        //     render: (text, record) => {
        //         return (
        //             <Button.Group type="ghost">
        //                 <Button title="Edit Expect Result"  size="small" onClick={showTestCaseDetailInfoDialog.bind(this, record, "expectResult")}><Icon type="edit" /></Button>
        //             </Button.Group>
        //         );
        //     }
        // },
        {
            title: intl.formatMessage({id: "testCase.expectResult"}),
            dataIndex: 'expectResult',
            width: '32%',
            onCellClick: (record)=>{
                dispatch({type:"interfaces/showTestCase", record: record, testCaseParamFrom: "expectResult"});
            },
        },
        {
            title: intl.formatMessage({id: "testCase.operation"}),
            width: '8%',
            render: (text, record) => {
                return (
                    <Button.Group type="ghost">
                        <Button title="edit"  size="small" onClick={showEditTestCaseModal.bind(this, record)}><Icon type="edit"/></Button>
                        <Popconfirm title={intl.formatMessage({id: "interface.request.deleteTestCaseConfirm"})}
                                    onConfirm={
                                        () => {
                                            dispatch({type: "interfaces/deleteTestCase", payload: record});
                                        }
                                    }>
                            <Button title="delete" size="small"><Icon type="delete" /></Button>
                        </Popconfirm>
                    </Button.Group>
                );
            }
        }
    ];
    const handleSubmit=(e)=> {
        e.preventDefault();
        validateFields((err, values) => {
            console.log("values:", values);
            values.moduleId = moduleKey;
            values.requestParam = interfaceInfo.requestParam;
            values.responseResult = interfaceInfo.responseResult;
            if(undefined != interfaceInfo.interfaceId){
                values.interfaceId = interfaceInfo.interfaceId;
            }
            dispatch({type:"interfaces/add", payload:values});
        });
        dispatch({type:"interfaces/info", selectModuleKey:interfaceInfo.moduleId, selectInterfaceKey:interfaceInfo.interfaceId, operatorType: "info"});
    };

    const showInterfaceParamDialog =(param)=>{
      dispatch({type:"interfaces/showParam", interfaceInfo: interfaceInfo, reqOrResp: param});
    };
    const showTestCaseDetailInfoDialog =(testCase, testCaseParamFrom)=>{
        dispatch({type:"interfaces/showTestCase", record: testCase, testCaseParamFrom: testCaseParamFrom});
    };
    const showEditInterfaceInfoModal =()=>{
        dispatch({type:"interfaces/showInterfaceInfo", from:"interfaceEdit"});
    };
    let ParamEditor = Form.create()(
        (props) => {
            return <InterfaceParamEditor
                form={props.form}
                dispatch={dispatch}
                interfaceInfo = {interfaceInfo}
                reqOrResp={reqOrResp}
                currentReqParam= {currentReqParam}
                currentRespParam={currentRespParam}/>
        }
    );
    let title = "";
    if("requestParam" == reqOrResp){
        title=intl.formatMessage({id: "interface.request.title"})
    }else if("responseParam" == reqOrResp){
        title=intl.formatMessage({id: "interface.response.title"})
    }
    const addParamEditorModal = <Modal
        width="750px"
        title={title}
        visible={displayInterParamDia}
        onCancel={showInterfaceParamDialog}
        maskClosable = {false}
        footer={null}>
        <ParamEditor />
    </Modal>;
    let TcDetailInfo = Form.create()(
        (props) => {
            return <TestCaseDetailInfo
                form={props.form}
                dispatch={dispatch}
                interfaceInfo = {interfaceInfo}
                testCaseDetailInfo = {testCaseDetailInfo}
                jsonEditModal = {jsonEditModal}
                testCaseParamFrom = {testCaseParamFrom}/>
        }
    );
    const TestCaseDetailInfoModal = <Modal
        width="950"
        title={intl.formatMessage({id: "interface.testCase.editParams"})}
        visible={displayTestCaseDia}
        onCancel={showTestCaseDetailInfoDialog}
        maskClosable = {false}
        footer={null}>
        <TcDetailInfo />
    </Modal>;

    let InterfaceEditInfo = Form.create()(
        (props) => {
            return <InterfaceInfoEdit
                form={props.form}
                dispatch={dispatch}
                interfaceInfo = {interfaceInfo}
                moduleId ={moduleId}
                from={interfaceEditFrom}/>
        }
    );
    let interfaceTitle = "";
    if("interfaceCreate" == interfaceEditFrom){
        interfaceTitle = intl.formatMessage({id: "interface.createInterface"});
    }else if("interfaceEdit" == interfaceEditFrom){
        interfaceTitle = intl.formatMessage({id: "interface.editInterface"});
    }
    const InterfaceInfoEditModal = <Modal
        width="750px"
        title={interfaceTitle}
        visible={displayInterfaceInfoDia}
        onCancel={showEditInterfaceInfoModal}
        maskClosable = {false}
        footer={null}>
        <InterfaceEditInfo />
    </Modal>;
    if("info" == operatorType){
        requestTableData = interfaceInfo.requestParams;
        responseTableData = interfaceInfo.responseParams;

        const {interfaceName,interfaceType, interfaceUrl, interfaceId, run} = interfaceInfo;
        return (
           <div>
               <Button onClick={showEditInterfaceInfoModal.bind(this,"")} className={style.editInterfaceBtn}><Icon type="edit"/>{intl.formatMessage({id: "interface.editInterface"})}</Button>
               <div className={style.interfaceInfoDiv}><b>{intl.formatMessage({id: "interface.interfaceName"})}</b>{interfaceName}</div>
               <div className={style.interfaceInfoDiv}><b>{intl.formatMessage({id: "interface.interfaceType"})}</b>{interfaceType}</div>
               <div className={style.interfaceInfoDiv}><b>{intl.formatMessage({id: "interface.interfaceURL"})}</b>{interfaceUrl}</div>
               <div className={style.interfaceInfoDiv}><b>{intl.formatMessage({id: "interface.interfaceRun"})}</b>{run?"YES":"NO"}</div>
               <div>
                   <b>{intl.formatMessage({id: "interface.requestParam"})}</b>
                   <Button className={style.editInterfaceBtn} onClick={showInterfaceParamDialog.bind(this, "requestParam")}><Icon type="save"/>{intl.formatMessage({id: "interface.addRequestParam"})}</Button>
               </div>
               <div className={style.interfaceInfoDiv}>
                   <Table columns={columns} dataSource={requestTableData} rowKey={record => record.requestParamId} pagination={false} bordered/>
               </div>
               <div>
                   <b>{intl.formatMessage({id: "interface.responseParam"})}</b>
                   <Button className={style.editInterfaceBtn} onClick={showInterfaceParamDialog.bind(this, "responseParam")}><Icon type="save"/>{intl.formatMessage({id: "interface.addResponseParam"})}</Button>
               </div>
               <div className={style.interfaceInfoDiv}>
                   <Table columns={responseResultColumns} dataSource={responseTableData} rowKey={record => record.responseParamId} pagination={false} bordered/>
               </div>
               <div>
                   <b>{intl.formatMessage({id: "interface.testCaseList"})}<Button onClick={showEditTestCaseModal.bind(this,"")} className={style.editInterfaceBtn}><Icon type="save"/>{intl.formatMessage({id: "testCase.addTestCase"})}</Button></b>
                   <div className={style.promptMessage}>{"(Click cell of each test case to edit 'Param Case' and 'Expect Result')"}</div>
               </div>
               <div className={style.interfaceInfoDiv}>
                   <Table columns={testCaseColumns} dataSource={interfaceInfo.testCaseViews} rowKey={record => record.interfaceTestCaseId} pagination={false} bordered/>
               </div>
               {TestCaseDetailInfoModal}
               {addParamEditorModal}
               {editTestCaseModal}
               {InterfaceInfoEditModal}
           </div>
        );
    }
    else{
        return (<div></div>);
    }
});
