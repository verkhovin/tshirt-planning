import Vue from 'vue'
import App from './App.vue'
import router from "./router"
import store from "./store"
import { BootstrapVue, BootstrapVueIcons } from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import VueNativeSock from 'vue-native-websocket'

var VueCookie = require('vue-cookie');
Vue.use(VueCookie);
Vue.config.productionTip = false
Vue.use(BootstrapVue)
Vue.use(BootstrapVueIcons)

Vue.use(VueNativeSock, 'ws://' + location.host + '/ws/planning', { connectManually: true })
new Vue({
  router,
  store,
  render: h => h(App),
}).$mount('#app')
