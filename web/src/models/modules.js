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
    addModuleModalVisible: false,
    modalKey:'',
    projectId:'',
    currentModule: {},
    activeKey : '',
    displayInterfaceCreateDia: false,
    refreshModule:false,
  },
  reducers: {
    update(state, {modules,projectId:projectId}) {
      state.modules = modules;
      state.moduleIds = [];
      state.interfaceIds = [];
      state.projectId = projectId;
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
      modules.map(module =>{
        if(module.moduleId == moduleId){
          module.interfaceViews = newInterfaces;
        }
      });
      state.flag = !state.flag;
      state.modules = modules;
      return state;
    },

    changeShow(state){
      state.addModuleModalVisible = !state.addModuleModalVisible;
      state.modalKey = Math.random();
      state.currentModule = {};
      return state;
    },

    addModule(state, {payload:newModule}){
      state.modules.push(newModule);
      state.addModuleModalVisible = !state.addModuleModalVisible;
      state.modalKey = Math.random();
      state.currentModule = {};
      state.flag = !state.flag;
      return state;
    },

    editModule(state, {payload:newModule}){
      state.modules.map(module => {
        if(module.moduleId == newModule.moduleId){
          module.moduleName = newModule.moduleName;
          module.run = newModule.run;
        }
      });
      state.addModuleModalVisible = !state.addModuleModalVisible;
      state.modalKey = Math.random();
      state.currentModule = {};
      state.flag = !state.flag;
      return state;
    },

    deleteModule(state, {payload:moduleId}){
      let newModules = [];
      state.modules.map(module => {
        if(module.moduleId != moduleId){
          newModules.push(module);
        }
      });
      state.modules = newModules;
      state.flag = !state.flag;
      return state;
    },

    deleteInterface(state, {moduleId: moduleId, interfaceId: interfaceId}){
      state.modules.map(module => {
        if(module.moduleId == moduleId){
          let newInterface = [];
          module.interfaceViews.map(face => {
            if(face.interfaceId != interfaceId){
              newInterface.push(face);
            }
          });

          module.interfaceViews = newInterface;
        }
      });
      state.flag = !state.flag;
      return state;
    },

    updateCurrentModule(state, {payload: moduleId}){
      state.modules.map(module => {
        if(module.moduleId == moduleId){
          state.currentModule = module;
        }
      });
      state.addModuleModalVisible = !state.addModuleModalVisible;
      state.modalKey = Math.random();
      return state;
    },

    updateAcKey(state, {payload: moduleId}){
      state.activeKey = moduleId;
      return state;
    },

    updateTestCase(state, {moduleId:moduleId, interfaceId: interfaceId, testCases: testCase}){
      state.modules.map(module => {
        if(module.moduleId == moduleId){
          module.interfaceViews.map(face =>{
            if(face.interfaceId == interfaceId){
              face.testCaseViews = testCase;
            }
          });
        }
      });
      state.flag = !state.flag;
      return state;
    },

    duplicateInterface(state, {moduleId: moduleId, newInterface:newInterface}){
      state.modules.map(module => {
        if(module.moduleId == moduleId){
          module.interfaceViews.push(newInterface);
        }
      });
      return state;
    }
  },
  effects: {
    *reload({projectId}, { select, call, put}) {
      const {userAuthority, userId, userRole} = yield select(state => state.common);
      const result = yield call(moduleService.listmodules, projectId);
      const modules = result.data;
      yield put({
        type: 'update',
        modules: modules,
        projectId: projectId
      });
    },
    *interfacelist({payload: mIds}, {select, call, put}){
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
    *show({payload: payload}, {put}){
      yield put({ type: 'changeShow' , payload: payload});
    },
    *add({payload: values}, {call, put}){
      const result = yield call(moduleService.addModule, values);
      yield put({ type: 'addModule' , payload: result.data});
    },
    *edit({payload: values}, {call, put}){
      const result = yield call(moduleService.editModule, values);
      yield put({ type: 'editModule' , payload: result.data});
    },
    *deleteMo({payload: moduleId}, {call, put}){
      yield call(moduleService.deleteModule, moduleId);
      yield put({ type: 'deleteModule' , payload: moduleId});
    },
    *deleteIn({moduleId: moduleId, interfaceId: interfaceId}, {call, put}){
      yield call(moduleService.deleteInterface, interfaceId);
      yield put({ type: 'deleteInterface' , moduleId: moduleId, interfaceId: interfaceId});
    },

    *showCurrentModule({payload: moduleId}, {put}){
      yield put({ type: 'updateCurrentModule' , payload: moduleId});
    },

    *updateActiveKey({payload: moduleId}, {put}){
      yield put({ type: 'updateAcKey' , payload: moduleId});
    },

    *testCaseList({moduleId : moduleId, interfaceId: interfaceId}, {call, put}){
      const result = yield call(moduleService.testCaseList, interfaceId);
      yield put({ type: 'updateTestCase' , moduleId:moduleId, interfaceId: interfaceId, testCases: result.data});
    },

    *duplicate({moduleId : moduleId, interfaceId: interfaceId}, {call, put}){
      const result = yield call(moduleService.duplicateInterface, interfaceId);
      yield put({ type: 'duplicateInterface', moduleId:moduleId, newInterface: result.data});
    }
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
