<h1 align="center"><a href="https://github.com/xiaoyuanboke" target="_blank">小袁博客平台</a></h1>
<p align="center">
  <a href="https://blog.csdn.net/weixin_47971206?spm=1000.2115.3001.5343"><img alt="author" src="https://user.xiaoyuan-boke.com/svg/author-xiaoyuan.svg"/></a>
  <a href="https://www.oracle.com/technetwork/java/javase/downloads/index.html"><img alt="JDK" src="https://img.shields.io/badge/jdk1.8.0_131-orange.svg"/></a>
  <a href="https://docs.spring.io/spring-boot/docs/2.2.1.RELEASE/reference/html/"><img alt="Spring Boot" src="https://img.shields.io/badge/Spring Boot-2.1.0.RELEASE-brightgreen.svg"/></a>
  <a href="https://github.com/yuanprogrammer/xiaoyuanboke/blob/master/LICENSE"><img alt="LICENSE" src="https://img.shields.io/github/license/yuanprogrammer/xiaoyuanboke.svg"/></a>
</p>

<p align="center">
  <a href="https://github.com/yuanprogrammer/xiaoyuanboke/stargazers"><img alt="star" src="https://img.shields.io/github/stars/yuanprogrammer/xiaoyuanboke.svg?label=Stars&style=social"/></a>
  <a href="https://github.com/yuanprogrammer/xiaoyuanboke/network/members"><img alt="star" src="https://img.shields.io/github/forks/yuanprogrammer/xiaoyuanboke.svg?label=Fork&style=social"/></a>
  <a href="https://github.com/yuanprogrammer/xiaoyuanboke/watchers"><img alt="star" src="https://img.shields.io/github/watchers/yuanprogrammer/xiaoyuanboke.svg?label=Watch&style=social"/></a>
</p>

## 小袁个人博客平台
个人博客平台（原创设计）开源，包括前台和后台管理，文章浏览，点赞，收藏，评论，搜索，问题反馈和建议反馈，文章档案，时间线，文章分类列表，博客留言，友链介绍，用户登录，用户注册（普通用户名注册，邮箱注册，手机号码注册），修改密码，忘记密码，个人信息修改，邮箱号码激活，音乐小插件听歌，后台可以有文章管理（发布编辑等）、文章分类管理、时间线管理、留言管理、用户管理、问题和建议反馈管理等


## 更新日志
#### V2.0
（1）更换了架构，采用微服务技术，引入eureka注册中心(下次换成nacos)，以及其他的openfeign/rabbitmq/websocket/quartz等技术<br>
（2）模块重构，简洁清晰结构分明，实体类分离，第三方服务分离，利用openfeign跨模块调用，减少模块间的频繁注入<br>
（3）后台系统增加了登录（可以微信登录），增加了权限/角色等功能，微信登录的用户默认注册为笔者账号(拥有文章相关权限)<br>
（4）增加了定时发布/评论通知/收藏通知/点赞通知等功能，完善后台系统的全部页面缺少的操作（查询、删除、修改、增加）<br>
（5）增加了实时聊天功能，支持发送离线消息，用户上线即可接受消息

## 文档
#### 启动文档地址：
#### 开发文档地址：

## 项目体验地址：
// https://www.xiaoyuan-boke.com (前台) <br>
// https://www.xiaoyuan-boke.com/back-manager (后台管理)<br>
域名重新备案中...
http://180.76.158.195/ （前台）<br>
http://180.76.158.195/back-manager （后台）<br>

## 后端工程结构

![未命名文件](https://user-images.githubusercontent.com/86464456/201698374-badcbf7c-9625-48ff-9322-7672727e5c2e.png)

### 后端开源地址：
https://github.com/yuanprogrammer/xiaoyuanboke

### 前端开源地址：
https://github.com/yuanprogrammer/xiaoyuanboke-vue

## 后台管理Vue项目结构

![image](https://user-images.githubusercontent.com/86464456/176443195-5f728111-1700-4be2-bab4-abb1c41ec4ff.png)


## 代码相关运行截图 （部分）
### 前台

#### 文章列表首页
![image](https://user-images.githubusercontent.com/86464456/172376841-a64ae35e-2acf-41fa-989d-96a31cc0895f.png)

#### 文章详细页
![image](https://user-images.githubusercontent.com/86464456/172376941-986b59ba-cdfe-41a1-bae7-73d18e0bd13f.png)

#### 时间线
![image](https://user-images.githubusercontent.com/86464456/172377042-57aa24da-40bd-43bd-b78a-f790b7b0574d.png)

#### 留言板
![image](https://user-images.githubusercontent.com/86464456/172377122-660a38a8-8ee0-4ce5-acbb-394762a7a3a8.png)

### 后台管理

#### 实时聊天
![bc110fd247f0b02bc8fe0f1ece9ea7e](https://user-images.githubusercontent.com/86464456/204419293-af442fe9-7636-494b-afc9-0a42591280ee.jpg)


#### 文章列表
![image](https://user-images.githubusercontent.com/86464456/172377815-aa19c8ae-4d90-49f9-a852-975195f4a126.png)

#### 文章发布
![image](https://user-images.githubusercontent.com/86464456/172377861-ce433f80-a4f3-475c-9cba-04a1a641e09d.png)

#### 分类管理
![image](https://user-images.githubusercontent.com/86464456/172377959-9b33682f-7c0c-4db3-a047-3d74abe0c1d4.png)

#### 用户管理
![image](https://user-images.githubusercontent.com/86464456/172378087-cda88364-91cf-4bd6-8d05-8d9cc9496d19.png)

#### 用户操作日志
![image](https://user-images.githubusercontent.com/86464456/172378029-adfaecad-b1c3-4854-9e5c-6a451993a0bc.png)


### 你的点赞鼓励，是我们前进的动力~
### 你的点赞鼓励，是我们前进的动力~
### 你的点赞鼓励，是我们前进的动力~
