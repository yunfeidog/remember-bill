<template>
    <div class="home">
        <div class="header">
            <div class="select">
                <div class="tag">
                    <span class="title" @click="showTag">
                        {{ query.category === '' ? '全部类型' : query.category }}
                    </span>
                </div>
                <div class="date" @click="showSelectMonth=true">
                    {{ query.date === '' ? '请选择日期' : query.date }}
                </div>
            </div>
            <div class="money">
                <span class="expense">总支出 ¥{{ totalExpense }}</span>
                <span class="income">总收入 ¥{{ totalIncome }}</span>
                <span class="balance">结余 ¥{{ balance }}</span>
            </div>
        </div>
        <div class="content">
            <Card v-for="item in data.list" :bills="item.bills" :date="item.date" :key="item"
            ></Card>
        </div>
        <div class="add" @click="toAddBill">
            <van-icon name="records"/>
        </div>

        <!-- 底部弹出选择标签 -->
        <van-popup
            position="bottom" round
            v-model:show="visibleTag"
            :style="{ height: '50%' }"
            closeable
        >
            <div class="type">
                <span @click="changeType('expense')"
                      :class="{ expense: true, active: bill.billType === 'expense' }">支出
                </span>
                <span @click="changeType('income')"
                      :class="{ income: true, active: bill.billType === 'income' }">收入
                </span>
            </div>
            <van-divider/>
            <div class="bill-type">
                <div class="type-body" v-if="bill.billType === 'expense'">
                    <div class="type-item" v-for="item in tagList.expense" :key="item.id" @click="choseType(item)"
                         :class=" { active: bill.category === item.name }">
                        <span class="item">{{ item.name }}</span>
                    </div>
                </div>
                <div class="type-body" v-else>
                    <div class="type-item" v-for="item in tagList.income" :key="item.id" @click="choseType(item)"
                         :class=" { active: bill.category === item.name }">
                        <span class="item">{{ item.name }}</span>
                    </div>
                </div>
            </div>
            <van-divider/>
            <div class="confirm" @click="onConfirmTag">确认</div>


        </van-popup>

        <!-- 底部弹出选择日期 -->
        <van-popup
            v-model:show="showSelectMonth"
            position="bottom"
            round
            :style="{ height: '50%' }">
            <van-date-picker
                v-model="currentDate"
                title="请选择日期"
                :min-date="month.minDate"
                :max-date="month.maxDate"
                :formatter="formatter"
                :columns-type="columnsType"
                @confirm="onConfirmDateSelect"
                @cancel="showSelectMonth = false"
            />
        </van-popup>

    </div>
</template>

<script setup lang="ts">

import {computed, onMounted, reactive, ref} from "vue";
import Card from "../../components/Card.vue";
import {useRouter} from "vue-router";
import {getBillByCategoryOrDate} from "../../api/bill.ts";
import {getTodayMonth} from "./data.js";
import {BillCard, getBillByCategoryOrDateResponse} from "../../model/bill";
import {showFailToast} from "vant";

const showSelectMonth = ref(false)
const router = useRouter()
const query = reactive({
    category: "",
    date: getTodayMonth()
})


const data = reactive({
    tag: "全部类型",
    balance: 0,
    list: [] as BillCard[]
})

//收入支出为计算属性
const totalExpense = computed(() => {
    let total = 0
    data.list.forEach(item => {
        item.bills.forEach(bill => {
            if (bill.money < 0) {
                total += bill.money
            }
        })
    })
    //保留两位小数
    return parseFloat(total.toFixed(2))
    // return total
})

const totalIncome = computed(() => {
    let total = 0
    data.list.forEach(item => {
        item.bills.forEach(bill => {
            if (bill.money > 0) {
                total += bill.money
            }
        })
    })
    return parseFloat(total.toFixed(2))
    // return total
})

//收入平衡
const balance = computed(() => {
    let total = totalIncome.value + totalExpense.value
    return parseFloat(total.toFixed(2))
    // return totalIncome.value + totalExpense.value
})


//处理标签部分

const visibleTag = ref(false)
const showTag = () => {
    visibleTag.value = true
}


const bill = reactive({
    userId: 0,
    money: '',
    billType: 'expense',
    category: '餐饮',
    date: '',
    shop: '',
    remark: '',
})

