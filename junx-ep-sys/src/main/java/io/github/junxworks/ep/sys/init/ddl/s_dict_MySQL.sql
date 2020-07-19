create table  `s_dict`
(
       `id`              bigint(20) auto_increment primary key not null comment '编号',
       `parentCode`      VARCHAR(100) comment '父编码',
       `dataCode`        VARCHAR(100) comment '数据项编码',
       `dataLabel`       VARCHAR(200) comment '数据项名称',
       `sort`            INT comment '排序',
       `remark`          VARCHAR(200) comment '备注',
       `status`          tinyint comment '状态 -1已删除 0正常',
       `creatorId`       bigint(20) comment '创建人编号',
       `createDate`      DATETIME comment '创建日期',
       `modifierId`      bigint(20) comment '修改人编号',
       `modifyDate`      DATETIME comment '修改日期'
);
alter table `s_dict` comment= '数据字典';