<template>
  <div>
    <el-table
      border
      :data="sysUserList"
      size="small"
      style="width: 100%">

      <!-- 序号 -->
      <el-table-column
        label="序号"
        width="100">
        <template slot-scope="scope">
          <span>{{ scope.$index + 1}}</span>
        </template>
      </el-table-column>

      <!-- 角色 -->
      <el-table-column
        label="角色"
        width="150">
        <template slot-scope="scope">
          <el-tag size="small" type="warning" v-for="item in scope.row.roles" :key="item.roleName">{{ item.roleName }}</el-tag>
        </template>
      </el-table-column>

      <!-- 昵称 -->
      <el-table-column
        label="昵称"
        width="150">
        <template slot-scope="scope">
          <span>{{ scope.row.nickname }}</span>
        </template>
      </el-table-column>

      <!-- 编号 -->
      <el-table-column
        label="编号"
        width="200">
        <template slot-scope="scope">
          <span>{{ scope.row.sysUserCode }}</span>
        </template>
      </el-table-column>

      <!-- 头像 -->
      <el-table-column
        label="头像"
        width="100">
        <template slot-scope="scope">
          <el-avatar :src="scope.row.avatar"></el-avatar>
        </template>
      </el-table-column>

      <!-- 状态 -->
      <el-table-column
        label="状态"
        width="100">
        <template slot-scope="scope">
          <span>{{ scope.row.state }}</span>
        </template>
      </el-table-column>

      <el-table-column
        label="操作"
        width="auto">
        <template slot-scope="scope">
          <el-button @click="chatUser(scope.$index)"
                     type="text"
                     style="color: #E6A23C;"
                     size="small">发起沟通</el-button>
          <el-button type="text"
                     style="color:#F56C6C;"
                     size="small">删除</el-button>
          <el-button type="text"
                     style="color:#F56C6C;"
                     size="small">加入黑名单</el-button>
        </template>
      </el-table-column>
    </el-table>

    <Pagination ref="pagination" @handleSizeChange="handleSizeChange" @handleCurrentChange="handleCurrentChange"></Pagination>

<!--    <el-dialog-->
<!--      :visible.sync="chatVisible"-->
<!--      width="28%"-->
<!--      @close="closeChat">-->
<!--      &lt;!&ndash; 发布和取消按钮 &ndash;&gt;-->
<!--      <div slot="title" class="chat-title">-->
<!--        <span>与</span>-->
<!--        <el-tag size="mini" type="warning">{{ title }}</el-tag>-->
<!--        <span>发起沟通</span>-->
<!--      </div>-->

<!--      <lemon-imui-->
<!--        width="380px"-->
<!--        simple-->
<!--        :user="user"-->
<!--        ref="IMUI"-->
<!--        @pull-messages="handlePullMessages"-->
<!--        @message-click="handleMessageClick"-->
<!--        @send="handleSend">-->
<!--        <template #cover>-->
<!--          <div class="cover">-->
<!--            <span><b>Lemon</b> IMUI</span>-->
<!--          </div>-->
<!--        </template>-->
<!--      </lemon-imui>-->
<!--    </el-dialog>-->
  </div>
</template>

<script>
import Pagination from "../../../../../components/Pagination";
import sysUserApi from "@/api/system/sysUser";
import {getToken} from "@/utils/auth";
import {mapGetters} from "vuex";
const getTime = () => new Date().getTime();
const generateRandId = () => Math.random().toString(36).substr(-8);
const chatKey = 'chat>>'
const chatSign = '>>'

