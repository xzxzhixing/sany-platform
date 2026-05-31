import request from '../utils/request'

export function getReportPage(params) {
  return request({ url: '/api/v1/report/page', method: 'get', params })
}

export function getReportDetail(id) {
  return request({ url: `/api/v1/report/${id}`, method: 'get' })
}

export function createReport(data) {
  return request({ url: '/api/v1/report', method: 'post', data })
}

export function deleteReport(id) {
  return request({ url: `/api/v1/report/${id}`, method: 'delete' })
}
