<template>
  <div class="app">
    <el-table
    border
    size="small"
    :data="suggestFeedbackList"
    style="width: 100%">

      <!-- 编号 -->
      <el-table-column
        label="编号"
        width="100">
        <template slot-scope="scope">
          <span>{{ scope.row.id }}</span>
        </template>
      </el-table-column>

      <!-- 反馈时间 -->
      <el-table-column
        label="反馈时间"
        width="160">
        <template slot-scope="scope">
          <span>{{ scope.row.gmtCreate }}</span>
        </template>
      </el-table-column>

      <!-- 昵称 -->
      <el-table-column
        label="昵称"
        width="120">
        <template slot-scope="scope">
          <span>{{ scope.row.name ? scope.row.name : '未设置昵称' }}</span>
        </template>
      </el-table-column>

      <!-- 提议 -->
      <el-table-column
        label="建议反馈"
        width="auto">
        <template slot-scope="scope">
          <span>{{ scope.row.content }}</span>
        </template>
      </el-table-column>


      <el-table-column
        label="操作"
        width="100">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="danger"
            icon="el-icon-delete"
            :disabled="!isAuth(['SUGGEST:DELETE'])"
            @click="deleteSuggest(scope.row.id)"></el-button>
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
        :total="pageParam.totalCount"
        layout="prev, pager, next, jumper">
      </el-pagination>
    </div>
  </div>
</template>

<script>
import feedback from '@/api/blog/feedback'

export default {
  data() {
    return {
      suggestFeedbackList: [],
      pageParam: {
        pageSize: 10, // 每页大小
        pageIndex: 1, // 当前页数
        totalCount: 0, //总记录数
      },
    }
  },
  methods: {
    handleCurrentChange(pageIndex) {
      this.findSuggestFeedbackList(pageIndex, this.pageParam.pageSize)
    },
    // 删除
    deleteSuggest(id) {
      this.$confirm('确认删除?', '提示', {
        confirmButtonText: '确定',
        type: 'warning'
      }).then(() => {
        feedback.deleteSuggest(id).then( _ => {
          this.$notify({
            title: '删除成功',
            type: 'success'
          })
          this.findSuggestFeedbackList(this.pageParam.pageIndex, this.pageParam.pageSize)
        })
      })
    },
    // 查询问题反馈列表
    findSuggestFeedbackList(pageIndex, pageSize) {
      feedback.findSuggestFeedbackList(pageIndex, pageSize).then((res) => {
        let data = res.data.suggestFeedbackList
        this.suggestFeedbackList = data.list

        this.pageParam.pageIndex = data.pageIndex
        this.pageParam.totalCount = data.totalCount
      })
    }
  },
  mounted() {
    this.findSuggestFeedbackList(1, this.pageParam.pageSize)
  }
}
</script>

<style>
.app {
  padding: 30px;
}
</style>
