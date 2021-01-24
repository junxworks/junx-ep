create table  `s_dict`
(
       `id`              bigint(20) auto_increment primary key not null comment '编号',
       `parentCode`      VARCHAR(100) comment '父编码',
       `dataCode`        VARCHAR(100) comment '数据项编码',
       `dataLabel`       VARCHAR(200) comment '数据项名称',
       `sort`            INT comment '排序',
       `remark`          VARCHAR(200) comment '备注',
       `status`          tinyint comment '状态 -1已删除 0正常',
	  `createUser`  bigint(20) DEFAULT NULL comment '创建人编号',
	  `createTime`  DATETIME DEFAULT NULL comment '创建日期',
	  `updateUser`  bigint(20) DEFAULT NULL comment '修改人编号',
	  `updateTime`  DATETIME DEFAULT NULL comment '修改日期'
);
alter table `s_dict` comment= '数据字典';