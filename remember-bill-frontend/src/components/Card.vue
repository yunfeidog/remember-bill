<template>
    <van-cell-group inset class="card" :class="{ 'income-card': income > expense, 'expense-card': income <= expense }">
        <div class="card-top">
            <div class="date">{{ date }}</div>
            <div class="money">
                <span class="income"><b>收</b>{{ income }}</span>
                <span class="expend"><b>支</b>{{ expense }}</span>
            </div>
        </div>
        <van-cell v-for="item in bills" :key="item.id"
                  :title="item.category"
                  :value="`${item.money<0  ? '' : '+'}${item.money}`"
                  :label="'备注：'+(item.remark===''?'无':item.remark)"
                  @click="goDetail(item)"
        />
    </van-cell-group>
</template>

<script setup lang="ts">

import {useRouter} from "vue-router";
import {Bill} from "../model/bill";
import {computed} from "vue";

const data = [
    {
        id: 1,
        userId: 1,
        money: 100,
        category: '其他',
        billDate: '2023-02-02',
        remark: '测试'
    },
    {
        id: 2,
        userId: 1,
        money: 100,
        category: '其他',
        billDate: '2023-02-02',
        remark: '测试'
    },
    {
        id: 3,
        userId: 1,
        money: -700,
        category: '其他',
        billDate: '2023-02-02',
        remark: ''
    },
]

const props = defineProps({
    date: { //月份
        type: String,
        default: '2023-02-02'
    },
    bills: {
        type: Array as () => Bill[],
        default: []
    },
})
const income = computed(() => {
    const total = props.bills.filter(item => item.money > 0).reduce((prev, curr) => prev + curr.money, 0);
    return parseFloat(total.toFixed(2));
})

const expense = computed(() => {
    const total = Math.abs(props.bills.filter(item => item.money < 0).reduce((prev, curr) => prev + curr.money, 0));
    return parseFloat(total.toFixed(2));
})

const router = useRouter()

const goDetail = (item) => {
    console.log(item)
    router.push({
        path: '/detail',
        query: {
            id: item.id
        }
    })
}

</script>

<style scoped lang="less">

.card {
    border-radius: 20px;
    margin: 15px;
    background-color: #fff;
    box-shadow: 0 0 0.2rem 0.1rem rgba(0, 0, 0, 0.1);

    .card-top {
        background-color: #f9f9f9;
        border-radius: 20px 20px 0 0;
        padding: 10px 15px;
        display: flex;
        justify-content: space-between;
        align-items: center;
        font-size: 30px;

        .date {
            color: #666;
        }

        .money {
            display: flex;
            justify-content: space-between;
            margin-bottom: 15px;
            margin-top: 15px;

            .income {
                color: #666;
                margin-right: 50px;
                //居中
                display: flex;
                align-items: center;

                b {
                    color: #666;
                    font-weight: normal;
                    background-color: #f5f5f5;
                    padding: 3px;
                    margin-right: 2px;
                }

            }

            .expend {
                color: #666;
                //居中
                display: flex;
                align-items: center;

                b {
                    color: #666;
                    font-weight: normal;
                    background-color: #f5f5f5;
                    padding: 3px;
                    margin-right: 2px;
                }
            }

        }
    }
}

.income-card {
    background-color: #d4edda;

    .card-top {
        background-color: #c3e6cb;
    }
}

.expense-card {
    background-color: #fff3cd;

    .card-top {
        background-color: #ffeeba;
    }
}

</style>
