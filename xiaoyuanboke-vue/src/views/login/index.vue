<template>
  <div class="login-container" :style="{backgroundImage: 'url('+loginBg+')'}">
    <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form" auto-complete="on"
             label-position="left">
      <h3 class="title">小袁博客管理平台</h3>
<!--      <div class="wx" id="wx">-->
        <!--        <wxlogin :appid="appid" :scope="scope" :redirect_uri="encodeURIComponent(redirect_uri)" :state="state" :href="href"></wxlogin>-->
<!--      </div>-->
      <el-form-item prop="username">
        <span class="svg-container">
          <svg-icon icon-class="user" />
        </span>
        <el-input v-model="loginForm.username" name="username" type="text" auto-complete="on" placeholder="username" />
      </el-form-item>
      <el-form-item prop="password">
        <span class="svg-container">
          <svg-icon icon-class="password" />
        </span>
        <el-input
          :type="pwdType"
          v-model="loginForm.password"
          name="password"
          auto-complete="on"
          placeholder="password"
          @keyup.enter.native="handleLogin" />
        <span class="show-pwd" @click="showPwd">
          <svg-icon icon-class="eye" />
        </span>
      </el-form-item>
      <el-form-item style="height: 30px">
        <el-button :loading="loading" type="primary" style="width:100%;" @click.native.prevent="handleLogin">
          Sign in
        </el-button>
      </el-form-item>
      <div class="tools">
        <span>其他方式</span>
        <div class="three" @click="wxLogin11">
          <!--        <div class="three" @click="wxCode = true">-->
          <img class="active" :src="weixinActive" alt=""/>
          <img class="noActive" :src="weixinNoActive" alt=""/>
        </div>
      </div>
      <div class="tips">
        <el-tag type="warning">游客登录</el-tag>
        <span style="margin-right:20px;">username: visitor</span>
        <span> password: visitor</span>
        <el-button style="margin-left: 10px;" size="mini" @click="switchVisitor">点击切换</el-button>
      </div>
      <div class="tips">
        <el-tag type="danger">笔者登录</el-tag>
        <span style="margin-right:20px;">username: author</span>
        <span> password: author</span>
        <el-button style="margin-left: 10px;" size="mini" @click="switchAuthor">点击切换</el-button>
      </div>
    </el-form>
  </div>
</template>

<script>
// import { isvalidUsername } from '@/utils/validate'
import {wxLogin} from "../../api/login";
import weixinActive from '@/assets/img/weixinActive.png'
import weixinNoActive from '@/assets/img/weixinNoActive.png'
import wxlogin from 'vue-wxlogin';
import './wxLogin.js'
import md5 from 'js-md5'

export default {
  components: {
    wxlogin
  },
  name: 'Login',
  data() {
    const validateUsername = (rule, value, callback) => {
      if (value == null || value === '' || value === undefined) {
        callback(new Error('用户名不能为空'))
      }else {
        callback()
      }
    }
    const validatePass = (rule, value, callback) => {
      if (value == null || value === '' || value === undefined) {
        callback(new Error('密码不能为空'))
      }else {
        callback()
      }
    }
    return {
      loginBg: require('@/assets/img/login_bg.jpg'),
      weixinActive: weixinActive,
      weixinNoActive: weixinNoActive,
      loginForm: {
        username: 'root',
        password: 'root'
      },
      loginRules: {
        username: [{required: true, trigger: 'blur', validator: validateUsername}],
        password: [{required: true, trigger: 'blur', validator: validatePass}]
      },
      loading: false,
      pwdType: 'password',
      redirect: undefined
    }
  },
  watch: {
    $route: {
      handler: function (route) {
        this.redirect = route.query && route.query.redirect
      },
      immediate: true
    }
  },
  methods: {
    showPwd() {
      if (this.pwdType === 'password') {
        this.pwdType = ''
      } else {
        this.pwdType = 'password'
      }
    },
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true
          // 加密
          this.loginForm.password = md5(this.loginForm.password)
          // setTimeout(() => {
          //   this.$router.push("/home")
          //   this.loading = false
          // },1000)
          this.$store.dispatch('Login', this.loginForm).then(() => {
            this.loading = false
            // this.$router.push({ path: this.redirect || '/' })
            this.$router.push("/home")
          }).catch(() => {
            this.$router.push("/home")
            this.loading = false
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
      // this.$router.push({ path: this.redirect || '/' })
      // console.log(1)
    },
    wxLogin11() {
      wxLogin().then( res => {
        if (res) {
          window.location.href = res.data
        }
      })
    },
    // 切换游客登录
    switchVisitor() {
      this.loginForm.username = 'visitor'
      this.loginForm.password = 'visitor'
    },
    // 笔者
    switchAuthor() {
      this.loginForm.username = 'author'
      this.loginForm.password = 'author'
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss">
$bg: #2d3a4b;
$light_gray: #eee;

/* reset element-ui css */
.login-container {
  .el-input {
    display: inline-block;
    height: 47px;
    width: 85%;

    input {
      background: transparent;
      border: 0px;
      -webkit-appearance: none;
      border-radius: 0px;
      padding: 12px 5px 12px 15px;
      color: $light_gray;
      height: 47px;

      &:-webkit-autofill {
        -webkit-box-shadow: 0 0 0px 1000px $bg inset !important;
        -webkit-text-fill-color: #fff !important;
      }
    }
  }

  .el-form-item {
    border: 1px solid rgba(255, 255, 255, 0.1);
    background: rgba(0, 0, 0, 0.1);
    border-radius: 5px;
    color: #454545;
  }
}

</style>

<style rel="stylesheet/scss" lang="scss" scoped>
$bg: #2d3a4b;
$dark_gray: #889aa4;
$light_gray: #eee;
.login-container {
  position: fixed;
  height: 100%;
  width: 100%;
  //background: url('../../assets/img/login_bg.jpg') fixed no-repeat;
  background-size: 100% 100%;
  //background-color: $bg;
  .login-form {
    position: absolute;
    left: 0;
    right: 0;
    width: 520px;
    max-width: 100%;
    padding: 35px 35px 15px 35px;
    margin: 120px auto;
  }

  .tools {
    font-size: 13px;
    margin-bottom: 20px;

    span {
      &:first-of-type {
        color: #DCDFE6;
      }
    }

    .three {
      margin-left: 5px;
      display: inline-block;

      .active {
        display: inline;
      }

      .noActive {
        display: none;
      }

      img {
        width: 20px;
        height: 20px;
      }

      &:hover {
        .active {
          display: none;
        }

        .noActive {
          display: inline;
        }
      }
    }
  }

  .tips {
    font-size: 14px;
    color: #fff;
    margin-bottom: 10px;

    span {
      &:first-of-type {
        margin-right: 16px;
      }
    }
  }

  .svg-container {
    padding: 6px 5px 6px 15px;
    color: $dark_gray;
    vertical-align: middle;
    width: 30px;
    display: inline-block;
  }

  .title {
    font-size: 26px;
    color: $light_gray;
    margin: 0 auto 40px auto;
    text-align: center;
    font-weight: bold;
  }

  .show-pwd {
    position: absolute;
    right: 10px;
    top: 7px;
    font-size: 16px;
    color: $dark_gray;
    cursor: pointer;
    user-select: none;
  }

  //.wx {
  //  display: flex;
  //  justify-content: center;
  //  align-items: center;
  //  height: 200px;
  //  //width: 300px;
  //}
}
</style>
