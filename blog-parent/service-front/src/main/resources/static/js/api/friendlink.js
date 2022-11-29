import request from '../utils/request.js'

export default {
  findFriendLinkAll() {
    return request({
      url: '/friendlink/list',
      method: 'get'
    })
  }
}