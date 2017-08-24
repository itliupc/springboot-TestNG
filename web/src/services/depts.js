/**
 * Created by admin on 2017/7/26.
 */
import request from '../utils/request';

const JSON = window.JSON;

export function listDept() {
  return request(`api/v1/depts`, {
    method: 'GET'
  });
}

export function addDept(deptInfo) {
  return request('api/v1/dept', {
    method: 'POST',
    body: JSON.stringify(deptInfo)
  });
}

export function editDept(deptInfo) {
  return request('api/v1/dept', {
    method: 'PUT',
    body: JSON.stringify(deptInfo)
  });
}

export function removeDept(deptId) {
  return request(`api/v1/dept/${deptId}`, {
    method: 'DELETE'
  });
}

