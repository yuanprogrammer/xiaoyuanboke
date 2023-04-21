<template>
  <div>
    <el-menu class="navbar" mode="horizontal">
      <hamburger :toggle-click="toggleSideBar" :is-active="sidebar.opened" class="hamburger-container"/>
      <breadcrumb />

      <el-dropdown @command="openNotice" class="notice-container" trigger="click">
<!--        通知<i class="el-icon-caret-bottom el-icon&#45;&#45;right"></i>-->
        <el-badge :is-dot="censusNoticeCount() !== 0" class="item el-dropdown-link">
          <i class="el-icon-bell" style="font-size: 20px;"></i>
        </el-badge>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item class="clearfix" command="comment">
            评论
            <el-badge class="mark" :hidden="noticeCount.comment === 0" :value="noticeCount.comment" :max="99"/>
          </el-dropdown-item>
          <el-dropdown-item class="clearfix" command="reply">
            回复
            <el-badge class="mark" :hidden="noticeCount.reply === 0" :value="noticeCount.reply" :max="99"/>
          </el-dropdown-item>
          <el-dropdown-item class="clearfix" command="like">
            点赞
            <el-badge class="mark" :hidden="noticeCount.like === 0" :value="noticeCount.like" :max="99"/>
          </el-dropdown-item>
          <el-dropdown-item class="clearfix" command="collect">
            收藏
            <el-badge class="mark" :hidden="noticeCount.collect === 0" :value="noticeCount.collect" :max="99"/>
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>

      <el-dropdown class="avatar-container" trigger="click">
        <div class="avatar-wrapper">
          <img :src="avatar+'?imageView2/1/w/80/h/80'" class="user-avatar" alt=""/>
          <i class="el-icon-caret-bottom"/>
        </div>
        <el-dropdown-menu slot="dropdown" class="user-dropdown">
          <router-link class="inlineBlock" to="/">
            <el-dropdown-item>
              首页
            </el-dropdown-item>
          </router-link>
          <el-dropdown-item divided>
            <span style="display:block;" @click="logout">退出登录</span>
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </el-menu>

<!--    <el-drawer-->
<!--      title="通知"-->
<!--      :visible.sync="notice.visible"-->
<!--      direction="rtl">-->
<!--      <span>我来啦!</span>-->
<!--    </el-drawer>-->
    <notice-drawer :notice-param="notice"></notice-drawer>
  </div>
</template>

<script>
import {getToken} from "@/utils/auth";
import { mapGetters } from 'vuex'
import Breadcrumb from '@/components/Breadcrumb'
import Hamburger from '@/components/Hamburger'
import NoticeDrawer from "../../../components/NoticeDrawer";

