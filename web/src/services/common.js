/**
 * Created by Administrator on 2017/8/16 0016.
 */
import request from '../utils/request';

const json = window.json;
export function login(values){
    console.log(values);
    return request('api/v1/login',{
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(values),
    });
}

export function logout(account) {
  return request('api/v1/logout', {
    method: 'POST',
    body: JSON.stringify({account}),
  });
}
