
-- ----------------------------
-- Table structure for log_request_exception
-- ----------------------------
DROP TABLE IF EXISTS `log_request_exception`;
CREATE TABLE `log_request_exception` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `gid` char(36) NOT NULL COMMENT '全局唯一键',
  `create_time` int(11) NOT NULL COMMENT '创建时间',
  `update_time` int(11) NOT NULL COMMENT '更新时间',
  `is_valid` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否有效: 1有效，0无效',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',

  `request_url` varchar(512) NOT NULL COMMENT '请求地址',
  `post_body` varchar(2048) DEFAULT NULL COMMENT '请求参数',
  `enter_time` int(11) NOT NULL COMMENT '进入时间',
  `error_code` varchar(128) DEFAULT NULL COMMENT '返回状态',
  `message` varchar(512) DEFAULT NULL COMMENT '返回消息',
  `description` varchar(2048) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`),
  KEY `idx_create_time` (`create_time`),
  KEY `idx_request_url` (`request_url`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统日志-请求异常日志';
