import {createRouter, createWebHashHistory} from "vue-router";
import store from "../store";
import {showToast} from "vant";
import Login from "../views/login/index.vue";
import Register from "../views/register/index.vue";
import Detail from "../views/detail/index.vue";
import AddBill from "../views/addBill/index.vue";
import UpdateBill from "../views/updateBill/index.vue";
import Layout from "../views/layout/index.vue";
import Home from "../views/home/index.vue";
import Statistics from "../views/statistics/index.vue";
import User from "../views/user/index.vue";

const routes:any = [
    {
        path: '/login',
        name: 'login',
        component: Login
    },
    {
        path: '/register',
        name: 'register',
        component: Register
    },
    {

        path: '/detail/',
        name: 'detail',
        component: Detail
    },
    {
        path: '/addBill',
        name: 'addBill',
        component: AddBill
    },
    {
        path: '/update',
        name: 'updateBill',
        component: UpdateBill
    },
    {
        path: '/',
        name: 'layout',
        component:Layout,
        children: [
            {
                path: '',
                name: 'home',
                component: Home
            },
            {
                path: '/statistics',
                name: 'statistics',
                component: Statistics
            },
            {
                path: '/user',
                name: 'user',
                component: User
            },

        ]
    }
]

const router = createRouter({
    // 4. 内部提供了 history 模式的实现。为了简单起见，我们在这里使用 hash 模式。
    history: createWebHashHistory(),
    routes, // `routes: routes` 的缩写
})

//限制未登录用户访问
router.beforeEach((to, from, next) => {
    const {user} = store.state;
    if (to.path !== "/login" && to.path !== "/register" && !user) {
        showToast("请先登录")
        next({path: "/login"});
    } else {
        next();
    }
})


export default router


