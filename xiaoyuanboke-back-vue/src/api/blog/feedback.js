import request from '@/utils/request'

export default {

  /**
   * 查询问题反馈列表
   * @param {number} pageIndex 
   * @param {number} pageSize 
   * @returns 
   */
  findProblemFeedbackList(pageIndex, pageSize) {
    return request({
      url: `/api/feedback/problem/${pageIndex}/${pageSize}`,
      method: 'get',
    })
  },

  /**
   * 修改问题状态
   * @param {object} problem 
   * @returns 
   */
  editProblemState(problem) {
    return request({
      url: '/api/feedback/problem',
      method: 'put',
      data: problem
    })
  },

  /**
   * 删除问题
   * @param {string} id 
   * @returns 
   */
  deleteProblem(id) {
    return request({
      url: `/api/feedback/problem/${id}`,
      method: 'delete'
    })
  },

  /**
   * 邮件通知用户问题已经解决
   * @param {object} problem 
   * @returns 
   */
   noticeUser(problem) {
    return request({
      url: '/api/feedback/problem/notice',
      method: 'post',
      data: problem
    })
  },

  /**
   * 查询建议反馈列表
   * @param {number} pageIndex 
   * @param {*} pageSize 
   * @returns 
   */
  findSuggestFeedbackList(pageIndex, pageSize) {
    return request({
      url: `/api/feedback/suggest/${pageIndex}/${pageSize}`,
      method: 'get'
    })
  },

  /**
   * 删除建议
   * @param {number} id 
   * @returns 
   */
  deleteSuggest(id) {
    return request({
      url: `/api/feedback/suggest/${id}`,
      method: 'delete'
    })
  }
}