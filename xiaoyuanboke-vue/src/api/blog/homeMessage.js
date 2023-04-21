import request from '@/utils/request'

export default {
  deleteMessage(id) {
    return request({
      url: `/homeMessage/${id}`,
      method: 'delete'
    })
  },

  deleteMoreMessage(ids) {
    return request({
      url: '/homeMessage',
      method: 'delete',
      data: ids
    })
  },

  editMessage(message) {
    return request({
      url: '/homeMessage',
      method: 'put',
      data: message
    })
  },

  findMessageList(pageIndex, pageSize) {
    return request({
      url: `/homeMessage/${pageIndex}/${pageSize}`,
      method: 'get'
    })
  }
}
