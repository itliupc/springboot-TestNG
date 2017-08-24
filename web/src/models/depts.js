import { message } from 'antd';
import * as deptService from '../services/depts';

export default {
  namespace: 'depts',
  state: {
    modalKey: 0,
    showDialog: false,
    type: "",
    depts: [],
    deptInfo: {}
  },
  reducers: {
    update(state, { payload: newDepts }) {
      return newDepts;
    },
    changeShow(state, { payload: type, deptInfo: deptInfo }){
      let newState = state;
      newState.showDialog = !newState.showDialog;
      if(!newState.showDialog){
        // 关闭对话框时，重置操作类型
        newState.type = "";
        newState.deptInfo = {};
        // 同时更新对话框的key，下次打开重新渲染
        newState.modalKey = Math.random();
      }else {
        newState.type = type;
        newState.deptInfo = deptInfo;
      }
      return newState;
    }
  },
  effects: {
    *reload(action,{ select, call, put }) {
      const result = yield call(deptService.listDept, {});
      const newDepts = {depts: result.data, showDialog: false, type: "", modalKey: Math.random(), deptInfo: {}};
      yield put({
        type: 'update',
        payload: newDepts
      });
    },
    // 改变弹出框显示状态
    *show({ payload, deptInfo }, {put}){
      yield put({ type: 'changeShow' , payload: payload, deptInfo: deptInfo});
    },
    *add({payload: deptInfo}, {select, put, call}){
      yield call(deptService.addDept, deptInfo);
      yield put({type: "reload"});
      const messages  = yield select(state => state.i18n.messages);
      message.info(messages["dept.success.addDept"]);
    },
    *edit({payload: deptInfo}, {select, put, call}){
      yield call(deptService.editDept, deptInfo);
      yield put({type: "reload"});
      const messages  = yield select(state => state.i18n.messages);
      message.info(messages["dept.success.editDept"]);
    },
    *delete({payload: deptId}, {select, put, call}){
      const {result, message: msgKey} = yield call(deptService.removeDept, deptId);
      const messages  = yield select(state => state.i18n.messages);
      if(result){
        yield put({type: "reload"});
        message.info(messages["dept.success.deleteDept"]);
      }else {
        message.error(messages[`msgKey.${msgKey}`]);
      }
    }
  },
  subscriptions: {
    setup({history, dispatch}){
      return history.listen(({ pathname, query }) => {
        if (pathname === '/dept/list' || pathname === '/user/list') {
          dispatch({ type: 'reload'});
        }
      });
    }
  }
};
