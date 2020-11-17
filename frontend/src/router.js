import Vue from 'vue'
import VueRouter from 'vue-router'
import Index from "@/pages/Index";
import RoomPage from "@/pages/RoomPage";

Vue.use(VueRouter)

export default new VueRouter({
  base: '/poker/',
  mode: 'history',
  routes: [
    {path: '/', component: Index},
    {path: '/rooms/:id', component: RoomPage}
  ]
})