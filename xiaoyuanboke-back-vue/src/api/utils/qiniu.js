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
        'AK': 'dec4596c5ace06b713db8756e47b049f',
        'SK': 'c252ab432572b3d92161fce84f226f24'
      }
    })
  },
}