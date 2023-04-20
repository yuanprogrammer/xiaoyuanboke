import request from "../utils/request.js";

export default {
  login(loginParam) {
    return request({
      url: '/user/login',
      method: 'post',
      data: loginParam
    })
  },

  register(loginParam) {
    return request({
      url: '/user/register',
      method: 'post',
      data: loginParam
    })
  },

  editNickname(param) {
    return request({
      url: '/user/nickname/modify',
      method: 'put',
      data: param
    })
  },

  findPassword(param) {
    return request({
      url: '/user/password/find',
      method: 'put',
      data: param
    })
  },

  editPassword(loginParam) {
    return request({
      url: '/user/password/modify',
      method: 'put',
      data: loginParam
    })
  },

  editEmail(param) {
    return request({
      url: '/user/modify/email',
      method: 'put',
      data: param
    })
  },

  editMobileNumber(param) {
    return request({
      url: '/user/modify/mobileNumber',
      method: 'put',
      data: param
    })
  },

  getUserInfo() {
    return request({
      url: '/user/info',
      method: 'post',
    })
  },

  logout() {
    return request({
      url: '/user/logout',
      method: 'post',
    })
  },
}