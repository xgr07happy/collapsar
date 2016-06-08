
-- ----------------------------
-- Table structure for config_global
-- ----------------------------
DROP TABLE IF EXISTS `config_global`;
CREATE TABLE `config_global` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `gid` char(36) NOT NULL COMMENT '全局唯一键',
  `create_time` int(11) NOT NULL COMMENT '创建时间',
  `update_time` int(11) NOT NULL COMMENT '更新时间',
  `is_valid` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否有效: 1有效，0无效',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',

  `global_key` varchar(128) NOT NULL COMMENT '全局配置key;',
  `global_value` char(36) NOT NULL COMMENT '全局配置value',

  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_global_key` (`global_key`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='配置信息-全局配置表';

-- ----------------------------
-- Table structure for config_outer_channel
-- ----------------------------
DROP TABLE IF EXISTS `config_outer_channel`;
CREATE TABLE `config_outer_channel` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `gid` char(36) NOT NULL COMMENT '全局唯一键',
  `create_time` int(11) NOT NULL COMMENT '创建时间',
  `update_time` int(11) NOT NULL COMMENT '更新时间',
  `is_valid` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否有效: 1有效，0无效',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',

  `channel_code` int(11) NOT NULL COMMENT '渠道编码',
  `channel_name` varchar(100) NOT NULL COMMENT '渠道名称',
  `daily_max_user_cnt` int(11) NOT NULL COMMENT '允许的每日最大用户数',
  `token_expire_period` int(11) NOT NULL COMMENT 'token有时长（秒）',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_channel_code` (`channel_code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='配置信息-外部部渠道信息';
