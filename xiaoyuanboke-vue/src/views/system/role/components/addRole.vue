<template>
  <el-dialog title="添加新的角色" :visible.sync="addRoleVisible" width="45%" @close="closeAddRole" :show-close="false">
    <el-form label-position="right" :model="roleParam" :rules="rules" ref="roleParam" size="small" style="margin-bottom: 10px;">
      <el-form-item label="角色标识" prop="roleKey" label-width="80px">
        <el-input v-model="roleParam.roleKey" autocomplete="off" placeholder="字母大写"></el-input>
      </el-form-item>
      <el-form-item label="角色名称" prop="roleName" label-width="80px">
        <el-input v-model="roleParam.roleName" autocomplete="off" placeholder="请输入角色名称"></el-input>
      </el-form-item>
      <el-form-item label="角色描述" prop="describe" label-width="80px">
        <el-input v-model="roleParam.describe" autocomplete="off" placeholder="请输入角色描述"></el-input>
      </el-form-item>
    </el-form>
    <permission-select ref="addPer"></permission-select>
    <div slot="footer" class="dialog-footer">
      <el-button size="small" type="warning" @click="closeAddRole" round>取 消</el-button>
      <el-button size="small" type="primary" @click="submitAddRole" round>确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
import permissionSelect from "../../permission/components/PermissionSelect";
import roleApi from "@/api/system/role";
export default {
  components: {
    permissionSelect
  },
  props: {
    'isOpen': {
      type: Boolean,
      default: false
    }
  },
  watch: {
    isOpen() {
      this.addRoleVisible = this.isOpen
    }
  },
  data() {
    return {
      addRoleVisible: false,
      roleParam: {
        roleKey: '',
        roleName: '',
        describe: ''
      },
      rules: {
        roleKey: [
          { required: true, message: '请输入角色标识符(字母大写)', trigger: 'blur' },
        ],
        roleName: [
          { required: true, message: '请输入角色名称', trigger: 'blur' },
        ],
        describe: [
          { required: true, message: '请输入角色描述', trigger: 'blur' },
        ]
      }
    }
  },
  methods: {
    closeAddRole() {
      this.$refs['roleParam'].resetFields()
      this.$emit('changeIsOpen', false)
    },
    init() {
      this.$nextTick(() => {
        this.$refs.addPer.value = []
        this.$refs.addPer.permissions = []
        this.$refs.addPer.getPermissionList()
      })
    },
    submitAddRole() {
      this.$refs['roleParam'].validate((valid) => {
        if (valid) {
          if (this.$refs.addPer.value.length === 0) {
            this.$message({
              message: '请至少选择一项权限！',
              type: 'warning'
            })
          }else {
            // 提交
            let param = {
              ...this.roleParam,
              'permissions': this.$refs.addPer.value
            }

            roleApi.insertRole(param).then( res => {
              if (res.success) {
                this.$message({
                  message: '添加成功',
                  type: 'success'
                })
                this.$emit('refreshRole')
                this.closeAddRole()
              }
            })
          }
        }
      })
    }
  }
}
</script>
