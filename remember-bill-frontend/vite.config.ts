import vue from '@vitejs/plugin-vue'
import Components from 'unplugin-vue-components/vite';
import { VantResolver } from 'unplugin-vue-components/resolvers';

export default {
  plugins: [
    vue(),
    Components({
      resolvers: [VantResolver()],
    })
  ],
  server: {
    host: "0.0.0.0",
    port: 7073,
    proxy: {
      '/api': {
        target: "http://localhost:8080/",
        pathRewrite: {
          '^/api': '/api',
        },
        changeOrigin: true,
      }
    }
  }
}


//
// import vue from '@vitejs/plugin-vue'
// import Components from 'unplugin-vue-components/vite';
// import {VantResolver} from 'unplugin-vue-components/resolvers';
//
//
// module.exports = {
//   plugins: [
//     vue(),
//     Components({
//       resolvers: [VantResolver()],
//     })
//   ],
//   // 解决实验楼环境启动的 https，服务端接口是 http 造成跨域的问题。
//   server: {
//     host: "0.0.0.0",
//     port: 7070,
//     proxy: {
//       '/api': {
//         target: "http://localhost:8080/",  // 配置到服务器后端的API头部
//         // pathRewrite: {
//         '^/api': '/api' ,  // 路径重写，第一个与上面相同，第二个/queue-admin 为server.context-path（服务器的上下文）
//         // },
//         // 以下解决https 访问问题。上面的可以访问http
//         changeOrigin: true,
//         // secure: false // 如果是https接口，需要配置这个参数
//       }
//     }
//   }
// }
//
//
