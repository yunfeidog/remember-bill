import axios from "axios";
import store from "../store/index";
import router from "../router/index";
import {showToast} from "vant";

const request = axios.create({
    baseURL: "/api",
    // //需要跨域的话，这里配置为true
    // withCredentials: true,
});

// 请求拦截器
request.interceptors.request.use(
    function (config) {
        // 在发送请求之前做些什么
        const {user} = store.state;
        if (user && user.token) {
            config.headers.Authorization = `Bearer ${user.token}`;
        }
        return config;
    }
);

// 响应拦截器
request.interceptors.response.use(
    function (response) {
        //请求的路径
        console.log("请求的路径", response.config.url)
        console.log("接收到响应", response)
        const data = response.data;
        console.log("data：", data)
        if (data.code === 200) {
            return data;
        }
        if (data.code === 500) {
            //没有登录，跳转到登录页面
            store.commit("setUser", null);
            // 跳转到登录页面
            showToast("请先登录")
            console.log("登录拦截检测到未登录，跳转到登录页面")
            router.push({path: "/login"});
        }
        else{
            // @ts-ignore
            return Promise.reject(data);
        }
    },
    function (error) {
        // // 对响应错误做点什么
        // if (error.response) {
        //     const {status} = error.response;
        //     if (status === 401) {
        //         // 清除用户登录状态
        //         store.commit("setUser", null);
        //         // 跳转到登录页面
        //     }
        // }
        // return Promise.reject(error);
    }


);


export default request;
