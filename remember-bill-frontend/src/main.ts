import {createApp} from 'vue'
import App from './App.vue'
import "amfe-flexible"
import router from "./router/index.ts";
import "./assets/index.css"
import store from "./store/index.ts";
import "vant/lib/index.css";
import './assets/vant-rewrite.less'

const app =createApp(App);
app.use(router);
app.use(store);
app.mount('#app')

