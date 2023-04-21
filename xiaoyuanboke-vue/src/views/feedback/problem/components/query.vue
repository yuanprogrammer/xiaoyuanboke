<template>
  <div class="problem-query">
    <!-- 搜索筛选 -->
    <el-form :inline="true" :model="queryParam" class="user-search" ref="queryParam" size="mini">
      <el-form-item prop="id" class="query-item">
        <el-input size="small" v-model="queryParam.id" clearable placeholder="问题编号"></el-input>
      </el-form-item>
      <el-form-item prop="email" class="query-item">
        <el-input size="small" v-model="queryParam.email" clearable placeholder="邮箱"></el-input>
      </el-form-item>
      <el-form-item prop="problemState" class="query-item">
        <el-select size="small" v-model="queryParam.problemState" clearable placeholder="问题状态">
          <el-option
            v-for="item in problemStateSelect"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item prop="noticeState" class="query-item">
        <el-select size="small" v-model="queryParam.noticeState" clearable placeholder="通知状态">
          <el-option
            v-for="item in noticeStateSelect"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item class="time" prop="startTime">
        <el-date-picker size="small" v-model="queryParam.startTime" type="date" clearable value-format="yyyy-MM-dd" placeholder="开始时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item style="width: 1px;">
        <span>-</span>
      </el-form-item>
      <el-form-item class="time" prop="endTime">
        <el-date-picker size="small" v-model="queryParam.endTime" type="date" clearable value-format="yyyy-MM-dd" placeholder="结束时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item class="search">
        <el-button size="small" type="primary" icon="el-icon-search" @click="search">搜索</el-button>
      </el-form-item>
      <el-form-item class="search">
        <el-button size="small" type="warning" icon="el-icon-refresh-left" @click="refresh('queryParam')">刷新</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  props: {
    'queryParamF': {
      type: Object,
    }
  },
  data() {
    return {
      queryParam: this.queryParamF,
      problemStateSelect: [
        {
          value: '0',
          label: '未解决'
        },
        {
          value: '1',
          label: '处理中'
        },
        {
          value: '2',
          label: '已解决'
        }
      ],
      noticeStateSelect: [
        {
          value: '0',
          label: '未通知'
        },
        {
          value: '1',
          label: '已通知'
        }
      ],
    }
  },
  methods: {
    // 刷新
    refresh(formName) {
      this.$refs[formName].resetFields();
      this.$emit('refreshPage', 1, 10)
      this.search()
    },
    // 搜索
    search() {
      this.$emit('query')
    },
  }
}
</script>

<style lang="scss" scoped>
.problem-query {
  .query-item {
    width: 160px;
  }
  .time {
    width: 160px;
    .el-form-item__content {
      width: 160px;
      .el-date-editor {
        width: 160px;
      }
    }
    input {
      width: 100%;
    }
  }
}
</style>
