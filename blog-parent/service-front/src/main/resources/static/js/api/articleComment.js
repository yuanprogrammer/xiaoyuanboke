import request from '../utils/request.js'

export default {

  /**
   * 添加一条评论
   * @param commentParam
   * @returns {*}
   */
  addComment(commentParam) {
    return request({
      url: '/article/comment',
      method: 'post',
      data: commentParam
    })
  },

  /**
   * 删除一条评论
   * @param number
   * @returns {*}
   */
  deleteComment(number){
    return request({
      url: '/article/comment',
      method: 'delete',
      data: {
        number
      }
    })
  },

  /**
   * 编辑一条评论
   * @param commentParam
   * @returns {*}
   */
  editComment(commentParam) {
    return request({
      url: '/article/comment',
      method: 'put',
      data: commentParam
    })
  },

  /**
   * 查询文章评论
   * @param param
   * @returns {*}
   */
  findCommentList(param) {
    return request({
      url: '/article/comment/list',
      method: 'post',
      data: param
    })
  }
}
