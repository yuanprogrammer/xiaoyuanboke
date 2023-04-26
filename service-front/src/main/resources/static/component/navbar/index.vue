<template>
  <div class="app" v-cloak>
    <!-- 导航栏 -->
    <header id="header" data-spy="affix" data-offset-top="70">
      <nav class="navbar-inverse" id="daohang">
        <div class="daohang">
          <div class="navbar-header clearfix">
            <button type="button" class="zd" id="zd">
              <span class="glyphicon glyphicon-align-justify"></span>
            </button>
            <a href="/index" class="navbar-brand"><img src="https://file.xiaoyuan-boke.com/project/logo.png"
                                                       alt="LOGO"></a>
          </div>
          <div class="collapse navbar-collapse" id="daohangtiao">
            <ul class="nav navbar-nav">
              <li><a href="/home/home">文章首页</a></li>
              <li><a href="/story/story">免费开源</a></li>
              <li><a href="/archive/archive">档案</a></li>
              <li><a href="/timeline/timeLine">创作历程</a></li>
              <li><a href="/classify/classify">专栏</a></li>
              <li><a href="/message/message">留言</a></li>
              <li class="gy1"><a href="/aboutme/aboutMe">关于我</a>
                <ul class="gy2 clearfix">
                  <li class="pull-left clearfix" style="width: 130px;">
                    <p><a href="#" class="a1">公司介绍</a></p>
                    <ul class="ul1">
                      <li><a href="#">公司介绍</a></li>
                      <li><a href="#">服务条款</a></li>
                      <li><a href="#">法律声明</a></li>
                      <li><a href="#">可接受服务</a></li>
                      <li><a href="#">免责声明</a></li>
                    </ul>
                  </li>
                  <li class="lianxi pull-left clearfix" style="width: 478px;">
                    <p><a href="#" class="a1">联系客服</a></p>
                    <ul class="col-lg-12 col-md-12">
                      <li><a href="#" class="aa1">客户服务热线</a></li>
                      <li><a href="#" class="aa2">4006-285-729</a></li>
                      <li><a href="#" class="aa1">QQ客户中心</a></li>
                      <li><a href="#" class="aa2">800088546</a></li>
                      <li><a href="#" class="aa1">公司地址</a></li>
                      <li><a href="#" class="aa2">苏州市工业园区加成大厦F4-D2</a></li>
                    </ul>
                  </li>
                </ul>
              </li>
              <li><a href="/friendlink/friendLink">友链</a></li>
            </ul>

            <div v-if="JSON.stringify(userInfo) === '{}'">
              <ul class="zcdl nav navbar-nav pull-right">
                <li><a href="/login">登录</a></li>
                <li><a href="/login">注册</a></li>
              </ul>
            </div>
            <div class="userInfo" v-if="JSON.stringify(userInfo) !== '{}'">
              <ul class="nav navbar-nav pull-right">
                <el-dropdown trigger="click" @command="handleUserCommand">
                  <img :src="userInfo.avatar" style="width: 50px;height: 50px;border-radius: 100%;" alt=""/>
                  <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item command="personalInfo" icon="el-icon-user">个人信息</el-dropdown-item>
                    <el-dropdown-item command="findPassword" icon="el-icon-question">忘记密码</el-dropdown-item>
                    <el-dropdown-item command="modifyPassword" icon="el-icon-key">修改密码</el-dropdown-item>
                    <el-dropdown-item command="modifyEmail" icon="el-icon-message">
                      {{ !userInfo.email ? '邮箱激活' : '修改邮箱'}}
                    </el-dropdown-item>
                    <el-dropdown-item command="modifyMobile" icon="el-icon-mobile-phone">
                      {{ !userInfo.mobileNumber ? '号码激活' : '修改号码'}}
                    </el-dropdown-item>
                    <el-dropdown-item command="appleWriter" icon="el-icon-edit-outline">申请创作者</el-dropdown-item>
                    <el-dropdown-item command="personalManager" icon="el-icon-setting">个人后台</el-dropdown-item>
                    <el-dropdown-item command="logout" icon="el-icon-back">退出登录</el-dropdown-item>
                  </el-dropdown-menu>
                </el-dropdown>
              </ul>
            </div>
          </div>
        </div>
      </nav>
    </header>

    <!-- Dialog弹框 -->
    <!-- 密码修改 -->
    <el-dialog title="修改密码" :visible.sync="editPasswordFormVisible" width="40%">
      <el-form label-position="top" label-width="100px" :model="editPasswordForm" :rules="editPasswordRules"
               ref="editPasswordForm">
        <!-- 原密码 -->
        <el-form-item label="原密码" prop="password">
          <el-input prefix-icon="el-icon-lock" placeholder="请填写原密码" show-password
                    v-model="editPasswordForm.password" class="input-with-select" maxlength="20"
                    clearable>
          </el-input>
        </el-form-item>

        <!-- 新密码 -->
        <el-form-item label="新密码" prop="password1">
          <el-input prefix-icon="el-icon-lock"
                    v-model="editPasswordForm.password1"
                    placeholder="请填写新密码（长度在 6 - 20 个字符之间）"
                    maxlength="20"
                    show-password clearable></el-input>
        </el-form-item>

        <!-- 新密码 -->
        <el-form-item label="确认新密码" prop="passwordConfirm">
          <el-input prefix-icon="el-icon-lock"
                    v-model="editPasswordForm.passwordConfirm"
                    placeholder="确认新密码（长度在 6 - 20 个字符之间）"
                    maxlength="20"
                    @keyup.native.enter="submitEditPassword('editPasswordForm')"
                    show-password clearable></el-input>
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button type="warning" icon="el-icon-circle-close" @click="clearForm('editPasswordForm')">清 空</el-button>
        <el-button type="danger" icon="el-icon-check" @click="submitEditPassword('editPasswordForm')">提 交</el-button>
      </div>
    </el-dialog>

    <!-- 个人信息 -->
    <el-dialog @close="clearForm('personalForm')" title="个人信息" :visible.sync="personalFormVisible" width="40%">
      <el-form :model="personalForm" label-width="120px" :rules="personalFormRules" ref="personalForm">

        <el-form-item label="昵称" prop="nickname">
          <el-input style="width: 90%;" v-model="personalForm.nickname"
                    placeholder="请填写昵称" maxlength="30"
                    clearable show-word-limit></el-input>
        </el-form-item>

        <el-form-item label="用户名" prop="username">
          <el-input style="width: 90%;" v-model="personalForm.username"
                    placeholder="请填写用户名（确认之后不能再更改，请慎重考虑）" maxlength="25"
                    clearable show-word-limit
                    :disabled="userInfo.username !== null"></el-input>
        </el-form-item>

        <el-form-item label="邮箱" prop="email">
          <el-input placeholder="请尽快完成邮箱激活" style="width: 90%;" v-model="personalForm.email" disabled></el-input>
        </el-form-item>

        <el-form-item label="手机号码" prop="mobileNumber">
          <el-input placeholder="请尽快完成号码激活" style="width: 90%;" v-model="personalForm.mobileNumber" disabled></el-input>
        </el-form-item>

        <div v-if="userInfo.email === null && userInfo.mobileNumber === null">
          <span style="font-size: 12px;margin-left: 50px;color: #888888">
            <span style="color: orange">* </span>温馨提示：请尽快激活邮箱和号码其中之一，便于找回密码等操作
          </span>
        </div>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button icon="el-icon-check" type="danger" @click="submitEditPersonal('personalForm')">提 交</el-button>
      </div>
    </el-dialog>

    <!-- 找回密码 -->
    <el-dialog @close="clearForm('findPasswordForm')" title="找回密码"
               :visible.sync="findPasswordFormVisible" width="35%">
      <el-form @close="clearForm('findPasswordForm')" :model="findPasswordForm" label-width="70px" :rules="findPasswordFormRules" ref="findPasswordForm">

        <el-form-item label="邮箱号码">
          <el-input prefix-icon="el-icon-message" style="width: 90%;"
                    :value="findPasswordForm.account" disabled></el-input>
        </el-form-item>

        <!-- 新密码 -->
        <el-form-item label="新密码" prop="password">
          <el-input prefix-icon="el-icon-lock"
                    v-model="findPasswordForm.password"
                    placeholder="请填写新密码（长度在 6 - 20 个字符之间）"
                    maxlength="20"
                    style="width: 90%;"
                    show-password clearable></el-input>
        </el-form-item>

        <el-form-item label="验证码" prop="code">
          <el-input style="width: 90%;" v-model="findPasswordForm.code"
                    placeholder="请填写验证码" maxlength="6">
            <el-button slot="append" @click="sendFindCode()" :disabled="findCodeDisabled">{{ findCodeMsg }}</el-button>
          </el-input>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button icon="el-icon-check" type="danger" @click="submitFindPassword('findPasswordForm')">提 交</el-button>
      </div>
    </el-dialog>

    <!-- 修改邮箱 -->
    <el-dialog @close="clearForm('emailForm')" :title="userInfo.email === null ? '激活邮箱' : '修改邮箱'"
               :visible.sync="emailFormVisible" width="30%">
      <el-form :model="emailForm" label-width="70px" :rules="emailFormRules" ref="emailForm">

        <el-form-item v-if="userInfo.email !== null" label="原邮箱">
          <el-input prefix-icon="el-icon-message" style="width: 90%;"
                    v-model="userInfo.email" disabled></el-input>
        </el-form-item>

        <el-form-item label="新邮箱" prop="email">
          <el-input prefix-icon="el-icon-message" style="width: 90%;" v-model="emailForm.email"
                    placeholder="请填写邮箱" maxlength="30"
                    clearable></el-input>
        </el-form-item>

        <el-form-item label="验证码" prop="code">
          <el-input style="width: 90%;" v-model="emailForm.code"
                    placeholder="请填写验证码" maxlength="6">
            <el-button slot="append" @click="sendEmailCode()" :disabled="emailCodeDisabled">{{ emailCodeMsg }}</el-button>
          </el-input>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button icon="el-icon-check" type="danger" @click="submitEditEmail('emailForm')">提 交</el-button>
      </div>
    </el-dialog>

    <!-- 修改号码 -->
    <el-dialog @close="clearForm('mobileForm')" :title="userInfo.mobileNumber === null ? '激活号码' : '修改号码'"
               :visible.sync="mobileFormVisible" width="30%">
      <el-form :model="mobileForm" label-width="70px" :rules="mobileFormRules" ref="mobileForm">

        <el-form-item v-if="userInfo.mobileNumber !== null" label="原号码">
          <el-input prefix-icon="el-icon-message" style="width: 90%;"
                    :value="userInfo.mobileNumber" disabled></el-input>
        </el-form-item>

        <el-form-item label="新号码" prop="mobileNumber">
          <el-input prefix-icon="el-icon-mobile-phone" style="width: 90%;" v-model="mobileForm.mobileNumber"
                    placeholder="请填写手机号码" maxlength="11"
                    clearable></el-input>
        </el-form-item>

        <el-form-item label="验证码" prop="code">
          <el-input style="width: 90%;" v-model="mobileForm.code"
                    placeholder="请填写验证码" maxlength="6">
            <el-button slot="append" @click="sendMobileCode()" :disabled="mobileCodeDisabled">{{ mobileCodeMsg }}</el-button>
          </el-input>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button icon="el-icon-check" type="danger" @click="submitEditMobileNumber('mobileForm')">提 交</el-button>
      </div>
    </el-dialog>
    <!-- Dialog弹框 -->
  </div>
