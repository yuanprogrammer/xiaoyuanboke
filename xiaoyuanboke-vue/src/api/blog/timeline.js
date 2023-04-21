import request from '@/utils/request'

export default {

  /**
   * 添加时间线
   * @param {object} timeline
   * @returns
   */
  saveTimeLine(timeline) {
    return request({
      url: '/timeline',
      method: 'post',
      data: timeline
    })
  },

  /**
   * 删除某条时间线
   * @param {string} id
   * @returns
   */
  deleteTimeLine(id) {
    return request({
      url: `/timeline/${id}`,
      method: 'delete'
    })
  },

  /**
   * 查询时间线列表
   * @param {number} pageIndex
   * @param {number} pageSize
   * @returns
   */
  findTimeLineList(pageIndex, pageSize) {
    return request({
      url: `/timeline/${pageIndex}/${pageSize}`,
      method: 'get',
    })
  },

  /**
   * 根据ID查询某条时间线
   * @param {string} id
   * @returns 一条时间线的数据
   */
  getTimeLineInfo(id) {
    return request({
      url: `/timeline/${id}`,
      method: 'get'
    })
  },

  /**
   * 编辑时间线
   * @param {object} timeLineVo
   * @returns 编辑结果 成功？失败？
   */
  editTimeLine(timeLineVo) {
    return request({
      url: '/timeline',
      method: 'put',
      data: timeLineVo
    })
  }
}
