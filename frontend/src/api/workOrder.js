import request from '../utils/request'

export function getWorkOrderPage(params) {
  return request({ url: '/api/v1/work-order/page', method: 'get', params })
}

export function getWorkOrderDetail(id) {
  return request({ url: `/api/v1/work-order/${id}`, method: 'get' })
}

export function createWorkOrder(data) {
  return request({ url: '/api/v1/work-order', method: 'post', data })
}

export function updateWorkOrder(data) {
  return request({ url: '/api/v1/work-order', method: 'put', data })
}

export function dispatchWorkOrder(id, assigneeId) {
  return request({ url: `/api/v1/work-order/${id}/dispatch`, method: 'put', params: { assigneeId } })
}

export function updateWorkOrderStatus({ id, newStatus, oldStatus }) {
  return request({ url: `/api/v1/work-order/${id}/status`, method: 'put', params: { newStatus, oldStatus } })
}

export function deleteWorkOrder(id) {
  return request({ url: `/api/v1/work-order/${id}`, method: 'delete' })
}
