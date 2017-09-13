import React from 'react';
import {connect} from 'dva';
import moment from 'moment'
import {Table, Button, Popconfirm, Spin, Icon, Form, Modal} from 'antd';
import {routerRedux} from 'dva/router';
import {FormattedMessage, injectIntl} from 'react-intl';
import DeptEditor from "../components/DeptEditor";

const DeptListPage = ({dispatch, depts, intl, loading, deptInfo, handleType, modalKey, visible}) => {
  const columns = [
    {
      title: intl.formatMessage({id: "dept.deptName"}),
      dataIndex: 'deptName'
    },
    {
      title: intl.formatMessage({id: "dept.deptCode"}),
      dataIndex: 'deptCode'
    },
    {
      title: intl.formatMessage({id: "dept.createTime"}),
      dataIndex: 'createTime',
      render: (text, record) => (moment(record.createTime).format("YYYY-MM-DD HH:mm:ss"))
    },
    {
      title: intl.formatMessage({id: "dept.operate"}),
      render: (text, record) => (
        <Button.Group type="ghost">
          <Button title={intl.formatMessage({id: "dept.editDept"})} size="small" onClick={
            () => {
              dispatch({type: "depts/show", payload: "mod", deptInfo: record});
            }
          }><Icon type="edit"/></Button>
          <Popconfirm
            title={intl.formatMessage({id: "dept.confirm.deleteDept"})}
            onConfirm={
              () => {
                dispatch({type: "depts/delete", payload: record.deptId});
              }
            }>
            <Button title={intl.formatMessage({id: "dept.deleteDept"})} size="small"><Icon type="delete"/></Button>
          </Popconfirm>
        </Button.Group>
      )
    }
  ];

  const closeHandle = () => {
    dispatch({type: "depts/show", payload: ""});
  };

  let modalTitleId = {id: "dept.typeAdd"};
  if(handleType === "mod"){
    modalTitleId = {id: "dept.typeMod"};
  }

  let DeptForm = Form.create()(
    (props) => {
      return <DeptEditor
        form={props.form}
        dispatch={dispatch}
        type={handleType}
        deptInfo={deptInfo}/>
    }
  );

  return (
    <div>
      <div>
        <Button type="primary" onClick={() => {
          dispatch({type: "depts/show", payload: "add", deptInfo: {}});
        }
        }><FormattedMessage id="dept.addDept"/></Button>
      </div>
      <Spin spinning={loading} tip={intl.formatMessage({id: 'loading'})}>
        <Table
          columns={columns}
          dataSource={depts}
          rowKey={row => row.deptId}
        />
      </Spin>
      <Modal title={intl.formatMessage(modalTitleId)}
             visible={visible}
             footer={null}
             key={modalKey}
             onCancel={closeHandle}
             maskClosable = {false}>
        <DeptForm/>
      </Modal>
    </div>
  );
};

const mapStateToProps = (state, ownProps) => {
  const depts = state.depts;
  return {
    depts: depts.depts,
    deptInfo: depts.deptInfo,
    visible: depts.showDialog,
    modalKey: depts.modalKey,
    handleType: depts.type,
    i18n: state.i18n,
    loading: state.loading.effects['depts/reload']
  };
};

export default connect(mapStateToProps)(injectIntl(DeptListPage));
