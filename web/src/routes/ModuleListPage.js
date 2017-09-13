import React from 'react';
import {connect} from 'dva';
import {Table, Button, Popconfirm, Spin, Icon, Modal, Form, Collapse, Tree, Select, Input, Radio, Popover} from 'antd';
import {FormattedMessage, injectIntl} from 'react-intl';
import {routerRedux} from 'dva/router';
import * as constants from '../utils/constants';
import style from './ModulePage.less';
import InterfaceEditor from '../components/InterfaceEditor'
import ModuleEditor from '../components/ModuleEditor';
import InterfaceInfoEdit from '../components/InterfaceInfoEdit'

const TreeNode = Tree.TreeNode;
const RadioGroup = Radio.Group;
const FormItem = Form.Item;

const ModuleListPage = ({dispatch, modules = [],interfaces =[], addModuleModalVisible = false, modalKey = '', projectId, currentModule, activeKey, refreshModule, intl, userRole, userAuthority, loading, flag, currentInterfaceId}) => {
  console.log(modules);
  console.log(currentModule);
  // 左边module的树结构
  // 异步加载数据
  const onLoadData = (treeNode) => {
    // 获取后台数据，刷新state
    return new Promise((resolve) => {
      let keys = treeNode.props.eventKey.split('-');
      if(keys.length == 1){
        // 加载interface
        console.log("moduleId = "+keys[0]);
        dispatch({type: "modules/list", payload: keys[0]});
      }else if(keys.length == 2){
        console.log("interfaceId = "+keys[1] + "; moduleId = "+keys[0]);
        dispatch({type: "modules/testCaseList", moduleId: keys[0], interfaceId: keys[1]});
      }
      setTimeout(() => {
        resolve();
      },500)
    });
  };
  console.log("refreshModule", interfaces.refreshModule);
  if(interfaces.refreshModule){
    if(activeKey.split('-').length == 1){
      dispatch({type: "modules/list", payload: activeKey});
    }else if(activeKey.split('-').length == 2){
      dispatch({type: "modules/list", payload: activeKey.split('-')[0]});
    }
    dispatch({type: "interfaces/updateModuleState", payload: false});
  }
  let InterfaceInfoView = Form.create()(
      (props) => {
          return <InterfaceEditor
              form={props.form}
              dispatch={dispatch}
              operatorType={interfaces.operatorType}
              interfaceInfo={interfaces.interfaceInfo}
              moduleKey={interfaces.moduleKey}
              displayInterParamDia={interfaces.displayInterParamDia}
              displayTestCaseDia={interfaces.displayTestCaseDia}
              testCaseDetailInfo={interfaces.testCaseDetailInfo}
              reqOrResp={interfaces.reqOrResp}
              displayEditTestCaseModal= {interfaces.displayEditTestCaseModal}
              currentTestCase = {interfaces.currentTestCase}
              currentReqParam={interfaces.currentReqParam}
              currentRespParam={interfaces.currentRespParam}
              jsonEditModal={interfaces.jsonEditModal}
              testCaseParamFrom={interfaces.testCaseParamFrom}
              displayInterfaceInfoDia={interfaces.displayInterfaceInfoDia}
              interfaceEditFrom={interfaces.editInterfaceFrom}
              moduleId = {activeKey}/>
      }
  );
  // module
  const moduleNode = data => data.map((item) => {

    const deleteHandle = () => {
      if(activeKey){
        if(activeKey.split('-').length == 1){
          // 删除模块
          dispatch({type: "modules/deleteMo", payload: activeKey.split('-')[0]});
        }else if(activeKey.split('-').length == 2){
          // 删除接口
          dispatch({type: "modules/deleteIn", moduleId : activeKey.split('-')[0], interfaceId: activeKey.split('-')[1]});
          if(interfaces.interfaceInfo.interfaceId == activeKey.split('-')[1]){
            dispatch({type: "interfaces/clearInterfaceInfo"});
          }
        }
      }
    };

    const interfaceNode = data => data.map((item) => {
      // testCase node
      const testCaseNode = (data,item) => data.map((tesCase) => {
        return <TreeNode title={tesCase.testCaseName} key={item.moduleId+"-"+tesCase.interfaceId +"-"+ tesCase.interfaceTestCaseId} isLeaf={true}></TreeNode>;
      });
      const testCaseNodes = (item.testCaseViews && item.testCaseViews.length > 0)? testCaseNode(item.testCaseViews, item) :[];

      const interfacePopContent = <div className={style.popBtnSpan}>
        {/*<Button onClick={() => {*/}
          {/*if(activeKey && activeKey.split('-').length == 2){*/}
            {/*dispatch({type: "modules/duplicate", moduleId : activeKey.split('-')[0], interfaceId: activeKey.split('-')[1]});*/}
          {/*}*/}
        {/*}}><FormattedMessage id="interface.duplicate"/></Button>*/}
        <Popconfirm title={<FormattedMessage id="interface.delete.confirmTitle"/>}
                    onConfirm={deleteHandle}
                    okText={<FormattedMessage id="module.delete.confirm"/>}
                    cancelText={<FormattedMessage id="module.delete.cancel"/>}>
          <Button><FormattedMessage id="interface.delete"/></Button>
        </Popconfirm>
      </div>;
      const interfaceName = <Popover content={interfacePopContent} trigger="click" placement="rightTop">
        {item.interfaceName}
      </Popover>;
      return <TreeNode title={interfaceName} key={item.moduleId +"-"+ item.interfaceId} isLeaf={false}>
        {testCaseNodes}
      </TreeNode>;
    });
    const interFaceNodes = (item.interfaceViews && item.interfaceViews.length > 0)? interfaceNode(item.interfaceViews) : [];

    // 编辑【模块】
    const editModuleHandle = () => {
      if(activeKey && activeKey.split('-').length == 1){
        dispatch({type: "modules/showCurrentModule", payload: activeKey});
      }
    };
    // 添加接口
    const addInterfaceHandle = () => {
      if(activeKey && activeKey.split('-').length == 1){
        dispatch({type:"interfaces/showInterfaceInfo", from:"interfaceCreate"});
      }
    };

    const editDeleteBtn = <div className={style.popBtnSpan}>
        <Button onClick={editModuleHandle}><FormattedMessage id="module.edit"/></Button>
        <Popconfirm title={<FormattedMessage id="module.delete.confirmTitle"/>}
                    onConfirm={deleteHandle}
                    okText={<FormattedMessage id="module.delete.confirm"/>}
                    cancelText={<FormattedMessage id="module.delete.cancel"/>}>
          <Button><FormattedMessage id="module.delete"/></Button>
        </Popconfirm>
        <Button onClick={addInterfaceHandle}><FormattedMessage id="module.add.interface"/></Button>
    </div>;
    const moduleHandle = <Popover content={editDeleteBtn} trigger="click" placement="rightTop">
      {item.moduleName}
    </Popover>;
    return <TreeNode title={moduleHandle} key={item.moduleId} isLeaf={false}>
        {interFaceNodes}
      </TreeNode>;
  });

  const onSelectHandle = (key) => {
    const selectId = key[0];

    // 对Module节点的处理
    if(selectId && (selectId.split('-').length == 1 || selectId.split('-').length == 2)){
      dispatch({type: "modules/updateActiveKey", payload: selectId});
    }

    // 对interface节点的处理
    if(key[0] && key[0].split('-').length == 2){
      let selectInterfaceKey = key[0].split("-")[1];
      let selectModuleKey = key[0].split("-")[0];
      if(selectInterfaceKey == 0){
        dispatch({type:"interfaces/show", selectModuleKey:selectModuleKey, selectInterfaceKey:selectInterfaceKey, operatorType: "add", interfaceInfo: {}});
      }else{
        dispatch({type:"interfaces/info", selectModuleKey:selectModuleKey, selectInterfaceKey:selectInterfaceKey, operatorType: "info"});
      }
    }
  };
  const moduleNodes = moduleNode(modules);
  const moduleTree = <Spin spinning={loading} tip={intl.formatMessage({id: 'loading'})}><Tree  loadData={onLoadData} onSelect={onSelectHandle}>
    {moduleNodes}
  </Tree></Spin>;

  // Add Module
  const addModuleShow = () => {
    dispatch({type: "modules/show", payload: ""});
  };

  let ModuleForm = Form.create()(
    (props) => {
      return <ModuleEditor
        form={props.form}
        dispatch={dispatch}
        moduleInfo = {currentModule}
        projectId={projectId}/>
    }
  );
  const moduleTitle = currentModule.moduleId ? intl.formatMessage({id: "module.editModule.modal"}) : intl.formatMessage({id: "module.addModule.modal"});
  const addModuleModal = <Modal
    width="750px"
    title={moduleTitle}
    visible={addModuleModalVisible}
    onCancel={addModuleShow}
    maskClosable = {false}
    footer={null}
    key={modalKey}>

    <ModuleForm />
  </Modal>;
  const showEditInterfaceInfoModal =()=>{
    dispatch({type:"interfaces/showInterfaceInfo"});
  };
  let InterfaceEditInfo = Form.create()(
      (props) => {
        return <InterfaceInfoEdit
            form={props.form}
            dispatch={dispatch}
            interfaceInfo = {(null!=interfaces.interfaceInfo)?interfaces.interfaceInfo:[]}
            moduleId = {activeKey}
            from={interfaces.editInterfaceFrom}/>
      }
  );
  let interfaceTitle = "";
  if("interfaceCreate" == interfaces.editInterfaceFrom){
    interfaceTitle = intl.formatMessage({id: "interface.createInterface"});
  }else if("interfaceEdit" == interfaces.editInterfaceFrom){
    interfaceTitle = intl.formatMessage({id: "interface.editInterface"});
  }
  const InterfaceInfoEditModal = <Modal
      width="750px"
      title={interfaceTitle}
      visible={interfaces.displayInterfaceInfoDia}
      onCancel={showEditInterfaceInfoModal}
      footer={null}
      maskClosable = {false}>
    <InterfaceEditInfo />
  </Modal>;
  return (
    <div className={style.moduleContainer}>
      <div className={style.ModuleCollapseLeft}>
        {moduleTree}
        <Button className={style.addModuleBtn} onClick={addModuleShow}><Icon type="plus-circle-o"/>{intl.formatMessage({id: "add.module"})}</Button>
      </div>
      <div className={style.ModuleCollapseRight}>
        <InterfaceInfoView />
      </div>
      {addModuleModal}
      {InterfaceInfoEditModal}
    </div>
  );
};

const mapStateToProps = (state, ownProps) => {
  const {userRole = constants.USER_ROLE_STUDENT} = ownProps.location.query;
  const {userAuthority = constants.USER_AUTHORITY_NORMAL} = state.common;
  const loading = state.loading.effects['modules/reload'];
  const interfaces = state.interfaces;
  const {modules, flag, currentInterfaceId, addModuleModalVisible, modalKey, projectId, currentModule, activeKey, refreshModule} = state.modules;
  return {
    currentInterfaceId,
    flag,
    modules: modules,
    interfaces:interfaces,
    addModuleModalVisible: addModuleModalVisible,
    modalKey:modalKey,
    projectId: projectId,
    activeKey,
    refreshModule,
    currentModule : currentModule,
    userRole: parseInt(userRole),
    userAuthority: parseInt(userAuthority),
    loading
  };
};

export default connect(mapStateToProps)(injectIntl(ModuleListPage));
