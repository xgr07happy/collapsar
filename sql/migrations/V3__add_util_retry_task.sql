
-- ----------------------------
-- Table structure for util_async_task
-- ----------------------------
DROP TABLE IF EXISTS `util_retry_task`;
CREATE TABLE `util_retry_task` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `gid` char(36) NOT NULL COMMENT '全局唯一键',
  `create_time` int(11) NOT NULL COMMENT '创建时间',
  `update_time` int(11) NOT NULL COMMENT '更新时间',

	`type` int(11) NOT NULL COMMENT '任务类型：0',
	`context` varchar(2048) DEFAULT NULL COMMENT '异步任务请求参数',
  `status` int(11) NOT NULL COMMENT '任务状态：0 未知，1 成功， 2 失败， 3 服务不可用，4 请求失败，5 请求超上限',
  `next_time` int(11) DEFAULT NULL COMMENT '下次重试时间',
  `retry_num` int(11) DEFAULT NULL COMMENT '重试次数',

  PRIMARY KEY (`id`),
  KEY `idx_gid` (`gid`),
  KEY `idx_status` (`status`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='工具-重试任务表';
