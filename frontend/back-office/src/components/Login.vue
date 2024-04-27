<template>
    back-office
    <el-form label-position="left" label-width="auto" :model="accountStore" ref="formRef" :rules="rules" status-icon>
        <div class="container">
            <div class="row">
                <div class="col-12"><br></div>
            </div>
            <div class="row">
                <div class="col-12">
                    <el-form-item label="email" prop="email">
                        <el-input v-model="accountStore.email" />
                    </el-form-item>
                    <el-form-item label="password" prop="password">
                        <el-input v-model="accountStore.password" />
                    </el-form-item>
                </div>
            </div>
            <div class="row">
                <div class="col">
                    <hr>
                </div>
            </div>
            <div class="row">
                <div class="col">
                    <el-button type="primary" @click="login(formRef)">login</el-button>
                </div>
            </div>
        </div>
    </el-form>

</template>

<script setup lang="ts">
import useAccountStore from "../stores/account-store"
import { ref, reactive } from 'vue'
import type { FormInstance, FormRules } from 'element-plus'

const inputRegex = /^[0-9a-zA-Z]+$/

interface RuleForm {
    email: string
    password: string
}
const formRef = ref<FormInstance>()

const rules = reactive<FormRules<RuleForm>>({
    email: [
        {
            required: true,
            message: 'Please input email',
            trigger: 'blur',
        }
    ],
    password: [
        {
            required: true,
            message: 'passowrd can not be empty',
            trigger: 'blur'
        },
        {
            pattern: inputRegex,
            message: 'only accept 0-9, A-Z, a-z words',
            trigger: 'change'
        }
    ]
})

const accountStore = useAccountStore()

const login = async (formEl: FormInstance | undefined) => {
    if (!formEl) return
    await formEl.validate((valid) => {
        if (valid) {
            accountStore.login();
        }
    })
}

</script>

<style scoped></style>