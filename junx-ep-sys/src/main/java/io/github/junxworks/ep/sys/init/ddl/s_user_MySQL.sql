create table  `s_user`
(
       `id`              bigint(20) auto_increment primary key not null comment '用户编号',
       `name`            VARCHAR(50) default 0 not null comment '用户姓名',
       `username`        VARCHAR(50) comment '账号',
       `password`        VARCHAR(100) comment '登录密码',
       `mobile`          VARCHAR(11) comment '手机号码',
       `id_card`          VARCHAR(20) comment '身份证号码',
       `email`           VARCHAR(30) comment '邮箱',
       `org_no`           VARCHAR(50) comment '组织编码',
	  `create_user`  bigint(20) DEFAULT NULL comment '创建人编号',
	  `create_time`  DATETIME DEFAULT NULL comment '创建日期',
	  `update_user`  bigint(20) DEFAULT NULL comment '修改人编号',
	  `update_time`  DATETIME DEFAULT NULL comment '修改日期',
       `status`          tinyint comment '状态 0：正常，1：冻结，2：锁定，3：离职 '
);
alter table `s_user` comment= '用户信息表';