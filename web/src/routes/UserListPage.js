import React from 'react';
import moment from 'moment'
import { connect } from 'dva';
import { Table, Button, Popconfirm, Spin, Icon, Modal, Form } from 'antd';
import { routerRedux } from 'dva/router';
import { FormattedMessage, injectIntl } from 'react-intl';
import UserEditor from "../components/UserEditor";

const UserListPage = ({ dispatch, userList, deptList, userInfo, intl, loading, visible, modalKey, handleType}) => {
  const columns = [
    {
      title: intl.formatMessage({id: "user.userName"}),
      dataIndex: 'userName'
    },
    {
      title: intl.formatMessage({id: "user.dept"}),
      dataIndex: 'deptName'
    },
    {
      title: intl.formatMessage({id: "user.emailAddr"}),
      dataIndex: 'email'
    },
    {
      title: intl.formatMessage({id: "user.lastAccess"}),
      dataIndex: 'latestLoginTime',
      render: (text, record) => record.latestLoginTime ? (moment(record.latestLoginTime).format("YYYY-MM-DD HH:mm:ss")) : ""
    },
    {
      title: intl.formatMessage({id: "user.operation"}),
      render: (text, record) => {
        return (
          <Button.Group type="ghost">
            <Button title={intl.formatMessage({id: "user.editUser"})}  size="small" onClick={
              () => {
              dispatch({type: "users/show", payload: "mod", userInfo: record});
              }
            }><Icon type="edit" /></Button>
            <Popconfirm title={intl.formatMessage({id: "user.delConfirm"})}
                        onConfirm={
                          () => {
                            dispatch({type: "users/delete", payload: record.userId});
                          }
                        }>
              <Button title={intl.formatMessage({id: "user.delUser"})} size="small"><Icon type="delete" /></Button>
            </Popconfirm>
          </Button.Group>
        );
      }
    }
  ];

  const closeHandle = () => {
    dispatch({type: "users/show", payload: ""});
  };

  let modalTitleId = {id: "user.typeAdd"};
  if(handleType === "mod"){
    modalTitleId = {id: "user.typeMod"};
  }

  let UserForm = Form.create()(
    (props) => {
      return <UserEditor
        form={props.form}
        dispatch={dispatch}
        type={handleType}
        deptList={deptList}
        userInfo={userInfo}/>
    }
  );

  return (
    <div>
      <Button type="primary" onClick={
              () => {
                dispatch({type: "users/show", payload: "add", userInfo: {}});
              }}
      ><FormattedMessage id="user.addUser" /></Button>
      <Spin spinning={loading} tip={intl.formatMessage({id: 'loading'})}>
        <Table columns={columns} dataSource={userList} rowKey={row => row.userId} />
      </Spin>
      <Modal title={intl.formatMessage(modalTitleId)}
             visible={visible}
             footer={null}
             key={modalKey}
             onCancel={closeHandle}
             maskClosable = {false}>
        <UserForm/>
      </Modal>
    </div>
  );
};

const mapStateToProps = state => {
  const users = state.users;
  return { userList: users.users,
           deptList: state.depts.depts,
           userInfo: users.userInfo,
           i18n:state.i18n,
           loading: state.loading.effects['users/reload'],
           visible: users.showDialog,
           modalKey: users.modalKey,
           handleType: users.type
  };
};

export default connect(mapStateToProps)(injectIntl(UserListPage));
