import request from '@/utils/request'

export default {
  getPermissionList() {
    return request({
      url: '/permission/list',
      method: 'get'
    })
  },

  getPermissionListByRoleKey(param) {
    return request({
      url: '/permission/list',
      method: 'post',
      data: param
    })
  },

  selectModuleList(param) {
    return request({
      url: '/permission/selectModule',
      method: 'post',
      data: param
    })
  },

  insertModule(param) {
    return request({
      url: '/permission/insertModule',
      method: 'post',
      data: param
    })
  },

  updateModule(param) {
    return request({
      url: '/permission/updateModule',
      method: 'put',
      data: param
    })
  },

  deleteModule(param) {
    return request({
      url: '/permission/deleteModule',
      method: 'delete',
      data: param
    })
  },

  selectActionList(param) {
    return request({
      url: '/permission/selectAction',
      method: 'post',
      data: param
    })
  },

  insertAction(param) {
    return request({
      url: '/permission/insertAction',
      method: 'post',
      data: param
    })
  },

  updateAction(param) {
    return request({
      url: '/permission/updateAction',
      method: 'put',
      data: param
    })
  },

  deleteAction(param) {
    return request({
      url: '/permission/deleteAction',
      method: 'delete',
      data: param
    })
  },
}
