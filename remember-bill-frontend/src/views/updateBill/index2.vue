<template>
    <van-nav-bar title="修改账单" left-text="返回" @click-left="goBack" left-arrow right-text="确认"
                 @click-right="submitBill"/>
    <div class="add-bill">
        <div class="type">
            <span @click="changeType('expense')"
                  :class="{ expense: true, active: bill.billType === 'expense' }">支出
            </span>
            <span @click="changeType('income')"
                  :class="{ income: true, active: bill.billType === 'income' }">收入
            </span>
        </div>
        <div class="comment">金额</div>
        <div class="money">
            <van-field v-model="bill.money" placeholder="请输入金额" type="number" class="amount"/>
            <van-icon name="photograph" class="photo"/>
        </div>
        <div class="comment" style="margin-bottom: 10px;">类型</div>
        <div class="bill-type">
            <div class="type-body" v-if="bill.billType === 'expense'">
                <div class="type-item" v-for="item in data.expense" :key="item.id" @click="choseType(item)"
                     :class=" { active: bill.category === item.name }">
                    <span class="item">{{ item.name }}</span>
                </div>
            </div>
            <div class="type-body" v-else>
                <div class="type-item" v-for="item in data.income" :key="item.id" @click="choseType(item)"
                     :class=" { active: bill.category === item.name }">
                    <span class="item">{{ item.name }}</span>
                </div>
            </div>
        </div>
        <div class="comment" style="margin-bottom: 10px;">日期</div>
        <van-field v-model="bill.date" placeholder="请选择日期"/>


        <div class="comment" style="margin-bottom: 10px;">商家</div>
        <van-field v-model="bill.shop" placeholder="请输入商家名称"/>
        <div class="comment" style="margin-bottom: 10px;">备注</div>
        <van-field v-model="bill.remark" placeholder="请输入备注信息"/>
    </div>

</template>

<script setup>

import {useRouter, useRoute} from "vue-router";
import {onMounted, reactive, ref, toRaw} from "vue";
import {addBill, getBillById} from "../../api/bill.ts";
import {showFailToast, showSuccessToast} from "vant";

const router = useRouter()

const bill = reactive({
    userId: 0,
    money: '',
    billType: 'expense',
    category: '餐饮',
    date: '',
    shop: '',
    remark: '',
})
const active = ref(0)
//收入和支出类型各9个
const data = reactive({
    income: [
        {id: 1, name: '工资'},
        {id: 2, name: '奖金'},
        {id: 3, name: '兼职'},
        {id: 4, name: '理财'},
        {id: 5, name: '投资'},
        {id: 6, name: '红包'},
        {id: 7, name: '转账'},
        {id: 8, name: '退款'},
        {id: 9, name: '其他'},
    ],
    expense: [
        {id: 1, name: '餐饮'},
        {id: 2, name: '购物'},
        {id: 3, name: '交通'},
        {id: 4, name: '住房'},
        {id: 5, name: '娱乐'},
        {id: 6, name: '医疗'},
        {id: 7, name: '通讯'},
        {id: 8, name: '学习'},
        {id: 9, name: '其他'},
    ],
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

const goBack = () => {
    window.history.length > 1 ? router.go(-1) : router.push('/')
}

//切换收入和支出
const changeType = (type) => {
    bill.billType = type
    bill.category = data[type][0].name
}

//选择类型
const choseType = (item) => {
    bill.category = item.name
}


//提交账单
const submitBill = async () => {
    //todo 提交账单 0收入 1支出
    console.log(bill)
    const newBill = {
        billAmount: bill.money,
        billCategory: bill.category,
        billDate: bill.date,
        billRemark: bill.remark,
        billShopkeeper: bill.shop,
        billType: bill.billType === 'expense' ? 1 : 0,
    }
    console.log(newBill)
    const res = await addBill(newBill)
    console.log(res)
    if (res.code === 200) {
        showSuccessToast('添加成功')
        await router.push('/')
    } else {
        showFailToast('添加失败')
    }
}
const route = useRoute()
const id = toRaw(route.query.id)


const getById = async () => {
    const res = await getBillById(id)
    if (res.code === 200) {
        data.bill = res.data
        console.log(data.bill)
        //date:格式化
        data.bill.billDate = data.bill.billDate.substring(0, 10)
    } else {
        console.log(res.msg)
    }
    console.log(data.bill)
    //赋值
    bill.money = data.bill.money
    bill.billType = data.bill.billType === 0 ? 'income' : 'expense'
    bill.category = data.bill.category
    bill.date = data.bill.billDate
    bill.shop = data.bill.shop
    bill.remark = data.bill.remark
}

onMounted(() => {
    getById()
})


</script>

<style scoped lang="less">
@import "src/assets/theme.less";

.add-bill {
    background-color: #fff;

    .comment {
        font-size: 20px;
        //深灰色字体
        color: #666;
        padding: 10px 50px 0 50px;
    }


    .money {
        display: flex;
        justify-content: space-between;

        .amount {
            padding-left: 50px;
            font-size: 60px;
            color: @primary;
        }

        .photo {
            width: 100px;
            height: 100px;
            margin-right: 50px;
            line-height: 100px;
            text-align: center;
            font-size: 50px;
        }
    }

    .type {
        display: flex;
        justify-content: space-between;
        padding: 0 100px;

        span {
            border-radius: 20px;
            border: 1px solid #ccc;
            text-align: center;
            font-size: 30px;
            color: #666;
            padding: 10px 0;
            width: 200px;
            margin-bottom: 20px;
            margin-top: 20px;
            height: 50px;
            line-height: 50px;

            &.active {
                color: #fff;
                background-color: @primary;
            }
        }
    }

    .bill-type {
        .type-body {
            display: flex;
            flex-wrap: wrap;
            justify-content: space-between;
            padding: 0 100px;


            .type-item {
                border-radius: 20px;
                border: 1px solid #ccc;
                text-align: center;
                font-size: 30px;
                color: #666;
                padding: 10px 0;
                width: 160px;
                height: 50px;
                margin-bottom: 20px;
                line-height: 50px;

                &.active {
                    color: #fff;
                    background-color: @primary;
                }

            }
        }
    }


}

</style>
