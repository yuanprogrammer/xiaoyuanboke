import request from '@/utils/request'

 export default {
   findUserList(queryParam) {
     return request({
       url: '/user/list',
       method: 'post',
       data: queryParam
     })
   },

   findUserOperationList(queryParam) {
     return request({
       url: '/user/list/operation',
       method: 'post',
       data: queryParam
     })
   },

   findOperationType() {
     return request({
       url: '/userOperation/type',
       method: 'get'
     })
   }
 }
