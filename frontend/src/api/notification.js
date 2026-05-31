import request from '../utils/request'

export function getNotificationPage(params) {
  return request({ url: '/api/v1/notification/page', method: 'get', params })
}

export function readNotification(id) {
  return request({ url: `/api/v1/notification/${id}/read`, method: 'put' })
}

export function readAllNotification() {
  return request({ url: '/api/v1/notification/read-all', method: 'put' })
}

export function deleteNotification(id) {
  return request({ url: `/api/v1/notification/${id}`, method: 'delete' })
}