</template>

<script>
  module.exports = {
  // export default {
    data() {
      var nickname = (rule, value, callback) => {
        if (!value) return callback(new Error('请填写昵称'));
        if (value.length > 30) return callback(new Error('昵称过长，最多30个字符'));
        callback()
      };
      var username = (rule, value, callback) => {
        if (!value) return callback();
        if (!(/(?=.*[a-zA-Z])[a-zA-Z0-9]{5,20}/.test(value))) return callback(new Error("请输入 5-20 个字符，只能包含数字、大小写字母 且 至少包含一个字母"));
        callback()
      };
      var password = (rule, value, callback) => {
        if (!value) return callback(new Error('请填写密码'));
        else if (value.length < 6 || value.length > 20) return callback(new Error('密码长度在 6 - 20 个字符之间'));
        callback()
      };
      var password1 = (rule, value, callback) => {
        if (!value) return callback(new Error('请填写新密码'));
        else if (value.length < 6 || value.length > 20) return callback(new Error('密码长度在 6 - 20 个字符之间'));
        else if (this.editPasswordForm.passwordConfirm !== '') {
          this.$refs.editPasswordForm.validateField('editPasswordForm');
        }
        callback()
      };
      var passwordConfirm = (rule, value, callback) => {
        if (!value) return callback(new Error('请填写确认密码'));
        else if (value.length < 6 || value.length > 20) return callback(new Error('密码长度在 6 - 20 个字符之间'));
        else if (value !== this.editPasswordForm.password1) return callback(new Error('两次密码不一致'));
        callback()
      };
      var email = (rule, value, callback) => {
        if (!value) return callback(new Error('请填写邮箱'));
        if (!/^([a-zA-Z0-9]+[-_\.]?)+@[a-zA-Z0-9]+\.[a-z]+$/.test(value)) return callback(new Error('请填写正确的邮箱'));
        callback()
      };
      var mobileNumber = (rule, value, callback) => {
        if (!value) return callback(new Error('请填写手机号码'));
        if (!/^[1][3,4,5,7,8][0-9]{9}$/g.test(value)) return callback(new Error('请填写正确的手机号码'));
        callback()
      };
      var code = (rule, value, callback) => {
        if (value === '' || value.length !== 6) return callback(new Error('请填写6位有效验证码'));
        if (!/^\d+$/.test(value)) return callback(new Error('请填写6位数字'));
        callback()
      };
      return {
        // 个人信息
        personalFormVisible: false,
        personalForm: {},
        personalFormRules: {
          nickname: [
            { validator: nickname, trigger: 'blur'}
          ],
          username: [
            { validator: username, trigger: 'blur'}
          ]
        },
        // 找回密码
        findPasswordFormVisible: false,
        findPasswordForm: {
          account: '',
          password: '',
          code: ''
        },
        findCodeDisabled: false,
        findCodeMsg: '点击获取验证码',
        findCount: 60,
        findTime: 0,
        findPasswordFormRules: {
          password: [
            { validator: password, trigger: 'blur'}
          ],
          code: [
            { validator: code, trigger: 'blur'}
          ]
        },
        // 修改密码
        editPasswordFormVisible: false,
        editPasswordForm: {
          password: '',
          password1: '',
          passwordConfirm: ''
        },
        editPasswordRules: {
          password: [
            {validator: password, trigger: 'blur'}
          ],
          password1: [
            {validator: password1, trigger: 'blur'}
          ],
          passwordConfirm: [
            {validator: passwordConfirm, trigger: 'blur'}
          ]
        },
        // 邮箱
        emailFormVisible: false,
        emailForm: {
          email: '',
          code: ''
        },
        emailCodeDisabled: false,
        emailCodeMsg: '点击获取验证码',
        emailCount: 60,
        emailTimer: 0,
        emailFormRules: {
          email: [
            { validator: email, trigger: 'blur' }
          ],
          code: [
            { validator: code, trigger: 'blur'}
          ]
        },
        // 手机号码
        mobileFormVisible: false,
        mobileForm: {
          mobileNumber: '',
          code: '',
        },
        mobileCodeDisabled: false,
        mobileCodeMsg: '点击获取验证码',
        mobileCount: 60,
        mobileTimer: 0,
        mobileFormRules: {
          mobileNumber: [
            { validator: mobileNumber, trigger: 'blur' }
          ],
          code: [
            { validator: code, trigger: 'blur'}
          ]
        },
      }
    },
    props: {
      userInfo: {
        type: Object,
        default: () => ({})
      }
    },
    watch: {
      userInfo(to, from) {
        this.userInfo = to
      }
    },
    methods: {
      // 清空表单
      clearForm(formName) {
        this.$refs[formName].resetFields();
      },
      // 指令
      handleUserCommand(command) {
        switch (command) {
          case 'personalInfo':
            this.personalForm = JSON.parse(JSON.stringify(this.userInfo));
            this.personalFormVisible = true;
            break;
          case 'findPassword':
            if (this.userInfo.email !== null) {
              this.findPasswordForm.account = this.userInfo.email;
            }else if (this.userInfo.mobileNumber !== null) {
              this.findPasswordForm.account = this.userInfo.mobileNumber;
            }else {
              this.$message({
                message: '请先绑定邮箱或者手机号码',
                type: 'error'
              });
              break;
            }
            this.findPasswordFormVisible = true;
            break;
          case 'modifyPassword':
            this.editPasswordFormVisible = true;
            break;
          case 'modifyEmail':
            this.emailFormVisible = true;
            break;
          case 'modifyMobile':
            this.mobileFormVisible = true;
            break;
          case 'appleWriter':
            this.$message({
              message: '申请创作者功能正在完善中...',
              type: 'warning'
            });
            break;
          case 'personalManager':
            this.$message({
              message: '个人后台正在完善中。。。',
              type: 'warning'
            });
            break;
          case 'logout':
            this.$parent.logout();
            break;
          default:
            break
        }
      },
      // 提交修改个人信息
      submitEditPersonal(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            if (this.userInfo.nickname === this.personalForm.nickname && this.userInfo.username === this.personalForm.username) {
              this.$message('未做任何修改');
              this.personalFormVisible = false;
              return
            }
            var param = {};
            param['nickname'] = this.personalForm.nickname.replace(/\\s*/,'');
            if (this.userInfo.username === null) {
              param['username'] = this.personalForm.username.replace(/\\s*/,'');
            }
            // 提交
            this.$parent.submitEditPersonal(param)
          }
        });
      },
      // 回传覆盖
      coverUserInfo() {
        this.userInfo.nickname = this.personalForm.nickname;
        this.userInfo.username = this.personalForm.username;
      },
      // 发送找回密码验证码
      sendFindCode() {
        this.findCodeDisabled = true;
        this.findTime = setInterval(() => {
          this.findCodeMsg = --this.findCount + 's后重新获取';
          if (this.findCount < 0) {
            this.findCodeMsg = '点击获取验证码';
            this.findCount = 60; // 重置
            this.findCodeDisabled = false;
            clearInterval(this.findTime);
          }
        }, 1000);

        if (this.userInfo.email !== null) {
          this.$parent.sendEmailCode(this.findPasswordForm.account);
        }else if (this.userInfo.mobileNumber !== null) {
          this.$parent.sendSmsCode(this.findPasswordForm.account);
        }
      },
      // 提交找回密码请求
      submitFindPassword(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.$parent.submitFindPassword(this.findPasswordForm);
          }
        });
      },
      // 提交修改密码
      submitEditPassword(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.$parent.submitEditPassword(this.editPasswordForm)
          }
        });
      },
      // 发送邮箱验证码
      sendEmailCode() {
        if (/^([a-zA-Z0-9]+[-_\.]?)+@[a-zA-Z0-9]+\.[a-z]+$/.test(this.emailForm.email)) {
          this.emailCodeDisabled = true;
          this.emailTimer = setInterval(() => {
            this.emailCodeMsg = --this.emailCount + 's后重新获取';
            if (this.emailCount < 0) {
              this.emailCodeMsg = '点击获取验证码';
              this.emailCount = 60; // 重置
              this.emailCodeDisabled = false;
              clearInterval(this.emailTimer);
            }
          }, 1000);
          // 发送验证码
          this.$parent.sendEmailCode(this.emailForm.email);
        }else {
          this.$message({
            message: '请填写正确的邮箱',
            type: 'warning'
          })
        }
      },
      // 提交修改邮箱
      submitEditEmail(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.$parent.submitEditEmail(this.emailForm);
          }
        });
      },
      // 覆盖原邮箱
      coverEmail() {
        this.userInfo.email = this.emailForm.email;
      },
      // 发送号码短信验证码
      sendMobileCode() {
        this.$refs.mobileForm.validateField('mobileNumber', (result) => {
          if (!result) {
            if (this.userInfo.mobileNumber === this.mobileForm.mobileNumber) {
              this.$message('未做任何修改');
              this.mobileFormVisible = false;
            }else {
              this.mobileCodeDisabled = true;
              this.mobileTimer = setInterval(() => {
                this.mobileCodeMsg = --this.mobileCount + 's后重新获取';
                if (this.mobileCount < 0) {
                  this.mobileCodeMsg = '点击获取验证码';
                  this.mobileCount = 60; // 重置
                  this.mobileCodeDisabled = false;
                  clearInterval(this.mobileTimer);
                }
              }, 1000);
              // 发送短信
              this.$parent.sendSmsCode(this.mobileForm.mobileNumber);
            }
          }
        })
      },
      // 提交修改或激活手机号码
      submitEditMobileNumber(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            // 提交
            if (this.userInfo.mobileNumber === this.mobileForm.mobileNumber) {
              this.$message('未做任何修改');
              this.mobileFormVisible = false;
            }else {
              this.$parent.submitEditMobileNumber(this.mobileForm);
            }
          }
        })
      },
      // 覆盖原号码
      coverMobileNumber() {
        this.userInfo.mobileNumber = this.mobileForm.mobileNumber;
      }
    },
    created() {

    }
  }
</script>

<style>
</style>
