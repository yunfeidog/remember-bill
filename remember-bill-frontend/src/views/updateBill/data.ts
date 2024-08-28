import {reactive} from "vue";

export //收入和支出类型各9个
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
})
