<template>
  <div class="app-container">
    <div style="margin-bottom: 10px;">
      <el-button :disabled="!isAuth(['TIMELINE:INSERT'])" type="danger" size="small" icon="el-icon-position" @click="addTimeLine" round>添加时间线</el-button>
    </div>

    <el-table
      v-loading="loading"
      size="small"
      element-loading-text="数据加载中"
      element-loading-spinner="el-icon-loading"
      element-loading-background="rgba(0, 0, 0, 0.8)"
      :default-sort = "{prop: 'startTime', order: 'ascending'}"
      :data="timeLineDate"
      style="width: 100%">

      <!-- 时间线描述 -->
      <!-- <el-table-column type="expand">
        <template slot-scope="props">
          <el-form label-position="left" class="demo-table-expand">
            <el-form-item label="描述">
              <span>{{ props.row.describe }}</span>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column> -->

      <!-- 开始时间 -->
      <el-table-column
        label="开始时间"
        prop="startTime"
        width="100"
        sortable>
      </el-table-column>

      <!-- 结束时间 -->
      <el-table-column
        label="结束时间"
        prop="endTime"
        width="100"
        sortable>
      </el-table-column>

      <!-- 标题 -->
      <el-table-column
        label="标题"
        prop="title"
        width="300">
      </el-table-column>

      <!-- 描述 -->
      <el-table-column
        label="描述"
        prop="describe"
        width="auto">
      </el-table-column>

      <!-- 查看、编辑、删除等操作区 -->
      <el-table-column
        align="center"
        label="操作"
        width="180">
        <template slot-scope="scope">
          <el-button
              size="mini"
              type="primary"
              icon="el-icon-search"
              :disabled="!isAuth(['TIMELINE:SELECT'])"
              @click="browseTimeline(scope.row.content)"></el-button>
          <router-link :to="'/timeline/edit/' + scope.row.id">
            <el-button
            size="mini"
            type="warning"
            :disabled="!isAuth(['TIMELINE:UPDATE'])"
            icon="el-icon-edit"></el-button>
          </router-link>
          <el-button
            size="mini"
            type="danger"
            icon="el-icon-delete"
            :disabled="!isAuth(['TIMELINE:DELETE'])"
            @click="deleteTimeline(scope.row.id)"></el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <div class="block" style="text-align:center;margin-top:5px;">
      <el-pagination
        background
        @current-change="handleCurrentChange"
        :current-page.sync="pageParam.pageIndex"
        :page-size="pageParam.pageSize"
        :pager-count="19"
        :total="pageParam.totalCount"
        layout="prev, pager, next, jumper">
      </el-pagination>
    </div>

    <!-- 详细内容弹框 -->
    <el-dialog title="详细内容" :visible.sync="detailVisible">
      <div>
        <le-preview ref="md-preview" :is-md="true" :value="content" hljs-css="agate"></le-preview>
     </div>
    </el-dialog>
  </div>
</template>
<script>
import timelineApi from '@/api/blog/timeline'
export default {
  data() {
    return {
      loading: false,
      timeLineDate: [],
      pageParam: {
        pageSize: 10, // 每页大小
        pageIndex: 0, // 当前页数
        totalCount: 0, //总记录数
      },
      detailVisible: false, // 是否显示详细内容框
      content: '', // markdown正文
    }
  },
  methods: {
    /**
     * 分页函数区域
     */
    handleCurrentChange(pageIndex) {
      this.findArticleList(pageIndex, this.pageParam.pageSize)
    },
    findTimelineList(pageIndex, pageSize) {
      timelineApi.findTimeLineList(pageIndex, pageSize).then((response) => {
        // console.log(response)
        // 赋值对应页码等数据
        let date = response.data.timeLineList
        this.pageParam.pageIndex = date.pageIndex
        this.pageParam.totalCount = date.totalCount

        // 赋值文章列表数据
        this.timeLineDate = date.list

        // this.loading = false
      })
    },
    addTimeLine() {
      this.$router.push({ path: '/timeline/save' })
    },
    /**
     * 查看文章
     * 编辑文章
     * 删除文章
     * @param {} index: 索引
     * @param {} row: 当前行数据
     */
    browseTimeline(content) {
      this.detailVisible = true
      this.content = content
    },
    deleteTimeline(id) {
      this.$confirm('此操作将删除该条时间线, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'error'
      }).then(() => {
        // this.loading = true
        timelineApi.deleteTimeLine(id).then((response) => {
          /**
           * 当前页数据判断，this.timeLineDate.length 为当前页数据列表长度，为 1 时 再 - 1 就没有数据了
           * 避免出现，删除当前行数据时页面没有数据显示，保持删除之后当前页内容只改变一行
           * 1.删除之后，当前页数据至少还有一行，pageIndex当前索引保持不变
           * 2.删除之后，当前页数据已经没有，并且pageIndex不是第一页，pageIndex - 1 向前挪动
           * 3.删除之后，当前页数据已经没有，并且pageIndex是第一页，pageIndex = 1 原样赋值
           */
          this.pageParam.pageIndex = this.timeLineDate.length == 1 ? (this.pageParam.pageIndex - 1 <= 0 ? 1 : this.pageParam.pageIndex - 1) : this.pageParam.pageIndex
          this.findArticleList(this.pageParam.pageIndex, this.pageParam.pageSize)
          this.$notify({
            title: '操作',
            message: response.message,
            type: 'success'
          });
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
  },
  mounted() {
    this.findTimelineList(1, this.pageParam.pageSize)
  }
}
</script>

<style>
.demo-table-expand .el-form-item {
  margin-left: 50px;
  margin-bottom: 0;
  width: 50%;
}
</style>
