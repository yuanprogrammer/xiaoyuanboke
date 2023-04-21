<template>
  <div>
    <div style="text-align: center" class="permission-select">
      <el-transfer
        style="text-align: left; display: inline-block;"
        v-model="value"
        filterable
        :titles="['权限列表', '已有权限']"
        :format="{
          noChecked: '${total}',
          hasChecked: '${checked}/${total}'
      }"
        @change="handleChange"
        :data="permissions">
        <span slot-scope="{ option }">{{ option.label }}</span>
<!--        <el-button class="transfer-footer" slot="left-footer" size="small">操作</el-button>-->
<!--        <el-button class="transfer-footer" slot="right-footer" size="small">操作</el-button>-->
      </el-transfer>
    </div>
  </div>
</template>

<script>
import permissionApi from '@/api/system/permission'
export default {
  data() {
    const generateData = _ => {
    };
    return {
      permissions: [],
      value: [],
    };
  },

  methods: {
    handleChange(value, direction, movedKeys) {
      console.log(value, direction, movedKeys);
    },
    getPermissionList() {
      permissionApi.getPermissionList().then( res => {
        if (res.success) {
          const data = res.data
          for (let i = 0; i < data.length; i++) {
            this.permissions.push({
              key: data[i].id,
              label: `${data[i].modName}-${data[i].actName}`
            })
          }
        }
      })
    },
    getPermissionListByRoleKey(param) {
      permissionApi.getPermissionListByRoleKey(param).then( res => {
        if (res.success) {
          const data = res.data
          for (let i = 0; i < data.length; i++) {
            this.value.push(data[i].id)
          }
        }
      })
    }
  }
};
</script>

<style lang="scss" scoped>
//.transfer-footer {
//  margin-left: 20px;
//  padding: 6px 5px;
//}

</style>