export default {
  components: {
    Breadcrumb,
    Hamburger,
    NoticeDrawer
  },
  computed: {
    ...mapGetters([
      'sidebar',
      'avatar',
      'sysUserCode',
      'token'
    ])
  },
  data() {
    return {
      notice: {
        visible: false,
        title: '',
        noticeContentList: []
      },
      noticeCount: {
        comment: 0,
        reply: 0,
        like: 0,
        collect: 0
      }
    }
  },
  created() {
    this.connectWebsocket()
    this.showNoticeCount()
  },
  methods: {
    toggleSideBar() {
      this.$store.dispatch('ToggleSideBar')
    },
    logout() {
      this.$store.dispatch('LogOut').then(() => {
        location.reload() // 为了重新实例化vue-router对象 避免bug
      })
    },
    openNotice(command) {
      let key = ''
      switch (command) {
        case 'comment':
          key = 'comment'
          this.notice.title = '评论'
          break
        case 'reply':
          key = 'reply'
          this.notice.title = '回复'
          break
        case 'like':
          key = 'like'
          this.notice.title = '点赞'
          break
        case 'collect':
          key = 'collect'
          this.notice.title = '收藏'
          break
      }
      this.getNoticeList(key)
      this.notice.visible = true
    },
    pushNewNotice(data) {
      let key = '';
      switch (data.noticeType) {
        case 1:
          key = 'commentNoticeList'
          this.noticeCount.comment = this.noticeCount.comment + 1
          break
        case 2:
          key = 'replyNoticeList'
          this.noticeCount.reply = this.noticeCount.reply + 1
          break;
        case 3:
          key = 'likeNoticeList'
          this.noticeCount.like = this.noticeCount.like + 1
          break;
        case 4:
          key = 'collectNoticeList'
          this.noticeCount.collect = this.noticeCount.collect + 1
          break;
      }
      // 存储到本地
      let list = localStorage.getItem(this.sysUserCode + ':' + key)
      let newData = list == null ? [] : JSON.parse(list)
      newData.push(data)
      localStorage.setItem(this.sysUserCode + ':' + key, JSON.stringify(newData))
    },
    showNoticeCount() {
      let keys = ['comment', 'reply', 'like', 'collect']
      for (let i = 0; i < keys.length; i++) {
        let count = 0;
        let list = localStorage.getItem(this.sysUserCode + ':' + keys[i] + 'NoticeList')
        if (list != null) {
          count = JSON.parse(list).length
        }else {
          // TODO, 向后台拿数据
        }
        this.noticeCount[keys[i]] = count
      }
    },
    censusNoticeCount() {
      return this.noticeCount.comment + this.noticeCount.reply + this.noticeCount.like + this.noticeCount.collect
    },
    getNoticeList(key) {
      if (localStorage.getItem(this.sysUserCode + ':' + key + 'NoticeList') != null) {
        let list = JSON.parse(localStorage.getItem(this.sysUserCode + ':' + key + 'NoticeList'))
        this.notice.noticeContentList = list.length === 0 ? [] : list
      }
    },
    connectWebsocket() {
      let websocket;
      if (typeof WebSocket === "undefined") {
        console.log("当前浏览器不支持WebSocket")
      } else {
        // 打开一个websocket
        websocket = new WebSocket(`${this.$noticeSocketUrl}/${getToken()}/${this.sysUserCode}`)
        // 建立连接
        websocket.onopen = () => {
          // 发送数据
          // websocket.send("发送数据")
          // console.log("websocket发送数据中")
        }
        // 客户端接收服务端返回的数据
        websocket.onmessage = evt => {
          let data = JSON.parse(evt.data)
          this.$notify({
            title: '您有新的通知, 请及时查看',
            message: data.title,
            type: 'success',
            position: 'bottom-right'
          })
          // 添加到本地
          this.pushNewNotice(data)
        }
        // 发生错误时
        websocket.onerror = evt => {
          console.log("websocket错误：", evt)
        }
        // 关闭连接
        websocket.onclose = evt => {
          console.log("websocket关闭：", evt)
        }
      }
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.navbar {
  height: 50px;
  line-height: 50px;
  border-radius: 0px !important;
  background-image: linear-gradient(to top, #accbee 0%, #e7f0fd 100%);
  .hamburger-container {
    line-height: 58px;
    height: 50px;
    float: left;
    padding: 0 10px;
  }
  .screenfull {
    position: absolute;
    right: 90px;
    top: 16px;
    color: red;
  }
  .notice-container {
    height: 50px;
    display: inline-block;
    position: absolute;
    right: 100px;
    cursor: pointer;
  }
  .avatar-container {
    height: 50px;
    display: inline-block;
    position: absolute;
    right: 35px;
    .avatar-wrapper {
      cursor: pointer;
      margin-top: 5px;
      position: relative;
      line-height: initial;
      .user-avatar {
        width: 40px;
        height: 40px;
        border-radius: 10px;
      }
      .el-icon-caret-bottom {
        position: absolute;
        right: -20px;
        top: 25px;
        font-size: 12px;
      }
    }
  }
}
</style>

