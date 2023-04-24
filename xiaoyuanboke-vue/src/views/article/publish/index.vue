<template>
  <div id="app">
    <el-container>
      <el-header>
        <el-row>

          <!-- 回退 -->
          <el-col :span="2">
            <el-button style="color: #000; font-size: 15px;" type="text" icon="el-icon-arrow-left" @click="returnBack">返 回</el-button>
          </el-col>

          <!-- 标题 -->
          <el-col :span="18">
            <el-input v-model="article.title" class="title"
              maxlength="80" minlength="5" show-word-limit type="text" clearable
              prefix-icon="el-icon-edit" placeholder="请输入文章标题（5~50字）"></el-input>
          </el-col>

          <!-- 发布按钮 -->
          <el-col :span="4">
            <el-button type="danger" @click="publish()" round>发布文章</el-button>
          </el-col>

        </el-row>
      </el-header>

      <!-- Markdown编辑器 -->
      <el-main>
        <div id="editor-main">
          <le-editor v-model="article.articleContentParam.content" :hljs-css="hljsCss" :fullscreen="fullScreen" :toolbar="toolbar" :image-uploader="imageUploader" @uploadImg="uploadImg" @save="save"></le-editor>
        </div>
      </el-main>

    </el-container>

    <!-- 发布文章前准备 -->
    <!-- dialog动态弹框 -->
    <el-dialog title="发布文章" :visible.sync="dialogFormVisible">
      <el-form>
        <!-- 封面和摘要 -->
        <el-form-item label="封面&摘要" :label-width="formLabelWidth">

          <!-- 封面选择 -->
          <div>
            <el-radio v-model="radio" :label="true" @change="coverSelect">有封面</el-radio>
            <el-radio v-model="radio" :label="false" @change="coverSelect">无封面</el-radio>
          </div>

          <div>
            <el-row>
              <!-- 上传文章封面 -->
              <el-col :span="10">
                <el-upload v-if="articleCoverVisible"
                           class="article-cover"
                           :action="BASE_API + '/article/upload'"
                           :show-file-list="false"
                           :disabled="isStopUpload"
                           :on-success="handleAvatarSuccess"
                           :before-upload="beforeAvatarUpload">
                  <img v-if="article.cover" :src="article.cover" class="cover">
                  <i v-else class="el-icon-plus article-cover-icon"></i>
                </el-upload>
              </el-col>

              <!-- 文章摘要 -->
              <el-col :span="14">
                <el-input
                  type="textarea"
                  placeholder="摘要（选填）：会在推荐、首页列表等页面展示，帮助读者更快了解内容，如不填写则默认抓取正文前256字符"
                  v-model="article.digest"
                  maxlength="256"
                  :rows="6"
                  show-word-limit
                  resize="none">
                </el-input>
              </el-col>
            </el-row>
          </div>
        </el-form-item>

        <el-form-item label="文章标签" :label-width="formLabelWidth">
          <div>
            <el-tag
            :key="tag"
            v-for="tag in tags"
            closable
            :disable-transitions="false"
            @close="handleClose(tag)">
            {{tag}}
          </el-tag>
          <el-input
            class="input-new-tag"
            v-if="inputVisible"
            v-model="inputValue"
            ref="saveTagInput"
            size="small"
            @keyup.enter.native="handleInputConfirm"
            @blur="handleInputConfirm">
          </el-input>
          <el-button v-else class="button-new-tag" size="medium" @click="showInput">+ 添加文章标签</el-button>
          </div>
        </el-form-item>

        <!--
          分类选择
          options: 数据源配置项
          props: 作用域
            {
              checkStrictly: 父级分类是否可选
              multiple: 是否可以多选
              expandTrigger: 分类展开方式 hover鼠标悬浮展开
            }
          show-all-levels: 是否显示完整路径
          clearable: 清空所有选中的分类
          @change: 选中节点后触发
        -->
        <el-form-item label="分类专栏" :label-width="formLabelWidth">
          <!-- 分类栏目选择 -->
          <div>
            <el-cascader
              class="category"
              v-model="value"
              :options="categorySelectList"
              :props="{ checkStrictly: true, multiple: true, expandTrigger: 'hover' }"
              :show-all-levels="false"
              @change="handleChange"
              clearable></el-cascader>
          </div>
        </el-form-item>

      </el-form>

      <!-- 发布和取消按钮 -->
      <div slot="footer" class="dialog-footer">
        <el-button size="small" @click="dialogFormVisible = false">取 消</el-button>
        <el-button v-show="updateOrPublish" type="warning" size="small" plain @click="schDialogVisible = true">定时发布</el-button>
        <el-button type="danger" size="small" round @click="publishConfirm">发布文章</el-button>
      </div>

    </el-dialog>

    <el-dialog
      title="定时发布"
      :visible.sync="schDialogVisible"
      width="25%"
      top="30vh"
      :before-close="handleClose">
      <div style="margin-bottom: 15px">请选择后一天 至 7天 进行定时发布</div>

      <el-row>
        <el-col :span="12">
          <div>
            <el-date-picker
              style="width: 90%"
              v-model="publishDate"
              type="date"
              :picker-options="pickerOptions"
              value-format="yyyy-MM-dd"
              placeholder="选择日期">
            </el-date-picker>
          </div>
        </el-col>
        <el-col :span="12">
          <div>
            <el-time-select
              style="width: 90%"
              v-model="publishTime"
              :picker-options="{
                start: '00:00',
                step: '00:15',
                end: '23:45'
              }"
              :disabled="!publishDate"
              placeholder="选择时间">
            </el-time-select>
          </div>
        </el-col>
      </el-row>
      <span slot="footer" class="dialog-footer">
        <el-button size="medium" round @click="schDialogVisible = false">取 消</el-button>
        <el-button size="medium" round type="danger" @click="scheduledPublish">定时发布</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
