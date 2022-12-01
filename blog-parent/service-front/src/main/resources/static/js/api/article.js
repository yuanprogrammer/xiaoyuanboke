import request from '../utils/request.js'

export default {

  /**
   * 查询热门文章
   * @returns {*}
   */
  findHostArticle() {
    return request({
      url: '/article/host',
      method: 'post'
    })
  },

  /**
   * 查询最新文章
   * @returns {*}
   */
  findNewArticle() {
    return request({
      url: '/article/new',
      method: 'post'
    })
  },

  /**
   * 查询文章列表
   * @param param
   * @returns {*}
   */
  findArticleList(param) {
    return request({
      url: '/article/list',
      method: 'post',
      data: param
    })
  },

  /**
   * 查询分类文章列表
   * @param param
   * @returns {*}
   */
  findCategoryArticleList(param) {
    return request({
      url: '/article/category/list',
      method: 'post',
      data: param
    })
  },

  findArchivesArticleList() {
    return request({
      url: '/article/archives/list',
      method: 'post'
    })
  },

  /**
   * 查询文章详细内容
   * @param number
   * @returns {obj}
   */
  findArticleDetail(number) {
    return request({
      url: `/article/detail/${number}`,
      method: 'get',
    })
  },

  /**
   * 查询文章markdown文本
   * @param number
   * @returns {string}
   */
  findArticleMarkdown(number) {
    return request({
      url: '/article/markdown/' + number,
      method: 'get',
    })
  },

  /**
   * 文章点赞
   * @param number
   * @returns {*}
   */
  likeArticle(number) {
    return request({
      url: `/article/like`,
      method: 'post',
      data: {
        'number': number
      }
    })
  },

  getLikeInfo(number) {
    return request({
      url: '/article/like/info',
      method: 'post',
      data: {
        'number': number
      }
    })
  },

  /**
   * 收藏文章
   * @param number
   * @returns {*}
   */
  collectArticle(number) {
    return request({
      url: `/article/collect`,
      method: 'post',
      data: {
        'number': number
      }
    })
  },

  getCollectInfo(number) {
    return request({
      url: '/article/collect/info',
      method: 'post',
      data: {
        'number': number
      }
    })
  }
}
