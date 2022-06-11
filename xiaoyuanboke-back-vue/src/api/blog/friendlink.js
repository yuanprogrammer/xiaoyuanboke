import request from '@/utils/request'

export default{
  addFriendLink(friend) {
    return request({
      url: '/api/friendlink',
      method: 'post',
      data: friend
    })
  },

  deleteFriendLink(id) {
    return request({
      url: `/api/friendlink/${id}`,
      method: 'delete'
    })
  },

  editFriendLink(friend) {
    return request({
      url: '/api/friendlink',
      method: 'put',
      data: friend
    })
  },

  findFriendLinkList(pageIndex, pageSize) {
    return request({
      url: `/api/friendlink/${pageIndex}/${pageSize}`,
      method: 'get'
    })
  },
  
}