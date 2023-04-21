<template>
  <div class="app">
    <el-row>
      <el-col :span="18">
        <div id="main">
          <!-- 标题 -->
          <h2>{{ articleInfo.title }}</h2>
          <!-- <el-divider></el-divider> -->

          <!-- 文章数据 -->
          <!-- <el-row :gutter="20" style="font-size: 14px; color: #8395a7;">
            <el-col :span="4"><i class="el-icon-view"> 164678</i></el-col>
            <el-col :span="4"><i class="el-icon-chat-dot-square"> 154</i></el-col>
            <el-col :span="4">点赞数量</el-col>
            <el-col :span="4"><i class="el-icon-star-on"> 1154</i></el-col>
            <el-col :span="4">发布时间</el-col>
            <el-col :span="4">标签</el-col>
          </el-row> -->
          <el-divider></el-divider>

          <!-- 正文 -->
          <le-preview ref='md-preview' :is-md="true" :value='html' :hljs-css='hljsCss'></le-preview>
        </div>
      </el-col>

      <!-- 右侧信息列表 -->
      <el-col :span="6">
        <div class="right-info">
          <!-- 用户资料 -->
          <el-card class="box-card" shadow="always">
            <div class="aa">
              <el-avatar :size="60" :src="articleInfo.authorAvatar"></el-avatar>
              <span style="font-weight: bold; font-size: 15px;margin-top:5px;margin-left:10px;">{{ articleInfo.authorName }}</span>
            </div>
          </el-card>

          <!-- 文章数据 -->
          <el-card class="box-card" style="margin-top: 15px;">
            <div slot="header" class="clearfix">
              <span style="font-weight: bold;">文章数据</span>
            </div>
            <div class="text item info"><i class="el-icon-view"></i> 访问： {{ articleInfo.viewCount }}</div>
            <div class="text item info"><i style="font-size: 15px;" class="el-icon-chat-dot-square"></i> 评论： {{ articleInfo.commentCount }}</div>
            <div class="text item info"><i class="el-icon-star-on"></i> 收藏： </div>
            <div class="text item info"><i style="font-size: 15px;" class="el-icon-thumb"></i> 点赞： </div>
            <div class="text info"><i class="el-icon-time"></i> 发布时间： {{ articleInfo.gmtCreate }}</div>
          </el-card>

          <!-- 标签 -->
          <el-card class="box-card" shadow="always" style="margin-top: 15px;">
            <div slot="header" class="clearfix">
              <span style="font-weight: bold;">文章标签</span>
            </div>
            <div>
              <span>
                <el-tag v-for="(item, index) in tagsCut('Java,Android')" :key="index"
                  size="small" :type="randomReturnTheme()" style="margin-right: 10px;">
                  {{ item }}
                </el-tag>
              </span>
            </div>
          </el-card>
        </div>
      </el-col>
    </el-row>

    <el-backtop></el-backtop>
  </div>
</template>

<script>
import article from '@/api/blog/article'

export default {
  data() {
    return {
      hljsCss: 'agate',
      html: '',
      tags: "Java,SpringBoot",
      articleInfo: []
    }
  },
  methods: {
    getArticleDetailContent(articleId) {
      article.getArticleDetailContent(articleId).then((response) => {
        this.html = response.data.articleContent
      })
    },

    // 标签切割
    tagsCut(arr) {
      // return this.articleInfo.tags.split(',')
      return arr.split(',')
    },
    // 返回随机主题色
    randomReturnTheme() {
      let themes = ['', 'success', 'info', 'warning', 'danger']
      return themes[Math.floor(Math.random()*5)]
    },
  },
  mounted() {
    if(this.articleInfo.length == 0) {
      this.$message({
        message: '空内容......',
        type: 'error'
      })
    }else {
      this.getArticleDetailContent(this.articleInfo.id)
    }
  },
  // mounted挂载前
  beforeMount() {
    this.articleInfo = JSON.parse(sessionStorage.getItem('articleInfo'))
  },
  // 刷新后
  beforeUpdate() {
    sessionStorage.setItem('articleInfo', JSON.stringify(this.articleInfo))
  },
  beforeRouteLeave(to, from, next) {
    sessionStorage.removeItem('articleInfo')
    next()
  }
}
</script>

<style>
#app {
  width: 100%;
  height: 100%;
  margin: auto;
}

#main {
  width: auto;
  height: auto;
  margin-top: 30px;
  margin-left: 10px;
  margin-right: 10px;
  padding: 20px;
  /* margin: 0px 50px;
  padding: 20px 80px; */
  background-color: rgb(255, 255, 255);
}
/* 
  右侧信息区域样式
*/
.right-info {
  margin-top: 30px;
}
.text {
  font-size: 14px;
}

.item {
  margin-bottom: 18px;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}
.clearfix:after {
  clear: both
}

.box-card {
  width: 98%;
}
.box-card .info{
  color: #8395a7;
}
/* 
  右侧信息区域样式
*/

.aa{
  display: flex;
  flex-direction: row;
}
</style>