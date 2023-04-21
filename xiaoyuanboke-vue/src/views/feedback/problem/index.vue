<template>
  <div class="app">
    <query :query-param-f="queryParam" @query="findProblemFeedbackList" @refreshPage="modifyPage"></query>

    <el-table
    border
    :data="problemFeedbackDate"
    size="small"
    style="width: 100%">

      <!-- 编号 -->
      <el-table-column
        label="编号"
        width="180">
        <template slot-scope="scope">
          <span>{{ scope.row.id }}</span>
        </template>
      </el-table-column>

      <!-- 邮箱 -->
      <el-table-column
        label="邮箱"
        width="200">
        <template slot-scope="scope">
          <el-popover trigger="hover" placement="top">
              <el-button type="primary" icon="el-icon-message" :disabled="!isAuth(['PROBLEM:NOTICE'])" @click="openNoticeDialog(scope.$index)">发送通知</el-button>
            <!-- <p style="text-align: center">发送通知</p> -->
            <div slot="reference" class="name-wrapper">
              <span class="email">{{ scope.row.email }}</span>
            </div>
          </el-popover>
        </template>
      </el-table-column>

      <!-- 问题状态 -->
      <el-table-column
        label="问题状态"
        width="90">
        <template slot-scope="scope">
          <el-tag size="medium" type="danger" v-if="scope.row.problemState == 0">未解决</el-tag>
          <el-tag size="medium" type="warning" v-if="scope.row.problemState == 1">处理中</el-tag>
          <el-tag size="medium" type="success" v-if="scope.row.problemState == 2">已解决</el-tag>
        </template>
      </el-table-column>

      <!-- 通知状态 -->
      <el-table-column
        label="通知状态"
        width="90">
        <template slot-scope="scope">
          <el-tag size="medium" type="danger" v-if="scope.row.noticeState == 0">未通知</el-tag>
          <el-tag size="medium" type="success" v-if="scope.row.noticeState == 1">已通知</el-tag>
        </template>
      </el-table-column>

      <!-- 反馈时间 -->
      <el-table-column
        label="反馈时间"
        width="100">
        <template slot-scope="scope">
          <span>{{ scope.row.createTime }}</span>
        </template>
      </el-table-column>

      <!-- 问题 -->
      <el-table-column
        label="问题"
        width="auto">
        <template slot-scope="scope">
          <span>{{ scope.row.problem }}</span>
        </template>
      </el-table-column>


      <el-table-column
      width="130">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="warning"
            icon="el-icon-edit"
            :disabled="!isAuth(['PROBLEM:UPDATE'])"
            @click="editProblemState(scope.$index)"></el-button>
          <el-button
            size="mini"
            type="danger"
            icon="el-icon-delete"
            :disabled="!isAuth(['PROBLEM:DELETE'])"
            @click="deleteProblem(scope.$index)"></el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <div class="block" style="text-align:center;margin-top:5px;">
      <Pagination ref="pagination" @handleSizeChange="handleSizeChange" @handleCurrentChange="handleCurrentChange"></Pagination>
    </div>

    <!-- 状态编辑框 -->
    <el-dialog title="修改问题状态" :visible.sync="editDialogVisible" width="25%">
      <el-form>
        <el-form-item label="活动区域" label-width="80">
          <el-select v-model="state" placeholder="请选择活动区域">
            <el-option :disabled="state == 0 ? true : false" label="未解决" value="0"></el-option>
            <el-option :disabled="state == 1 ? true : false" label="处理中" value="1"></el-option>
            <el-option :disabled="state == 2 ? true : false" label="已解决" value="2"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="danger" @click="editProblemStateSubmit()">修 改</el-button>
      </div>
    </el-dialog>

    <!-- 通知用户框 -->
    <el-dialog
      title="通知用户问题已解决"
      :visible.sync="noticeDialogVisible"
      width="30%">
      <!-- 用户邮件 和 问题编号 -->
      <el-form  label-width="100px">
        <el-form-item label="通知邮件：">
          <span class="notice-info">{{ notice.target }}</span>
        </el-form-item>
        <el-form-item label="问题编号：">
          <span class="notice-info">{{ notice.number }}</span>
        </el-form-item>
      </el-form>

      <!-- 用户反馈的问题 -->
      <el-form label-position="top" label-width="100px" style="margin-left: 18px">
        <el-form-item label="反馈内容：">
          <span>{{ notice.content }}</span>
        </el-form-item>
      </el-form>

      <!-- 通知 和 取消 按钮 -->
      <span slot="footer" class="dialog-footer">
        <el-button type="info" @click="noticeDialogVisible = false">暂不通知</el-button>
        <el-button type="danger" icon="el-icon-message" @click="noticeUser()">通知用户</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import feedback from '@/api/blog/feedback'
import query from "./components/query";
import Pagination from "../../../components/Pagination";

