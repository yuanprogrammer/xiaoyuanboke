import { login, logout, getInfo } from '@/api/login'
import { getToken, setToken, removeToken } from '@/utils/auth'

const user = {
  state: {
    token: getToken(),
    name: '',
    avatar: '',
    sysUserCode: '',
    roles: []
  },

  mutations: {
    SET_TOKEN: (state, token) => {
      state.token = token
    },
    SET_NAME: (state, name) => {
      state.name = name
    },
    SET_AVATAR: (state, avatar) => {
      state.avatar = avatar
    },
    SET_USER_CODE: (state, sysUserCode) => {
      state.sysUserCode = sysUserCode
    },
    SET_ROLES: (state, roles) => {
      state.roles = roles
    }
  },

  actions: {
    // 登录
    Login({ commit }, userInfo) {
      const username = userInfo.username.trim()
      return new Promise((resolve, reject) => {
        login(username, userInfo.password).then(response => {
          const data = response.data
          setToken(data.token)
          commit('SET_TOKEN', data.token)
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
      // const data = { token: "admin" }
      // setToken(data.token)
      // commit("SET_TOKEN", data.token)
    },

    // 获取用户信息
    GetInfo({ commit, state }) {
      return new Promise((resolve, reject) => {
        getInfo().then(response => {
          const data = response.data
          if (data.roles && data.roles.length > 0) { // 验证返回的roles是否是一个非空数组
            let roles = []
            for (let i = 0; i < data.roles.length; i++) {
              roles.push(data.roles[i].roleName)
            }
            commit('SET_ROLES', roles)
          }
          commit('SET_NAME', data.nickname)
          commit('SET_AVATAR', data.avatar)
          commit('SET_USER_CODE', data.sysUserCode)
          localStorage.setItem("permissions", data.permissions)
          resolve(response)
        }).catch(error => {
          reject(error)
        })
      })
      // const data = {
      //   roles: "admin",
      //   name: "admin",
      //   avatar:
      //     "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif"
      // };
      // if (data.roles && data.roles.length > 0) {
      //   // 验证返回的roles是否是一个非空数组
      //   commit("SET_ROLES", data.roles)
      // } else {
      //   reject("getInfo: roles must be a non-null array !")
      // }
      // commit("SET_NAME", data.name)
      // commit("SET_AVATAR", data.avatar)
    },

    // 登出
    LogOut({ commit, state }) {
      return new Promise((resolve, reject) => {
        logout(state.token).then(() => {
          commit('SET_TOKEN', '')
          commit('SET_ROLES', [])
          removeToken()
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
      // commit("SET_TOKEN", "")
      // commit("SET_ROLES", [])
      // removeToken()
    },

    // 前端 登出
    FedLogOut({ commit }) {
      return new Promise(resolve => {
        commit('SET_TOKEN', '')
        removeToken()
        resolve()
      })
      // commit("SET_TOKEN", "")
      // removeToken()
    }
  }
}

export default user
