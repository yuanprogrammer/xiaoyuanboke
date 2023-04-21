<template>
  <div class="app-container">
    <!-- 添加 -->
    <div style="width: 100%;">
      <el-button class="addBtn1" icon="el-icon-plus" type="primary" @click="addDialogVisible = true" :disabled="!isAuth(['FRIEND:INSERT'])"> 添 加</el-button>
    </div>

    <el-table
    border
    size="small"
    :data="friendLinkList"
    style="width: 100%">

      <!-- 编号 -->
      <el-table-column
        label="编号"
        width="60">
        <template slot-scope="scope">
          <span>{{ scope.row.id }}</span>
        </template>
      </el-table-column>

      <!-- 反馈时间 -->
      <el-table-column
        label="创建时间"
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
          <span>{{ scope.row.nickname ? scope.row.nickname : 'null' }}</span>
        </template>
      </el-table-column>

      <!-- 提议 -->
      <el-table-column
        label="领域"
        width="120">
        <template slot-scope="scope">
          <span>{{ scope.row.field }}</span>
        </template>
      </el-table-column>

      <el-table-column
        label="链接"
        width="150">
        <template slot-scope="scope">
          <span>{{ scope.row.link }}</span>
        </template>
      </el-table-column>

      <el-table-column
        label="描述"
        width="auto">
        <template slot-scope="scope">
          <span>{{ scope.row.describe }}</span>
        </template>
      </el-table-column>

      <el-table-column
        align="center"
        label="操作"
        width="130">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="danger"
            icon="el-icon-edit"
            :disabled="!isAuth(['FRIEND:UPDATE'])"
            @click="updateFriendLink(scope.row)"></el-button>
          <el-button
            size="mini"
            type="danger"
            icon="el-icon-delete"
            :disabled="!isAuth(['FRIEND:DELETE'])"
            @click="deleteFriendLink(scope.row.id)"></el-button>
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

    <!-- 添加框 -->
    <el-dialog title="添加新的友链" :visible.sync="addDialogVisible" top="5vh">
      <el-form :model="addFriendForm" label-width="80px" label-position="right" :rules="rules" ref="addFriendForm">

        <el-form-item label="昵称" prop="nickname">
          <el-input type="text" v-model="addFriendForm.nickname" clearable show-word-limit maxlength="32"></el-input>
        </el-form-item>

        <el-form-item label="领域" prop="field">
          <el-input type="text" v-model="addFriendForm.field" clearable show-word-limit maxlength="16"></el-input>
        </el-form-item>

        <el-form-item label="链接" prop="link">
          <el-input type="text" v-model="addFriendForm.link" clearable show-word-limit maxlength="128"></el-input>
        </el-form-item>

        <el-form-item label="简单描述" prop="describe">
          <el-input type="textarea" v-model="addFriendForm.describe" rows="6" resize="none" clearable show-word-limit maxlength="125"></el-input>
        </el-form-item>

      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button type="warning" icon="el-icon-circle-close" @click="clearForm('addFriendForm')">清 空</el-button>
        <el-button type="danger" icon="el-icon-check" @click="submitFriendLink('addFriendForm', true)">提 交</el-button>
      </div>
    </el-dialog>

    <!-- 编辑框 -->
    <el-dialog title="编辑友链" :visible.sync="editDialogVisible" top="5vh">
      <el-form :model="editFriendForm" label-width="80px" label-position="right" :rules="rules" ref="editFriendForm">

        <el-form-item label="昵称" prop="nickname">
          <el-input type="text" v-model="editFriendForm.nickname" clearable show-word-limit maxlength="32"></el-input>
        </el-form-item>

        <el-form-item label="领域" prop="field">
          <el-input type="text" v-model="editFriendForm.field" clearable show-word-limit maxlength="16"></el-input>
        </el-form-item>

        <el-form-item label="链接" prop="link">
          <el-input type="text" v-model="editFriendForm.link" clearable show-word-limit maxlength="128"></el-input>
        </el-form-item>

        <el-form-item label="简单描述" prop="describe">
          <el-input type="textarea" v-model="editFriendForm.describe" rows="6" resize="none" clearable show-word-limit maxlength="125"></el-input>
        </el-form-item>

      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button type="danger" icon="el-icon-check" @click="submitFriendLink('editFriendForm', false)">提 交</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import friendlink from '@/api/blog/friendlink'

export default {
  data() {
    return {
      friendLinkList: [],
      pageParam: {
        pageSize: 10, // 每页大小
        pageIndex: 1, // 当前页数
        totalCount: 0, //总记录数
      },
      // 添加
      addDialogVisible: false,
      addFriendForm: {
        nickname: '',
        field: '',
        describe: '',
        link: ''
      },
      // 编辑
      editFriendForm: { },
      editDialogVisible: false,
      // 规则
      rules: {
        nickname: [
          { required: true, message: '请填写昵称', trigger: 'blur' },
        ],
        field: [
          { required: true, message: '请填写领域', trigger: 'blur' },
        ],
        link: [
          { required: true, message: '请填写链接', trigger: 'blur' },
        ],
        describe: [
          { required: true, message: '请填写描述', trigger: 'blur' },
        ]
      }
    }
  },
  methods: {
    handleCurrentChange(pageIndex) {
      this.findFriendLinkList(pageIndex, this.pageParam.pageSize)
    },
    // 清空表单
    clearForm(formName) {
      this.$refs[formName].resetFields()
    },
    // 删除
    deleteFriendLink(id) {
      this.$confirm('确认删除?', '提示', {
        confirmButtonText: '确定',
        type: 'warning'
      }).then(() => {
        friendlink.deleteFriendLink(id).then( _ => {
          this.$notify({
            title: '删除成功',
            type: 'success'
          })
          this.findFriendLinkList(this.pageParam.pageIndex, this.pageParam.pageSize)
        })
      })
    },
    // 查询问题反馈列表
    findFriendLinkList(pageIndex, pageSize) {
      friendlink.findFriendLinkList(pageIndex, pageSize).then((res) => {
        let data = res.data.friendLinkList
        this.friendLinkList = data.list

        this.pageParam.pageIndex = data.pageIndex
        this.pageParam.totalCount = data.totalCount
      })
    },
    // 编辑
    updateFriendLink(friend) {
      this.editFriendForm = friend
      this.editDialogVisible = true
    },
    // 提交
    submitFriendLink(formName, status) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          // 判断编辑还是添加 true -> 添加, false -> 编辑
          if(status) {
            friendlink.addFriendLink(this.addFriendForm).then( _ => {
              this.findFriendLinkList(this.pageParam.pageIndex, this.pageParam.pageSize)
              this.$message({
                message: '添加成功',
                type: 'success'
              })
              this.addDialogVisible = false
              this.clearForm('addFriendForm')
            })
          }else {
            friendlink.editFriendLink(this.editFriendForm).then( _ => {
              this.findFriendLinkList(this.pageParam.pageIndex, this.pageParam.pageSize)
              this.$message({
                message: '修改成功',
                type: 'success'
              })
              this.editDialogVisible = false
            })
          }
        }
      });
    },
  },
  mounted() {
    this.findFriendLinkList(1, this.pageParam.pageSize)
  }
}
</script>

<style>
.addBtn1 {
  float: right;
  margin-bottom: 15px;
}
</style>
