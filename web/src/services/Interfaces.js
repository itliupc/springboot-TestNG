/**
 * Created by wafer on 2017/7/25.
 */
import request from '../utils/request';

const JSON = window.JSON;

export function interfaceinfo(interfaceId) {
  return request(`api/v1/interface/testcase/${interfaceId}`, {
    method: 'GET'
  });
}

export function listinterfaces(moduleId) {
  return request(`api/v1/interface/module/${moduleId}`, {
    method: 'GET'
  });
}

export function addinterfaces(interfacevalues) {
  return request(`api/v1/interface`, {
    method: 'POST',
    body: JSON.stringify(interfacevalues)
  });
}


export function editinterfaces(interfacevalues) {
  return request(`api/v1/interface`, {
    method: 'PUT',
    body: JSON.stringify(interfacevalues)
  });
}

export function addTestCase(testCase) {
  return request(`api/v1/interfacecase`, {
    method: 'POST',
    body: JSON.stringify(testCase)
  });
}

export function editTestCase(testCase) {
  return request(`api/v1/interfacecase`, {
    method: 'PUT',
    body: JSON.stringify(testCase)
  });
}

export function addInterfaceRequestParam(requestParam) {
  return request(`api/v1/interface/requestParam`, {
    method: 'POST',
    body: JSON.stringify(requestParam)
  });
}

export function editInterfaceRequestParam(requestParam) {
  return request(`api/v1/interface/requestParam`, {
    method: 'PUT',
    body: JSON.stringify(requestParam)
  });
}

export function addInterfaceResponseParam(responseParam) {
  return request(`api/v1/interface/responseParam`, {
    method: 'POST',
    body: JSON.stringify(responseParam)
  });
}

export function editInterfaceResponseParam(responseParam) {
  return request(`api/v1/interface/responseParam`, {
    method: 'PUT',
    body: JSON.stringify(responseParam)
  });
}

export function deleteRequestParam(interfaceId, requestParam) {
  return request(`api/v1/interface/requestParam/${requestParam.requestParamId}/${interfaceId}`, {
    method: 'DELETE',
  });
}

export function deleteResponseParam(interfaceId, responseParam) {
  return request(`api/v1/interface/responseParam/${responseParam.responseParamId}/${interfaceId}`, {
    method: 'DELETE',
  });
}
