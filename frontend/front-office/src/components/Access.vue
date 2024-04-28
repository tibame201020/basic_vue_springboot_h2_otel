<template>
    <div class="container">
        <div class="row">
            <div class="col">
                <el-button type="warning" @click="getdata('/frontoffice/publishApi')">get-open-data</el-button>
            </div>
            <div class="col">
                <el-button type="warning" @click="getdata('/frontoffice/needAuthApi')">get-auth-data</el-button>
            </div>
        </div>
        <div class="row">
            <div class="col">
                <hr />
            </div>
        </div>
        <div class="row">
            <div class="col">
                <el-button type="info" @click="getdata('/frontoffice/publisher/role')">get-publisher-data</el-button>
            </div>
            <div class="col">
                <el-button type="info" @click="getdata('/frontoffice/writer/role')">get-writer-data</el-button>
            </div>
            <div class="col">
                <el-button type="info" @click="getdata('/frontoffice/reader/role')">get-reader-data</el-button>
            </div>
            <div class="col">
                <el-button type="info" @click="getdata('/frontoffice/writerPublisher/role')">get-publisher/writer-data</el-button>
            </div>
            <div class="row">
                <div class="col">
                    <hr />
                </div>
            </div>
            <div class="row">
                <div class="col">
                    <div class="col">
                        <el-button type="danger" @click="getdata('/frontoffice/mockException')">mockException</el-button>
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
