import React from 'react';
import { connect } from 'dva';
import { Table, Button, Popconfirm, Spin, Icon, Modal, Form, Collapse } from 'antd';
import { FormattedMessage, injectIntl } from 'react-intl';
import { routerRedux } from 'dva/router';
import * as constants from '../utils/constants';

const Panel = Collapse.Panel;
console.log("AAAAAAAAAA");
debugger;
const InterfaceListPage = ({dispatch, moduleIds, intl, userRole, userAuthority, loading}) => {
    debugger;
    dispatch({type: "interface/list", payload: moduleIds});

    return (<div>TTTTTTTT</div>);

};

const mapStateToProps = (state, ownProps) => {
    debugger;
    const {userRole = constants.USER_ROLE_STUDENT} = ownProps.location.query;
    const {userAuthority = constants.USER_AUTHORITY_NORMAL} = state.common;
    const loading = state.loading.effects['interfaces/reload'];
    const moduleIds = state.moduleIds;
    return ({...state, moduleIds: moduleIds, userRole: parseInt(userRole), userAuthority: parseInt(userAuthority), loading});
};

export default connect(mapStateToProps)(injectIntl(InterfaceListPage));
