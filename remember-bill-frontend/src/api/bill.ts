//封装账单请求模块

import request from '../utils/request.js'
import {OCRResult, statisticsResponse} from "../model/bill/response/BillResponse";
import {
    addBillRequest,
    getBillByCategoryOrDateRequest,
    getBillByCategoryOrDateResponse,
    updateBillRequest
} from "../model/bill";
import {BaseResponse} from "../model/baseResponse";

export const addBill = (data: addBillRequest): Promise<BaseResponse> => {
    return request({
        method: 'POST',
        url: '/bill/add',
        data
    })
}


export const getBillByCategoryOrDate = (data: getBillByCategoryOrDateRequest): Promise<getBillByCategoryOrDateResponse> => {
    return request({
        method: 'POST',
        url: '/bill/listByDateOrCategory',
        data
    })
}

export const getBillByMonth = (data: string): Promise<any> => {
    return request({
        method: 'POST',
        url: '/bill/queryByMonth',
        data
    })
}

export const getAllBill = () => {
    return request({
        method: 'GET',
        url: '/bill/list',
    })
}

export const getBillById = (id: number): Promise<{
    code: number,
    data: {
        id: number,
        money: number,
        category: string,
        billDate: string,
        shop: string,
        remark: string,
        userId: number,
    }
    msg: string
}> => {
    return request({
        method: 'GET',
        url: `/bill/${id}`,
    })
}

export const deleteBillById = (id: number): Promise<BaseResponse> => {
    return request({
        method: 'DELETE',
        url: `/bill/${id}`,
    })
}

export const updateBill = (data: updateBillRequest): Promise<BaseResponse> => {
    return request({
        method: 'PUT',
        url: '/bill/update',
        data
    })
}


export const statisticsByMonth = (data: { year: string, month: string }): Promise<statisticsResponse> => {
    return request({
        method: 'POST',
        url: '/bill/statisticsByMonth',
        data
    })
}

export const statisticsByYear = (data: { year: string }): Promise<statisticsResponse> => {
    return request({
        method: 'POST',
        url: '/bill/statisticsByYear',
        data
    })
}


//上传图片
export const uploadImage = (data: FormData): Promise<{ code: number, data: OCRResult, msg: string }> => {
    return request({
        method: 'POST',
        url: '/bill/upload',
        headers: {
            'Content-Type': 'multipart/form-data'
        },
        data
    })
}
