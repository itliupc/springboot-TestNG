/**
 * Created by wafer on 2017/7/25.
 */
import request from '../utils/request';

const JSON = window.JSON;

export function getUsers() {
  return request(`api/v1/users`, {
    method: 'GET'
  });
}

export function editUser(user) {
  return request(`api/v1/user`, {
    method: 'PUT',
    body: JSON.stringify(user)
  });
}

export function delUser(id){
  return request(`api/v1/user/${id}`, {
    method: 'DELETE'
  });
}

export function addUser(user){
  return request(`api/v1/user`, {
    method: 'POST',
    body: JSON.stringify(user)
  });
}
