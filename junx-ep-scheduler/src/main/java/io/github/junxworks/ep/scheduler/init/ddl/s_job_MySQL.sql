CREATE TABLE `s_job` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务id',
  `jobName` varchar(100) DEFAULT NULL COMMENT '任务名称',
  `beanName` varchar(200) DEFAULT NULL COMMENT 'spring bean名称',
  `methodName` varchar(100) DEFAULT NULL COMMENT '方法名',
  `params` varchar(2000) DEFAULT NULL COMMENT '参数',
  `cronExpression` varchar(100) DEFAULT NULL COMMENT 'cron表达式',
  `status` tinyint(4) DEFAULT NULL COMMENT '任务状态  0：正常  1：暂停',
  `remark` varchar(255) DEFAULT NULL COMMENT '任务备注',
  `creatorId`       bigint(20) comment '创建人编号',
  `createDate`      DATETIME comment '创建日期',
  `modifierId`      bigint(20) comment '修改人编号',
  `modifyDate`      DATETIME comment '修改日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='定时任务';