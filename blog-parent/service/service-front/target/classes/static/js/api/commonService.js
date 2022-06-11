import request from '../utils/request.js'

export default {

  /**
   * 发送邮箱验证码
   * @param loginParam
   * @returns {*}
   */
  sendEmailCode(email) {
    return request({
      url: '/service/code/email',
      method: 'post',
      data: {
        'email': email
      }
    })
  },

  /**
   * 发送短信验证码
   * @param phone
   * @returns {*}
   */
  sendSmsCode(phone) {
    return request({
      url: '/service/code/sms',
      method: 'post',
      data: {
        'phone': phone
      }
    })
  },

  getSmsCodePermission(phone) {
    return request({
      url: '/service/code/sms/request',
      method: 'post',
      data: {
        'phone': phone
      }
    })
  },

  getEmailCodePermission(email) {
    return request({
      url: '/service/code/email/request',
      method: 'post',
      data: {
        'email': email
      }
    })
  }
}