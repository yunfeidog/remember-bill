<template>
    <div>
        <router-view></router-view>
        <van-tabbar v-model="active">
            <van-tabbar-item icon="home-o" to="/">主页</van-tabbar-item>
            <van-tabbar-item icon="chart-trending-o" to="/statistics">统计</van-tabbar-item>
            <van-tabbar-item icon="contact" to="/user">我的</van-tabbar-item>
        </van-tabbar>
    </div>
</template>

<script setup lang="ts">
import {ref} from "vue";

const active = ref(0);
</script>
<style scoped lang="less">


</style>
