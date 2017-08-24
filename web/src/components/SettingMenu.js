/**
 * Created by admin on 2017/8/17.
 */
import React from 'react';
import styles from './SettingMenu.less';
import {FormattedMessage} from 'react-intl';
import {Popover, Button, Menu} from 'antd';
import {CHINESE, ENGLISH} from '../utils/constants';

const SubMenu = Menu.SubMenu;

const SettingMenu = ({dispatch, userName}) => {

  let content = (
    <Menu mode="inline" className={styles.menu} onClick={
      ({item, key, keyPath}) => {
        switch (key) {
          case "2.1":
            dispatch({type: 'i18n/setLocale', locale: CHINESE});
            break;
          case "2.2":
            dispatch({type: 'i18n/setLocale', locale: ENGLISH});
            break;
          case "3":
            dispatch({type: 'common/logout'});
            break;
          default:
            break;
        }
      }
    }>
      <Menu.Item key="1" ><FormattedMessage id="home.username"/>:
        <b>{userName}</b></Menu.Item>
      <SubMenu key="2" title={<FormattedMessage id="home.language"/>}>
        <Menu.Item key="2.1" >中文</Menu.Item>
        <Menu.Item key="2.2" >English</Menu.Item>
      </SubMenu>
      <Menu.Item key="3" ><FormattedMessage id="home.logout"/></Menu.Item>
    </Menu>
  );

  return (
    <Popover content={content} trigger="click" placement="bottomRight" className="testtest">
      <Button type="primary" icon="setting" className={styles.btn}>{userName}</Button>
    </Popover>
  );
};

export default SettingMenu;
