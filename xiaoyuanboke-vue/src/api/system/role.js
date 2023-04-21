import request from "../../utils/request";

export default {
  getRoleList(param) {
    return request({
      url: '/role/list',
      method: 'post',
      data: param
    })
  },

  updateRolePermission(param) {
    return request({
      url: '/role/updatePermission',
      method: 'put',
      data: param
    })
  },

  insertRole(param) {
    return request({
      url: '/role/insertRole',
      method: 'post',
      data: param
    })
  },

  deleteRole(param) {
    return request({
      url: '/role/deleteRole',
      method: 'delete',
      data: param
    })
  }
}
