<template>
  <div class="app">
    <div class="addBtn">
       <el-button :disabled="deleteMore || !isAuth(['MESSAGE:DELETE'])" icon="el-icon-delete" type="danger" @click="deleteMoreMessage()">一键删除</el-button>
    </div>

    <el-table
    ref="multipleTable"
    size="small"
    :data="homeMessageList"
    @selection-change="handleSelectionChange"
    style="width: 100%;">

     <el-table-column
      type="selection"
      width="55">
    </el-table-column>

    <el-table-column type="expand">
      <template slot-scope="props">
        <el-form label-position="left" inline class="demo-table-expand">
          <el-form-item label="更多留言内容">
            <span>{{ props.row.detailContent }}</span>
          </el-form-item>
        </el-form>
      </template>
    </el-table-column>

      <!-- 编号 -->
      <el-table-column
        label="编号"
        width="180">
        <template slot-scope="scope">
          <span>{{ scope.row.id }}</span>
        </template>
      </el-table-column>

      <!-- 反馈时间 -->
      <el-table-column
        label="作者编号"
        width="180">
        <template slot-scope="scope">
          <span>{{ scope.row.authorId }}</span>
        </template>
      </el-table-column>

      <!-- 昵称 -->
      <el-table-column
        label="留言时间"
        width="160">
        <template slot-scope="scope">
          <span>{{ scope.row.gmtCreate }}</span>
        </template>
      </el-table-column>

      <!-- 提议 -->
      <el-table-column
        label="留言内容"
        width="auto"
         show-overflow-tooltip>
        <template slot-scope="scope">
          <span>{{ scope.row.content }}</span>
        </template>
      </el-table-column>


      <el-table-column
        align="center"
        label="操作"
        width="80">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="danger"
            icon="el-icon-delete"
            :disabled="!isAuth(['MESSAGE:DELETE'])"
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
import homeMessage from '@/api/blog/homeMessage'

export default {
  data() {
    return {
      homeMessageList: [],
      pageParam: {
        pageSize: 10, // 每页大小
        pageIndex: 1, // 当前页数
        totalCount: 0, //总记录数
      },
      deleteMore: true, // 一键删除按钮是否禁止
      messageSelection: [],
    }
  },
  methods: {
    handleSelectionChange(val) {
      if(val.length === 0) {
        this.deleteMore = true
      }else {
        this.deleteMore = false
      }
      this.messageSelection = val
    },
    handleCurrentChange(pageIndex) {
      this.findMessageList(pageIndex, this.pageParam.pageSize)
    },
    // 删除
    deleteSuggest(id) {
      this.$confirm('确认删除?', '提示', {
        confirmButtonText: '确定',
        type: 'warning'
      }).then(() => {
        homeMessage.deleteMessage(id).then( _ => {
          this.$notify({
            title: '删除成功',
            type: 'success'
          })
          this.findMessageList(this.pageParam.pageIndex, this.pageParam.pageSize)
        })
      })
    },
    // 一键删除多个
    deleteMoreMessage() {
      this.$confirm('此操作将删除多个内容，是否继续？', '提示', {
        confirmButtonText: '确定',
        type: 'warning'
      }).then(() => {
        let ids = []
        this.messageSelection.forEach(item => {
          ids.push(item.id)
        })
        homeMessage.deleteMoreMessage(ids).then( _ => {
          this.$notify({
            title: '删除成功',
            type: 'success'
          })
          this.findMessageList(this.pageParam.pageIndex, this.pageParam.pageSize)
        })
      })
    },
    // 查询问题反馈列表
    findMessageList(pageIndex, pageSize) {
      homeMessage.findMessageList(pageIndex, pageSize).then((res) => {
        let data = res.data.homeMessageList
        this.homeMessageList = data.list

        this.pageParam.pageIndex = data.pageIndex
        this.pageParam.totalCount = data.totalCount
      })
    }
  },
  mounted() {
    this.findMessageList(1, this.pageParam.pageSize)
  }
}
</script>

<style>
.app {
  padding: 15px;
}

.addBtn {
  width: 100%;
  height: 50px;
}

.addBtn button {
  float: right;
}

.demo-table-expand .el-form-item {
  margin-left: 115px;
  width: 80%;
}
</style>
