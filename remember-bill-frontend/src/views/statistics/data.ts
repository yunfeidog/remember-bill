export const lineByMonth = {
    title: {
        text: '消费趋势',
        //大小
        textStyle: {
            fontSize: 14,
        }
    },
    tooltip: { //
        trigger: 'axis'//
    },
    // toolbox: {
    //     feature: {
    //         saveAsImage: {} // 保存为图片
    //     }
    // },
    grid: { //
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
    },
    xAxis: {
        type: 'category',
        data: ['1','2'],
    },
    yAxis: {
        type: 'value',
    },
    series: [
        {
            name: '支出',
            data: [120, 200, 150, 80, 70, 110],
            type: 'line',
        },
        {
            name: '收入',
            data: [120, 200, 150, 80, 70, 110],
            type: 'line',
        }
    ],
    //两个折线图可以进行选择
    legend: {
        data: ['支出', '收入'],
    },

};


export const pieByMonth = {
    title: {
        text: '每月消费类型占比',
        x: 'center',
    },
    tooltip: {
        trigger: 'item',

    },
    legend: {
        orient: 'vertical',
        left: 'left',
        data: ['餐饮', '购物', '交通', '娱乐', '居家'],
    },
    series: [
        {
            name: '月度消费类型',
            type: 'pie',
            radius: '55%',
            center: ['50%', '60%'],
            data: [
                // {value: 335, name: '餐饮'},
                // {name: '餐饮', value: 335, itemStyle: {color: '#c23531'}},
                // {name: '购物', value: 310, itemStyle: {color: '#2f4554'}},
                // {name: '交通', value: 234, itemStyle: {color: '#61a0a8'}},
                // {name: '娱乐', value: 135, itemStyle: {color: '#d48265'}},
                // {name: '居家', value: 23, itemStyle: {color: '#91c7ae'}},

            ],
            emphasis: {
                itemStyle: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)',
                },
            },
        },
    ],
};



export const lineByYear = {
    title: {
        text: '消费趋势',
        //大小
        textStyle: {
            fontSize: 14,
        }
    },
    tooltip: { //
        trigger: 'axis'//
    },
    // toolbox: {
    //     feature: {
    //         saveAsImage: {} // 保存为图片
    //     }
    // },
    grid: { //
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
    },
    xAxis: {
        type: 'category',
        data:[],
        // data: ['1月', '2月', '3月', '4月', '5月', '6月'],
    },
    yAxis: {
        type: 'value',
    },
    series: [
        {
            name: '支出',
            data: [120, 200, 150, 80, 70, 600],
            type: 'line',
        },
        {
            name: '收入',
            data: [120, 200, 150, 80, 70, 110],
            type: 'line',
        }
    ],
    //两个折线图可以进行选择
    legend: {
        data: ['支出', '收入'],
    },

};


export const pieByYear = {
    title: {
        text: '年度消费类型占比',
        x: 'center',
    },
    tooltip: {
        trigger: 'item',
    },
    legend: {
        orient: 'vertical',
        left: 'left',
        data:[],
        // data: ['餐饮', '购物', '交通', '娱乐', '居家'],
    },
    series: [
        {
            name: '消费类型',
            type: 'pie',
            radius: '55%',
            center: ['50%', '60%'],
            data: [
                // {value: 700, name: '餐饮'},
                // {value: 310, name: '购物'},
                // {value: 234, name: '交通'},
                // {value: 343, name: '娱乐'},
                // {value: 1548, name: '居家'},
            ],
            emphasis: {
                itemStyle: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)',
                },
            },
        },
    ],
};
