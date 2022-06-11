import request from '@/utils/request'

export default {

  /**
   * 2.查询所有的文章分类专栏
   * @returns 
   */
  findCategoryList() {
    return request({
      url: '/api/category',
      method: 'get',
    })
  },

  /**
   * 3.发布文章
   * @param {*} article 
   * @returns 
   */
  publishArticle(article) {
    return request({
      url: '/api/article/publish',
      method: 'post',
      data: article
    })
  },

  /**
   * 4.发送分页查询文章请求
   * @param {number} pageIndex 当前页
   * @param {number} pageSize 一页的记录数
   * @returns 文章列表
   */
  findArticleList(pageIndex, pageSize) {
    return request({
      url: `/api/article/${pageIndex}/${pageSize}`,
      method: 'get',
    })
  },

  /**
   * 5.根据文章的编号查询所属分类
   * @param {string} articleId 
   * @returns 
   */
  getArticleCategoryById(articleId) {
    return request({
      url: `/api/category/${articleId}/article`,
      method: 'get'
    })
  },

  /**
   * 6.删除文章
   * @param {string} articleId 
   * @returns 删除文章结果
   */
  removeArticleById(articleId) {
    return request({
      url: `/api/article/${articleId}`,
      method: 'delete',
    })
  },

  /**
   * 7.获取文章详细内容
   * @param {string} articleId 
   * @returns 
   */
  getArticleDetailContent(articleId) {
    return request({
      url: `/api/article/detail/${articleId}`,
      method: 'get'
    })
  },

  /**
   * 8.获取文章编辑内容数据
   * @param {string} articleId 
   * @returns 文章编辑数据
   */
  getArticlePublishContent(articleId) {
    return request({
      url: `/api/article/${articleId}`,
      method: 'get'
    })
  },

  /**
   * 9.修改文章
   * @param {Array} article 
   * @returns 修改结果
   */
  updateArticle(article) {
    return request({
      url: '/api/article',
      method: 'put',
      data: article
    })
  }
}