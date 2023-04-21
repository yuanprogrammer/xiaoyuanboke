import request from '@/utils/request'

export default{
  addFriendLink(friend) {
    return request({
      url: '/friendlink',
      method: 'post',
      data: friend
    })
  },

  deleteFriendLink(id) {
    return request({
      url: `/friendlink/${id}`,
      method: 'delete'
    })
  },

  editFriendLink(friend) {
    return request({
      url: '/friendlink',
      method: 'put',
      data: friend
    })
  },

  findFriendLinkList(pageIndex, pageSize) {
    return request({
      url: `/friendlink/${pageIndex}/${pageSize}`,
      method: 'get'
    })
  },

}
