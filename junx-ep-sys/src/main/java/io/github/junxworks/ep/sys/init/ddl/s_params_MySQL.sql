CREATE TABLE `s_params` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `creatorId` bigint(20) DEFAULT NULL COMMENT '创建人编号',
  `createDate` datetime DEFAULT NULL COMMENT '创建日期',
  `modifierId` bigint(20) DEFAULT NULL COMMENT '修改人编号',
  `modifyDate` datetime DEFAULT NULL COMMENT '修改日期',
  `status` tinyint(2) DEFAULT NULL COMMENT '状态 -1已删除 0正常',
  `paramName` varchar(50) DEFAULT NULL COMMENT '参数名',
  `paramValue` varchar(500) DEFAULT NULL COMMENT '参数值',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `paramGroup` varchar(50) DEFAULT 'default' COMMENT '分组',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统参数表';
