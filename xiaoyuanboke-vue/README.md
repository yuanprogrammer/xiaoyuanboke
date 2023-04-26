<h1 align="center"><a href="https://github.com/xiaoyuanboke" target="_blank">小袁个人博客平台 —— Vue后台</a></h1>
<p align="center">
  <a href="https://blog.csdn.net/weixin_47971206?spm=1000.2115.3001.5343"><img alt="author" src="https://file.xiaoyuan-boke.com/project/svg/author-xiaoyuan.svg"/></a>
  <a href="https://www.oracle.com/technetwork/java/javase/downloads/index.html"><img alt="JDK" src="https://img.shields.io/badge/jdk1.8.0_131-orange.svg"/></a>
  <a href="https://docs.spring.io/spring-boot/docs/2.2.1.RELEASE/reference/html/"><img alt="Spring Boot" src="https://img.shields.io/badge/Spring Boot-2.1.0.RELEASE-brightgreen.svg"/></a>
  <a href="https://github.com/yuanprogrammer/xiaoyuanboke/blob/master/LICENSE"><img alt="LICENSE" src="https://img.shields.io/github/license/yuanprogrammer/xiaoyuanboke.svg"/></a>
</p>

<p align="center">
  <a href="https://github.com/yuanprogrammer/xiaoyuanboke-vue/stargazers"><img alt="star" src="https://img.shields.io/github/stars/yuanprogrammer/xiaoyuanboke-vue.svg?label=Stars&style=social"/></a>
  <a href="https://github.com/yuanprogrammer/xiaoyuanboke-vue/network/members"><img alt="star" src="https://img.shields.io/github/forks/yuanprogrammer/xiaoyuanboke-vue.svg?label=Fork&style=social"/></a>
  <a href="https://github.com/yuanprogrammer/xiaoyuanboke-vue/watchers"><img alt="star" src="https://img.shields.io/github/watchers/yuanprogrammer/xiaoyuanboke-vue.svg?label=Watch&style=social"/></a>
</p>

小袁博客管理平台，
- **系统管理**
  - 角色管理
    - 添加
    - 编辑权限
    - 删除
  - 权限管理
    - 添加模块
    - 编辑模块
    - 删除模块
    - 添加操作
    - 编辑操作
    - 删除操作
    - 权限查询（待完善）
    - 权限分配（待完善）
    - 权限编辑（待完善）
    - 权限删除（待完善）
  - 系统用户
    - 列表查询
    - 聊天
    - 加入黑名单
    - 编辑
    - 删除
- **文章管理**
  - 文章列表
    - 文章查询
    - 文章查看
    - 文章编辑
    - 文章删除
    - 发布文章/定时发布/支持markdown文本
  - 待发布文章（待完善）
  - 草稿箱（待完善）
- **分类管理**
  - 分类列表
    - 添加专栏
    - 编辑分类
    - 删除分类
    - 移动分类
- **时间线管理**
  - 时间线列表
    - 添加时间线
    - 查看时间线
    - 编辑
    - 删除
- **反馈管理**
  - 问题反馈
    - 列表查询
    - 编辑（问题状态）
    - 删除
    - 已解决通知
  - 建议反馈
    - 删除
- **用户管理**（只有我有权限）
  - 用户列表
    - 列表查询
    - 编辑
    - 删除
    - 加入黑名单
  - 用户操作
    - 列表查询
    - 删除
- **留言管理**
  - 留言列表
    - 一键删除
    - 删除
- **友链管理**
  - 友链列表
    - 添加
    - 编辑
    - 删除
- **公告栏（待完善）**
  - 更新日志
  - 文章通知


## 更新日志
#### V2.0
（1）更换了架构，采用微服务技术，引入eureka注册中心(下次换成nacos)，以及其他的openfeign/rabbitmq/websocket/quartz等技术<br>
（2）模块重构，简洁清晰结构分明，实体类分离，第三方服务分离，利用openfeign跨模块调用，减少模块间的频繁注入<br>
（3）后台系统增加了登录（可以微信登录），增加了权限/角色等功能，微信登录的用户默认注册为笔者账号(拥有文章相关权限)<br>
（4）增加了定时发布/评论通知/收藏通知/点赞通知等功能，完善后台系统的全部页面缺少的操作（查询、删除、修改、增加）<br>
（5）增加了实时聊天功能，支持发送离线消息，用户上线即可接受消息

