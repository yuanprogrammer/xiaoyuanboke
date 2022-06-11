<template>
  <div class="app-container">
    <!-- 搜索筛选 -->
    <el-form :inline="true" :model="queryParam" class="user-search" ref="queryParam">
      <el-form-item prop="nickname">
        <el-input size="small" v-model="queryParam.nickname" placeholder="昵称"></el-input>
      </el-form-item>
      <el-form-item prop="username">
        <el-input size="small" v-model="queryParam.username" placeholder="用户名"></el-input>
      </el-form-item>
      <el-form-item prop="email">
        <el-input size="small" v-model="queryParam.email" placeholder="邮箱"></el-input>
      </el-form-item>
      <el-form-item prop="mobileNumber">
        <el-input size="small" v-model="queryParam.mobileNumber" placeholder="号码"></el-input>
      </el-form-item>
      <el-form-item class="op-type" prop="describe">
        <el-select size="small" v-model="queryParam.describe" placeholder="操作类型">
          <el-option v-for="(item, index) in operationType" :key="index" :label="item" :value="item"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item class="time" prop="operatedTime">
        <el-date-picker size="small" v-model="queryParam.operatedTime" type="date" value-format="yyyy-MM-dd" placeholder="操作时间">
          </el-date-picker>
      </el-form-item>
      <el-form-item class="search">
        <el-button size="small" type="primary" icon="el-icon-search" @click="searchUser">搜索</el-button>
      </el-form-item>
      <el-form-item class="search">
        <el-button size="small" type="warning" icon="el-icon-refresh-left" @click="refresh('queryParam')">刷新</el-button>
      </el-form-item>
    </el-form>

    <el-table :data="operationList"
              border
              size="small"
              style="width: 100%">
      <el-table-column fixed
                       prop="nickname"
                       label="昵称"
                       label-class-name="tableTitle"
                       width="180">
      </el-table-column>
      <el-table-column fixed
                       prop="username"
                       label="用户名"
                       label-class-name="tableTitle"
                       width="180">
      </el-table-column>
      <el-table-column prop="email"
                       label="邮箱"
                       label-class-name="tableTitle"
                       width="150">
      </el-table-column>
      <el-table-column prop="mobileNumber"
                       label="号码"
                       label-class-name="tableTitle"
                       width="150">
      </el-table-column>
      <el-table-column prop="describe"
                       label="操作"
                       label-class-name="tableTitle"
                       width="auto">
      </el-table-column>
      <el-table-column label="头像"
                       label-class-name="tableTitle"
                       width="80">
        <template slot-scope="scope">
          <el-avatar :src="scope.row.avatar"></el-avatar>
        </template>
      </el-table-column>
      <el-table-column prop="operatedTime"
                       label="操作时间"
                       label-class-name="tableTitle"
                       width="150">
      </el-table-column>
      <el-table-column fixed="right"
                       label="操作"
                       label-class-name="tableTitle"
                       width="100">
        <template slot-scope="scope">
          <el-button type="text"
                     style="color:#F56C6C;"
                     disabled
                     @click="deleteOp(scope.row.id)"
                     size="small">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <div class="block" style="margin-top: 20px">
      <el-pagination @size-change="handleSizeChange"
                     @current-change="handleCurrentChange"
                     background
                     :current-page="pageParam.pageIndex"
                     :page-sizes="[10, 20, 30, 50]"
                     :page-size="pageParam.pageSize"
                     layout="total, sizes, prev, pager, next, jumper"
                     :total="pageParam.total">
      </el-pagination>
    </div>
  </div>
</template>

<script>
import userApi from '@/api/user'

export default {
  data() {
    return {
      operationList: [],
      operationType: [],
      queryParam: {
        nickname: '',
        username: '',
        email: '',
        mobileNumber: '',
        describe: '',
        operatedTime: ''
      },
      pageParam: {
        pageIndex: 1,
        pageSize: 10,
        total: 0
      },
    }
  },
  methods: {
    handleSizeChange(val) {
      this.pageParam.pageSize = val
      this.findUserOperationList()
    },
    handleCurrentChange(val) {
      this.pageParam.pageIndex = val
      this.findUserOperationList()
    },
    findOperationType() {
      userApi.findOperationType().then( res => {
        this.operationType = res.data.operationType
      })
    },
    findUserOperationList() {
      userApi.findUserOperationList(this.pageParam, this.queryParam).then( res => {
        this.operationList = res.data.list
        this.pageParam.total = res.data.total
      })
    },
    // 搜索
    searchUser() {
      this.findUserOperationList()
    },
    refresh(formName) {
      this.pageParam.pageIndex = 1
      this.pageParam.pageSize = 10
      this.$refs[formName].resetFields();
      this.findUserOperationList()
    }
  },
  created() {
    this.findOperationType()
    this.findUserOperationList()
  }
}
</script>

<style>
.tableTitle {
  font-weight: bold !important;
  color: rgb(72, 72, 72);
}
.time {
  width: 160px;
}

.time .el-form-item__content,
.time .el-form-item__content .el-date-editor {
  width: 160px;
}

.time input{
  width: 100%;
}

.op-type{
  width: 160px;
}
</style>