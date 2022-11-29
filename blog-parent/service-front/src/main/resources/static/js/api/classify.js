import request from '../utils/request.js'

export default {
  findCategoryList() {
    return request({
      url: '/classify/list',
      method: 'get',
    })
  },
}