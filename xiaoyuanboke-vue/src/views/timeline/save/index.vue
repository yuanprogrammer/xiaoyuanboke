<template>
  <div class="app-container">
    <div id="editor-main">
      <le-editor v-model="timeLineDate.content" hljs-css="agate" :image-uploader="imageUploader" @uploadImg="uploadImg" :toolbar="toolbar"></le-editor>
    </div>
    <div class="complete" @click="dialog = true">
      <span>完成</span>
    </div>

    <!--
      抽屉
      提交时间线的表单
     -->
    <el-drawer
      :title="editOrSave ? '添加时间线': '编辑时间线'"
      :visible.sync="dialog"
      direction="rtl"
      custom-class="demo-drawer"
      ref="drawer"
      size="40%">
        <div class="demo-drawer__content" style="margin-right:20px;">
          <el-form ref="timeLineDate" :model="timeLineDate" label-width="80px" :rules="rules">
            <!-- 标题 -->
            <el-form-item label="标题" prop="title">
              <el-input v-model="timeLineDate.title"></el-input>
            </el-form-item>

            <!-- 描述 -->
            <el-form-item label="描述" prop="describe">
              <el-input
                type="textarea"
                v-model="timeLineDate.describe"
                rows="5"
                resize="none"></el-input>
            </el-form-item>

            <!-- 开始时间 - 结束时间 -->
            <el-form-item label="时间" prop="times">
              <div class="block">
                <el-date-picker
                  v-model="timeLineDate.times"
                  value-format="yyyy-MM-dd"
                  type="daterange"
                  range-separator="-"
                  start-placeholder="开始日期"
                  end-placeholder="结束日期">
                </el-date-picker>
              </div>
            </el-form-item>

            <!-- 提交 按钮 -->
            <el-form-item>
              <el-button type="danger" @click="submit('timeLineDate')">提 交</el-button>
            </el-form-item>
          </el-form>
        </div>
    </el-drawer>
  </div>
</template>

<script>
import timeline from '@/api/blog/timeline'
import qiniu from '@/api/utils/qiniu'

export default {
  data() {
    return {
      dialog: false,
      editOrSave: true, // 页面状态, false->编辑, true->添加
      formLabelWidth: '80px',
      timeLineDate: {
        id: '',
        // title: '我是标题',     // 标题
        // describe: '我是时间线描述',  // 描述
        // content: '我是正文',   // 正文
        title: '',     // 标题
        describe: '',  // 描述
        content: '',   // 正文
        startTime: '', // 开始时间
        endTime: '',   // 结束时间
        times: [ ],
      },
      // 规则
      rules: {
        title: [
          { required: true, message: '请填写标题', trigger: 'blur' },
          { min: 3, max: 50, message: '长度在 3 到 50 之间', trigger: 'blur' }
        ],
        describe: [
          { required: true, message: '请填写描述', trigger: 'blur' },
          { min: 5, max: 100, message: '长度在 5 到 100 之间', trigger: 'blur'}
        ],
        times: [
          { type: 'array', required: true, message: '请选择日期', trigger: 'change' }
        ]
      },
       // 自定义
      imageUploader: {
        custom: true,
        fileType: 'file',
      },
      toolbar: {
        undo: true, // 撤销
        redo: true, // 重做
        bold: true, // 粗体
        del: true, // 删除线
        underline: true, // 下划线
        italic: true, // 斜体
        quote: true, // 引用
        bookmark: true, // 标记
        superscript: true, // 上角标
        subscript: true, // 下角标
        h1: true, // 标题1
        h2: true, // 标题2
        h3: true, // 标题3
        h4: true, // 标题4
        h5: true, // 标题5
        h6: true, // 标题6
        alignLeft: true, // 居左
        alignCenter: true, // 居中
        alignRight: true, // 居右
        ol: true, // 有序列表
        ul: true, // 无序列表
        hr: true, // 分隔线
        link: true, // 链接
        inlineCode: true, // 行内代码
        code: true, // 代码块
        image: true, // 图片
        table: true, // 表格
      }
    }
  },
  methods: {
     // 自定义图片上传
    uploadImg: function ($vm, file, fileName) {
      // 添加图片
      qiniu.uploadArticleImage(file, 'timeline').then((response) => {
        $vm.insertImg(response.data.url, response.data.url)
      })
      // 两个参数 第一个是图片访问路径 第二个是文件名
      // $vm.insertImg(`${$vm.config.imageUploader.imagePrefix}${fileName}`, fileName)
    },
    // 提交
    submit(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if(this.timeLineDate.content.length == 0) {
            this.$message({
              message: '请填写正文内容',
              type: 'error'
            })
            this.dialog = false
            return false
          }else {
            // 处理日期
            this.timeLineDate.startTime = this.timeLineDate.times[0]
            this.timeLineDate.endTime = this.timeLineDate.times[1]

            // 页面状态 编辑？添加？
            if(this.editOrSave) {
              this.saveTimeLine()
            }else {
              this.editTimeLine()
            }
          }
        } else {
          return false;
        }
      });
    },
    // 编辑时间线
    editTimeLine() {
      timeline.editTimeLine(this.timeLineDate).then((response) => {
        this.$message({
          message: response.message,
          type: 'success'
        })
        this.$router.push({ path: '/timeline/list' })
      })
    },
    // 添加时间线
    saveTimeLine() {
      timeline.saveTimeLine(this.timeLineDate).then((response) =>{
        this.$message({
          message: response.message,
          type: 'success'
        })
        this.$router.push({ path: '/timeline/list' })
      })
    },
    // 获取时间线的数据
    getTimeLineInfo(id) {
      timeline.getTimeLineInfo(id).then((response) => {
        let date = response.data.timeLine
        this.timeLineDate.id = date.id
        this.timeLineDate.title = date.title
        this.timeLineDate.describe = date.describe
        this.timeLineDate.content = date.content
        this.timeLineDate.times.push(date.startTime)
        this.timeLineDate.times.push(date.endTime)

        this.editOrSave = false // 编辑状态
      })
    },
    // 是否编辑模式
    checkIsEdit() {
      if(this.$route.params && this.$route.params.id) {
        this.getTimeLineInfo(this.$route.params.id)
      }
    }
  },
  mounted() {
    this.checkIsEdit()
  },
  watch: {
    $route(to, from) {
      // 先检查是否有权限
      if (!isAuth(['TIMELINE:INSERT'])) {
        this.$router.push({ path: '/timeline' })
        return
      }
      this.checkIsEdit()
    }
  }
}
</script>

<style>
#editor-main {
  color: #2c3e50;
  width: 100%;
  height: 600px;
}

.complete{
  position: absolute;
  right: 40px;
  bottom: 60px;
  width: 60px;
  height: 60px;
  background-color: #F2F6FC;
  text-align: center;
  border-radius: 100%;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.3);
  cursor: pointer;
}
.complete > span {
  line-height: 60px;
  font-size: 15px;
  color: #E6A23C;
  font-weight: bold;
}
</style>
