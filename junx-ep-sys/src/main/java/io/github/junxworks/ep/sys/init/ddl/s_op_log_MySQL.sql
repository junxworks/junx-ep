CREATE TABLE `s_op_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `userId` bigint(20) DEFAULT NULL COMMENT '用户编号',
  `operation` varchar(100) DEFAULT NULL COMMENT '操作名称',
  `url` varchar(500) DEFAULT NULL COMMENT '日志url',
  `ip` varchar(64) DEFAULT NULL COMMENT '日志ip',
  `data` varchar(2000) DEFAULT NULL COMMENT '请求数据',
  `method` varchar(100) DEFAULT NULL COMMENT '请求方法',
  `cost` bigint(20) DEFAULT NULL COMMENT '执行耗时',
  `createTime` datetime DEFAULT NULL COMMENT '创建日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=utf8 COMMENT='系统日志表';