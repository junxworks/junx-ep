create table  `s_sql`
(
       `id`              bigint(20) auto_increment primary key not null comment '编号',
	  `createUser`  bigint(20) DEFAULT NULL comment '创建人编号',
	  `createTime`  DATETIME DEFAULT NULL comment '创建日期',
	  `updateUser`  bigint(20) DEFAULT NULL comment '修改人编号',
	  `updateTime`  DATETIME DEFAULT NULL comment '修改日期',
       `status`          tinyint comment '状态 -1已删除 0正常',
       `uid`             VARCHAR(200) not null comment 'sql的唯一ID',
       `sqlContent`      text comment 'sql内容',
       `description`     VARCHAR(200) comment '描述',
       `category`        VARCHAR(200) default 'default' comment '所属分类'
);
create unique index `IDU_s_sql_uid` on `s_sql`(`uid`);
alter table `s_sql` comment= 'SQL配置表';