-- ----------------------------
-- Table structure for rbac_resource
-- ----------------------------
DROP TABLE IF EXISTS `rbac_resource`;
CREATE TABLE `rbac_resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `gid` char(36) NOT NULL COMMENT '全局唯一键',
  `create_time` int(11) NOT NULL COMMENT '创建时间',
  `update_time` int(11) NOT NULL COMMENT '更新时间',
  `is_valid` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否有效: 1有效，0无效',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',

  `resource_name` varchar(100) DEFAULT NULL COMMENT '资源名',
  `uri` varchar(255) DEFAULT NULL COMMENT '资源uri',
  `text` varchar(255) DEFAULT NULL COMMENT '资源名称',
  `icon` varchar(255) DEFAULT NULL COMMENT '资源图标',
  `type` int(11) DEFAULT NULL COMMENT '资源类型 1:普通连接，2:按钮资源，3:菜单资源',
  `parent` int(11) DEFAULT NULL COMMENT '上级资源ID',
  `sort_num` int(11) DEFAULT NULL COMMENT '同级序号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_resource_name` (`resource_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限管理-资源表';

-- ----------------------------
-- Table structure for rbac_user
-- ----------------------------
DROP TABLE IF EXISTS `rbac_user`;
CREATE TABLE `rbac_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `gid` char(36) NOT NULL COMMENT '全局唯一键',
  `create_time` int(11) NOT NULL COMMENT '创建时间',
  `update_time` int(11) NOT NULL COMMENT '更新时间',
  `is_valid` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否有效: 1有效，0无效',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',

  `user_name` varchar(100) DEFAULT NULL COMMENT '用户名',
  `login_passwd` varchar(100) DEFAULT NULL COMMENT '登录密码',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_name` (`user_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限管理-用户表';

-- ----------------------------
-- Table structure for rbac_user
-- ----------------------------
DROP TABLE IF EXISTS `rbac_group`;
CREATE TABLE `rbac_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `gid` char(36) NOT NULL COMMENT '全局唯一键',
  `create_time` int(11) NOT NULL COMMENT '创建时间',
  `update_time` int(11) NOT NULL COMMENT '更新时间',
  `is_valid` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否有效: 1有效，0无效',
  `remark` varchar(64) DEFAULT NULL COMMENT '备注',

  `group_name` varchar(100) DEFAULT NULL COMMENT '组名',
  `description` varchar(255) DEFAULT NULL COMMENT '描述信息',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_group_name` (`group_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限管理-工作组表';

-- ----------------------------
-- Table structure for rbac_role
-- ----------------------------
DROP TABLE IF EXISTS `rbac_role`;
CREATE TABLE `rbac_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `gid` char(36) NOT NULL COMMENT '全局唯一键',
  `create_time` int(11) NOT NULL COMMENT '创建时间',
  `update_time` int(11) NOT NULL COMMENT '更新时间',
  `is_valid` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否有效: 1有效，0无效',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',

  `role_name` varchar(100) DEFAULT NULL COMMENT '角色名',
  `description` varchar(255) DEFAULT NULL COMMENT '描述信息',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_role_name` (`role_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限管理-角色表';


-- ----------------------------
-- Table structure for rbac_role_resource
-- ----------------------------
DROP TABLE IF EXISTS `rbac_role_resource`;
CREATE TABLE `rbac_role_resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `gid` char(36) NOT NULL COMMENT '全局唯一键',
  `create_time` int(11) NOT NULL COMMENT '创建时间',
  `update_time` int(11) NOT NULL COMMENT '更新时间',
  `is_valid` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否有效: 1有效，0无效',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',

  `role_gid` char(36) DEFAULT NULL COMMENT '角色gid',
  `ressource_gid` char(36) DEFAULT NULL COMMENT '资源gid',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限管理-角色资源关联表';

-- ----------------------------
-- Table structure for rbac_user_role
-- ----------------------------
DROP TABLE IF EXISTS `rbac_user_role`;
CREATE TABLE `rbac_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `gid` char(36) NOT NULL COMMENT '全局唯一键',
  `create_time` int(11) NOT NULL COMMENT '创建时间',
  `update_time` int(11) NOT NULL COMMENT '更新时间',
  `is_valid` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否有效: 1有效，0无效',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',

  `user_gid` char(36) DEFAULT NULL COMMENT '用户gid',
  `role_gid` char(36) DEFAULT NULL COMMENT '角色gid',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限管理-用户角色关联表';

-- ----------------------------
-- Table structure for rbac_group_role
-- ----------------------------
DROP TABLE IF EXISTS `rbac_group_role`;
CREATE TABLE `rbac_group_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `gid` char(36) NOT NULL COMMENT '全局唯一键',
  `create_time` int(11) NOT NULL COMMENT '创建时间',
  `update_time` int(11) NOT NULL COMMENT '更新时间',
  `is_valid` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否有效: 1有效，0无效',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',

  `group_gid` char(36) DEFAULT NULL COMMENT '工作组gid',
  `role_gid` char(36) DEFAULT NULL COMMENT '角色gid',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限管理-工作组角色关联表';

-- ----------------------------
-- Table structure for rbac_user_group
-- ----------------------------
DROP TABLE IF EXISTS `rbac_user_group`;
CREATE TABLE `rbac_user_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `gid` char(36) NOT NULL COMMENT '全局唯一键',
  `create_time` int(11) NOT NULL COMMENT '创建时间',
  `update_time` int(11) NOT NULL COMMENT '更新时间',
  `is_valid` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否有效: 1有效，0无效',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',

  `group_gid` char(36) DEFAULT NULL COMMENT '工作组gid',
  `user_gid` char(36) DEFAULT NULL COMMENT '用户gid',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限管理-工作组用户关联表';