<template>
  <div class="row">

    <div class="col-12">
      <el-card style="max-width: 480px">
        <template #header>
          <div class="card-header">
            <div>{{ accountStore.user_info.email }}</div>
            <div>{{ accountStore.user_info.active }}</div>
          </div>
        </template>
        {{ accountStore.res_msg }}
        <template #footer>
          {{ accountStore.user_info.roleList }}
        </template>
      </el-card>
    </div>
  </div>
</template>

<script setup lang="ts">
import useAccountStore from "../stores/account-store";

const accountStore = useAccountStore();
</script>

<style scoped></style>
