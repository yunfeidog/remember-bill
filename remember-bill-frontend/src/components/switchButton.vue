<template>
    <div class="switch">
        <div class="switch-btn" :class="{ active: isMonthly }" @click="toggleMonthly">
            月度
        </div>
        <div class="switch-btn" :class="{ active: !isMonthly }" @click="toggleMonthly">
            年度
        </div>
        <van-popup v-model:show="showPicker" position="bottom">
            <van-picker
                :columns="isMonthly ? monthColumns : yearColumns"
                :value="isMonthly ? [selectedMonth] : [selectedYear]"
                @confirm="handleConfirm"
                @cancel="handleCancel"
            />
        </van-popup>
    </div>
</template>

<script>
export default {
    data() {
        return {
            isMonthly: true,
            showPicker: false,
            monthColumns: [],
            yearColumns: [],
            selectedMonth: '',
            selectedYear: '',
        }
    },
    mounted() {
        // 初始化月份和年份选择器数据
        const months = []
        for (let i = 1; i <= 12; i++) {
            months.push({
                text: `${i}月`,
                value: i,
            })
        }
        const years = []
        const currentYear = new Date().getFullYear()
        for (let i = currentYear - 10; i <= currentYear; i++) {
            years.push({
                text: `${i}年`,
                value: i,
            })
        }
        this.monthColumns = months
        this.yearColumns = years
        this.selectedMonth = months[0].value
        this.selectedYear = years[years.length - 1].value
    },
    methods: {
        toggleMonthly() {
            this.isMonthly = !this.isMonthly
            this.showPicker = true
        },
        handleConfirm(value) {
            if (this.isMonthly) {
                this.selectedMonth = value[0]
            } else {
                this.selectedYear = value[0]
            }
            this.showPicker = false
        },
        handleCancel() {
            this.showPicker = false
        },
    },
}
</script>

<style>
.switch {
    display: flex;
    align-items: center;
}

.switch-btn {
    padding: 10px;
    font-size: 16px;
    color: #333;
    cursor: pointer;
}

.switch-btn.active {
    color: #ff6a3d;
}
</style>
