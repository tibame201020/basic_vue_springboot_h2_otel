<template>
  <div class="row">

    <div class="col-12">
      <el-card style="max-width: 900px">
        {{ accountStore.user_info.email }}
      </el-card>
    </div>
  </div>
</template>

<script setup lang="ts">
import useAccountStore from "../stores/account-store";

const accountStore = useAccountStore();
</script>

<style scoped></style>
