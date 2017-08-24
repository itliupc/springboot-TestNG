import { message } from 'antd';
import * as moduleService from '../services/Modules';

export default {
  namespace: 'modules',
  state: {
    modules:[],
    moduleIds:[],
    interfaceIds:[],
    currentInterfaceId:"0",
    flag: false,
  },
  reducers: {
    update(state, {modules}) {
      debugger;
      state.modules = modules;
      state.moduleIds = [];
      state.interfaceIds = [];
      let newState = state;
      return newState;
    },
    change(state, { modules, moduleIds, interfaceIds, currentInterfaceId}) {
      state.moduleIds=moduleIds;
      state.modules = modules;
      state.interfaceIds = interfaceIds;
      state.flag = !state.flag;
      state.currentInterfaceId = currentInterfaceId;
      let newState = state;
      return newState;
    },

    updateInterfaces(state, {payload : newInterfaces, moduleId : moduleId}){
      // 1.循环遍历module ,将新获取到的interface更新
      let modules = state.modules;
      debugger
      modules.map(module =>{
        if(module.moduleId == moduleId){
          module.interfaceViews = newInterfaces;
        }
      });
      state.flag = !state.flag;
      state.modules = modules;
      return state;
    },

  },
  effects: {
    *reload({projectId}, { select, call, put}) {
      const {userAuthority, userId, userRole} = yield select(state => state.common);
      const result = yield call(moduleService.listmodules, projectId);
      const modules = result.data;
      yield put({
        type: 'update',
        modules: modules
      });
    },
    *interfacelist({payload: mIds}, {select, call, put}){
      debugger;
      const mods = yield select(state => state.modules);
      const modules = yield select(state => state.modules.modules);
      const moduleIds =  mods.moduleIds;
      const interfaceIds = mods.interfaceIds;
      let currentInterfaceId = mods.currentInterfaceId;
      let moduleId;
      let hasmoduleId = false;
      if(null != moduleIds && moduleIds.length>0){
        let tempArray1 = [];//临时数组1
        let tempArray2 = [];//临时数组2

        for(var i=0;i<moduleIds.length;i++){
          tempArray1[moduleIds[i]] = true;
        }

        for(var i=0;i<mIds.length;i++){
          if(!tempArray1[mIds[i]]){
            tempArray2.push(mIds[i]);
          }
        }
        if(null != tempArray2 && tempArray2.length>0){
          moduleId = tempArray2[0];
          moduleIds.push(moduleId);
        }else{
          hasmoduleId = true;
        }
      }else{
        moduleId = mIds[0];
        moduleIds.push(moduleId);
      }
      if(!hasmoduleId){
        const {data: interfaces} = yield call(moduleService.listinterfaces, moduleId);

        for(let i=0;i<modules.length;i++){
             if(modules[i].moduleId == moduleId){
               modules[i].interfaces = interfaces;
             }
        }
        yield put({
          type: 'change',
          modules: modules,
          moduleIds: moduleIds,
          interfaceIds: interfaceIds,
          currentInterfaceId: currentInterfaceId,
        });
      }
    },
    *testcaselist({payload: iIds}, {select, call, put}){
      debugger;
      const mods = yield select(state => state.modules);
      const modules = yield select(state => state.modules.modules);
      const moduleIds =  mods.moduleIds;
      const interfaceIds =  mods.interfaceIds;
      let currentInterfaceId = mods.currentInterfaceId;
      let interfaceId;
      let hasinterfaceId = false;
      if(null != interfaceIds && interfaceIds.length>0){
        let tempArray1 = [];//临时数组1
        let tempArray2 = [];//临时数组2

        for(var i=0;i<interfaceIds.length;i++){
          tempArray1[interfaceIds[i]] = true;
        }

        for(var i=0;i<iIds.length;i++){
          if(!tempArray1[iIds[i]]){
            tempArray2.push(iIds[i]);
          }
        }
        if(null != tempArray2 && tempArray2.length>0){
          interfaceId = tempArray2[0];
          interfaceIds.push(interfaceId);
        }else{
          hasinterfaceId = true;
        }
      }else{
        interfaceId = iIds[0];
        interfaceIds.push(interfaceId);
      }
      currentInterfaceId = interfaceId;
      if(!hasinterfaceId){
        const {data: testcases} = yield call(moduleService.listtestcases, interfaceId);

        for(let i=0;i<modules.length;i++){
          let tempModule = modules[i];
          if(null != tempModule.interfaces && tempModule.interfaces.length>0){
            for(let j=0;j<tempModule.interfaces.length;j++){
              let tempInterface = tempModule.interfaces[j];
                 if(null != tempInterface && tempInterface.interfaceId == interfaceId){
                   tempModule.interfaces[j].testcases = testcases;
                 }
            }
          }
        }
        yield put({
          type: 'change',
          modules: modules,
          moduleIds: moduleIds,
          interfaceIds: interfaceIds,
          currentInterfaceId: currentInterfaceId,
        });
      }
    },
    *list({payload: moduleId}, {call, put}){
      const newInterfaces = yield call(moduleService.listinterfaces, moduleId);
      yield put({
        type: 'updateInterfaces',
        payload: newInterfaces.data,
        moduleId: moduleId
      });
    },
  },
  subscriptions: {
    setup({ dispatch, history }) {
      return history.listen(({pathname, query }) => {
        if(pathname === '/module/list'){
          const {projectId} = query;
          dispatch({ type: 'reload' , projectId});
        }
      });
    },
  }
};
