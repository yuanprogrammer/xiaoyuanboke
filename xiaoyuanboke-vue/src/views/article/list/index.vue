<template>
  <div style="padding: 20px;">
    <div>
      <query :query-param-f="queryParam" @query="findArticleList" @refreshPage="modifyPage" @publish="publishArticle"></query>
    </div>

    <el-table
      v-loading="loading"
      element-loading-text="数据加载中"
      element-loading-spinner="el-icon-loading"
      element-loading-background="rgba(0, 0, 0, 0.8)"
      :data="articleList"
      size="small"
      style="width: 100%">
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-form label-position="left" class="demo-table-expand">
            <el-form-item label="作者名称">
              <span>{{ props.row.authorName }}</span>
            </el-form-item>

            <!--
              文章标签
              v-for: 遍历标签
              key:  下标
              size: 标签样式大小
              type: 标签颜色样式
             -->
            <el-form-item label="文章标签">
              <span>
                <el-tag v-for="(item, index) in tagsCut(props.row.tags)" :key="index"
                  size="small" :type="randomReturnTheme()" style="margin-right: 10px;">
                  {{ item }}
                </el-tag>
              </span>
            </el-form-item>

            <!--
              文章分类
             -->
            <el-form-item label="所属分类">
              <span>
                <el-tag v-for="(item, index) in props.row.category" :key="index"
                  size="medium " :type="randomReturnTheme()" effect="dark" style="margin-right: 10px;">
                  {{ item }}
                </el-tag>
              </span>
            </el-form-item>
            <el-form-item label="点赞数量">
              <span>{{ props.row.goodCount }}</span>
            </el-form-item>
            <el-form-item label="收藏数量">
              <span>{{ props.row.collectCount }}</span>
            </el-form-item>
            <el-form-item label="浏览数量">
              <span>{{ props.row.viewCount }}</span>
            </el-form-item>
            <el-form-item label="评论数量">
              <span>{{ props.row.commentCount }}</span>
            </el-form-item>
            <el-form-item label="发布时间">
              <span>{{ props.row.publishTime }}</span>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>
      <el-table-column
        label="文章编号"
        prop="id"
        width="250">
      </el-table-column>
      <el-table-column
        label="文章标题"
        prop="title"
        width="auto">
      </el-table-column>
      <el-table-column
        align="center"
        label="操作"
        width="200">
        <template slot-scope="scope">
          <!-- <router-link :to="'/article/detail/' + scope.row.id">
              <el-button
              size="mini"
              type="primary"
              icon="el-icon-search">查看</el-button>
          </router-link> -->
          <el-button
              size="mini"
              type="primary"
              icon="el-icon-search"
              :disabled="!isAuth(['ARTICLE:SELECT'])"
              @click="browseArticle(scope.row)"></el-button>
          <el-button
            size="mini"
            type="warning"
            icon="el-icon-edit"
            :disabled="!isAuth(['ARTICLE:UPDATE'])"
            @click="modifyArticle(scope.row.id)"></el-button>
          <el-button
            size="mini"
            type="danger"
            icon="el-icon-delete"
            :disabled="!isAuth(['ARTICLE:DELETE'])"
            @click="deleteArticle(scope.row.id)"></el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <Pagination ref="pagination" @handleSizeChange="handleSizeChange" @handleCurrentChange="handleCurrentChange"></Pagination>
  </div>
</template>

<script>
// 引入文章请求管理
import article from '@/api/blog/article'
import query from "../components/query";
import Pagination from "../../../components/Pagination";

export default {
  components: {
    query,
    Pagination
  },
  data() {
    return {
      loading: true,
      articleList: [],
      queryParam: {
        id: '',
        authorName: '',
        title: '',
        tag: '',
        category: '',
        sort: '',
        startTime: '',
        endTime: ''
      }
    }
  },
  methods: {
    handleSizeChange() {
      this.findArticleList()
    },
    handleCurrentChange() {
      this.findArticleList()
    },
    findArticleList() {
      let query = {
        ...this.queryParam,
        pageIndex: this.$refs.pagination.pageParam.pageIndex,
        pageSize: this.$refs.pagination.pageParam.pageSize
      }
      article.findArticleList(query).then((response) => {
        if (response.success) {
          // 赋值文章列表数据
          this.articleList = response.data.list
          // 赋值对应页码等数据
          this.$refs.pagination.pageParam.totalCount = response.data.total
        }
      }).finally(() => {
        this.loading = false
      })
    },

    /**
     * 查看文章
     * 编辑文章
     * 删除文章
     * @param {} index: 索引
     * @param {} row: 当前行数据
     */
    browseArticle(article) {
      this.$router.push({ path: '/article/detail' })
      sessionStorage.setItem('articleInfo', JSON.stringify(article))
      // this.$router.push({ name: 'detail', params: { articleInfo: article }})
    },
    modifyArticle(articleId) {
      sessionStorage.setItem("articleId", String(articleId))
      this.$router.push( '/publishArticle' )
      // const {href} = this.$router.resolve({
      //   name: 'publish',
      // })
      // window.open(href, '_blank')
    },
    deleteArticle(articleId) {
      this.$confirm('此操作将删除该篇文章, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'error'
      }).then(() => {
        article.removeArticleById(articleId).then((response) => {
          if (response.success) {
            this.findArticleList()
            this.$notify({
              title: '操作',
              message: response.message,
              type: 'success'
            });
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },

    /**
     * 标签处理函数区
     */

    // 标签切割
    tagsCut(tags) {
      return tags.split(',')
    },
    // 返回随机主题色
    randomReturnTheme() {
      let themes = ['', 'success', 'info', 'warning', 'danger']
      return themes[Math.floor(Math.random()*5)]
    },
    // 发布文章
    publishArticle() {
      sessionStorage.removeItem("articleId")
      this.$router.push( '/publishArticle' )
    },
    modifyPage(index, size) {
      this.$refs.pagination.pageParam.pageIndex = index
      this.$refs.pagination.pageParam.pageSize = size
    }
  },
  mounted() {
    this.findArticleList()
  },
  created(){}
  // beforeRouteEnter(to, from, next) {
  //   next(vm => {
  //     if (from.path === '/publishArticle') {
  //       sessionStorage.removeItem("articleId")
  //     }
  //   })
  // }
}
</script>

<style>
.demo-table-expand {
  font-size: 0;
}
.demo-table-expand label {
  width: 90px;
  color: #99a9bf;
}
.demo-table-expand .el-form-item {
  margin-left: 50px;
  margin-bottom: 0;
  width: 50%;
}
</style>
