/**
 * Created by wafer on 2017/7/25.
 */
import request from '../utils/request';

const JSON = window.JSON;

export function listinterfaces(moduleId) {
  return request(`api/v1/interface/module/${moduleId}`, {
    method: 'GET'
  });
}
