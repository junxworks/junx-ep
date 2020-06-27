create table  `s_dict`
(
       `id`              bigint(20) auto_increment primary key not null comment '编号',
       `parentCode`      VARCHAR(50) comment '父编码',
       `dataCode`        VARCHAR(50) comment '数据编码',
       `dataValue`       VARCHAR(200) comment '数据值',
       `sort`            INT comment '排序',
       `memo`            VARCHAR(4000) comment '字段描述',
       `status`          tinyint comment '状态 -1已删除 0正常',
       `creatorId`       bigint(20) comment '创建人编号',
       `createDate`      DATETIME comment '创建日期',
       `modifierId`      bigint(20) comment '修改人编号',
       `modifyDate`      DATETIME comment '修改日期'
);
alter table `s_dict` comment= '数据字典';