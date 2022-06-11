import Vue from 'vue'

import 'normalize.css/normalize.css' // A modern alternative to CSS resets

import locale from 'element-ui/lib/locale/lang/en' // lang i18n

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

// Vue.use(ElementUI, { locale })
Vue.use(ElementUI)

Vue.config.productionTip = false

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
 
var EventBus = new Vue()

Object.defineProperties(Vue.prototype, {
	$bus: {
		get: function () {
			return EventBus
		}
	}
})