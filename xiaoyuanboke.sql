/*
 Navicat Premium Data Transfer

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 80013
 Source Host           : localhost:3306
 Source Schema         : xiaoyuanboke

 Target Server Type    : MySQL
 Target Server Version : 80013
 File Encoding         : 65001

 Date: 30/11/2022 18:18:46
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_action
-- ----------------------------
DROP TABLE IF EXISTS `sys_action`;
CREATE TABLE `sys_action`  (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `act_key` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '操作Key',
  `act_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '操作名',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_action
-- ----------------------------
INSERT INTO `sys_action` VALUES (1, 'INSERT', '添加');
INSERT INTO `sys_action` VALUES (2, 'DELETE', '删除');
INSERT INTO `sys_action` VALUES (3, 'UPDATE', '修改');
INSERT INTO `sys_action` VALUES (4, 'SELECT', '查询');
INSERT INTO `sys_action` VALUES (5, 'PUBLISH', '发布');
INSERT INTO `sys_action` VALUES (6, 'DISABLE', '禁用');
INSERT INTO `sys_action` VALUES (7, 'NOTICE', '通知');

-- ----------------------------
-- Table structure for sys_module
-- ----------------------------
DROP TABLE IF EXISTS `sys_module`;
CREATE TABLE `sys_module`  (
  `id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `mod_key` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '模块Key',
  `mod_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '模块名',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '模块表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_module
-- ----------------------------
INSERT INTO `sys_module` VALUES (1, 'USER', '用户管理');
INSERT INTO `sys_module` VALUES (2, 'ARTICLE', '文章管理');
INSERT INTO `sys_module` VALUES (3, 'TIMELINE', '时间线管理');
INSERT INTO `sys_module` VALUES (4, 'PERMISSION', '权限管理');
INSERT INTO `sys_module` VALUES (5, 'CATEGORY', '分类管理');
INSERT INTO `sys_module` VALUES (6, 'PROBLEM', '问题反馈');
INSERT INTO `sys_module` VALUES (7, 'SUGGEST', '建议反馈');
INSERT INTO `sys_module` VALUES (8, 'MESSAGE', '留言管理');
INSERT INTO `sys_module` VALUES (9, 'FRIEND', '友链管理');
INSERT INTO `sys_module` VALUES (10, 'ROLE', '角色管理');

-- ----------------------------
-- Table structure for sys_notice
-- ----------------------------
DROP TABLE IF EXISTS `sys_notice`;
CREATE TABLE `sys_notice`  (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `sender_code` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '发送方编号',
  `receiver_code` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '接收方编号',
  `title` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '标题',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '内容',
  `read_time` datetime(0) NULL DEFAULT NULL COMMENT '已读时间',
  `notice_time` datetime(0) NOT NULL COMMENT '通知时间',
  `notice_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '通知类型(1-评论,2-回复,3-点赞,4-收藏)',
  `state` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '通知状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission`  (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `module_id` bigint(10) NOT NULL COMMENT '模块ID',
  `action_id` bigint(10) NOT NULL COMMENT '操作ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 44 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES (1, 1, 1);
INSERT INTO `sys_permission` VALUES (2, 1, 2);
INSERT INTO `sys_permission` VALUES (3, 1, 3);
INSERT INTO `sys_permission` VALUES (4, 1, 4);
INSERT INTO `sys_permission` VALUES (5, 2, 1);
INSERT INTO `sys_permission` VALUES (6, 2, 2);
INSERT INTO `sys_permission` VALUES (7, 2, 3);
INSERT INTO `sys_permission` VALUES (8, 2, 4);
INSERT INTO `sys_permission` VALUES (9, 2, 5);
INSERT INTO `sys_permission` VALUES (10, 3, 1);
INSERT INTO `sys_permission` VALUES (11, 3, 2);
INSERT INTO `sys_permission` VALUES (12, 3, 3);
INSERT INTO `sys_permission` VALUES (13, 3, 4);
INSERT INTO `sys_permission` VALUES (14, 4, 1);
INSERT INTO `sys_permission` VALUES (15, 4, 2);
INSERT INTO `sys_permission` VALUES (16, 4, 3);
INSERT INTO `sys_permission` VALUES (17, 4, 4);
INSERT INTO `sys_permission` VALUES (18, 4, 6);
INSERT INTO `sys_permission` VALUES (19, 4, 7);
INSERT INTO `sys_permission` VALUES (20, 5, 1);
INSERT INTO `sys_permission` VALUES (21, 5, 2);
INSERT INTO `sys_permission` VALUES (22, 5, 3);
INSERT INTO `sys_permission` VALUES (23, 5, 4);
INSERT INTO `sys_permission` VALUES (24, 6, 1);
INSERT INTO `sys_permission` VALUES (25, 6, 2);
INSERT INTO `sys_permission` VALUES (26, 6, 3);
INSERT INTO `sys_permission` VALUES (27, 6, 4);
INSERT INTO `sys_permission` VALUES (28, 6, 7);
INSERT INTO `sys_permission` VALUES (29, 7, 1);
INSERT INTO `sys_permission` VALUES (30, 7, 2);
INSERT INTO `sys_permission` VALUES (31, 7, 3);
INSERT INTO `sys_permission` VALUES (32, 7, 4);
INSERT INTO `sys_permission` VALUES (33, 8, 1);
INSERT INTO `sys_permission` VALUES (34, 8, 2);
INSERT INTO `sys_permission` VALUES (35, 8, 3);
INSERT INTO `sys_permission` VALUES (36, 8, 4);
INSERT INTO `sys_permission` VALUES (37, 9, 1);
INSERT INTO `sys_permission` VALUES (38, 9, 2);
INSERT INTO `sys_permission` VALUES (39, 9, 3);
INSERT INTO `sys_permission` VALUES (40, 9, 4);
INSERT INTO `sys_permission` VALUES (41, 10, 1);
INSERT INTO `sys_permission` VALUES (42, 10, 2);
INSERT INTO `sys_permission` VALUES (43, 10, 3);
INSERT INTO `sys_permission` VALUES (44, 10, 4);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` tinyint(5) NOT NULL AUTO_INCREMENT,
  `role_key` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色标识',
  `role_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `permissions` json NULL COMMENT '权限集合',
  `describe` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, 'VISITOR', '游客', '[8, 13, 17, 23, 27, 32, 36, 40, 44]', '游客只有查看权限');
INSERT INTO `sys_role` VALUES (2, 'ADMIN', '管理员', '[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44]', NULL);
INSERT INTO `sys_role` VALUES (6, 'AUTHOR', '笔者', '[5, 6, 7, 8, 9, 13, 17, 23, 27, 32, 36, 37, 38, 39, 40, 44]', '除基本的游客权限外，还拥有文章相关的权限');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` tinyint(5) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `open_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '微信ID',
  `role_id` json NOT NULL COMMENT '角色ID',
  `sys_user_code` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '系统用户编号',
  `username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `nickname` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `avatar` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像',
  `sex` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '性别',
  `state` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '状态(0-无事,1-冻结)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, NULL, '[2]', 'sysUser:8cb3150a-cf27-4748-ae4f-e58ba3faa7c9', 'root', 'b9be11166d72e9e3ae7fd407165e4bd2', '管理员', 'https://user.xiaoyuan-boke.com/xiaoyuan-avatar.jpg', '0', '0');
INSERT INTO `sys_user` VALUES (2, NULL, '[1]', 'sysUser:5296e57a-9e96-4bf9-ba74-903b01ff89f7', 'visitor', 'dd1a0f5091fc4096e1ce3a99e2d6e468', '游客', 'https://user.xiaoyuan-boke.com/xiaoyuan-avatar.jpg', '0', '0');
INSERT INTO `sys_user` VALUES (3, NULL, '[6]', 'sysUser:f634555e-a72b-4dc8-a420-36e2d3060c38', 'author', '9b0df5635ad72060bb548e118a910506', '笔者', 'https://user.xiaoyuan-boke.com/xiaoyuan-avatar.jpg', '0', '0');

-- ----------------------------
-- Table structure for xy_article
-- ----------------------------
DROP TABLE IF EXISTS `xy_article`;
CREATE TABLE `xy_article`  (
  `id` bigint(20) NOT NULL COMMENT '文章ID',
  `author_id` bigint(20) NOT NULL COMMENT '作者ID',
  `title` varchar(81) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文章标题',
  `cover` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文章封面',
  `digest` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文章摘要',
  `tags` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文章标签',
  `deleted` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '逻辑删除（0->未删除, 1->逻辑删除）',
  `view_count` int(11) NOT NULL COMMENT '文章访问量',
  `gmt_create` datetime(0) NOT NULL COMMENT '文章发布时间',
  `gmt_update` datetime(0) NOT NULL COMMENT '文章修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for xy_article_category
-- ----------------------------
DROP TABLE IF EXISTS `xy_article_category`;
CREATE TABLE `xy_article_category`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `article_id` bigint(20) NOT NULL COMMENT '文章编号',
  `category_id` int(11) NOT NULL COMMENT '分类编号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for xy_article_collect
-- ----------------------------
DROP TABLE IF EXISTS `xy_article_collect`;
CREATE TABLE `xy_article_collect`  (
  `id` bigint(20) NOT NULL COMMENT '编号',
  `user_id` bigint(20) NOT NULL COMMENT '收藏用户',
  `article_id` bigint(20) NOT NULL COMMENT '收藏的文章',
  `gmt_create` datetime(0) NOT NULL COMMENT '收藏时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for xy_article_comment
-- ----------------------------
DROP TABLE IF EXISTS `xy_article_comment`;
CREATE TABLE `xy_article_comment`  (
  `id` bigint(20) NOT NULL COMMENT '评论编号',
  `user_id` bigint(20) NOT NULL COMMENT '用户编号',
  `article_id` bigint(20) NOT NULL COMMENT '文章编号',
  `parent_id` bigint(20) NULL DEFAULT NULL COMMENT '父级评论编号',
  `content` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '评论内容',
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '评论时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for xy_article_content
-- ----------------------------
DROP TABLE IF EXISTS `xy_article_content`;
CREATE TABLE `xy_article_content`  (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `article_id` bigint(20) NOT NULL COMMENT '文章ID',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '文章内容（普通内容文本格式）',
  `content_html` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '文章内容（markdown文本格式）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for xy_article_like
-- ----------------------------
DROP TABLE IF EXISTS `xy_article_like`;
CREATE TABLE `xy_article_like`  (
  `id` bigint(20) NOT NULL COMMENT '编号',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '点赞的用户',
  `article_id` bigint(20) NULL DEFAULT NULL COMMENT '点赞的文章',
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '点赞时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for xy_category
-- ----------------------------
DROP TABLE IF EXISTS `xy_category`;
CREATE TABLE `xy_category`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '分类栏目ID',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分类栏目名称',
  `parent_id` int(11) NOT NULL COMMENT '父级分类的ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 54 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for xy_common_user
-- ----------------------------
DROP TABLE IF EXISTS `xy_common_user`;
CREATE TABLE `xy_common_user`  (
  `id` bigint(20) NOT NULL COMMENT '普通用户ID',
  `com_user_code` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户编号',
  `username` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '加密后的密码',
  `nickname` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `email` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `mobile_number` char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号码',
  `wechat_number` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '微信号',
  `avatar` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户头像',
  `salt` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '加密盐',
  `gmt_create` datetime(0) NOT NULL COMMENT '注册时间',
  `gmt_login` datetime(0) NULL DEFAULT NULL COMMENT '登陆时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for xy_friend_link
-- ----------------------------
DROP TABLE IF EXISTS `xy_friend_link`;
CREATE TABLE `xy_friend_link`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `nickname` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `field` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '领域',
  `describe` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '简单描述',
  `link` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '友情链接',
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for xy_front_log
-- ----------------------------
DROP TABLE IF EXISTS `xy_front_log`;
CREATE TABLE `xy_front_log`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ip` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'IP',
  `url` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求地址',
  `httpMethod` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求方法',
  `classMethod` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类方法',
  `timeCost` int(11) NULL DEFAULT NULL COMMENT '接口耗时',
  `os` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作系统',
  `browser` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '浏览器',
  `userAgent` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '浏览器标识',
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 783 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for xy_home_message
-- ----------------------------
DROP TABLE IF EXISTS `xy_home_message`;
CREATE TABLE `xy_home_message`  (
  `id` bigint(20) NOT NULL COMMENT '主页留言编号',
  `author_id` bigint(20) NULL DEFAULT NULL COMMENT '留言人',
  `content` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '简单描述（正面）',
  `detail_content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '详细内容（背面）',
  `background` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '背景图（随机生成）',
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '留言时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for xy_job
-- ----------------------------
DROP TABLE IF EXISTS `xy_job`;
CREATE TABLE `xy_job`  (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `job_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '任务名称',
  `job_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '任务标识',
  `job_detail_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '作业详细ID',
  `trigger_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '触发器ID',
  `run_count` int(5) NULL DEFAULT NULL COMMENT '执行次数',
  `job_state` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '任务状态',
  `start_time` datetime(0) NULL DEFAULT NULL COMMENT '任务开始时间',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '任务结束时间',
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_update` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for xy_problem_feedback
-- ----------------------------
DROP TABLE IF EXISTS `xy_problem_feedback`;
CREATE TABLE `xy_problem_feedback`  (
  `id` bigint(20) NOT NULL,
  `email` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `problem` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '问题',
  `problem_state` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '问题状态（0-未解决，1-处理中，2-已解决）',
  `notice_state` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '通知状态（0-未通知，1-已通知）',
  `gmt_create` date NOT NULL COMMENT '反馈时间',
  `gmt_update` date NULL DEFAULT NULL COMMENT '处理时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for xy_suggest_feedback
-- ----------------------------
DROP TABLE IF EXISTS `xy_suggest_feedback`;
CREATE TABLE `xy_suggest_feedback`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '建议反馈人',
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '建议反馈内容',
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '反馈时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 31 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for xy_time_line
-- ----------------------------
DROP TABLE IF EXISTS `xy_time_line`;
CREATE TABLE `xy_time_line`  (
  `id` bigint(20) NOT NULL COMMENT '编号',
  `title` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标题',
  `describe` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '正文',
  `start_time` date NULL DEFAULT NULL COMMENT '开始时间',
  `end_time` date NULL DEFAULT NULL COMMENT '结束时间',
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for xy_user_operation
-- ----------------------------
DROP TABLE IF EXISTS `xy_user_operation`;
CREATE TABLE `xy_user_operation`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户编号',
  `describe` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户描述',
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 141 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
