<template>
  <div class="sys-user">
    <user-list></user-list>
  </div>
</template>

<script>
import userList from "./components/userList";
export default {
  components: {
    userList
  },
  data() {
    return {

    }
  }
}
</script>

<style lang="scss" scoped>
.sys-user {
  padding: 20px;
}
</style>
