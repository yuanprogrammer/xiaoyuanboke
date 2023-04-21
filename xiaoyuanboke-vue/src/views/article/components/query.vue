<template>
  <div class="article-query">
    <!-- 搜索筛选 -->
    <el-form :inline="true" :model="queryParam" class="user-search" ref="queryParam" size="mini">
      <el-form-item prop="id" class="query-item">
        <el-input size="small" v-model="queryParam.id" clearable placeholder="文章编号"></el-input>
      </el-form-item>
      <el-form-item prop="authorName" class="query-item">
        <el-input size="small" v-model="queryParam.authorName" clearable placeholder="作者"></el-input>
      </el-form-item>
      <el-form-item prop="title" class="query-item">
        <el-input size="small" v-model="queryParam.title" clearable placeholder="标题"></el-input>
      </el-form-item>
      <el-form-item prop="tag" class="query-item">
        <el-input size="small" v-model="queryParam.tag" clearable placeholder="标签"></el-input>
      </el-form-item>
      <el-form-item prop="category" class="query-item">
        <el-input size="small" v-model="queryParam.category" clearable placeholder="分类"></el-input>
      </el-form-item>
      <el-form-item prop="sort" class="query-item">
        <el-select size="small" v-model="queryParam.sort" clearable placeholder="排序方式">
          <el-option
            v-for="item in sortSelect"
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
      <el-form-item>
        <el-button :disabled="!isAuth(['ARTICLE:PUBLISH'])" type="danger" size="small" icon="el-icon-position" @click="publishArticle" round>发布文章</el-button>
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
      sortSelect: [
        {
          value: 'good_count',
          label: '点赞'
        },
        {
          value: 'collect_count',
          label: '收藏'
        },
        {
          value: 'view_count',
          label: '浏览'
        },
        {
          value: 'comment_count',
          label: '评论'
        },
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
    publishArticle() {
      this.$emit('publish')
    }
  }
}
</script>

<style lang="scss" scoped>
.article-query {
  .query-item {
    width: 140px;
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
