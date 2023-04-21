<template>
  <div class="permission">
    <el-row>
      <el-col :span="12">
        <module></module>
      </el-col>
      <el-col :span="12">
        <action></action>
      </el-col>
    </el-row>

    <el-row>
      <permission-list></permission-list>
    </el-row>
  </div>
</template>

<script>
import module from "./components/module";
import action from "./components/action";
import permissionList from "./components/permissionList";
export default {
  components: {
    module,
    action,
    permissionList
  },
  data() {
    return {

    }
  },
  methods: {

  }
}
</script>

<style lang="scss" scoped>
.permission {
  padding: 10px;
}
</style>