export default {
  components: {
    query,
    Pagination
  },
  data() {
    return {
      problemFeedbackDate: [],
      editDialogVisible: false, // 编辑框
      noticeDialogVisible: false, // 通知框
      notice: {
        target: '',
        number: '',
        content: ''
      },
      state: '',
      index: '',
      queryParam: {
        id: '',
        email: '',
        problemState: '',
        noticeState: '',
        startTime: '',
        endTime: ''
      }
    }
  },
  methods: {
    handleSizeChange() {
      this.findProblemFeedbackList()
    },
    handleCurrentChange() {
      this.findProblemFeedbackList()
    },
    modifyPage(index, size) {
      this.$refs.pagination.pageParam.pageIndex = index
      this.$refs.pagination.pageParam.pageSize = size
    },
    openNoticeDialog(index) {
      if(this.problemFeedbackDate[index].problemState == 0) {
        this.$message.error('问题未解决，不能通知用户')
        return
      }
      if(this.problemFeedbackDate[index].problemState == 1) {
        this.$message.error('问题正在处理中，不能通知用户')
        return
      }
      this.index = index
      this.noticeDialogVisible = true
      console.log(this.problemFeedbackDate[index])
      this.notice.target = this.problemFeedbackDate[index].email
      this.notice.number = this.problemFeedbackDate[index].id
      this.notice.content = this.problemFeedbackDate[index].problem
    },
    // 通知用户
    noticeUser() {
      if(this.problemFeedbackDate[this.index].noticeState == 1) {
        this.$message({
          message: '该用户通知过，请勿重复通知...',
          type: 'warning',
        })
        this.noticeDialogVisible = false
        return
      }
      // 通知用户接口
      let problemParam = {}
      problemParam['id'] = this.problemFeedbackDate[this.index].id
      problemParam['email'] = this.problemFeedbackDate[this.index].email
      problemParam['problem'] = this.problemFeedbackDate[this.index].problem
      feedback.noticeUser(problemParam).then( _ => {
        this.problemFeedbackDate[this.index].noticeState = "1"
        this.noticeDialogVisible = false
        this.$notify({
          title: '通知',
          message: '发送成功',
          type: 'success'
        });
      })
    },
    // 编辑问题状态
    editProblemState(index) {
      // 设置当前编辑行索引
      this.index = index
      // 打开编辑框
      this.editDialogVisible = true
      // 设置当前问题状态
      this.state = this.problemFeedbackDate[index].problemState
    },
    // 删除
    deleteProblem(index) {
      // 问题状态
      let problemState = this.problemFeedbackDate[index].problemState
      // 是否处于未解决
      if(problemState == 0) {
        this.$message({
          message: '该问题未解决，不能删除',
          type: 'warning'
        })
        return
      }
      // 是处于处理中
      if(problemState == 1) {
        this.$message({
          message: '该问题正在处理中...',
          type: 'warning'
        })
        return
      }
      // 是否已解决但未通知，提示
      if(this.problemFeedbackDate[index].noticeState == 0) {
        this.$confirm('未通知用户，是否继续删除？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '发送通知',
          distinguishCancelAndClose: true,
          type: 'warning'
        }).then(() => {
          // 删除接口
          feedback.deleteProblem(this.problemFeedbackDate[index].id).then(_ => {
            this.$refs.pagination.pageParam.pageIndex = this.problemFeedbackDate.length == 1 ? (
              this.$refs.pagination.pageParam.pageIndex - 1 <= 0 ? 1 :
                this.$refs.pagination.pageParam.pageIndex - 1) :
              this.$refs.pagination.pageParam.pageIndex

            this.findProblemFeedbackList()
            this.$notify({
              title: '操作',
              message: '删除成功',
              type: 'success'
            });
          })
        }).catch((action) => {
          if(action === 'cancel') {
            this.index = index
            // 通知操作
            this.noticeDialogVisible = true
          }
        });
      }else {
        this.$confirm('此操作将删除该条记录，是否继续？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          // 删除接口
          feedback.deleteProblem(this.problemFeedbackDate[index].id).then(_ => {
            this.$refs.pagination.pageParam.pageIndex = this.problemFeedbackDate.length == 1 ?
              (this.$refs.pagination.pageParam.pageIndex - 1 <= 0 ? 1 :
                this.$refs.pagination.pageParam.pageIndex - 1) :
                this.$refs.pagination.pageParam.pageIndex

            this.findProblemFeedbackList()
            this.$notify({
              title: '操作',
              message: '删除成功',
              type: 'success'
            });
          })
        }).catch(() => {

        })
      }
    },
    // 提交编辑
    editProblemStateSubmit() {
      if(this.problemFeedbackDate[this.index].problemState == this.state) {
        this.$message('未做任何改变')
        // 关闭编辑框
        this.editDialogVisible = false
        return
      }

      // 修改问题状态接口
      let problemParam = {}
      problemParam['id'] = this.problemFeedbackDate[this.index].id
      problemParam['problemState'] = this.state
      feedback.editProblemState(problemParam).then(() => {
        this.problemFeedbackDate[this.index].problemState = this.state
        this.editDialogVisible = false
        this.$message({
          message: '修改成功',
          type: 'success'
        })
      })
    },
    // 查询问题反馈列表
    findProblemFeedbackList() {
      let param = {
        ...this.queryParam,
        pageIndex: this.$refs.pagination.pageParam.pageIndex,
        pageSize: this.$refs.pagination.pageParam.pageSize
      }

      feedback.findProblemFeedbackList(param).then((response) => {
        this.problemFeedbackDate = response.data.list
        this.$refs.pagination.pageParam.totalCount = response.data.total
      })
    }
  },
  mounted() {
    this.findProblemFeedbackList()
  }
}
</script>

<style>
.app {
  padding: 10px;
}
.email {
  cursor: pointer;
  color: #E6A23C;
}
.email:hover {
  color: #409EFF;
}

.notice-info{
  font-weight: bold;
  color: #E6A23C;
}
</style>
