<template>
    <div class="my">
        <van-nav-bar title="登录"/>

        <van-form @submit="onSubmit">
            <van-cell-group inset>
                <van-field name="手机号" placeholder="手机号" v-model="user.phone" label="手机号"
                           :rules="userFormRules.phone"/>
                <van-field name="密码" placeholder="密码" v-model="user.userPassword" type="password" label="密码"
                           :rules="userFormRules.userPassword"/>
            </van-cell-group>
            <div style="margin: 16px;">
                <van-button round block type="primary" native-type="submit">登录</van-button>
            </div>
        </van-form>

        <div class="no-phone">
            <div class="text">没有账号</div>
            <div class="register" @click="toRegister">立即注册</div>
        </div>

    </div>
</template>

<script setup lang="ts">
import {reactive} from "vue";
import {login} from "../../api/user.ts";
import {showFailToast, showLoadingToast, showSuccessToast} from "vant";
import {useStore} from "vuex";
import {useRouter} from "vue-router";
import {UserLoginRequest, UserLoginResponse} from "../../model/user.ts";

const user = reactive<UserLoginRequest>({
    phone: "",
    userPassword: ""
})
const store = useStore()
const router = useRouter()
//  账号和密码校验规则
const userFormRules = reactive({
    phone: [
        {required: true, message: '请输入手机号'},
    ],
    userPassword: [
        {required: true, message: '请输入密码'},
        {min: 4, message: '密码长度不能小于6位'},
        {max: 16, message: '密码长度不能大于16位'}
    ]
})

//  登录
const onSubmit = async () => {
    console.log(user)
    showLoadingToast({
        message: '登录中...',
        forbidClick: true,
    });
    const res:UserLoginResponse = await login(user);
    // console.log(res);//包含code和data,message
    if (res.code === 200) {
        showSuccessToast("登录成功");
        console.log("登录成功")
        store.commit("setUser", res.data)//将用户信息存入vuex
        // 跳转到主页
        window.location.href = "/"
    } else {
        showFailToast("登录失败");
        console.log("登录失败")
    }
}

const toRegister = () => {
    router.push("/register")
}

</script>

<style scoped lang="less">

.my {

    .no-phone {
        display: flex;
        justify-content: center;
        align-items: center;
        margin-top: 20px;

        .text {
            font-size: 30px;
            color: #999999;
        }

        .register {
            font-size: 30px;
            color: #39be77;
            margin-left: 5px;
        }
    }
}


:root:root {
    --van--nav-backgr: #39be77;
}


</style>
