// 打开问题反馈框
import feedback from "../../api/feedback.js";

export default {
  openProblemDialog() {
    return function () {
      this.$refs.xyFeedback.problemDialogVisible = true
    }
  },
// 打开提交建议框
  openSuggestDialog() {
    return function () {
      this.$refs.xyFeedback.suggestDialogVisible = true
    }
  },
// 提交问题反馈
  submitProblemFeedback() {
    return function (param) {
      // 提交问题反馈
      feedback.submitProblemFeedback(param).then(res => {
        // 清空表单
        this.$refs.xyFeedback.clearForm('problemForm');
        // 关闭反馈框
        this.$refs.xyFeedback.problemDialogVisible = false;
        this.$notify({
          title: '问题提交成功',
          message: '感谢您的反馈',
          type: 'success'
        })
      })
    }
  },
// 提交建议反馈
  submitSuggestFeedback() {
    return function (param) {
      feedback.submitSuggestFeedback(param).then(res => {
        // 清空表单
        this.$refs.xyFeedback.clearForm('suggestForm');
        // 关闭反馈框
        this.$refs.xyFeedback.suggestDialogVisible = false;
        this.$notify({
          title: '建议提交成功',
          message: '感谢您的建议',
          type: 'success'
        })
      })
    }
  },
}