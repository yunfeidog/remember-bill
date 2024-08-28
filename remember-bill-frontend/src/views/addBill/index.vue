<template>
    <van-nav-bar title="添加账单" left-text="返回" @click-left="goBack" left-arrow right-text="确认"
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
            <van-uploader :after-read="afterRead" :before-read="beforeRead" accept="image/*">
                <van-icon name="photograph" class="photo"/>
            </van-uploader>
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
        <van-field v-model="bill.date" placeholder="请选择日期" @click="showSelectMonth=true"/>

        <div class="comment" style="margin-bottom: 10px;">商家</div>
        <van-field v-model="bill.shop" placeholder="请输入商家名称"/>
        <div class="comment" style="margin-bottom: 10px;">备注</div>
        <van-field v-model="bill.remark" placeholder="请输入备注信息"/>
    </div>

    <!-- 底部弹出选择日期 -->
    <van-popup
        v-model:show="showSelectMonth"
        position="bottom"
        roun
        :style="{ height: '50%' }">
        <van-date-picker
            v-model="currentDate"
            title="请选择日期"
            :min-date="month.minDate"
            :max-date="month.maxDate"
            :formatter="formatterYearMonthDay"
            :columns-type="columnsType"
            @confirm="onConfirmDateSelect"
            @cancel="showSelectMonth = false"
        />
    </van-popup>

</template>

<script setup lang="ts">

import {useRouter} from "vue-router";
import {onMounted, reactive, ref} from "vue";
import {addBill, uploadImage} from "../../api/bill.ts";
import {showFailToast, showLoadingToast, showSuccessToast} from "vant";
import {formatterYearMonthDay, getTodayDate, getTodayDateArray} from "../../utils/dateUtils";
import {data} from "./data";
import {addBillRequest} from "../../model/bill";
import axios from "axios";
import {OCRResult} from "../../model/bill/response/BillResponse";

const router = useRouter()


const bill = reactive<{
    money: string,
    category: string,
    date: string,
    shop: string,
    remark: string,
    billType: string,
}>({
    money: '',
    category: '餐饮',
    date: '',
    shop: '',
    remark: '',
    billType: 'expense',
})
const active = ref(0)


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
    let money = Number(bill.money)
    if (bill.billType === 'expense') {
        money = -money
    }
    const newBill: addBillRequest = {
        billAmount: money,
        billCategory: bill.category,
        billDate: bill.date,
        billRemark: bill.remark,
        billShopkeeper: bill.shop,
    }
    console.log("提交的账单是：", newBill)
    const res = await addBill(newBill)
    console.log(res)
    if (res.code === 200) {
        showSuccessToast({
            message: '添加成功',
            className: 'success-toast',
        })
        await router.push('/')
    } else {
        showFailToast({
            message: '添加失败',
            className: 'fail-toast',
        })
    }
}


//日历部分
const showSelectMonth = ref(false)
const month = reactive({
    minDate: new Date(2020, 0, 12),
    maxDate: new Date(2030, 11, 1)
})
const currentDate = ref(getTodayDateArray())
const columnsType = ['year', 'month', 'day'];
const onConfirmDateSelect = () => {
    showSelectMonth.value = false
    bill.date = currentDate.value[0] + '-' + currentDate.value[1] + '-' + currentDate.value[2]
    console.log("你选择的日期是：" + bill.date)
}


const afterRead = (file) => {
    // 获取要上传的文件
    console.log("afterRead")
    console.log(file)
};

const beforeRead = async (file) => {
    console.log("beforeRead")
    console.log(file)
    // 发给后端
    const formData = new FormData();
    formData.append('file', file);
    showLoadingToast({
        message: '正在识别中...',
        forbidClick: true,
        duration: 120000,
    });
    const res:{ code: number, data: OCRResult, msg: string } = await uploadImage(formData);
    if (res.code == 200) {
        showSuccessToast("识别成功")
        bill.money = res.data.money
        bill.date = res.data.date.substring(0, 10)
        bill.shop = res.data.shop
        if (res.data.no != "") {
            bill.remark += "单号:" + res.data.no + ";";
        }
        if (res.data.shop_no != "") {
            bill.remark += "商户单号:" + res.data.shop_no + ";";
        }
        if (res.data.sku != "") {
            bill.remark += "商品名称:" + res.data.sku + ";";
        }
        if (res.data.category!=""){
            bill.billType = "expense"
            bill.category=res.data.category
        }
        console.log("res中的数据为：")
        console.log(res.data)
    }
}


const initData = () => {
    //账单中的日期
    bill.date = getTodayDate();
}

onMounted(() => {
    console.log('mounted')
    initData();
})
const goBack = () => {
    window.history.length > 1 ? router.go(-1) : router.push('/')
}
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
