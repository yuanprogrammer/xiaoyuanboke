export default {

  /* 合法邮箱 */
  validateEmail(email) {
    return /^([a-zA-Z0-9]+[-_\.]?)+@[a-zA-Z0-9]+\.[a-z]+$/.test(email)
  },

  /* 合法号码 */
  validatePhone(phone) {
    return /^[1][3,4,5,7,8][0-9]{9}$/.test(phone)
  },

  /* 是否为空 */
  isEmpty(obj){
    return typeof obj == "undefined" || obj == null || obj === "";
  }
}