let articleJson = require('@/utils/json/article.json')
// 引入article.js
import article from '@/api/blog/article'
export default {
  // ...
    data() {
      return {
        article: {
          id: '',
          title: '', // 标题
          digest: '', // 摘要
          cover: '', // 封面
          tags: '', // 标签
          categoryList: [ ], // 分类
          articleContentParam: {
            content: '', // markdown文本
            contentHtml: '', // 解析后的HTML文本
          },
          publishTime: null
        },
        hljsCss: 'agate',
        fullScreen: true,
        toolbar: { },
        // 自定义
        BASE_API: process.env.BASE_API,
        isStopUpload: false,
        imageUploader: {
          custom: true,
          fileType: 'file',
        },
        // 发布文章表单
        dialogFormVisible: false,
        formLabelWidth: '100px',
        // 封面摘要选项
        radio: true,
        articleCoverVisible: true,
        // 标签
        inputVisible: false,
        inputValue: '',
        tags: [],
        // 分类栏目
        value: [],
        categorySelectList: [],
        // 编辑还是发布？ true: 发布, false: 编辑
        updateOrPublish: true,
        schDialogVisible: false,
        publishDate: '',
        publishTime: '',
        pickerOptions: {
          disabledDate(time) {
            return time <= Date.now()
          }
        }
      }
    },
    methods: {
      // 自定义图片上传
      uploadImg: function ($vm, file, fileName) {
        // 添加图片
        const formData = new FormData();
        formData.append('image', file);
        article.uploadImage(formData).then(res => {
          $vm.insertImg(res.data.url, res.data.url)
        })
        // 两个参数 第一个是图片访问路径 第二个是文件名
        // $vm.insertImg(`${$vm.config.imageUploader.imagePrefix}${fileName}`, fileName)
      },

      save: function (val) {
        // 获取预览文本
        if(this.article.title.length === 0) {
          this.article.title = '【无标题】'
        }

        if(this.article.articleContentParam.content.length === 0) {
          this.$message({
            message: '文章内容不能为空',
            type: 'warning',
          })
          return
        }
        // 获取解析后的HTML格式文本
        this.article.articleContentParam.contentHtml = val
      },
      // 发布文章按钮
      publish: function() {
        this.dialogFormVisible = true
      },
      returnBack() {
        this.$router.go(-1)
      },

      /**
       * 标签相关函数区域
       */
      handleClose(tag) {
        this.tags.splice(this.tags.indexOf(tag), 1);
      },

      showInput() {
        this.inputVisible = true;
        this.$nextTick(_ => {
          this.$refs.saveTagInput.$refs.input.focus();
        });
      },

      handleInputConfirm() {
        let inputValue = this.inputValue;
        if (inputValue) {
          this.tags.push(inputValue);
        }
        this.inputVisible = false;
        this.inputValue = '';
      },

      /**
       * 封面选择函数区
       */
      coverSelect(coverOption) {
        this.articleCoverVisible = coverOption
      },
      handleAvatarSuccess(res, file) {
        // this.article.cover = res.data.url
        // this.imageUrl = URL.createObjectURL(file.raw);
      },
      beforeAvatarUpload(file) {
        const isJPG = /^image\//.test(file.type);
        const isLt2M = file.size / 1024 / 1024 < 10;
        if (!isJPG) {
          this.$message.error('请上传图片类型文件！')
        }

        if (!isLt2M) {
          this.$message.error('上传头像图片大小不能超过 10MB!');
        }

        try {
          this.isStopUpload = true

          const formData = new FormData();
          formData.append('image', file);
          article.uploadImage(formData).then(res => {
            this.article.cover = res.data.url
          })
        } finally {
          this.isStopUpload = false
        }
        return false
      },

      /**
       * 分类分栏函数区
       */
      findCategoryList() {
        article.findCategoryList().then((response) => {
          this.categorySelectList = JSON.parse(JSON.stringify(response.data.categoryList)
            .replace(/id/g,"value").replace(/id/g, "value")
            .replace(/name/g, "label").replace(/name/g, "label")
            .replace(/children/g, "children"))
        })
      },
      handleChange(value) {
        console.log(this.value);
      },

      /**
       * 确认发布和取消函数区
       */
      publishConfirm() {
        if(!this.globalCheck()) {
          return
        }

        // 解析分类专栏
        this.article.categoryList.length = 0
        this.value.forEach(item => {
          if(item.length === 1) {
            this.article.categoryList.push(item[0])
          }else {
            this.article.categoryList.push(item[1])
          }
        })

        // 解析文章标签
        this.article.tags = ''
        this.tags.forEach((item, index) =>{
          if(index === 0) {
            this.article.tags = this.article.tags + item
          }else {
            this.article.tags = this.article.tags + "," + item
          }
        })

        // 清楚遗留封面数据
        if(!this.articleCoverVisible) {
          this.article.cover = ''
        }

        // 向后端发送发布文章请求
        if(this.updateOrPublish) {
          article.publishArticle(this.article).then((response) => {
            this.$message({
              message: '发布成功！',
              type: 'success'
            })
            this.$router.push({ path: '/article' })
          })
        }else {
          article.updateArticle(this.article).then((response) => {
            this.$message({
              message: response.message,
              type: 'success'
            })
            this.$router.push({ path: '/article' })
          })
        }
      },
      // 定时发布
      scheduledPublish() {
        // debugger
        if (this.publishDate.length === 0 || this.publishTime.length === 0) {
          this.$message({
            message: '请选择发布时间',
            type: 'warning'
          })
          return
        }

        this.article.publishTime = JSON.parse(JSON.stringify(this.publishDate)).replace(/-/g, '/') + " " + this.publishTime + ":00"

        this.publishConfirm()
      },
      // 全局非空检查
      globalCheck() {
        // 检查标题是否为空
        if(this.article.title.length === 0) {
          this.$message({
            message: '文章标题不能为空哦~',
            type: 'warning'
          })
          return false
        }

        // 检查文章内容是否为空
        if(this.article.articleContentParam.content.length === 0) {
          this.$message({
            message: '文章内容不能为空哦~',
            type: 'warning'
          })
          return false
        }

        // 检查选了有封面的情况下, 是否有上传封面图片
        if(this.articleCoverVisible && this.article.cover.length === 0) {
          this.$message({
            message: '请上传文章封面哦~',
            type: 'warning'
          })
          return false
        }

        // 检查文章标签是否最少有一个
        if(this.tags.length === 0) {
          this.$message({
            message: '请至少选择一个标签哦~',
            type: 'warning'
          })
          return false
        }

        // 检查分类专栏是否最少有一个
        // if(this.value.length == 0) {
        //   this.$message({
        //     message: '请至少选择一个分类哦~',
        //     type: 'warning'
        //   })
        //   return false
        // }

        return true
      },
      dateFormat(dateData) {
        var date = new Date(dateData)
        var y = date.getFullYear()
        var m = date.getMonth() + 1
        m = m < 10 ? ('0' + m) : m
        var d = date.getDate()
        d = d < 10 ? ('0' + d) : d
        const time = y + '-' + m + '-' + d
        return time
      }
    },
    mounted() {
      this.toolbar = articleJson.markdown.toolbar
      // this.options = articleJson.categorySelectList.options
      this.findCategoryList()
    },
    beforeMount() {
      /**
       * 判断是编辑还是发布
       */
      if(sessionStorage.getItem('articleId') != null) {
        article.getArticlePublishContent(sessionStorage.getItem('articleId')).then((response) => {
          let date = response.data.articlePublish
          this.article.id = sessionStorage.getItem('articleId')
          this.article.title = date.title
          this.article.digest = date.digest
          this.article.cover = date.cover
          this.article.articleContentParam.content = date.content

          // 封面处理
          if(date.cover.length === 0) {
            this.radio = false
            this.articleCoverVisible = false
          }

          // 标签处理
          this.tags = date.tags.split(',')

          // 分类处理
          this.value = date.categorySelected

          // 设置为编辑文章状态
          this.updateOrPublish = false
        })
      }
    }
}
</script>

