<template>
  <div class="app">
    <!-- 问题反馈 -->
    <el-dialog title="问题反馈" :visible.sync="problemDialogVisible" width="40%">
      <el-form :model="problemFeedbackDate" :rules="rules" ref="problemForm" label-position="top">
        <!-- 邮箱 -->
        <el-form-item label="邮箱（接收通知）" label-width="120" prop="email">
          <el-input
            maxlength="33"
            autocomplete="off"
            placeholder="请填写自己的邮箱"
            v-model="problemFeedbackDate.email"
            clearable
            show-word-limit></el-input>
        </el-form-item>

        <!-- 问题描述 -->
        <el-form-item label="问题描述" label-width="120" prop="problem">
          <el-input
            type="textarea"
            resize="none"
            rows="5"
            maxlength="120"
            placeholder="请填写问题所在"
            v-model="problemFeedbackDate.problem"
            show-word-limit></el-input>
        </el-form-item>
      </el-form>
      <!-- 操作区 -->
      <div slot="footer" class="dialog-footer">
        <el-button type="warning" icon="el-icon-circle-close" @click="clearForm('problemForm')">清 空</el-button>
        <el-button type="danger" icon="el-icon-check" @click="submitProblemFeedback('problemForm')">确 定</el-button>
      </div>
    </el-dialog>

    <!-- 建议反馈 -->
    <el-dialog title="提交建议" :visible.sync="suggestDialogVisible" width="40%">
      <el-form :model="suggestFeedbackParam" label-width="100" ref="suggestForm">
        <!-- 昵称 -->
        <el-form-item label="是否匿名" prop="name">
          <!-- 选项开关 -->
          <el-switch
            v-model="isName"
            active-color="#13ce66"
            inactive-color="#ff4949">
          </el-switch>

          <el-input
            v-if="isName"
            class="block"
            maxlength="20"
            autocomplete="off"
            placeholder="请填写邮箱（不填写当作匿名）"
            v-model="suggestFeedbackParam.name"
            clearable
            show-word-limit></el-input>
        </el-form-item>

        <!-- 建议内容 -->
        <el-form-item label="建议内容" prop="content">
          <el-input
            type="textarea"
            resize="none"
            rows="8"
            maxlength="255"
            placeholder="请填写建议内容"
            v-model="suggestFeedbackParam.content"
            show-word-limit></el-input>
        </el-form-item>
      </el-form>
      <!-- 操作区 -->
      <div slot="footer" class="dialog-footer">
        <el-button type="warning" icon="el-icon-circle-close" @click="clearForm('suggestForm')">清 空</el-button>
        <el-button type="danger" icon="el-icon-check" @click="submitSuggestFeedback()">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  module.exports = {
    data() {
      var email = (rule, value, callback) => {
        if (value === '') return callback(new Error('请填写邮箱'))
        if (!/^([a-zA-Z0-9]+[-_\.]?)+@[a-zA-Z0-9]+\.[a-z]+$/.test(value)) return callback(new Error('请填写正确的邮箱'))
        callback()
      };
      // 问题内容校验
      var problemContent = (rule, value, callback) => {
        if (value === '') return callback(new Error('请填写问题描述'))
        callback()
      };
      return {
        // 问题反馈和提交建议相关
        problemDialogVisible: false,
        suggestDialogVisible: false,
        isName: false,
        problemFeedbackDate: {
          email: '',
          problem: ''
        },
        suggestFeedbackParam: {
          name: '',
          content: ''
        },
        // 规则
        rules: {
          email: [
            {validator: email, trigger: 'blur'}
          ],
          problem: [
            {validator: problemContent, trigger: 'blur'}
          ]
        },
      }
    },
    methods: {
      // 清空表单
      clearForm(formName) {
        this.$refs[formName].resetFields();
      },
      // 提交问题反馈
      submitProblemFeedback(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.$parent.submitProblemFeedback(this.problemFeedbackDate)
            // 清空
            // this.$refs[formName].resetFields();
          }
        });
      },
      // 提交建议反馈
      submitSuggestFeedback() {
        if (this.suggestFeedbackParam.content === '') {
          this.$message({
            message: '请填写提交的建议内容',
            type: 'warning'
          });
          return
        }
        if (this.isName && this.suggestFeedbackParam.name !== '') {
          if (!/^([a-zA-Z0-9]+[-_\.]?)+@[a-zA-Z0-9]+\.[a-z]+$/.test(this.suggestFeedbackParam.name)) {
            this.$message({
              message: '请填写正确的邮箱',
              type: 'warning'
            });
            return;
          }
        }
        this.$parent.submitSuggestFeedback(this.suggestFeedbackParam)
      },
    }
  }
</script>

<style>

</style>