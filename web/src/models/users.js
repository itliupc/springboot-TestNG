import { message } from 'antd';
import * as userService from '../services/users';

export default {
  namespace: 'users',
  state: {
    modalKey: 0,
    showDialog: false,
    type: "",
    users: [],
    userInfo: {}
  },
  reducers: {
    update(state, { payload: newUsers }) {
      return newUsers;
    },
    changeShow(state, { payload: type, userInfo: userInfo }){
      let newState = state;
      newState.showDialog = !newState.showDialog;
      if(!newState.showDialog){
        // 关闭对话框时，重置操作类型
        newState.type = "";
        newState.userInfo = {};
        // 同时更新对话框的key，下次打开重新渲染
        newState.modalKey = Math.random();
      }else {
        newState.type = type;
        newState.userInfo = userInfo;
      }
      return newState;
    }
  },
  effects: {
    *reload(action, { call, put }) {
      const result = yield call(userService.getUsers);
      const newUsers = {users: result.data, showDialog: false, type: "", modalKey: Math.random(), userInfo: {}};
      yield put({
        type: 'update',
        payload: newUsers
      });
    },
    // 改变弹出框显示状态
    *show({ payload, userInfo }, {put}){
      yield put({ type: 'changeShow' , payload: payload, userInfo: userInfo});
    },
    *delete({payload: id}, {select, put, call}){
      yield call(userService.delUser, id);
      yield put({type: "reload"});
      const messages  = yield select(state => state.i18n.messages);
      message.info(messages["user.success.delUser"]);
    },
    *edit({payload: userInfo}, {select, put, call}){
      const {result, message: msgKey} = yield call(userService.editUser, userInfo);
      const messages  = yield select(state => state.i18n.messages);
      if(result){
        yield put({type: "reload"});
        message.info(messages["user.success.editUser"]);
      }else {
        message.error(messages[`msgKey.${msgKey}`]);
      }

    },
    *add({payload: userInfo}, {select, put, call}){
      const {result, message: msgKey} = yield call(userService.addUser, userInfo);
      const messages  = yield select(state => state.i18n.messages);
      if(result){
        yield put({type: "reload"});
        message.info(messages["user.success.addUser"]);
      }else {
        message.error(messages[`msgKey.${msgKey}`]);
      }

    }
  },
  subscriptions: {
    setup({ dispatch, history }) {
      return history.listen(({pathname}) => {
        if(pathname === '/user/list'){
          dispatch({ type: 'reload' });
        }
      });
    },
  }
};
