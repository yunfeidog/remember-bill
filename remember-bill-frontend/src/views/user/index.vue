<template>
    <div class="my">
        <div class="title">
            <div class="avatar">
                <img src="/public/vite.svg" alt="">
            </div>
            <div class="info">
                <div class="name">{{ userName }}</div>
                <div class="user-id">id:{{ userInfo.id }}</div>
            </div>
        </div>


        <div class="content">
            <van-cell icon="edit" @click="visibleUserName=true" title="修改用户名" is-link/>
            <van-cell icon="smile-o" title="其他功能" is-link/>
            <van-cell icon="smile-o" title="其他功能" is-link/>
            <van-cell icon="smile-o" title="其他功能" is-link/>
            <van-cell icon="smile-o" title="其他功能" is-link/>
            <van-cell icon="smile-o" title="其他功能" is-link/>
            <van-cell icon="setting-o" title="退出登录" @click="visibleLogout=true" is-link/>
        </div>

        <van-dialog v-model:show="visibleUserName" @confirm="confirmUserName" title="修改用户名" show-cancel-button>
            <van-field rows="2" autosize type="textarea" maxlength="20" placeholder="请输入用户名" show-word-limit
                       v-model="userInfo.userName"/>
        </van-dialog>

        <van-dialog class="rate" v-model:show="visibleLogout" title="确认退出登录？" show-cancel-button
                    @confirm="logout">
        </van-dialog>

    </div>


</template>

<script setup lang="ts">

import {reactive, ref} from "vue";
import {useStore} from "vuex";
import {clear} from "../../utils/storage.ts";
import {useRouter} from "vue-router";
import {updateUserName} from "../../api/user.ts";
import {showToast} from "vant";
import {UserUpdateUserNameRequest} from "../../model/user";
import {BaseResponse} from "../../model/baseResponse";

const router = useRouter();
const userInfo = reactive<UserUpdateUserNameRequest>({
    id: 0,
    userName: ""
});


//获取vuex中的用户信息
const store = useStore();
const user = store.state.user;
console.log(user)
const userName = ref(user.userName);
userInfo.userName = user.userName
userInfo.id = user.id


//修改用户名
const visibleUserName = ref(false);
const confirmUserName = async () => {
    //todo 修改用户名 发送axios请求
    console.log(userInfo)
    const res: BaseResponse = await updateUserName(userInfo);
    console.log(res)
    if (res.code === 200) {
        visibleUserName.value = false;
        console.log("修改成功")
        userName.value = userInfo.userName;
        //修改vuex中的用户名
        //现将vuex中的用户信息取出来
        user.userName = userInfo.userName;
        //再将修改后的用户信息放回去
        store.commit("setUser", user);
    } else {
        console.log("修改失败")
    }


}

//退出登录
const visibleLogout = ref(false);
const logout = () => {
    //todo 退出登录 发送axios请求
    showToast({
        type: "success",
        message: "退出成功",
    })
    clear();
    router.push("/login")
}

</script>


<style scoped lang="less">

.my {
    min-height: 100%;
    background-color: #f5f5f5;
    padding: 12px;
    margin: 10px auto;

    .title {
        display: flex;
        background: linear-gradient(315deg, #7fcea4 0%, #39be77 100%);
        padding: 12px;
        border-radius: 10px;
        margin-bottom: 12px;
        margin-top: 10px;
        //盒子变大一点
        height: 100px;


        .avatar {
            display: flex;
            justify-content: center;
            align-items: center;
            width: 100px;
            height: 100px;
            border-radius: 50%;
            overflow: hidden;

            img {
                width: 100%;
                height: 100%;
                object-fit: cover;
            }

            //距离右侧的距离
            margin-right: 20px;
        }

        .info {
            display: flex;
            flex-direction: column;
            justify-content: center;

            .name {
                font-size: 30px;
                color: #fff;
                font-weight: 600;
            }

            .user-id {
                font-size: 14px;
                color: #fff;
                margin-top: 10px;
            }
        }
    }

    .content {
        .van-cell {
            margin-bottom: 5px;
            border-radius: 10px;
            background-color: #fff;

            .van-cell__title {
                font-size: 16px;
            }
        }


    }

}

</style>