<style lang="scss">
  #app {
    width: 100%;
    height: 100%;
    // display: flex;
    // justify-content: center;
  }

  #app .el-container {
    width: 100%;
    height: 100%;
  }

  #editor-main {
    color: #2c3e50;
    width: 100%;
    height: 100%;
  }

  .el-header {
    background-color: #B3C0D1;
    color: #333;
    text-align: center;
    line-height: 60px;
  }

  .el-main {
    background-color: #E9EEF3;
    // color: #333;
    // text-align: center;
    // line-height: 100%;
  }

  .title .el-input__inner{
    font-weight: bold;
    font-size: 18px;
  }

  // 封面图片
  .article-cover .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .article-cover .el-upload:hover {
    border-color: #409EFF;
  }
  .article-cover-icon {
    font-size: 28px;
    color: #8c939d;
    width: 220px;
    height: 135px;
    line-height: 135px;
    text-align: center;
  }
  .cover {
    width: 220px;
    height: 135px;
    display: block;
  }

  // 摘要
  .article-digest{
    height: 130px;
  }

  // 标签选择
  .el-tag + .el-tag {
    margin-left: 10px;
  }
  .button-new-tag {
    margin-left: 10px;
    height: 32px;
    line-height: 30px;
    padding-top: 0;
    padding-bottom: 0;
  }
  .input-new-tag {
    width: 90px;
    margin-left: 10px;
    vertical-align: bottom;
  }

  // 分类专栏
  .category{
    width: 300px;
  }
</style>
