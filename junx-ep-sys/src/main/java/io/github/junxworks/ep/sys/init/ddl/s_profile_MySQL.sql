create table  `s_profile`
(
       `id`              bigint(20) auto_increment primary key not null comment '编号',
       `name`            VARCHAR(100) comment '配置项名称',
       `value`           VARCHAR(200) comment '配置值',
       `remark`          VARCHAR(100) comment '备注'
);
alter table `s_profile` comment= '系统配置';