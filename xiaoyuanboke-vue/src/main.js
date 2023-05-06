import Vue from 'vue'

import 'normalize.css/normalize.css' // A modern alternative to CSS resets

// import locale from 'element-ui/lib/locale/lang/en' // lang i18n

import '@/styles/index.scss' // global css

import App from './App'
import router from './router'
import store from './store'

import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'

import '@/icons' // icon
import '@/permission' // permission control

import leMarkdownEditor from 'le-markdown-editor'


Vue.use(leMarkdownEditor)

Vue.use(ElementUI)


Vue.config.productionTip = false
// 全局WebSocket
Vue.prototype.$chatSocketUrl = 'ws://localhost:9004/back/api/chat'
Vue.prototype.$noticeSocketUrl = 'ws://localhost:9004/back/api/notice'

// router.afterEach((to, from, next) => {
//   window.scrollTo(0, 0);
// })

new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})

// 创建全局EventBus
Vue.prototype.$EventBus = new Vue()

// 封装用于判断用户是否具有某些权限的公共函数
import { isAuth } from "./utils/auth";

Vue.prototype['isAuth'] = isAuth

var EventBus = new Vue()

Object.defineProperties(Vue.prototype, {
	$bus: {
		get: function () {
			return EventBus
		}
	}
})
