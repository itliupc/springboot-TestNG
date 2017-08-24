/**
 * Created by admin on 2017/8/10.
 */
import React from 'react';
import {Link} from 'dva/router';
import style from './top.less';
import {FormattedMessage} from 'react-intl';
import {Popover, Button, Menu, Icon} from 'antd';
import SettingMenu from '../SettingMenu.js';


const Top = ({dispatch, userName}) => {
  return (
    <header className={style.header}>
      <Link to="/"><FormattedMessage id="home.title"/></Link>
      <SettingMenu
        dispatch={dispatch}
        userName={userName}
      />
    </header>
  );
};

export default Top;