const tagList = reactive({
    income: [
        {id: 1, name: '工资'},
        {id: 2, name: '奖金'},
        {id: 3, name: '兼职'},
        {id: 4, name: '理财'},
        {id: 5, name: '投资'},
        {id: 6, name: '红包'},
        {id: 7, name: '转账'},
        {id: 8, name: '退款'},
        {id: 9, name: '全部'},
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
        {id: 9, name: '全部'},
    ],
})

//todo 刚进入页面时，获取全部数据
const getData = async () => {
    query.date = getTodayMonth() //2023-04
    const res: getBillByCategoryOrDateResponse = await getBillByCategoryOrDate(query)
    if (res.code === 200) { //code, msg, data
        data.list = res.data
    } else {
        showFailToast(res.msg)
    }
}

const changeType = (type) => {
    bill.billType = type
    bill.category = tagList[type][0].name
}

//选择类型
const choseType = (item) => {
    bill.category = item.name
}


//确认选择标签
const onConfirmTag = async () => {
    visibleTag.value = false
    query.category = bill.category
    if (bill.category === '全部') {
        query.category = ''
    }
    if (query.category === '' && query.date === '') {
        return
    }
    console.log(query)
    const res = await getBillByCategoryOrDate(query)
    if (res.code === 200) {
        data.list = res.data
        data.list.forEach(item => {
            let date = new Date(item.date)
            let year = date.getFullYear()
            let month = date.getMonth() + 1
            let day = date.getDate()
            item.date = year + "-" + month + "-" + day
        })
    } else {
        console.log(res.msg)
    }
}


//日历
const month = reactive({
    minDate: new Date(2022, 1, 12),
    maxDate: new Date(2030, 11, 1)
})
const currentDate = ref(['2023', '02']);
const columnsType = ['year', 'month'];

const formatter = (type, option) => {
    if (type === 'year') {
        option.text += '年';
    }
    if (type === 'month') {
        option.text += '月';
    }
    return option;
};


const onConfirmDateSelect = async () => {
    showSelectMonth.value = false
    //将日期转换为2023-02格式
    query.date = currentDate.value[0] + "-" + currentDate.value[1]
    const res: getBillByCategoryOrDateResponse = await getBillByCategoryOrDate(query)
    if (res.code === 200) {
        data.list = res.data
    } else {
        showFailToast(res.msg)
    }
}

onMounted(() => {
    getData()
})

const toAddBill = () => {
    router.push('/addBill')
}
</script>

<style scoped lang="less">
@import "src/assets/theme.less";

.home {
    width: 100%;
    height: 100%;
    //背景色为灰色
    background-color: #f5f5f5;
    display: flex;
    flex-direction: column;
    //防止被底部导航栏遮挡
    padding-bottom: 1.5rem;


    .header {
        width: 100%;
        height: 3rem;
        justify-content: space-between;
        align-items: center;
        box-shadow: 0 0 0.2rem 0.1rem rgba(0, 0, 0, 0.1);
        background-color: @primary;
        color: #eeeeee;
        //display: flex;
        //flex-direction: column;

        //标签和日期在第一排，第二排是各项金额
        .select {
            font-size: 0.4rem;
            display: flex;
            height: 1.5rem;
            justify-content: space-between;
            align-items: center;
            padding: 0 1.5rem;

            .tag {
                background-color: @primary-small;
                padding: 0.2rem 0.5rem;

                .title {
                }

                .iconfont {
                }
            }

            .date {
                background-color: @primary-small;
                padding: 0.2rem 0.5rem;
            }
        }

        .money {
            font-size: 0.35rem;
            display: flex;
            justify-content: space-between;
            align-items: center;
            height: 1.5rem;
            padding: 0 1.5rem;

        }


    }

    .content {
        width: 100%;
        background-color: #f5f5f5;

    }

    .add {
        position: fixed;
        background-color: #fff;
        display: flex;
        justify-content: center;
        align-items: center;
        color: @primary;
        //放到右下角，底部bar的上面，用px做单位
        bottom: 2.5rem;
        right: 0.5rem;
        width: 1.5rem;
        height: 1.5rem;
        border-radius: 50%;
        box-shadow: 0 0 0.2rem 0.1rem rgba(0, 0, 0, 0.1);

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

    .confirm {
        width: 200px;
        height: 1.5rem;
        background-color: @primary;
        color: #fff;
        text-align: center;
        line-height: 1.5rem;
        //居中
        margin: 0 auto;
        border-radius: 0.5rem;
    }


}

</style>
