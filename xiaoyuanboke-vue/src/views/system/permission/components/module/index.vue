<template>
  <div style="padding: 5px;">
    <div style="margin-bottom: 10px;">
      <el-button :disabled="!isAuth(['PERMISSION:INSERT'])" type="danger" size="small" @click="addModule" round>添加模块</el-button>
    </div>

    <el-table
      border
      :data="moduleData"
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


      <!-- 模块标识 -->
      <el-table-column
        label="模块标识"
        width="auto">
        <template slot-scope="scope">
          <span>{{ scope.row.modKey }}</span>
        </template>
      </el-table-column>

      <!-- 模块名称 -->
      <el-table-column
        label="模块名称"
        width="auto">
        <template slot-scope="scope">
          <span>{{ scope.row.modName }}</span>
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
            @click="editModule(scope.row)"></el-button>
          <el-button
            size="mini"
            type="danger"
            icon="el-icon-delete"
            :disabled="!isAuth(['PERMISSION:UPDATE'])"
            @click="deleteModule(scope.row.id)"></el-button>
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
      :title=" isAdd ? '添加新的模块' : '编辑模块'"
      :visible.sync="isOpenDialog"
      width="30%"
      @close="resetForm">
      <el-form :model="moduleParam" ref="moduleParam" :rules="rules">
        <el-form-item label="模块标识" prop="modKey" label-width="80px">
          <el-input size="medium" v-model="moduleParam.modKey" placeholder="模块标识(全大写)"></el-input>
        </el-form-item>
        <el-form-item label="模块名称" prop="modName" label-width="80px">
          <el-input size="medium" v-model="moduleParam.modName" placeholder="模块名称"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
      <el-button size="small" type="info" @click="isOpenDialog = false" round>取 消</el-button>
      <el-button size="small" type="danger" @click="submitModule" round>{{ isAdd ? '添 加': '修 改'}}</el-button>
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
      moduleData: [],
      moduleParam: {
        id: '',
        modKey: '',
        modName: ''
      },
      rules: {
        modKey: [ { required: true, message: '请填写模块标识(字母全大写)', trigger: 'blur' } ],
        modName: [ { required: true, message: '请填写模块名称', trigger: 'blur' } ]
      },
      pageParam: {
        pageIndex: 1,
        pageSize: 7,
        totalCount: 0
      }
    }
  },
  created() {
    this.getModuleList()
  },
  methods: {
    handleCurrentChange(val) {
      this.pageParam.pageIndex = val
      this.getModuleList()
    },
    getModuleList() {
      let param = {
        'pageIndex': this.pageParam.pageIndex,
        'pageSize': this.pageParam.pageSize
      }
      permissionApi.selectModuleList(param).then( res => {
        if (res.success) {
          this.moduleData = res.data.list
          this.pageParam.totalCount = res.data.total
        }
      })
    },
    editModule(index) {
      this.isAdd = false
      this.initDialog(index)
    },
    deleteModule(id) {
      let param = {
        'id': id
      }
      permissionApi.deleteModule(param).then( res => {
        if (res.success) {
          this.getModuleList()
          this.isOpenDialog = false
          this.$message({
            message: '删除成功',
            type: 'success'
          })
        }
      })
    },
    addModule() {
      this.isAdd = true
      this.initDialog()
    },
    resetForm() {
      this.$refs['moduleParam'].resetFields()
    },
    initDialog(row) {
      this.isOpenDialog = true
      if (row !== undefined) {
        console.log(row)
        this.moduleParam.id = row.id
        this.moduleParam.modKey = row.modKey
        this.moduleParam.modName = row.modName
      }else {
        this.moduleParam.id = ''
        this.moduleParam.modKey = ''
        this.moduleParam.modName = ''
      }
    },
    submitModule() {
      // 添加
      if (this.isAdd) {
        let param = {
          'modKey': this.moduleParam.modKey,
          'modName': this.moduleParam.modName
        }
        permissionApi.insertModule(param).then( res => {
          if (res.success) {
            this.getModuleList()
            this.isOpenDialog = false
            this.$message({
              message: '添加成功',
              type: 'success'
            })
          }
        })
      }else {
        // 修改
        permissionApi.updateModule(this.moduleParam).then( res => {
          if (res.success) {
            this.getModuleList()
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
