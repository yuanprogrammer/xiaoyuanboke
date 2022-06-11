import request from '@/utils/request'

export default {
  deleteMessage(id) {
    return request({
      url: `/api/homeMessage/${id}`,
      method: 'delete'
    })
  },

  deleteMoreMessage(ids) {
    return request({
      url: '/api/homeMessage',
      method: 'delete',
      data: ids
    })
  },
  
  editMessage(message) {
    return request({
      url: '/api/homeMessage',
      method: 'put',
      data: message
    })
  },

  findMessageList(pageIndex, pageSize) {
    return request({
      url: `/api/homeMessage/${pageIndex}/${pageSize}`,
      method: 'get'
    })
  }
}