//封装用户请求模块
import request from "../utils/request.js";
import {UserLoginRequest, UserLoginResponse, UserUpdateUserNameRequest} from "../model/user";
import {BaseResponse} from "../model/baseResponse";



//data是一个对象，包含了用户名和密码，：后面的是返回值类型
export const login = (data:UserLoginRequest):Promise<UserLoginResponse> => {
    return request({
        method: "POST",
        url: "/user/login",
        data,
    });
}

export const register = (data:UserLoginRequest):Promise<BaseResponse> => {
    return request({
        method: "POST",
        url: "/user/register",
        data,
    });
}


export const updateUserName = (data:UserUpdateUserNameRequest):Promise<BaseResponse> => {
    return request({
        method: "POST",
        url: "/user/updateUserName",
        data,
    });
}
