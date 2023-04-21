import url from 'postcss-url'
import Vue from 'vue'
import Router from 'vue-router'

// in development-env not use lazy-loading, because lazy-loading too many pages will cause webpack hot update too slow. so only in production use lazy-loading;
// detail: https://panjiachen.github.io/vue-element-admin-site/#/lazy-loading

Vue.use(Router)

/* Layout */
import Layout from '../views/layout/Layout'

/**
* hidden: true                   if `hidden:true` will not show in the sidebar(default is false)
* alwaysShow: true               if set true, will always show the root menu, whatever its child routes length
*                                if not set alwaysShow, only more than one route under the children
*                                it will becomes nested mode, otherwise not show the root menu
* redirect: noredirect           if `redirect:noredirect` will no redirect in the breadcrumb
* name:'router-name'             the name is used by <keep-alive> (must set!!!)
* meta : {
    title: 'title'               the name show in submenu and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar,
  }
**/
export const constantRouterMap = [
  { path: '/login', component: () => import('@/views/login/index'), hidden: true },
  { path: '/404', component: () => import('@/views/404'), hidden: true },
  { path: '/publishArticle', component: () => import('@/views/article/publish'), hidden: true },

  {
    path: '/',
    component: Layout,
    redirect: '/home',
    name: 'home',
    hidden: true,
    meta: { title: '首页', noCache: true },
    children: [{
      path: 'home',
      component: () => import('@/views/dashboard/index'),
    }]
  },

  {
    path: '/system',
    component: Layout,
    redirect: '/system/',
    name: 'system',
    meta: { title: '系统管理', icon: 'system'},
    children: [
      {
        path: 'roles',
        name: 'roles',
        component: () => import('@/views/system/role'),
        meta: { title: '角色管理', icon: 'role'}
      },
      {
        path: 'permission',
        name: 'permission',
        component: () => import('@/views/system/permission'),
        meta: { title: '权限管理', icon: 'permission'}
      },
      {
        path: '/system/user',
        name: 'systemUser',
        component: () => import('@/views/system/user'),
        meta: { title: '系统用户', icon: 'user'}
      }
    ]
  },

  {
    path: '/article',
    component: Layout,
    redirect: '/article/list',
    name: '文章管理',
    meta: { title: '文章管理', icon: 'form' },
    children: [
      {
        path: 'list',
        name: 'list',
        component: () => import('@/views/article/list'),
        meta: { title: '文章列表', icon: 'list' }
      },
      {
        path: 'schedule',
        name: 'schedule',
        component: () => import('@/views/article/schedule'),
        meta: { title: '待发布文章', icon: 'article_publish' }
      },
      // {
      //   path: 'toPublish',
      //   name: 'toPublish',
      //   redirect: '/article/publish',
      //   // component: () => import('@/views/article/publish'),
      //   meta: { title: '发布文章', icon: 'publish' }
      // },
      // 文章详细页
      {
        // path: 'detail/:id',
        path: 'detail',
        name: 'detail',
        component: () => import('@/views/article/detail'),
        meta: { title: '文章详细内容' },
        hidden: true
      },
      {
        path: 'draft',
        name: 'draft',
        component: () => import('@/views/article/draft'),
        meta: { title: '草稿箱', icon: 'draft' },
      },
    ]
  },

  {
    path: '/category',
    component: Layout,
    children: [
      {
        path: 'list',
        name: 'categoryList',
        component: () => import('@/views/category'),
        meta: { title: '分类管理', icon: 'category' }
      }
    ]
  },

  {
    path: '/timeline',
    component: Layout,
    redirect: '/timeline/list',
    name: 'timeline',
    // meta: { title: '时间线', icon: 'time' },
    children: [
      {
        path: 'list',
        name: 'timelineList',
        component: () => import('@/views/timeline'),
        meta: { title: '时间线管理', icon: 'time' }
      },
      {
        path: 'save',
        name: 'save',
        component: () => import('@/views/timeline/save'),
        meta: { title: '添加', icon: 'add' },
        hidden: true
      },
      {
        path: 'edit/:id',
        name: 'edit',
        component: () => import('@/views/timeline/save'),
        meta: { title: '编辑', noCache: true },
        hidden: true //隐式路由
      }
    ]
  },

  {
    path: '/feedback',
    component: Layout,
    redirect: '/feedback/problem',
    name: 'feedback',
    meta: { title: '反馈管理', icon: 'feedback' },
    children: [
      {
        path: 'problem',
        name: 'problem',
        component: () => import('@/views/feedback/problem'),
        meta: { title: '问题反馈', icon: 'problem' }
      },
      {
        path: 'suggest',
        name: 'suggest',
        component: () => import('@/views/feedback/suggest'),
        meta: { title: '建议反馈', icon: 'suggest' }
      }
    ]
  },

  {
    path: '/user',
    name: 'user',
    redirect: '/user/list',
    component: Layout,
    meta: { title: '用户管理', icon: 'user' },
    children: [
      {
        path: '/list',
        name: 'userList',
        component: () => import('@/views/user'),
        meta: { title: '用户列表', icon: 'list' }
      },{
        path: '/operation',
        name: 'operation',
        component: () => import('@/views/user/operation'),
        meta: { title: '用户操作', icon: 'user_operation' }
      }
    ]
  },

  {
    path: '/message',
    component: Layout,
    children: [{
      path: 'list',
      name: 'messageList',
      component: () => import('@/views/message'),
      meta: { title: '留言管理', icon: 'message' },
    }]
  },

  {
    path: '/friendlink',
    component: Layout,
    children: [{
      path: 'list',
      name: 'friendlinkList',
      component: () => import('@/views/friendlink'),
      meta: { title: '友链管理', icon: 'friend' }
    }]
  },

  {
    path: '/notice',
    name: 'notice',
    redirect: '/notice/renew',
    component: Layout,
    meta: { title: '公告栏', icon: 'notice' },
    children: [
      {
        path: '/renew',
        name: 'renew',
        component: () => import('@/views/notice/renew'),
        meta: { title: '更新日志', icon: 'renew' }
      },{
        path: '/notice/article',
        name: 'article',
        component: () => import('@/views/notice/article'),
        meta: { title: '文章通知', icon: 'article' }
      }
    ]
  },

  {
    path: 'external-link',
    component: Layout,
    hidden: true,
    children: [
      {
        path: 'https://panjiachen.github.io/vue-element-admin-site/#/',
        meta: { title: '博客日志', icon: 'log' }
      }
    ]
  },
  // { path: '/404', component: () => import('@/views/404'), hidden: true },

  { path: '*', redirect: '/404', hidden: true }
]

export default new Router({
  // mode: 'hash', //后端支持可开
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRouterMap
})
