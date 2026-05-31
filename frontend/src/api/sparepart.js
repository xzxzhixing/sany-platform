import request from '../utils/request'

export function getSparePartPage(params) {
  return request({ url: '/api/v1/spare-part/page', method: 'get', params })
}

export function getSparePartDetail(id) {
  return request({ url: `/api/v1/spare-part/${id}`, method: 'get' })
}

export function createSparePart(data) {
  return request({ url: '/api/v1/spare-part', method: 'post', data })
}

export function updateSparePart(data) {
  return request({ url: '/api/v1/spare-part', method: 'put', data })
}

export function deleteSparePart(id) {
  return request({ url: `/api/v1/spare-part/${id}`, method: 'delete' })
}
