import request from '@/utils/request'

 export default {
   findUserList(param, queryParam) {
     return request({
       url: '/api/user/list',
       method: 'post',
       data: {
         'pageUtils': param,
         'userQueryParam': queryParam
       }
     })
   },

   findUserOperationList(param, queryParam) {
     return request({
       url: '/api/user/list/operation',
       method: 'post',
       data: {
         'pageUtils': param,
         'userOperationParam': queryParam
       }
     })
   },

   findOperationType() {
     return request({
       url: '/api/userOperation/type',
       method: 'get'
     })
   }
 }