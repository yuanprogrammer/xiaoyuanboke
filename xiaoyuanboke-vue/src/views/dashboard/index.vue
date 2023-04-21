<template>
  <div class="dashboard-container">
    <github-corner style="position: absolute; top: 0px; border: 0; right: 0;"/>
<!--    <div class="dashboard-text">-->
<!--      名称:{{ name }}</div>-->
<!--    <div class="dashboard-text">-->
<!--      角色:-->
<!--      <el-tag-->
<!--        style="margin-right: 20px"-->
<!--        v-for="role in roles"-->
<!--        :key="role.label"-->
<!--        size="medium"-->
<!--        :hit="true"-->
<!--        effect="dark">-->
<!--        {{ role }}-->
<!--      </el-tag>-->
<!--    </div>-->
    <el-row :gutter="8">
      <el-col :xs="{span: 24}" :sm="{span: 24}" :md="{span: 24}" :lg="{span: 12}" :xl="{span: 12}" style="padding-right:8px;margin-bottom:30px;">
      </el-col>
      <el-col :xs="{span: 24}" :sm="{span: 12}" :md="{span: 12}" :lg="{span: 6}" :xl="{span: 6}" style="margin-bottom:30px;">
      </el-col>
      <el-col :xs="{span: 24}" :sm="{span: 12}" :md="{span: 12}" :lg="{span: 6}" :xl="{span: 6}" style="margin-bottom:30px;margin-top: 20px;">
        <box-card />
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import BoxCard from './components/BoxCard'
import GithubCorner from '@/components/GithubCorner'

export default {
  components: {
    BoxCard,
    GithubCorner
  },
  name: 'home',
  computed: {
    ...mapGetters([
      'name',
      'roles'
    ])
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.dashboard {
  &-container {
    margin: 30px;
  }
  &-text {
    font-size: 22px;
    line-height: 46px;
  }
  .personal {
    .box-card-header {
      position: relative;
      height: 220px;
      img {
        width: 100%;
        height: 100%;
        transition: all 0.2s linear;
        &:hover {
          transform: scale(1.1, 1.1);
          filter: contrast(130%);
        }
      }
    }
  }
}
</style>
