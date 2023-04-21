<template>
  <div class="role">
    <div style="margin-bottom: 10px;">
      <el-button :disabled="!isAuth(['ROLE:INSERT'])" type="danger" size="small" @click="addRole" round>添加角色</el-button>
    </div>

    <el-table
      border
      :data="roleDate"
      size="small"
      style="width: 100%">

      <!-- 编号 -->
      <el-table-column
        label="序号"
        width="180">
        <template slot-scope="scope">
          <span>{{ scope.row.id }}</span>
        </template>
      </el-table-column>

      <!-- 角色标识 -->
      <el-table-column
        label="角色标识"
        width="200">
        <template slot-scope="scope">
          <span>{{ scope.row.roleKey }}</span>
        </template>
      </el-table-column>

      <!-- 角色名称 -->
      <el-table-column
        label="角色名称"
        width="200">
        <template slot-scope="scope">
          <el-tag size="medium" type="success">{{ scope.row.roleName }}</el-tag>
        </template>
      </el-table-column>

      <!-- 角色描述 -->
      <el-table-column
        label="角色描述"
        width="auto">
        <template slot-scope="scope">
          <span>{{ scope.row.describe }}</span>
        </template>
      </el-table-column>

      <el-table-column
        width="250">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="warning"
            icon="el-icon-edit"
            :disabled="!isAuth(['ROLE:UPDATE'])"
            @click="editPermission(scope.$index)">编辑权限</el-button>
          <el-button
            size="mini"
            type="danger"
            icon="el-icon-delete"
            :disabled="!isAuth(['ROLE:UPDATE'])"
            @click="deleteRole(scope.row.id)"></el-button>
        </template>
      </el-table-column>
    </el-table>

    <Pagination ref="pagination" @handleSizeChange="handleSizeChange" @handleCurrentChange="handleCurrentChange"></Pagination>

    <el-dialog
      :title="'权限分配 - 当前角色: ' + permissionTitle"
      :visible.sync="permissionVisible"
      width="45%">
      <permission-select ref="per"></permission-select>
      <span slot="footer" class="dialog-footer">
        <el-button size="medium" @click="permissionVisible = false">取 消</el-button>
        <el-button size="medium" type="warning" @click="updateRolePermission">确 认</el-button>
      </span>
    </el-dialog>

    <add-role ref="addRole" :is-open.sync="isOpenAddRole" @changeIsOpen="changeIsOpen" @refreshRole="getRoleList"></add-role>
  </div>
</template>

<script>
import permissionSelect from "../permission/components/PermissionSelect";
import addRole from "./components/addRole";
import Pagination from "../../../components/Pagination";
import roleApi from '@/api/system/role'

export default {
  components: {
    permissionSelect,
    addRole,
    Pagination
  },
  data() {
    return {
      roleDate: [],
      roleIndex: 0,
      permissionVisible: false,
      permissionTitle: '',
      isOpenAddRole: false,
      queryParam: {
        roleName: ''
      }
    }
  },
  methods: {
    handleSizeChange() {
      this.getRoleList()
    },
    handleCurrentChange() {
      this.getRoleList()
    },
    getRoleList() {
      let param = {
        ...this.queryParam,
        pageIndex: this.$refs.pagination.pageParam.pageIndex,
        pageSize: this.$refs.pagination.pageParam.pageSize,
      }

      roleApi.getRoleList(param).then( res => {
        this.roleDate = res.data.list
        this.$refs.pagination.pageParam.totalCount = res.data.total
      })
    },
    addRole() {
      this.isOpenAddRole = true
      this.$nextTick(() => {
        this.$refs.addRole.init()
      })
    },
    changeIsOpen(isOpen) {
      this.isOpenAddRole = isOpen
    },
    editPermission(index) {
      this.permissionVisible = true
      this.permissionTitle = this.roleDate[index].roleName
      this.$nextTick(() => {
        this.roleIndex = index
        this.$refs.per.value = []
        this.$refs.per.permissions = []
        this.$refs.per.getPermissionList()
        this.$refs.per.getPermissionListByRoleKey({
          roleKey: this.roleDate[index].roleKey
        })
      })
    },
    updateRolePermission() {
      this.$refs.per.value.sort((a, b) => { return a - b})
      let param = {
        roleKey: this.roleDate[this.roleIndex].roleKey,
        permissions: this.$refs.per.value
      }

      roleApi.updateRolePermission(param).then( res => {
        if (res.success) {
          this.$message({
            type: 'success',
            message: '修改成功'
          })
        }
      }).finally( _ => {
        this.permissionVisible = false
      })
    },
    deleteRole(id) {
      this.$confirm('此操作将删除该角色, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'error'
      }).then(() => {
        let param = {
          'id': id
        }
        roleApi.deleteRole(param).then( res => {
          if (res.success) {
            this.getRoleList()
            this.$message({
              type: 'success',
              message: '删除成功'
            })
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    }
  },
  mounted() {
    this.getRoleList()
  }
}
</script>

<style lang="scss" scoped>
.role {
  padding: 10px;
}
</style>
