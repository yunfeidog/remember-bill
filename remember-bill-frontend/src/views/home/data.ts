export const getTodayMonth = () => {
    let date = new Date()
    let year = date.getFullYear()
    let month = date.getMonth() + 1
    if (month < 10) {
        return year + "-0" + month
    }
    return year + "-" + month
}
