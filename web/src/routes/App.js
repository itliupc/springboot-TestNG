/**
 * Created by admin on 2017/7/25.
 */
import React from 'react';
import { connect } from 'dva';
import {IntlProvider} from 'react-intl';
import {LocaleProvider} from 'antd';
import {ENGLISH} from '../utils/constants';
import enUS from 'antd/lib/locale-provider/en_US';

const App = ({children, i18n}) => {
  const {locale, messages} = i18n;
  return (
    <IntlProvider locale={locale} messages={messages}>
      <LocaleProvider locale={locale === ENGLISH ? enUS : null}>
        {children}
      </LocaleProvider>
    </IntlProvider>
  );
};

export default connect(state => state)(App)
