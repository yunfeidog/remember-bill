export interface User {
    id: number;
    phone: string;
    userPassword: string;
    userName: string;

    isDelete: number; // 0:未删除 1:已删除

    createTime: string;

    updateTime: string;
}



export interface UserLoginRequest {
    phone: string;
    userPassword: string;
}


export interface UserLoginResponse {
    code: number;
    data: User;
    msg: string;
}


export interface UserRegisterRequest {
    userName: string;
    phone: string;
    userPassword: string;
    checkPassword: string;
}

export interface UserRegisterResponse {

}

export interface UserUpdateUserNameRequest {
    userName: string;
    id: number;
}



