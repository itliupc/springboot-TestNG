import React from 'react';
import {connect} from 'dva';
import {Table, Button, Popconfirm, Spin, Icon, Form, Modal, Popover} from 'antd';
import {routerRedux} from 'dva/router';
import {FormattedMessage, injectIntl} from 'react-intl';
import style from './project-list-page.less';
import ProjectEditor from "../components/ProjectEditor";

const ProjectComponent = injectIntl(({dispatch, intl, projectInfo}) => {
  const content = (
    <div>
      <Button className={style.marginRight5} onClick={
        () => {
          dispatch({type: "projects/show", payload: "mod", projectInfo: projectInfo});
        }
      }><Icon type="edit" /></Button>

      <Popconfirm placement="rightTop" title={intl.formatMessage({id: "project.delConfirm"})}
                  onConfirm={
                    () => {
                      dispatch({type: "projects/delete", payload: projectInfo.projectId});
                    }
                  }>
        <Button className={style.marginLeft5}><Icon type="delete" /></Button>
      </Popconfirm>

      <Popconfirm placement="rightTop" title={intl.formatMessage({id: "project.export.confirm"})}
                  onConfirm={
                    () => {
                      dispatch({type: "projects/download", projectId: projectInfo.projectId, fileType:"excel"});
                    }
                  }
                  okText="Excel"
                  onCancel={
                    ()=>{
                      dispatch({type: "projects/download", projectId: projectInfo.projectId, fileType:"json"});
                    }}
                  cancelText="JSON">
        <Button className={style.marginLeft5}><Icon type="download" /></Button>
      </Popconfirm>
    </div>
  );
  return (
    <div className={style.project} onClick={
      () => {
        dispatch(routerRedux.push({pathname: '/module/list', query: {projectId: projectInfo.projectId}}));
      }
    }>
      <Popover content={content}>
        <div className={style.info}>
          <div className={style.title}>{projectInfo.projectName}</div>
        </div>
      </Popover>
      <div className={style.status}>
        <span className={style.creator}>{"@" + projectInfo.createUserName}</span>
      </div>
    </div>
  );
});

const ProjectListPage = ({dispatch, projects, projectInfo, intl, loading, handleType, modalKey, visible, deptName}) => {
  let projectComponents = projects.map(function(project){
      return (
        <ProjectComponent
            key = {project.projectId}
            dispatch={dispatch}
            projectInfo={project}/>
          );
      });

  const closeHandle = () => {
    dispatch({type: "projects/show", payload: ""});
  };

  let modalTitleId = {id: "project.typeAdd"};
  if(handleType === "mod"){
    modalTitleId = {id: "project.typeMod"};
  }

  let ProjectForm = Form.create()(
    (props) => {
      return <ProjectEditor
        form={props.form}
        dispatch={dispatch}
        type={handleType}
        projectInfo={projectInfo}/>
    }
  );
  return (
    <div>
      <Spin spinning={loading} tip={intl.formatMessage({id: 'loading'})}>
        <h3><span className={style.deptName}>{deptName}</span></h3>
        <div className={style.projectContainer}>
          {projectComponents}
          <div className={style.boxToAdd} onClick={
            () => {
              dispatch({type: "projects/show", payload: "add", projectInfo: {}});
            }
          }></div>
        </div>
      </Spin>
      <Modal title={intl.formatMessage(modalTitleId)}
             visible={visible}
             footer={null}
             key={modalKey}
             onCancel={closeHandle}
             maskClosable = {false}>
        <ProjectForm />
      </Modal>
    </div>
  );
};

const mapStateToProps = (state, ownProps) => {
  const projects = state.projects;
  const {deptName} = state.common;
  return {
    projects: projects.projects,
    projectInfo: projects.projectInfo,
    visible: projects.showDialog,
    modalKey: projects.modalKey,
    handleType: projects.type,
    i18n: state.i18n,
    deptName:deptName,
    loading: state.loading.effects['projects/reload']
  };
};

export default connect(mapStateToProps)(injectIntl(ProjectListPage));
