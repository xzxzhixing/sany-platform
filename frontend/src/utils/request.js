import axios from 'axios'
import { Message } from 'element-ui'

const request = axios.create({
  baseURL: '/monitor',
  timeout: 15000
})

request.interceptors.request.use(config => {
  const token = sessionStorage.getItem('token')
  if (token) config.headers.Authorization = `Bearer ${token}`
  return config
}, e => Promise.reject(e))

request.interceptors.response.use(res => {
  if (res.data.code !== 200) {
    Message.error(res.data.message || '请求失败')
    return Promise.reject(new Error(res.data.message))
  }
  return res.data
}, err => {
  if (err.response) {
    const s = err.response.status
    if (s === 401) { Message.error('登录过期'); sessionStorage.clear(); window.location.href = '/monitor/' }
    else if (s === 403) Message.error('无权限')
    else Message.error(err.response.data?.message || `请求失败(${s})`)
  } else Message.error('网络异常')
  return Promise.reject(err)
})

export default request
