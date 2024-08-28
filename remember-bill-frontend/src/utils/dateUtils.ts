export const getTodayDate = () => {
    //格式化日期：yyyy-MM-dd
    const date = new Date();
    const year = date.getFullYear();
    const month = date.getMonth() + 1;
    const day = date.getDate();
    if (month < 10) {
        if (day < 10) {
            return `${year}-0${month}-0${day}`;
        } else {
            return `${year}-0${month}-${day}`;
        }
    } else {
        if (day < 10) {
            return `${year}-${month}-0${day}`;
        } else {
            return `${year}-${month}-${day}`;
        }
    }
}

export const getTodayDateArray = () => {
    // 格式 [yyyy,MM,dd]，用于日期选择器
    const date = new Date();
    const year = date.getFullYear().toString();
    const month = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1);
    const day = (date.getDate() < 10 ? '0' + (date.getDate()) : ''+date.getDate());
    return [year, month, day];
};


export const formatterYearMonthDay = (type, option) => {
    if (type === 'year') {
        option.text += '年';
    }
    if (type === 'month') {
        option.text += '月';
    }
    if (type === 'day') {
        option.text += '日';
    }
    return option;
};


export const formatterMonth = (type, option) => {
    if (type === 'year') {
        option.text += '年';
    }
    if (type === 'month') {
        option.text += '月';
    }
    return option;
};


export const formatterYear = (type, option) => {
    if (type === 'year') {
        option.text += '年';
    }
    return option;
};
