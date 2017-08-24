/**
 * Created by admin on 2017/7/26.
 */
import request from '../utils/request';
import * as constants from '../utils/constants';

const JSON = window.JSON;

export function listProject({userAuthority, deptId}) {
  if(userAuthority === constants.USER_AUTHORITY_ADMIN) {
    return request(`api/v1/projects`, {
      method: 'GET'
    });
  }else{
    return request(`api/v1/projects/${deptId}`, {
      method: 'GET'
    });
  }
}

export function addProject(projectInfo) {
  return request('api/v1/project', {
    method: 'POST',
    body: JSON.stringify(projectInfo)
  });
}

export function editProject(projectInfo) {
  return request('api/v1/project', {
    method: 'PUT',
    body: JSON.stringify(projectInfo)
  });
}

export function deleteProject({userId, projectId}) {
  return request(`api/v1/project/${userId}/${projectId}`, {
    method: 'DELETE'
  });
}

