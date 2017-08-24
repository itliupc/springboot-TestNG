/**
 * Created by admin on 2017/7/25.
 */
import * as i18n from '../i18n';
import {CHINESE} from '../utils/constants';


export default{
  namespace: 'i18n',
  state: {
    locale: CHINESE,
    messages: null
  },
  reducers: {
    update(state, {payload: {locale, messages}}){
      return Object.assign({}, state, {locale, messages});
    }
  },
  effects: {
    *setLocale({locale}, {put}){
      yield put({
        type: 'update',
        payload: {
          locale: locale,
          messages: i18n[locale.split("-").join("_")]
        }
      })
    }
  },
  subscriptions: {
    init({dispatch}){//Model加载时，初始化locale配置以及对应的messages信息
      let language = CHINESE;
      const type = navigator.appName;
      if (type === 'Netscape') {
        language = navigator.language
      } else {
        language = navigator.userLanguage
      }
      let sec = language.split('-');//部分浏览器返回的language是zh-cn，需要将ch换成大写的CH
      sec[1] = sec[1].toUpperCase();
      language = sec.join('-');
      dispatch({type: 'setLocale', locale: language});
    }
  }
}