## 文档
#### 启动文档地址：<br>
https://github.com/yuanprogrammer/xiaoyuanboke-vue/blob/main/startup.md

## 项目体验地址：
// https://www.xiaoyuan-boke.com (前台) <br>
// https://www.xiaoyuan-boke.com/back-manager (后台管理)<br>
域名重新备案中...<br>
http://180.76.158.195/ （前台）<br>
http://180.76.158.195/back-manager （后台）<br>

### 后端开源地址：
https://github.com/yuanprogrammer/xiaoyuanboke

### 前端开源地址：
https://github.com/yuanprogrammer/xiaoyuanboke-vue

## 项目结构
- **api（接口）**
  - *blog*
    - article.js（文章接口）
    - catrgory.js（分类接口）
    - feedback.js（反馈接口）
    - friendlink.js（友链接口
    - homeMessage.js（留言接口）
    - timeline.js（时间线接口）
  - *system*
    - permission.js（权限接口）
    - role.js（角色接口）
    - sysUSer.js（系统用户接口）
  - utils
    - qiniu.js（七牛接口）
  - login.js（登录接口）
  - user.js（用户操作接口）
- **views（页面）**
  - *article（文章相关）*
    - components（搜索组件）
    - detail（详细页）
    - draft（草稿）
    - list（列表）
    - publish（发布文章）
    - schedule（待发布列表）
  - *category（分类列表）*
  - *dashboard（首页）*
    - components（用户资料等组件）
  - *feedback（反馈管理）*
    - problem（问题反馈）
    - suggest（建议反馈）
  - *friendlink（友链列表*
  - *layout（布局）*
  - *login（登录页）*
  - *message（留言管理）*
  - *notice（通知管理）*
    - article（文章通知）
    - renew（更新日志）
  - *system（系统管理）*
    - permission（权限管理）
    - role（角色管理）
    - user（系统用户管理）
  - *timeline（时间线管理）*
    - save（添加时间线）
  - *user*
    - 用户列表
    - 用户操作列表
  - *APP.vue（入口）*
  - *main.js（主函数）*
  - *permission.js（权限拦截）*


## 运行截图

#### 登录
![image](https://user-images.githubusercontent.com/86464456/204574036-d77ef428-65d2-403a-8398-7e18d46ef2d0.png)

#### 角色管理
![image](https://user-images.githubusercontent.com/86464456/204574197-b1a4769a-2cb1-4093-b6c4-00189e0953c5.png)

#### 权限管理
![image](https://user-images.githubusercontent.com/86464456/204574274-6cfe58b6-0cdb-422c-99f9-c062ed02d1bb.png)

#### 实时聊天
![bc110fd247f0b02bc8fe0f1ece9ea7e](https://user-images.githubusercontent.com/86464456/204419293-af442fe9-7636-494b-afc9-0a42591280ee.jpg)

#### 发布文章
![image](https://user-images.githubusercontent.com/86464456/204574696-9efe8e6a-a0e5-4eaf-937f-51a91bd6411b.png)

#### 文章列表
![image](https://user-images.githubusercontent.com/86464456/204574813-ef9fc40b-9f49-4c5d-903b-b815f75d8e3a.png)

#### 时间线
![image](https://user-images.githubusercontent.com/86464456/204575025-e88fc94d-b13e-4b29-a347-2e862cea8daf.png)

#### 问题反馈
![image](https://user-images.githubusercontent.com/86464456/204575163-023e0be0-c53f-4f0e-bb57-0808bdcb8482.png)

#### 前台用户列表
![image](https://user-images.githubusercontent.com/86464456/204575542-095eb42c-f8a3-49a8-8b94-94c2084a709a.png)

#### 用户操作日志
![image](https://user-images.githubusercontent.com/86464456/172378029-adfaecad-b1c3-4854-9e5c-6a451993a0bc.png)


### 你的点赞鼓励，是我们前进的动力~
### 你的点赞鼓励，是我们前进的动力~
### 你的点赞鼓励，是我们前进的动力~
