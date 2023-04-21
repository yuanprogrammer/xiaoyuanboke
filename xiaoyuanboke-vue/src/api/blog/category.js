import request from '@/utils/request'

export default {
  addCategory(categoryParam) {
    return request({
      url: '/category',
      method: 'post',
      data: categoryParam
    })
  },

  deleteCategory(id) {
    return request({
      url: `/category/${id}`,
      method: 'delete',
    })
  },

  editCategory(category) {
    return request({
      url: '/category',
      method: 'put',
      data: category
    })
  },

  moveCategory(category) {
    return request({
      url: '/category/move',
      method: 'put',
      data: category
    })
  },

  getCategoryList() {
    return request({
      url: '/category',
      method: 'get'
    })
  },

  findParentCategory() {
    return request({
      url: '/category/parent',
      method: 'get'
    })
  }
}
