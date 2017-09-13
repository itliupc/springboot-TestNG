import { message } from 'antd';
import * as interfaceService from '../services/Interfaces';
import * as testCaseService from '../services/testcase';

export default {
  namespace: 'interfaces',
  state: {
    modalKey: 0,
    showDialog: false,
    type: "",
    interfaces: [],
    interfaceInfo: null,
    operatorType:"",
    moduleKey: 0,
    displayInterParamDia: false,
    displayTestCaseDia: false,
    testCaseDetailInfo:null,
    fromWhere:"" ,
    testCaseParamFrom:"",
    displayEditTestCaseModal: false,
    currentTestCase:"",
    reqOrResp:"",
    currentReqParam:"",
    currentRespParam:"",
    jsonEditModal:false,
    displayInterfaceInfoDia: false,
    editInterfaceFrom: "",
    refreshModule:false
  },
  reducers: {
    update(state, { payload: interfaceInfo, operatorType: operatorType, moduleKey: moduleKey}) {
      state.interfaceInfo = interfaceInfo;
      state.operatorType = operatorType;
      state.moduleKey = moduleKey;
      state.displayInterParamDia = false;
      state.displayInterfaceInfoDia = false;
      let newState = state;
      return newState;
    },
    updateInterface(state, { payload: interfaceInfo, operatorType: operatorType, moduleKey: moduleKey}) {
      state.interfaceInfo = interfaceInfo;
      state.operatorType = operatorType;
      state.moduleKey = moduleKey;
      state.displayInterParamDia = false;
      state.displayInterfaceInfoDia = false;
      state.refreshModule = true;
      let newState = state;
      return newState;
    },
    changeState(state,{}){
      state.displayInterParamDia = false;
      state.displayInterfaceInfoDia = false;
      state.displayEditTestCaseModal = false;
      state.displayTestCaseDia = false;
      let newState = state;
      return newState;
    },
    // 是否展示requestPram ResponseParam
    changeParamModalState(state, { interfaceId: interfaceId, currentReqParam: currentReqParam, currentRespParam: currentRespParam, reqOrResp: reqOrResp}) {
     debugger;
      state.currentReqParam = currentReqParam;
      state.currentRespParam = currentRespParam;
      state.displayInterParamDia = !state.displayInterParamDia;
      state.reqOrResp =  reqOrResp;
      state.moduleKey = Math.random();
      let newState = state;
      return newState;
    },
    changetestcasestate(state, {testCaseDetailInfo, testCaseParamFrom}) {
      state.displayTestCaseDia = !state.displayTestCaseDia;
      state.testCaseDetailInfo = testCaseDetailInfo;
      state.testCaseParamFrom = testCaseParamFrom;
      if("" != testCaseParamFrom && !state.displayTestCaseDia){
        state.jsonEditModal=false;
      }
      let newState = state;
      return newState;
    },
    changeInterfaceInfoState(state, {editInterfaceFrom: editInterfaceFrom}){
      state.displayInterfaceInfoDia = !state.displayInterfaceInfoDia;
      state.editInterfaceFrom = editInterfaceFrom;
      let newState = state;
      return newState;
    },
    displayEditModal(state,{currentTestCase}){
      state.displayEditTestCaseModal = !state.displayEditTestCaseModal;
      state.currentTestCase = currentTestCase;
      state.moduleKey = Math.random();
      return state;
    },

    modifyTestCase(state, {operateType, newTestCase}){
      if('add' == operateType){
        state.interfaceInfo.testCaseViews.push(newTestCase);
      }else{
        let cases = [];
        state.interfaceInfo.testCaseViews.map((testCase) => {
          if(testCase.interfaceTestCaseId == newTestCase.interfaceTestCaseId){
            cases.push(newTestCase);
          }else{
            cases.push(testCase);
          }
        });
        state.interfaceInfo.testCaseViews = cases;
      }
      state.displayEditTestCaseModal = false;
      state.displayTestCaseDia = false;
      state.jsonEditModal = false;
      state.currentTestCase = "";
      state.moduleKey = Math.random();
      return state;
    },

    // 增加一条新的参数列
    // addParam(state, {paramValue:paramValue}){
    //   if(state.interfaceInfo.requestParam){
    //     // state.interfaceInfo.requestParam.add(paramValue);
    //     console.log(state.interfaceInfo.requestParam)
    //   }else {
    //     // state.interfaceInfo.requestParam = [];
    //     // state.interfaceInfo.requestParam.add(paramValue);
    //   }
    //   // state.moduleKey = Math.random();
    //   state.displayInterParamDia = false;
    //   let newState = state;
    //   return newState;
    // },

    updateInterfaceInfo(state,{interfaceInfo}){
      state.interfaceInfo = interfaceInfo;
      state.moduleKey = Math.random();
      state.displayInterParamDia = !state.displayInterParamDia;
      return state;
    },
    jsonChangeEdit(state,{interfaceInfo}){
      state.interfaceInfo = interfaceInfo;
      state.jsonEditModal = !state.jsonEditModal;
      return state;
    },
    clearInfo(state,{}){
      state.interfaceInfo = null;
      state.operatorType = "";
      return state;
    },
    changeModuleState(state, {payload: isRefresh}){
      state.refreshModule = isRefresh;
      return state;
    }
  },
  effects: {
    *info({selectModuleKey, selectInterfaceKey, operatorType}, {call, put}){
      const result = yield call(interfaceService.interfaceinfo, selectInterfaceKey);
      const interfaceInfo = result.data;
      if(interfaceInfo && interfaceInfo.testCaseViews && interfaceInfo.testCaseViews.length > 0){
        interfaceInfo.testCaseViews.map(testCase => {
          testCase.key = testCase.interfaceTestCaseId;
        })
      }
      yield put({
        type: 'update',
        payload: interfaceInfo,
        operatorType: operatorType,
        moduleKey:selectModuleKey
      });
    },
    *show({selectModuleKey, selectInterfaceKey, operatorType, interfaceInfo}, {put}){
      yield put({
        type: 'update',
        payload: interfaceInfo,
        operatorType: operatorType,
        moduleKey: selectModuleKey
      });
    },
    *showParam({interfaceId: interfaceId, currentParam: currentParam, reqOrResp: reqOrResp}, {put}){
      if("requestParam" == reqOrResp){
        yield put({type: 'changeParamModalState',currentReqParam: currentParam, currentRespParam: "", interfaceId: interfaceId, reqOrResp: reqOrResp});
      }else{
        yield put({type: 'changeParamModalState',currentReqParam: "", currentRespParam: currentParam, interfaceId: interfaceId, reqOrResp: reqOrResp});
      }
    },
    *showTestCase({record: testCase, testCaseParamFrom: testCaseParamFrom}, {put}){
      yield put({
        type: 'changetestcasestate',
        testCaseDetailInfo: testCase,
        testCaseParamFrom: testCaseParamFrom
      });
    },
    *showInterfaceInfo({from}, {put}){
      yield put({
        type: 'changeInterfaceInfoState',
        editInterfaceFrom: from,
      });
    },
    *deleteTestCase({payload: record}, {call, put}){
      const {data} = yield call(testCaseService.deleteTestCase, record.interfaceTestCaseId, record.interfaceId);
      yield put({
        type: 'update',
        payload: data,
        operatorType: "info",
        moduleKey: data.moduleId
      });
    },
    *add({payload}, {call, put}){
      const {data} = yield call(interfaceService.addinterfaces, payload);
      yield put({
        type: 'updateInterface',
        payload: data,
        operatorType: "info",
        moduleKey: data.moduleId
      });
    },
    *edit({payload}, {call, put}){
      const {data} = yield call(interfaceService.editinterfaces, payload);
      yield put({
        type: 'updateInterface',
        payload: data,
        operatorType: "info",
        moduleKey: data.moduleId
      });
    },
    *showEditTestCaseModal({currentTestCase:currentTestCase}, {put}){
      yield put({type:"displayEditModal",currentTestCase:currentTestCase});
    },
    *editTestCase({testCase:testCase}, {call, put}){
      const result = yield call(interfaceService.editTestCase, testCase);
      yield put({type:"modifyTestCase",operateType:"edit", newTestCase:result.data});
    },

    *addTestCase({testCase:testCase}, {call, put}){
      const result = yield call(interfaceService.addTestCase, testCase);
      yield put({type:"modifyTestCase",operateType:"add", newTestCase:result.data});
    },
    // *addInterfaceParam({paramValue:paramValue}, {put}){
    //   yield put({type:"addParam",paramValue:paramValue});
    // },
    // 新增一个参数
    *addRequestParam({requestParam:requestParam}, {call, put}){
      debugger;
      const {data} = yield call(interfaceService.addInterfaceRequestParam, requestParam);
      // yield put({type:"addParam",requestParam:data});
      yield put({
        type: 'update',
        payload: data,
        operatorType: "info",
        moduleKey:data.moduleId
      });
    },

    *editRequestParam({requestParam:requestParam}, {call, put}){
      const {data} = yield call(interfaceService.editInterfaceRequestParam, requestParam);
      // yield put({type:"addParam",requestParam:data});
      yield put({
        type: 'update',
        payload: data,
        operatorType: "info",
        moduleKey:data.moduleId
      });
    },

    *deleteRequestParam({interfaceId: interfaceId, requestParam:requestParam}, {call, put}){
      const {data} = yield call(interfaceService.deleteRequestParam, interfaceId, requestParam);
      yield put({
        type: 'update',
        payload: data,
        operatorType: "info",
        moduleKey:data.moduleId
      });
    },


    *addResponseParam({responseParam:responseParam}, {call, put}){
      const {data} = yield call(interfaceService.addInterfaceResponseParam, responseParam);
      yield put({
        type: 'update',
        payload: data,
        operatorType: "info",
        moduleKey:data.moduleId
      });

    },
    *editResponseParam({responseParam:responseParam}, {call, put}){
      const {data} = yield call(interfaceService.editInterfaceResponseParam, responseParam);
      // yield put({type:"addParam",requestParam:data});
      yield put({
        type: 'update',
        payload: data,
        operatorType: "info",
        moduleKey:data.moduleId
      });
    },

    *deleteResponseParam({interfaceId: interfaceId, responseParam:responseParam}, {call, put}){
      const {data} = yield call(interfaceService.deleteResponseParam, interfaceId, responseParam);
      yield put({
        type: 'update',
        payload: data,
        operatorType: "info",
        moduleKey:data.moduleId
      });
    },
    *cancelDialog({}, {put}){
      yield put({type:"changeState"});
    },
    *changeEditWay({interfaceInfo: interfaceInfo}, {put}){
      yield put({type:"jsonChangeEdit", interfaceInfo});
    },
    *clearInterfaceInfo({},{put}){
      yield put({type:"clearInfo"});
   },
    *updateModuleState({payload: isRefresh}, {put}){
      yield put({ type: 'changeModuleState', payload: isRefresh});
    }
  },
};
