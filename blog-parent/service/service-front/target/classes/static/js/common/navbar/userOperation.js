import user from "../../api/user.js";
import commonService from "../../api/commonService.js";

export default {

  // 获取用户数据
  getUserInfo() {
    return function () {
      user.getUserInfo().then(res => {
        let data = res.data;
        if (Object.keys(data).length !== 0) {
          this.userInfo = data.userInfo
        }
      })
    }
  },

  // 退出登录
  logout() {
    return function () {
      this.$confirm('是否退出当前用户？', '提示', {
        confirmButtonText: '确定',
        type: 'warning'
      }).then(() => {
        user.logout().then(_ => {
          window.location.reload();
        });
      });
    }
  },

  // 修改个人信息
  submitEditPersonal() {
    return function (param) {
      user.editNickname(param).then( _ => {
        if (_) {
          // 回调指令, 刷新数据
          this.$refs.xyNavbar.coverUserInfo();
          // 关闭框
          this.$refs.xyNavbar.personalFormVisible = false;
          // 提示
          this.$message({
            message: '修改成功',
            type: 'success'
          });
        }
      })
    }
  },

  // 提交找回密码
  submitFindPassword() {
    return function (param) {
      user.findPassword(param).then(_ => {
        if (_) {
          // 关闭弹框
          this.$refs.xyNavbar.findPasswordFormVisible = false;
          // 提示
          // 提示
          this.$message({
            message: '修改成功',
            type: 'success'
          });
        }
      })
    }
  },

  // 提交修改密码
  submitEditPassword() {
    return function (param) {
      user.editPassword(param).then(_ => {
        if (_) {
          // 清空
          this.$refs.xyNavbar.clearForm('editPasswordForm');
          // 关闭弹出框
          this.$refs.xyNavbar.editPasswordFormVisible = false;
          this.$message({
            message: '密码修改成功',
            type: 'success'
          })
        }
      })
    }
  },

  // 发送验证码
  sendEmailCode() {
    return function (email) {
      commonService.getEmailCodePermission(email).then(_ => {
        if (_) {
          commonService.sendEmailCode(email).then( _ => {
            if (_) {
              // 通知邮箱发送
              this.$notify({
                title: '邮箱验证码已发送',
                message: '验证码有效时长5分钟, 失效请重新发送',
                type: 'success',
                duration: 10000
              })
            }
          })
        }
      })
    }
  },

  // 提交修改邮箱
  submitEditEmail() {
    return function (param) {
      user.editEmail(param).then(_ => {
        if (_) {
          // 回调指令, 覆盖邮箱
          this.$refs.xyNavbar.coverEmail();
          // 关闭
          this.$refs.xyNavbar.emailFormVisible = false;
          // 提示
          this.$notify({
            title: '操作',
            message: '邮箱修改成功',
            type: 'success'
          })
        }
      })
    }
  },

  // 发送短信验证码
  sendSmsCode() {
    return function (phone) {
      commonService.getSmsCodePermission(phone).then(_ => {
        if (_) {
          commonService.sendSmsCode(phone).then(_ => {
            if (_) {
              // 提示
              this.$notify({
                title: '短信验证码已发送',
                message: '验证码有效时长10分钟, 失效请重新发送',
                type: 'success',
                duration: 10000
              })
            }
          })
        }
      })
    }
  },

  // 提交号码修改或激活
  submitEditMobileNumber() {
    return function (param) {
      user.editMobileNumber(param).then(_ => {
        if (_) {
          // 回调指令
          this.$refs.xyNavbar.coverMobileNumber();
          // 关闭
          this.$refs.xyNavbar.mobileFormVisible = false;
          // 提示
          this.$notify({
            title: '操作',
            message: '号码修改成功',
            type: 'success'
          })
        }
      })
    }
  }
}