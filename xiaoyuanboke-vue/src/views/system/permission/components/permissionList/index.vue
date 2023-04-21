<template>
  <div>
    <el-table
      border
      :data="permissionData"
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

      <!-- 模块 -->
      <el-table-column
        label="模块"
        width="200">
        <template slot-scope="scope">
          <span>{{ scope.row.modName }}</span>
        </template>
      </el-table-column>

      <!-- 操作 -->
      <el-table-column
        label="操作"
        width="200">
        <template slot-scope="scope">
          <span>{{ scope.row.actName }}</span>
        </template>
      </el-table-column>

      <!-- 权限描述 -->
      <el-table-column
        label="权限描述"
        width="auto">
        <template slot-scope="scope">
          <span>{{ scope.row.describe }}</span>
        </template>
      </el-table-column>

      <el-table-column
        label="操作"
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
  </div>
</template>

<script>
export default {
  data() {
    return {
      permissionData:  []
    }
  }
}
</script>

<style lang="scss" scoped>

</style>
