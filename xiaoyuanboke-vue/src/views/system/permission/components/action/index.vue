<template>
  <div style="padding: 5px;">
    <div style="margin-bottom: 10px;">
      <el-button :disabled="!isAuth(['PERMISSION:INSERT'])" type="danger" size="small" @click="addAction" round>添加操作</el-button>
    </div>

    <el-table
      border
      :data="actionData"
      size="small"
      style="width: 100%">

      <!-- 编号 -->
      <el-table-column
        label="编号"
        width="100">
        <template slot-scope="scope">
          <span>{{ scope.row.id }}</span>
        </template>
      </el-table-column>


      <!-- 操作标识 -->
      <el-table-column
        label="操作标识"
        width="auto">
        <template slot-scope="scope">
          <span>{{ scope.row.actKey }}</span>
        </template>
      </el-table-column>

      <!-- 操作名称 -->
      <el-table-column
        label="操作名称"
        width="auto">
        <template slot-scope="scope">
          <span>{{ scope.row.actName }}</span>
        </template>
      </el-table-column>

      <el-table-column
        label="操作"
        width="150">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="warning"
            icon="el-icon-edit"
            :disabled="!isAuth(['PERMISSION:UPDATE'])"
            @click="editAction(scope.row)"></el-button>
          <el-button
            size="mini"
            type="danger"
            icon="el-icon-delete"
            :disabled="!isAuth(['PERMISSION:UPDATE'])"
            @click="deleteAction(scope.row.id)"></el-button>
        </template>
      </el-table-column>
    </el-table>

    <div class="block" style="margin-top:10px;">
      <el-pagination
        @current-change="handleCurrentChange"
        :current-page="pageParam.pageIndex"
        :page-size="pageParam.pageSize"
        layout="total, prev, pager, next"
        :total="pageParam.totalCount">
      </el-pagination>
    </div>

    <el-dialog
      :title=" isAdd ? '添加新的操作' : '编辑操作'"
      :visible.sync="isOpenDialog"
      width="30%"
      @close="resetForm">
      <el-form :model="actionParam" ref="actionParam" :rules="rules">
        <el-form-item label="操作标识" prop="actKey" label-width="80px">
          <el-input size="medium" v-model="actionParam.actKey" placeholder="操作标识(全大写)"></el-input>
        </el-form-item>
        <el-form-item label="操作名称" prop="actName" label-width="80px">
          <el-input size="medium" v-model="actionParam.actName" placeholder="操作名称"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
      <el-button size="small" type="info" @click="isOpenDialog = false" round>取 消</el-button>
      <el-button size="small" type="danger" @click="submitAction" round>{{ isAdd ? '添 加': '修 改'}}</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import permissionApi from '@/api/system/permission'
export default {
  data() {
    return {
      isOpenDialog: false,
      isAdd: true,
      actionData: [],
      actionParam: {
        id: '',
        actKey: '',
        actName: ''
      },
      rules: {
        actKey: [ { required: true, message: '请填写模块标识(字母全大写)', trigger: 'blur' } ],
        actName: [ { required: true, message: '请填写模块名称', trigger: 'blur' } ]
      },
      pageParam: {
        pageIndex: 1,
        pageSize: 7,
        totalCount: 0
      }
    }
  },
  created() {
    this.getActionList()
  },
  methods: {
    handleCurrentChange(val) {
      this.pageParam.pageIndex = val
      this.getModuleList()
    },
    getActionList() {
      let param = {
        'pageIndex': this.pageParam.pageIndex,
        'pageSize': this.pageParam.pageSize
      }
      permissionApi.selectActionList(param).then( res => {
        if (res.success) {
          this.actionData = res.data.list
          this.pageParam.totalCount = res.data.total
        }
      })
    },
    editAction(index) {
      this.isAdd = false
      this.initDialog(index)
    },
    deleteAction(id) {
      let param = {
        'id': id
      }
      permissionApi.deleteAction(param).then( res => {
        if (res.success) {
          this.getActionList()
          this.isOpenDialog = false
          this.$message({
            message: '删除成功',
            type: 'success'
          })
        }
      })
    },
    addAction() {
      this.isAdd = true
      this.initDialog()
    },
    resetForm() {
      this.$refs['actionParam'].resetFields()
    },
    initDialog(row) {
      this.isOpenDialog = true
      if (row !== undefined) {
        console.log(row)
        this.actionParam.id = row.id
        this.actionParam.actKey = row.actKey
        this.actionParam.actName = row.actName
      }else {
        this.actionParam.id = ''
        this.actionParam.actKey = ''
        this.actionParam.actName = ''
      }
    },
    submitAction() {
      // 添加
      if (this.isAdd) {
        let param = {
          'actKey': this.actionParam.actKey,
          'actName': this.actionParam.actName
        }
        permissionApi.insertAction(param).then( res => {
          if (res.success) {
            this.getActionList()
            this.isOpenDialog = false
            this.$message({
              message: '添加成功',
              type: 'success'
            })
          }
        })
      }else {
        // 修改
        permissionApi.updateAction(this.actionParam).then( res => {
          if (res.success) {
            this.getActionList()
            this.isOpenDialog = false
            this.$message({
              message: '修改成功',
              type: 'success'
            })
          }
        })
      }
    }
  }
}
</script>

<style lang="scss" scoped>

</style>
