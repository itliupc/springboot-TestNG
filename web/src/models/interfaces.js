import { message } from 'antd';
import * as interfaceService from '../services/Interfaces';

export default {
  namespace: 'interfaces',
  state: {
    modalKey: 0,
    showDialog: false,
    type: "",
    interfaces: [],
    interfaceInfo: {}
  },
  reducers: {
    update(state, { payload: newModules }) {
      return newModules;
    },
  },
  effects: {
    *reload({userRole, moduleId}, { select, call, put }) {
      const {userAuthority, userId} = yield select(state => state.common);
      const result = yield call(interfaceService.listinterfaces(moduleId), {userAuthority, userId, userRole});
      const newInterfaces = {interfaces: result.data, showDialog: false, type: "", modalKey: Math.random(), interfaceInfo: {}};
      yield put({
        type: 'update',
        payload: newInterfaces
      });
    },

  },
  subscriptions: {
    setup({ dispatch, history }) {
      return history.listen(({pathname, query}) => {
        if(pathname === '/interface/list'){
          const {userRole} = query;
          dispatch({ type: 'reload' , userRole, moduleId});
        }
      });
    },
  }
};
