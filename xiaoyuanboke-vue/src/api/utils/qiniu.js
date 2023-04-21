import request from '@/utils/request'

export default {
  /**
   * 1.文章图片上传请求
   * @param {file} file
   * @returns 文件地址
   */
   uploadArticleImage(file, folderName = '') {
    // 构造FormData
    let formDate = new FormData()
    formDate.append('file', file)
    formDate.append('folderName', folderName)
    // 返回请求模板
    return request({
      url: '/qiniu/kodo/upload/articleImage',
      method: 'post',
      data: formDate,
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded',
        'AK': 'xx',
        'SK': 'xx'
      }
    })
  },
}
