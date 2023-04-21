import request from "../../utils/request";

export default {
  getSysUserList(param) {
    return request({
      url: '/system/getSysUserList',
      method: 'post',
      data: param
    })
  }
}
