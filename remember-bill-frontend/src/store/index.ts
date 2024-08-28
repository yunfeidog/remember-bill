import {createStore} from "vuex";
import {getItem, setItem} from "../utils/storage.js";
const TOKEN_KEY="user";

const store=createStore({
    state:{
        user:getItem(TOKEN_KEY)
    },
    mutations:{
        setUser(state,data){
            state.user=data;
            setItem(TOKEN_KEY,state.user)
        }
    }
})

export default store;
