import Vue from 'vue'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import './assets/global.css'
import App from './App.vue'
import router from './router'
import request from './utils/request'
import installMock from './mock'

if (process.env.NODE_ENV !== 'production') {
  installMock(request)
}

Vue.use(ElementUI)

Vue.config.productionTip = false

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
