const BASE_API = 'https://www.xiaoyuan-boke.com:443';
// const BASE_API = 'http://localhost:9005';
// 创建axios实例
const service = axios.create({
  baseURL: BASE_API, // api 的 base_url
  timeout: 6000 // 请求超时时间
});


// response 拦截器
service.interceptors.response.use(
  response => {
    /**
     * code为非20000是抛错 可结合自己业务进行修改
     */
    const res = response.data;
    if (res.code !== 200) {

      // 50008:非法的token; 50012:其他客户端登录了;  50014:Token 过期了;
      if (res.code === 40005 || res.code === 40006) {
        Vue.prototype.$confirm(res.message, '提示',{
            confirmButtonText: '前往登录',
            cancelButtonText: '取消',
            type: 'warning'
          }
        ).then(() => {
          window.location.replace("/login")
        }).catch(() => {
          return res.success
        });
        return res.success
      }

      // 其他自定义错误
      if (res.code > 40000) {
        Vue.prototype.$message({
          message: res.message,
          type: 'error',
          duration: 5 * 1000
        });
        return response.success
      }

      Vue.prototype.$message({
        message: res.message,
        type: 'error',
        duration: 5 * 1000
      });
      return Promise.reject('error')
    } else {
      return response.data
    }
  },
  error => {
    console.log('err' + error) // for debug
    Vue.prototype.$message({
      message: error.message,
      type: 'error',
      duration: 5 * 1000
    });
    return Promise.reject(error)
  }
);

export default service