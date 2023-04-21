<template>
  <div class="notice">
    <el-drawer
      :title="notice.title + ' - 通知'"
      :visible.sync="notice.visible"
      direction="rtl">
      <span v-if="notice.noticeContentList.length === 0">暂无通知</span>
      <div v-for="(item, index) in notice.noticeContentList" class="notice-list">
        <el-avatar size="medium" :src="item.avatar"></el-avatar>
        <span class="title">{{ item.nickname }} - {{ item.title }}</span>
        <span class="content">多级分类、菜单等的数据库设计（一张表），以及mybatis-plus的多级分类查询（一条SQL语句）</span>
        <span class="time">{{ item.noticeTime }}</span>
        <span class="state">
          <i class="el-icon-remove-outline" style="color: green"></i>{{ item.state === 1 ? '未读' : '已读'}}
        </span>
        <el-divider><i class="el-icon-bell"></i></el-divider>
      </div>
    </el-drawer>
  </div>
</template>

<script>
export default {
  props: {
    'noticeParam': {
      type: Object
    }
  },
  data() {
    return {
      notice: this.noticeParam,
      // noticeContentList: [
      //   {
      //     avatar: 'https://user.xiaoyuan-boke.com/xiaoyuan-avatar.jpg',
      //     nickname: '十二',
      //     title: '点赞改文章',
      //     content: '多级分类、菜单等的数据库设计（一张表），以及mybatis-plus的多级分类查询（一条SQL语句）',
      //     time: '2022-04-26 15:11:19',
      //     state: 1
      //   },
      //   {
      //     avatar: 'https://user.xiaoyuan-boke.com/xiaoyuan-avatar.jpg',
      //     nickname: '十二',
      //     title: '点赞改文章',
      //     content: '多级分类、菜单等的数据库设计（一张表），以及mybatis-plus的多级分类查询（一条SQL语句）',
      //     time: '2022-04-26 15:11:19',
      //     state: 1
      //   }
      // ]
    }
  }
}
</script>

<style lang="scss" scoped>
.notice {
  .notice-list {
    span.title {
      display: inline-block;
      color: #333;
    }
    span.content {
      display: block;
      margin-top: 10px;
      color: #555;
    }
    span.time {
      display: inline-block;
      margin-top: 10px;
      color: #555;
    }
    .title {
      font-size: 15px;
      font-weight: 480;
    }
    .red-point{
      position: relative;
    }
    .red-point::before{
      content: " ";
      border: 3px solid red;/*设置红色*/
      border-radius:3px;/*设置圆角*/
      position: absolute;
      z-index: 1000;
      right: 0%;
      margin-right: -5px;
      margin-top: -5px;
    }
  }
}
</style>
