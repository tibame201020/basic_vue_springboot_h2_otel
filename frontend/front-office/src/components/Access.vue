<template>
    <div class="container">
        <div class="row">
            <div class="col">
                <el-button type="warning" @click="getdata('/publishApi')">get-open-data</el-button>
            </div>
            <div class="col">
                <el-button type="warning" @click="getdata('/needAuthApi')">get-auth-data</el-button>
            </div>
        </div>
        <div class="row">
            <div class="col">
                <hr />
            </div>
        </div>
        <div class="row">
            <div class="col">
                <el-button type="info" @click="getdata('/publisher/role')">get-publisher-data</el-button>
            </div>
            <div class="col">
                <el-button type="info" @click="getdata('/writer/role')">get-writer-data</el-button>
            </div>
            <div class="col">
                <el-button type="info" @click="getdata('/reader/role')">get-reader-data</el-button>
            </div>
            <div class="col">
                <el-button type="info" @click="getdata('/writerPublisher/role')">get-publisher/writer-data</el-button>
            </div>
            <div class="row">
                <div class="col">
                    <hr />
                </div>
            </div>
            <div class="row">
                <div class="col">
                    <div class="col">
                        <el-button type="danger" @click="getdata('/mockException')">mockException</el-button>
                    </div>
                    <br>
                    <div class="col">
                        <el-button type="danger" @click="getdata('/backoffice/test')">backoffice api test</el-button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
import useAccountStore from "../stores/account-store"

const accountStore = useAccountStore()

const getdata = (api: string) => {
    accountStore.sendRequest(api)
}
</script>

<style scoped></style>
