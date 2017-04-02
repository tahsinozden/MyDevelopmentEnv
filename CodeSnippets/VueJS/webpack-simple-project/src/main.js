import Vue from 'vue'
import VueRouter from 'vue-router'
import App from './App.vue'
import Message from './components/Message.vue'
import UserForm from './components/UserForm.vue'
import Home from './Home.vue'
import About from './About.vue'
// import AutoComplete from './AutoComplete.vue'

// enable router
Vue.use(VueRouter);

// globally register components
Vue.component('app-message', Message);
Vue.component('user-form', UserForm);
// Vue.component('auto-complete', AutoComplete);

const routes = [
  {path: '/', component: Home},
  {path: '/about', component: About}
];

const pageRouter = new VueRouter({
  // mode history is used for removing '#' in the address line
  // mode: 'history',
  routes: routes
})

new Vue({
  el: '#app',
  router: pageRouter,
  render: h => h(App)
})
