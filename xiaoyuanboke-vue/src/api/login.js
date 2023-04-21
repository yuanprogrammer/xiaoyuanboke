import request from '@/utils/request'

export function login(username, password) {
  return request({
    url: '/system/login',
    method: 'post',
    data: {
      username,
      password
    }
  })
}

export function getInfo() {
  return request({
    url: '/system/getInfo',
    method: 'post',
  })
}

export function logout() {
  return request({
    url: '/system/logout',
    method: 'post'
  })
}

export function wxLogin() {
  return request({
    url: '/system/wx_login',
    method: 'get'
  })
}
