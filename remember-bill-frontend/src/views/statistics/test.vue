<template>
    <div class="dashboard">
        <!-- 当月收支折线图 -->
        <div class="chart">
            <div class="chart-title">
                当月收支
            </div>
            <div ref="monthChart" class="chart-container">
            </div>
        </div>
        <!-- 当年收支饼状图 -->
        <div class="chart">
            <div class="chart-title">当年收支</div>
            <div ref="yearChart" class="chart-container"></div>
        </div>
    </div>
</template>


<script>
import * as echarts from 'echarts';

export default {
    name: 'test',
    data() {
        return {
            monthData: {
                // 当月数据
                income: [2000, 2800, 3200, 3500, 4000, 4500, 5000, 5500, 6000, 6500, 7000, 7500],
                expense: [1000, 1200, 1500, 1800, 2000, 2200, 2500, 2700, 3000, 3500, 4000, 4200]
            },
            yearData: {
                // 当年数据
                income: [
                    {value: 3350, name: '工资'},
                    {value: 1548, name: '红利'},
                    {value: 234, name: '股票'},
                    {value: 135, name: '其他'}
                ],
                expense: [
                    {value: 1100, name: '日常开销'},
                    {value: 800, name: '房租'},
                    {value: 400, name: '税费'},
                    {value: 300, name: '旅游'}
                ]
            }
        };
    },
    mounted() {
        // 初始化图表
        this.initMonthChart();
        this.initYearChart();
    },
    methods: {
        initMonthChart() {
            const monthChart = echarts.init(this.$refs.monthChart);

            // 设置当月收支折线图的配置选项
            const option = {
                title: {
                    text: '当月收支'
                },
                tooltip: { //
                    trigger: 'axis'//
                },
                legend: {
                    data: ['收入', '支出']
                },
                toolbox: {
                    feature: {
                        saveAsImage: {} // 保存为图片
                    }
                },
                grid: { //
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                xAxis: {
                    type: 'category',
                    boundaryGap: false,
                    data: ['01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12']
                },
                yAxis: {
                    type: 'value'
                },
                series: [
                    {
                        name: '收入',
                        type: 'line',
                        stack: '总量',
                        data: this.monthData.income
                    },
                    {
                        name: '支出',
                        type: 'line',
                        stack: '总量',
                        data: this.monthData.expense
                    }
                ]
            };

            // 使用刚指定的配置项和数据显示图表。
            monthChart.setOption(option);

            // 监听当月数据变化并更新图表
            this.$watch('monthData', (newVal) => {
                const option = {
                    series: [
                        {
                            name: '收入',
                            type: 'line',
                            stack: '总量',
                            data: newVal.income
                        },
                        {
                            name: '支出',
                            type: 'line',
                            stack: '总量',
                            data: newVal.expense
                        }
                    ]
                };
                monthChart.setOption(option);
            });
        },
        initYearChart() {
            const yearChart = echarts.init(this.$refs.yearChart);

            // 设置当年收支饼状图的配置选项
            const option = {
                title: {
                    text: '当年收支',
                    subtext: '2019年1月1日至2022年3月16日'
                },
                tooltip: {
                    trigger: 'item',
                    formatter: '{a} <br/>{b}: {c} ({d}%)'
                },
                legend: {
                    orient: 'vertical',
                    x: 'left',
                    data: ['工资', '红利', '股票', '其他']
                },
                series: [
                    {
                        name: '收入',
                        type: 'pie',
                        radius: ['50%', '70%'],
                        avoidLabelOverlap: false,
                        label: {
                            show: false,
                            position: 'center'
                        },
                        emphasis: {
                            label: {
                                show: true,
                                fontSize: '30',
                                fontWeight: 'bold'
                            }
                        },
                        labelLine: {
                            show: false
                        },
                        data: this.yearData.income
                    },
                    {
                        name: '支出',
                        type: 'pie',
                        radius: ['50%', '70%'],
                        avoidLabelOverlap: false,
                        label: {
                            show: false,
                            position: 'center'
                        },
                        emphasis: {
                            label: {
                                show: true,
                                fontSize: '30',
                                fontWeight: 'bold'
                            }
                        },
                        labelLine: {
                            show: false
                        },
                        data: this.yearData.expense
                    }
                ]
            };

            // 使用刚指定的配置项和数据显示图表。
            yearChart.setOption(option);

            // 监听当年数据变化并更新图表
            this.$watch('yearData', (newVal) => {
                const option = {
                    series: [
                        {
                            name: '收入',
                            type: 'pie',
                            radius: ['50%', '70%'],
                            avoidLabelOverlap: false,
                            label: {
                                show: false,
                                position: 'center'
                            },
                            emphasis: {
                                label: {
                                    show: true,
                                    fontSize: '30',
                                    fontWeight: 'bold'
                                }
                            },
                            labelLine: {
                                show: false
                            },
                            data: newVal.income
                        },
                        {
                            name: '支出',
                            type: 'pie',
                            radius: ['50%', '70%'],
                            avoidLabelOverlap: false,
                            label: {
                                show: false,
                                position: 'center'
                            },
                            emphasis: {
                                label: {
                                    show: true,
                                    fontSize: '30',
                                    fontWeight: 'bold'
                                }
                            },
                            labelLine: {
                                show: false
                            },
                            data: newVal.expense
                        }
                    ]
                };
                yearChart.setOption(option);
            });
        }
    }
};

</script>

<style>
.dashboard {
    display: flex;
    flex-direction: column;
}

.chart {
    display: flex;
    flex-direction: column;
    margin-bottom: 20px;
}

.chart-title {
    font-size: 18px;
    font-weight: bold;
    margin-bottom: 10px;
}

.chart-container {
    width: 100%;
    height: 400px;
}
</style>
