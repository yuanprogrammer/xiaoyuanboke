<template>
  <div class="app-container">
    <!-- 筛选 -->
    <!-- 搜索筛选 -->
    <el-form :inline="true" :model="queryParam" class="user-search" ref="queryParam">
      <el-form-item prop="id">
        <el-input size="small" v-model="queryParam.id" placeholder="编号"></el-input>
      </el-form-item>
      <el-form-item prop="username">
        <el-input size="small" v-model="queryParam.username" placeholder="用户名"></el-input>
      </el-form-item>
      <el-form-item prop="nickname">
        <el-input size="small" v-model="queryParam.nickname" placeholder="昵称"></el-input>
      </el-form-item>
      <el-form-item prop="email">
        <el-input size="small" v-model="queryParam.email" placeholder="邮箱"></el-input>
      </el-form-item>
      <el-form-item prop="mobileNumber">
        <el-input size="small" v-model="queryParam.mobileNumber" placeholder="号码"></el-input>
      </el-form-item>
      <el-form-item class="time" prop="gmtCreate">
        <el-date-picker size="small" v-model="queryParam.gmtCreate" type="date" value-format="yyyy-MM-dd" placeholder="注册时间">
          </el-date-picker>
      </el-form-item>
      <el-form-item class="search">
        <el-button size="small" type="primary" icon="el-icon-search" @click="searchUser">搜索</el-button>
      </el-form-item>
      <el-form-item class="search">
        <el-button size="small" type="warning" icon="el-icon-refresh-left" @click="refresh('queryParam')">刷新</el-button>
      </el-form-item>
    </el-form>

    <el-table :data="userList"
              border
              size="small"
              style="width: 100%">
      <el-table-column fixed
                       prop="id"
                       label="编号"
                       label-class-name="tableTitle"
                       width="160">
      </el-table-column>
      <el-table-column fixed
                       prop="username"
                       label="用户名"
                       label-class-name="tableTitle"
                       width="150">
      </el-table-column>
      <el-table-column prop="nickname"
                       label="昵称"
                       label-class-name="tableTitle"
                       width="150">
      </el-table-column>
      <el-table-column prop="email"
                       label="邮箱"
                       label-class-name="tableTitle"
                       width="150">
      </el-table-column>
      <el-table-column prop="mobileNumber"
                       label="号码"
                       label-class-name="tableTitle"
                       width="120">
      </el-table-column>
      <el-table-column prop="wechatNumber"
                       label="微信ID"
                       label-class-name="tableTitle"
                       width="120">
      </el-table-column>
      <el-table-column label="头像"
                       label-class-name="tableTitle"
                       width="80">
        <template slot-scope="scope">
          <el-avatar :src="scope.row.avatar"></el-avatar>
        </template>
      </el-table-column>
      <el-table-column prop="gmtCreate"
                       label="注册时间"
                       label-class-name="tableTitle"
                       width="150">
      </el-table-column>
      <el-table-column prop="gmtLogin"
                       label="登录时间"
                       label-class-name="tableTitle"
                       width="150">
      </el-table-column>
      <el-table-column fixed="right"
                       label="操作"
                       label-class-name="tableTitle"
                       width="180">
        <template slot-scope="scope">
          <el-button @click="handleClick(scope.row)"
                     type="text"
                     style="color: #E6A23C;"
                     :disabled="!isAuth(['USER:UPDATE'])"
                     size="small">编辑</el-button>
          <el-button type="text"
                     style="color:#F56C6C;"
                     :disabled="!isAuth(['USER:DELETE'])"
                     size="small">删除</el-button>
          <el-button type="text"
                     :disabled="!isAuth(['USER:DISABLE'])"
                     size="small">加入黑名单</el-button>
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
      pageParam: {
        pageIndex: 1,
        pageSize: 10,
        total: 0
      },
      userList: [],
      queryParam: {
        id: '',
        username: '',
        nickname: '',
        email: '',
        mobileNumber: '',
        gmtCreate: ''
      }
    }
  },
  methods: {
    handleClick(row) {
      console.log(row)
    },
    handleSizeChange(val) {
      this.pageParam.pageSize = val
      this.findUserList()
    },
    handleCurrentChange(val) {
      this.pageParam.pageIndex = val
      this.findUserList()
    },
    findUserList() {
      let queryParam = {
        ...this.pageParam,
        ...this.queryParam
      }

      userApi.findUserList(queryParam).then(res => {
        if (res.success) {
          this.userList = res.data.list
          this.pageParam.total = res.data.total
        }
      })
    },
    // 搜索
    searchUser() {
      this.findUserList()
    },
    refresh(formName) {
      this.pageParam.pageIndex = 1
      this.pageParam.pageSize = 10
      this.$refs[formName].resetFields();
      this.findUserList()
    }
  },
  created() {
    this.findUserList()
  }
}
</script>

<style scoped>
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

/* .search {
  left: 20px;
} */
</style>
