import request from '../utils/request.js'

export default {

  /**
   * 提交问题反馈
   * @param problemParam
   * @returns {*}
   */
  submitProblemFeedback(problemParam) {
    return request({
      url: '/feedback/problem',
      method: 'post',
      data: problemParam
    })
  },

  /**
   * 提交建议反馈
   * @param suggestParam
   * @returns {*}
   */
  submitSuggestFeedback(suggestParam) {
    return request({
      url: '/feedback/suggest',
      method: 'post',
      data: suggestParam
    })
  }
}