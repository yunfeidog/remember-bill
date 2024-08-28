<template>
    <!--顶部切换年月，选择年月-->
    <div class="top">
        <van-tabs v-model:active="active" @click-tab="onClickTab">
            <van-tab title="月度">
                <div @click=" showPopMonth = true" class="select">{{ month }}
                    <van-icon name="arrow-down"/>
                </div>

            </van-tab>
            <van-tab title="年度">
                <div @click=" showPopYear = true" class="select">{{ year }}
                    <van-icon name="arrow-down"/>
                </div>
            </van-tab>
        </van-tabs>
    </div>

    <!--中间折线图和饼状图-->
    <div class="container">
        <div class="chart" ref="lineChart"></div>
        <div class="chart" ref="pieChart"></div>
    </div>


    <van-popup v-model:show="showPopMonth" position="bottom" round>
        <van-date-picker
            v-model="currentMonth"
            title="选择年月"
            :min-date="new Date(2020, 0, 1)"
            :max-date="new Date(2025, 11, 31)"
            :formatter="formatterMonth"
            :columns-type="columnsTypeMonth"
            @confirm="onConfirmSelectMonth"
            @cancel="showPopMonth = false"
        />
    </van-popup>

    <van-popup v-model:show="showPopYear" position="bottom" round>
        <van-date-picker
            v-model="currentYear"
            title="选择年"
            :min-date="new Date(2020, 0, 1)"
            :max-date="new Date(2025 , 11, 31)"
            :formatter="formatterYear"
            :columns-type="columnsTypeYear"
            @confirm="onConfirmSelectYear"
            @cancel="showPopYear = false"
        />
    </van-popup>
</template>

<script setup lang="ts">
import {onMounted, reactive, ref} from 'vue';
import * as echarts from 'echarts/core';
import {LineChart, PieChart} from 'echarts/charts';
import {CanvasRenderer} from 'echarts/renderers';
import {GridComponent, LegendComponent, TitleComponent, TooltipComponent} from 'echarts/components';
import {statisticsByMonth, statisticsByYear} from "../../api/bill.ts";
import {showToast} from "vant";
import {lineByMonth, lineByYear, pieByMonth, pieByYear} from "./data.ts";
import {getTodayMonth} from "../home/data";
import {formatterMonth, formatterYear} from "../../utils/dateUtils";

echarts.use([GridComponent, TooltipComponent, LegendComponent, PieChart, LineChart, TitleComponent, CanvasRenderer]);

const month = ref();
//月和年的切换
const showPopMonth = ref(false);
//jint
const currentMonth = ref(['2023', '03']);//今天的日期
const currentYear = ref(['2023']);//今天的日期

const columnsTypeMonth = ['year', 'month'];
const columnsTypeYear = ['year'];

const query = reactive<{
    year: string,
    month: string,
}>({
    year: '',
    month: '',
});


const onConfirmSelectMonth = async (value) => {
    console.log(currentMonth)
    showPopMonth.value = false;
    month.value = currentMonth.value[0] + '-' + currentMonth.value[1];
    query.year = currentMonth.value[0];
    query.month = currentMonth.value[1];
    console.log("query", query)
    await getStatisticsDataByMonth();
    console.log("y坐标：")
    console.log(lineByMonth.series[0].data)
    pieChartInstance.value.setOption(pieByMonth);
    lineChartInstance.value.setOption(lineByMonth);
};


//切换到年
const showPopYear = ref(false);

const year = ref();

const onConfirmSelectYear = async (value) => {
    console.log(currentMonth)
    showPopYear.value = false;
    year.value = currentYear.value[0];
    query.year = currentYear.value[0];
    console.log("query", query)
    await getStatisticsDataByYear();
    console.log("y坐标：")
    console.log(lineByYear.series[0].data)
    pieChartInstance.value.setOption(pieByYear);
    lineChartInstance.value.setOption(lineByYear);

};


// 折线图和饼图
const pieChart:any = ref(null);
const lineChart:any = ref(null);

const active = ref(0);


//保证在watchEffect中可以访问到pieChartInstance和lineChartInstance
const pieChartInstance = ref({});
const lineChartInstance = ref({});

