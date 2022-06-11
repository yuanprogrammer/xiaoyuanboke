import request from "../utils/request.js";

export default {
  findTimeLineList() {
    let pageParam = {};
    pageParam['pageIndex'] = 1;
    pageParam['pageSize'] = 10;
    return request({
      url: '/timeline/list',
      method: 'post',
      data: pageParam
    });
  }
};