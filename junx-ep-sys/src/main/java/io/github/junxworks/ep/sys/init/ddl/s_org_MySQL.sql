CREATE TABLE `s_org` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `createUser`  bigint(20) DEFAULT NULL comment '创建人编号',
  `createTime`  DATETIME DEFAULT NULL comment '创建日期',
  `updateUser`  bigint(20) DEFAULT NULL comment '修改人编号',
  `updateTime`  DATETIME DEFAULT NULL comment '修改日期',
  `status` tinyint(2) DEFAULT NULL COMMENT '状态 -1已删除 0正常',
  `orgNo` varchar(50) DEFAULT NULL COMMENT '组织编码',
  `orgName` varchar(100) DEFAULT NULL COMMENT '组织名称',
  `orgType` varchar(20) DEFAULT NULL COMMENT '机构类型 参考码表机构类型 orgType',
  `parentNo` varchar(50) DEFAULT NULL COMMENT '直接上级机构编码',
  `topLevelNo` varchar(50) DEFAULT NULL COMMENT '顶级机构编码',
  `orgPath` varchar(200) DEFAULT NULL COMMENT '组织路径',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='组织机构';