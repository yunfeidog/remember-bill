
export interface statisticsResponse {
    code: number,
    data: {
        incomeList:number[],
        expenseList:number[],
        categoryList: string[],
        categoryListMoney: number[],

    },
    msg: string
}


export interface OCRResult{
    money: string,
    date: string,
    no: string,
    shop: string,
    shop_no: string,
    sku: string,

    category: string,
}

