import request from '../utils/request'

export function getEmployeePage(params) {
  return request({ url: '/api/v1/employee/page', method: 'get', params })
}

export function getEmployeeDetail(id) {
  return request({ url: `/api/v1/employee/${id}`, method: 'get' })
}

export function createEmployee(data) {
  return request({ url: '/api/v1/employee', method: 'post', data })
}

export function updateEmployee(data) {
  return request({ url: '/api/v1/employee', method: 'put', data })
}

export function deleteEmployee(id) {
  return request({ url: `/api/v1/employee/${id}`, method: 'delete' })
}
