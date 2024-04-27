<script setup lang="ts">
import useAccountStore from "./stores/account-store";
import Login from "./components/Login.vue";
import UserInfo from "./components/UserInfo.vue";
import Access from "./components/Access.vue";

const accountStore = useAccountStore();

const clearLogin = () => {
  accountStore.$reset();
};

</script>

<template>
  <div class="container-fluid">
    <div class="row">
      <div class="col">
        <hr />
      </div>
    </div>
    <div class="row">
      <div class="col" v-show="!accountStore.isLogin">
        <Login></Login>
      </div>
      <div class="col" v-show="accountStore.isLogin">
        <div class="row">
          <div class="col">
            <el-button type="primary" @click="clearLogin">logout</el-button>
          </div>
        </div>
        <div class="row">
          <div class="col">
            <hr>
          </div>
        </div>
        <div class="row">
          <div class="col">
            <UserInfo></UserInfo>
          </div>
        </div>
        <div class="row">
          <div class="col">
            <hr>
          </div>
        </div>

        <div class="row">
          <div class="col">
            <Access></Access>
          </div>
        </div>

      </div>
    </div>
  </div>
</template>

<style scoped></style>
