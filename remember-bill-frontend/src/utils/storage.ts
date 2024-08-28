//存储数据

export const setItem = (key, value) => {
    if (typeof value === 'object') {
        value = JSON.stringify(value)
    }
    window.localStorage.setItem(key, value);

}

//获取数据

export const getItem = (key) => {
    const data = window.localStorage.getItem(key)
    try {// 尝试把data转为js对象
        return JSON.parse(data)
    } catch (err) {// data不是json格式字符串
        return data
    }
}

//删除数据

export const removeItem = (key) => {
    window.localStorage.removeItem(key)
}

//清空数据

export const clear = () => {
    window.localStorage.clear()
}
