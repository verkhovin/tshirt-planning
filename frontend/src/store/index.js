import Vue from 'vue'
import Vuex from 'vuex'
import room from "./modules/room";

Vue.use(Vuex)
Vue.config.devtools = true
export default new Vuex.Store({
  modules: {
    room
  }
})