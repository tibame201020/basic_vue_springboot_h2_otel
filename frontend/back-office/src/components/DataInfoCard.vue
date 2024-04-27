<template>
  <div class="container">
    <div class="row">
      <div class="col">
        <el-button @click="accountStore.fetchAllUser">fetch all user</el-button>
      </div>
    </div>
    <div class="row">
      <div class="col">
        <el-table :data="accountStore.users" style="width: 100%" v-show="accountStore.users.length">
          <el-table-column prop="email" label="Email" width="180" />
          <el-table-column prop="active" label="Active" width="180" />
          <el-table-column prop="roleList" label="RoleList" />
        </el-table>
        <el-table :data="accountStore.records" style="width: 100%" v-show="accountStore.records.length">
          <el-table-column prop="recordId" label="recordId" />
          <el-table-column prop="requestPath" label="requestPath" />
          <el-table-column prop="userEmail" label="userEmail" />
          <el-table-column prop="recordDateTime" label="recordDateTime" />
          <el-table-column prop="isError" label="isError" />
          <el-table-column prop="responseStatus" label="responseStatus" />
        </el-table>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import useAccountStore from "../stores/account-store";

const accountStore = useAccountStore();
</script>

<style scoped></style>
