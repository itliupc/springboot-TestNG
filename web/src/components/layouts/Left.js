/**
 * Created by admin on 2017/8/11.
 */
import React from 'react';
import {Link} from 'dva/router';
import {FormattedMessage} from 'react-intl';
import {USER_AUTHORITY_NORMAL, USER_AUTHORITY_ADMIN} from '../../utils/constants';
import {Menu, Icon} from 'antd';

const SubMenu = Menu.SubMenu;
const MenuItem = Menu.Item;

const Left = ({userAuthority}) => {

  let subMenu = [];
  if (userAuthority === USER_AUTHORITY_ADMIN) {
    subMenu.push((
      <SubMenu key="user" title={<span><Icon type="user"/><FormattedMessage id="home.userManager"/></span>}>
        <MenuItem key="dept-list">
          <Link to="/dept/list"><FormattedMessage id="home.deptList"/></Link>
        </MenuItem>
        <MenuItem key="user-list">
          <Link to="/user/list"><FormattedMessage id="home.userList"/></Link>
        </MenuItem>
      </SubMenu>
    ));
  }
  subMenu.push((
      <SubMenu key="project" title={<span><Icon type="team"/><FormattedMessage id="home.projectManager"/></span>}>
        <MenuItem key="project-list">
          <Link to="/project/list"><FormattedMessage id="home.projectList"/></Link>
        </MenuItem>
      </SubMenu>
  ));

  return (
    <Menu mode="inline" theme="dark" style={{width: '240px'}}>
      {subMenu}
    </Menu>
  );
};

export default Left;
