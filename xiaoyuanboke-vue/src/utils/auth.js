import Cookies from 'js-cookie'

const TokenKey = 'token'

export function getToken() {
  return Cookies.get(TokenKey)
}

export function setToken(token) {
  return Cookies.set(TokenKey, token)
}

export function removeToken() {
  return Cookies.remove(TokenKey)
}

export function isAuth(permission) {
  let permissions = localStorage.getItem("permissions");
  let flag = false

  if (!permissions) {
    return flag
  }

  for (let one of permission) {
    if (permissions.includes(one)) {
      flag = true
      break;
    }
  }
  return flag;
}
