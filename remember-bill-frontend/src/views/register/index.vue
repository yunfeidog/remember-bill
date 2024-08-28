<template>
    <!--    注册标题-->
    <van-nav-bar title="用户注册" left-text="返回" left-arrow @click-left="onClickLeft"/>
    <!--    用户注册填写框-->
    <van-cell-group class="register-form">
        <van-field class="register-form-area" label="用户名" placeholder="请输入用户名"
                   v-model="user.userName" :rules="userFormRules.userName"/>
        <van-field class="register-form-area" label="手机号" placeholder="请输入手机号" type="email"
                   v-model="user.phone" :rules="userFormRules.phone"/>
        <van-field class="register-form-area" label="密码" placeholder="请输入密码" type="password"
                   v-model="user.userPassword" :rules="userFormRules.userPassword"/>
        <van-field class="register-form-area" label="确认密码" placeholder="请再次输入密码" type="password"
                   v-model="user.checkPassword" :rules="userFormRules.checkPassword"/>

        <!--        注册按钮-->
        <van-button class="register-button" type="primary" round block @click="onSubmit">注册</van-button>
    </van-cell-group>
</template>

<script setup lang="ts">
import {useRouter} from "vue-router";
import {reactive} from "vue";
import {showFailToast, showSuccessToast} from "vant";
import {register} from "../../api/user.ts";
import {BaseResponse} from "../../model/baseResponse";
import {UserRegisterRequest} from "../../model/user";

const user = reactive<UserRegisterRequest>({
    userName: "",
    userPassword: "",
    checkPassword: "",
    phone: "",
})

const router = useRouter();

//todo 注册校验规则 这里存在问题
const userFormRules = reactive({
    userName: [
        {required: true, message: '请输入用户名'},
    ],
    phone: [
        {required: true, message: '请输入手机号'},
    ],
    userPassword: [
        {required: true, message: '请输入密码'},
        {min: 4, message: '密码长度不能小于6位'},
        {max: 16, message: '密码长度不能大于16位'}
    ],
    checkPassword: [
        {required: true, message: '请再次输入密码'},
        {min: 4, message: '密码长度不能小于6位'},
        {max: 16, message: '密码长度不能大于16位'}
    ]
})

const onClickLeft = () => {
    router.push("/login")
}


const onSubmit = async () => {
    if (user.userPassword !== user.checkPassword) {
        showFailToast("两次密码不一致")
        return
    }
    //不为空
    if (user.userName === "" || user.userPassword === "" || user.checkPassword === "" || user.phone === "") {
        showFailToast("请填写完整信息")
        return
    }
    console.log(user)
    const data:BaseResponse = await register(user)
    console.log("注册返回的数据", data)
    if (data.code ===200) {
        showSuccessToast("注册成功")
        await router.push("/login")
    }else{
        showFailToast(data.msg)
    }


}


</script>


<style scoped lang="less">
.register-button {
    //宽度
    width: 90%;
    //居中
    margin: auto;

}

.register-form {
    .register-form-area {
        margin: 20px;
        //宽度
        width: 90%;
    }
}


</style>
