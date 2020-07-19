create table  `s_sqls`
(
       `id`              bigint(20) auto_increment primary key not null comment '编号',
       `creatorId`       bigint(20) comment '创建人编号',
       `createDate`      DATETIME comment '创建日期',
       `modifierId`      bigint(20) comment '修改人编号',
       `modifyDate`      DATETIME comment '修改日期',
       `status`          tinyint comment '状态 -1已删除 0正常',
       `uid`             VARCHAR(200) not null comment 'sql的唯一ID',
       `sqlContent`      text comment 'sql内容',
       `description`     VARCHAR(200) comment '描述',
       `category`        VARCHAR(200) default 'default' comment '所属分类'
);
create unique index `IDU_s_sqls_uid` on `s_sqls`(`uid`);
alter table `s_sqls` comment= 'SQL配置表';