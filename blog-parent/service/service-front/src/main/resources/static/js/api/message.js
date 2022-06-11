import request from '../utils/request.js'

export default {

  /**
   * 查询留言列表
   * @param pageParam
   * @returns {*}
   */
  findMessageList(pageParam) {
    return request({
      url: '/homeMessage/list',
      method: 'post',
      data: pageParam
    })
  },

  /**
   * 添加留言
   * @param homeMessageParam
   * @returns {*}
   */
  addMessage(homeMessageParam) {
    return request({
      url: '/homeMessage/insert',
      method: 'post',
      data: homeMessageParam
    })
  }
}