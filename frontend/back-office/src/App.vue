<script setup lang="ts">
import useAccountStore from "./stores/account-store"
import Login from "./components/Login.vue"
import UserInfo from "./components/UserInfo.vue"
import DataInfoCard from "./components/DataInfoCard.vue"
import EChartsCard from "./components/EChartsCard.vue"

const accountStore = useAccountStore()

const clearLogin = () => {
  accountStore.$reset()
}

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
            <hr />
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
          <div class="col-12">
            <EChartsCard></EChartsCard>
          </div>
          <div class="col-12">
            <hr>
          </div>
          <div class="col-12">
            <DataInfoCard></DataInfoCard>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
