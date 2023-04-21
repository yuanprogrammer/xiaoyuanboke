<template>
  <div class="block" style="text-align:center;margin-top:10px;">
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="pageParam.pageIndex"
      :page-sizes="pageParam.pageSizeSelect"
      :page-size="pageParam.pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="pageParam.totalCount">
    </el-pagination>
  </div>
</template>

<script>
export default {
  props: {
    'page': {
      type: Object,
      default() {
        return {
          pageSizeSelect: [10, 20, 30, 50],
          pageIndex: 1,
          pageSize: 10,
          totalCount: 0
        }
      }
    }
  },
  methods: {
    handleSizeChange(val) {
      this.pageParam.pageSize = val
      this.$emit('handleSizeChange')
    },
    handleCurrentChange(val) {
      this.pageParam.pageIndex = val
      this.$emit('handleCurrentChange')
    }
  },
  data() {
    return {
      pageParam: this.page,
    };
  }
}
</script>
