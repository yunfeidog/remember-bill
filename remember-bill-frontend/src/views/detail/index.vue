<template>
    <van-nav-bar title="账单详情" left-text="返回" @click-left="goBack" left-arrow/>
    <div class="card">
        <div class="type">{{ data.bill.category }}</div>
        <div class="amount ">{{ data.bill.money }}¥</div>
<!--        <div class="amount ">{{ (data.bill.billType === '0' ? '' : '-') + data.bill.money }}¥</div>-->
        <div class="info">
            <div class="time">
                <span>时间:</span><span>{{ data.bill.billDate }}</span>
            </div>
            <div class="remark">
                <span>备注:</span><span>{{ data.bill.remark }}</span>
            </div>
            <div class="shop">
                <span>商家:</span><span>{{ data.bill.shop }}</span>
            </div>
        </div>
        <div class="operation">
            <span @click="deleteBill">
                <van-icon name="delete"/>删除
            </span>
            <span @click="updateBill">
                <van-icon name="edit"/>编辑
            </span>
        </div>
    </div>
</template>

<script setup>

import {useRoute, useRouter} from "vue-router";
import {onMounted, reactive, toRaw} from "vue";
import {deleteBillById, getBillById} from "../../api/bill.ts";
import {showFailToast, showSuccessToast} from "vant";

const router = useRouter()
const route = useRoute()

const goBack = () => {
    window.history.length > 1 ? router.go(-1) : router.push('/')
}

//获取路由参数id
const data = reactive({
    bill: {
        id: 1,
        userId: 1,
        money: 100,
        billDate: '',
        billType: 0,
        category: '',
        remark: '',
        shop: ''
    }

})
const id = toRaw(route.query.id)

const getById = async () => {
    const res = await getBillById(Number(id))
    if (res.code === 200) { //code,data,msg
        data.bill = res.data
        console.log(data.bill)
        //date:格式化
        data.bill.billDate = data.bill.billDate.substring(0, 10)
    } else {
        console.log(res.msg)
    }

}

const deleteBill = async () => {
    const res = await deleteBillById(Number(id))
    if (res.code === 200) {//code,data,msg
        showSuccessToast(res.msg)
        goBack()
    } else {
        showFailToast(res.msg)
    }
}

const updateBill = () => {
    router.push({
        path: '/update',
        query: {id: id}
    })
}
onMounted(() => {
    getById()
})

</script>

<style scoped lang="less">

.card {
    background-color: #fff;
    padding: 20px;
    margin: 20px;
    border-radius: 30px;
    display: flex;
    flex-direction: column;
    align-items: center;


    .type {
        font-size: 20px;
        font-weight: 600;
        margin-bottom: 20px;
        color: #666;
    }

    .amount {
        font-size: 50px;
        font-weight: 600;
        margin-bottom: 20px;
    }

    .info {
        width: 100%;
        font-size: 30px;
        text-align: left;
        margin-bottom: 20px;
        font-weight: 300;
        margin-left: 50px;

        .time {
            margin-bottom: 10px;

            span:nth-of-type(1) {
                color: #666;
                margin-right: 50px;
            }
        }

        .remark {
            margin-bottom: 10px;

            span:nth-of-type(1) {
                color: #666;
                margin-right: 50px;
            }
        }

        .shop {
            margin-bottom: 10px;

            span:nth-of-type(1) {
                color: #666;
                margin-right: 50px;
            }
        }
    }

    .operation {
        width: 100%;
        display: flex;
        justify-content: space-between;
        align-items: center;
        font-size: 40px;

        span {
            display: flex;
            align-items: center;
            cursor: pointer;
            //内边距
            padding: 10px 100px;

            .van-icon :nth-of-type(1) {
                margin-right: 10px;

            }
        }

        span:nth-of-type(1) {
            border: 1px solid #666;
            border-radius: 20px;
            color: red;
        }

        span:nth-of-type(2) {
            border: 1px solid #666;
            border-radius: 20px;
        }
    }
}
</style>