const initData = () => {
    month.value = getTodayMonth();
    console.log("今天的日期：" + month.value);
    year.value = month.value.split('-')[0];
    query.year = year.value
    query.month = month.value.split('-')[1]
    console.log("query", query)
    //设置折线图横坐标
    const x = [];
    for (let i = 1; i <= 31; i++) {
        x.push(i);
    }
    lineByMonth.xAxis.data = x;

    //设置年度折线图横坐标
    const xYear = [];
    for (let i = 1; i <= 12; i++) {
        xYear.push(i);
    }
    lineByYear.xAxis.data = xYear;
}

const onClickTab = async ({title}) => {
    if (title === '月度') {
        showToast('月度')
        await getStatisticsDataByMonth()
        pieChartInstance.value.setOption(pieByMonth);
        lineChartInstance.value.setOption(lineByMonth);
    } else {
        showToast('年度')
        await getStatisticsDataByYear()
        pieChartInstance.value.setOption(pieByYear);
        lineChartInstance.value.setOption(lineByYear);
    }

}

const getStatisticsDataByMonth = async () => {
    console.log("查询条件：query.year: " + query.year + " query.month: " + query.month)
    //发送请求
    const res = await statisticsByMonth(query);
    console.log("统计信息：")
    console.log(res.data)
    const data = res.data;
    const expenseList = data.expenseList;
    const incomeList = data.incomeList;
    const categoryList = data.categoryList;
    const categoryListMoney = data.categoryListMoney;

    //将支出变成正数
    expenseList.forEach((item, index) => {
        expenseList[index] = -item;
    })

    categoryListMoney.forEach((item, index) => {
        categoryListMoney[index] = -item;
    })

    //设置折线图纵坐标
    lineByMonth.series[0].data = expenseList;
    lineByMonth.series[1].data = incomeList;
    //设置饼图数据
    //将expenseMapList转换为[{name: '餐饮', value: 1000}, {name: '购物', value: 2000}]
    let pieData = [];
    categoryList.forEach((item, index) => {
        pieData.push({name: item, value: categoryListMoney[index]})
    })
    pieByMonth.legend.data = categoryList;
    pieByMonth.series[0].data = pieData;
    console.log("categoryList : " + categoryList)
    console.log("categoryListMoney : " + categoryListMoney)

}

const getStatisticsDataByYear = async () => {
    //发送请求
    const res = await statisticsByYear(query);
    const data = res.data;
    console.log("data:", data)
    const expenseList = data.expenseList;
    const incomeList = data.incomeList;
    const categoryList = data.categoryList;
    const categoryListMoney = data.categoryListMoney;

    //将支出变成正数
    expenseList.forEach((item, index) => {
        expenseList[index] = -item;
    })

    categoryListMoney.forEach((item, index) => {
        categoryListMoney[index] = -item;
    })

    //设置折线图纵坐标
    lineByYear.series[0].data = expenseList;
    lineByYear.series[1].data = incomeList;
    //设置饼图数据
    //将expenseMapList转换为[{name: '餐饮', value: 1000}, {name: '购物', value: 2000}]
    let pieData = [];
    categoryList.forEach((item, index) => {
        pieData.push({name: item, value: categoryListMoney[index]})
    })
    console.log("pieData:", pieData)
    pieByYear.legend.data = categoryList;
    pieByYear.series[0].data = pieData;
    console.log("pieByYear:", pieByYear)
    console.log("categoryList : " + categoryList)

}

onMounted(async () => {
     initData()
    await getStatisticsDataByMonth()

    // 将实例化后的对象赋值给全局变量
    pieChartInstance.value= echarts.init(pieChart.value);
    lineChartInstance.value = echarts.init(lineChart.value);

    // 调用 setOption 方法时使用响应式对象上的 value 属性
    pieChartInstance.value.setOption(pieByMonth);
    lineChartInstance.value.setOption(lineByMonth);

});
</script>

<style scoped lang="less">
.container {
    height: 100%;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
}

.chart {
    width: 90%;
    height: 500px;
    margin-top: 20px;
}

.select {
    //居中
    justify-content: center;
    align-items: center;
    //宽度
    text-align: center;
    margin: 10px 200px;
    //背景色
    //圆角
    border-radius: 10px;
    //字体大小
    font-size: 40px;
}


</style>
