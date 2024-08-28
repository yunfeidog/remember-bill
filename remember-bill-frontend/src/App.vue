<template>
    <router-view></router-view>

</template>

<script setup>
import {useStore} from "vuex";
import Card from "./components/Card.vue";

const store = useStore()
console.log(store.state.user)
</script>


<style lang="less">

body {
    background-color: #f5f5f5;
}

</style>