export default {
  components: {
    Pagination
  },
  computed: {
    ...mapGetters([
      'sidebar',
      'avatar',
      'sysUserCode',
      'name',
      'token'
    ])
  },
  data() {
    return  {
      sysUserList: [],
      chatVisible: false,
      title: '',
      user: {
        id: '',
        displayName: '',
        avatar: '',
      },
      chatSocket: '',
      chatIMUI: ''
    }
  },
  methods: {
    initDateList() {
      let param = {
        pageIndex: this.$refs.pagination.pageParam.pageIndex,
        pageSize: this.$refs.pagination.pageParam.pageSize,
      }

      sysUserApi.getSysUserList(param).then( res => {
        if (res.success) {
          this.sysUserList = res.data.list
          this.$refs.pagination.pageParam.totalCount = res.data.total
        }
      })
    },
    handleSizeChange() {
      this.initDateList()
    },
    handleCurrentChange() {
      this.initDateList()
    },
    chatUser(index) {
      this.$message('功能升級中!')
      // if (this.sysUserCode === this.sysUserList[index].sysUserCode) {
      //   this.$message('不能和自己沟通!')
      // }else {
      //   // 初始化
      //   this.title = this.sysUserList[index].nickname
      //   this.chatVisible = true
      //   this.initChatInfo()
      //   this.initChatSocket()
      //   this.initIMUI(index)
      // }
    },
    // initChatInfo() {
    //   this.user.id = this.sysUserCode
    //   this.user.displayName = this.name
    //   this.user.avatar = this.avatar
    // },
    // initChatSocket() {
    //   // 建立连接
    //   if (typeof WebSocket === "undefined") {
    //     this.$message('当前浏览器不支持WebSocket')
    //   }else {
    //     this.chatSocket = new WebSocket(`${this.$chatSocketUrl}/${getToken()}/${this.sysUserCode}`)
    //     this.chatSocket.onopen = () => {}
    //   }
    // },
    // initIMUI(index) {
    //   this.$nextTick(() => {
    //     this.chatIMUI = this.$refs.IMUI
    //     this.chatIMUI.initContacts([
    //       {
    //         id: this.sysUserList[index].sysUserCode,
    //         displayName: this.sysUserList[index].nickname,
    //         avatar: this.sysUserList[index].avatar,
    //         type: "single",
    //         index: "T",
    //         unread: 1,
    //         lastSendTime: null,
    //         lastContent: null,
    //       },
    //     ]);
    //     this.chatIMUI.changeContact(this.sysUserList[index].sysUserCode);
    //     this.chatIMUI.initEmoji([{ label: '默认表情', children: [ { name: '1f62c', title: '微笑', src: 'https://twemoji.maxcdn.com/2/72x72/1f62c.png' } ] }])
    //
    //     // 开启监听消息
    //     this.startChatMessageListen()
    //     // 拉取未读消息
    //     this.pullUnreadMessage(index)
    //   })
    // },
    // pullUnreadMessage(index) {
    //   if (this.chatSocket.readyState === WebSocket.OPEN) {
    //     let message = {
    //       msgType: 7,
    //       toContactId: this.sysUserCode,
    //       fromUser: {
    //         id: this.sysUserList[index].sysUserCode
    //       }
    //     }
    //     // 发送拉取指令
    //     this.chatSocket.send(JSON.stringify(message))
    //   }else {
    //     setTimeout(() => { this.pullUnreadMessage(index)}, 100)
    //   }
    // },
    // startChatMessageListen() {
    //   // 监听消息
    //   this.chatSocket.onmessage = msg => {
    //     let data = JSON.parse(msg.data)
    //     console.log(data)
    //     for (let i = 0 ; i < data.length; i++) {
    //       this.storageChatMessage(data[i].toContactId, data[i])
    //       data[i].toContactId = data[i].fromUser.id
    //       this.chatIMUI.appendMessage(data[i], true)
    //     }
    //   }
    // },
    // closeChat() {
    //   this.chatSocket.close()
    //   this.chatSocket = ''
    //   this.chatIMUI.clearMessages()
    //   this.chatIMUI = ''
    // },
    // handleMessageClick(e, key, message) {
    //   // /(https?:\/\/)?(([0-9a-z.]+\.[a-z]+)|(([0-9]{1,3}\.){3}[0-9]{1,3}))(:[0-9]+)?(\/[0-9a-z%/.\-_]*)?(\?[0-9a-z=&%_\-]*)?(\#[0-9a-z=&%_\-]*)?/ig
    //   if (message.content.indexOf('https') !== -1) {
    //     window.open(message.content, '_blank')
    //   }
    //   // if (key === "status" && message.status === "failed") {
    //   //   this.chatIMUI.updateMessage({
    //   //     id: message.id,
    //   //     toContactId: message.toContactId,
    //   //     status: "going",
    //   //     content: "重新发送消息...",
    //   //   });
    //   //   setTimeout(() => {
    //   //     this.chatIMUI.updateMessage({
    //   //       id: message.id,
    //   //       toContactId: message.toContactId,
    //   //       status: "failed",
    //   //       content: "还是发送失败",
    //   //     });
    //   //   }, 2000);
    //   // }
    // },
    // handleSend(message, next, file) {
    //   message['msgType'] = 5
    //   this.chatSocket.send(JSON.stringify(message))
    //   message['status'] = 'succeed'
    //   this.storageChatMessage(message.toContactId, message)
    //   next()
    //   // setTimeout(() => {
    //   //   next({
    //   //     status: "failed",
    //   //   });
    //   // }, 1000);
    // },
    // handlePullMessages(contact, next) {
    //   const { currentContactId } = this.$refs.IMUI;
    //   // const otherUser = {
    //   //   id: contact.id,
    //   //   avatar: contact.avatar,
    //   //   displayName: contact.displayName,
    //   // };
    //   // const message = (content, fromUser = this.user) => {
    //   //   return {
    //   //     id: generateRandId(),
    //   //     status: "succeed",
    //   //     type: "text",
    //   //     sendTime: getTime(),
    //   //     content,
    //   //     toContactId: currentContactId,
    //   //     fromUser,
    //   //   };
    //   // };
    //
    //   // const messages = [
    //     // message("再bb，信不信我盗你号？", otherUser),
    //     // message("来"),
    //     // message("别后悔", otherUser),
    //     // message("验证码发一下", otherUser),
    //   // ];
    //   next(this.pullLocalMessage(currentContactId), true);
    // },
    // storageChatMessage(toContactId, message) {
    //   let localMessageList = localStorage.getItem(chatKey + this.sysUserCode + chatSign + toContactId)
    //   localMessageList = localMessageList === null ? [] : JSON.parse(localMessageList)
    //   // 插入本地记录
    //   localMessageList.push(message)
    //   localStorage.setItem(chatKey + this.sysUserCode + chatSign + toContactId, JSON.stringify(localMessageList))
    // },
    // pullLocalMessage(toContactId) {
    //   let messages = localStorage.getItem(chatKey + this.sysUserCode + chatSign + toContactId)
    //   return messages === null ? [] : JSON.parse(messages)
    // }
  },
  mounted() {
    this.initDateList()
  },
  destroyed() {

  }
}
</script>

<style scoped>
.cover {
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 24px;
  color: #ddd;
}
.chat-title {
}
</style>
