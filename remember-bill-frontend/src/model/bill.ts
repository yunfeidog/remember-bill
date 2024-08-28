export interface Bill {
    id?: number;//账单id

    userId?:number;//用户id
    money?: number;//金额

    category?: string;//账单分类
    billDate?: Date;//账单日期
    shop?: string;//商家
    remark?: string;//备注
    billType?:string;// expense 支出 income 收入
}


export interface StatisticsRequest {
    year: number;
    month: number;
}

export interface getBillByCategoryOrDateRequest {
    date: string;
    category: string;
}



export interface BillCard{
    date: string;
    bills: Bill[];
}

export interface getBillByCategoryOrDateResponse {
    code: number;
    data: BillCard[];
    msg: string;
}

export interface addBillRequest {
    billAmount: number;
    billCategory: string;
    billDate: string;
    billRemark: string
    billShopkeeper: string
}

export interface updateBillRequest {
    id?: number;//账单id

    userId?:number;//用户id
    money?: number;//金额

    category?: string;//账单分类
    billDate?: string;//账单日期
    shop?: string;//商家
    remark?: string;//备注
    billType?:string;// expense 支出 income 收入

}

export interface getBillResponse extends addBillRequest{
    id: number;
    userId: number;
}

