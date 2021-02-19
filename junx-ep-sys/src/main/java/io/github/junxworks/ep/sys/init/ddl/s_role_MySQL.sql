create table  `s_role`
(
       `id`              bigint(20) auto_increment primary key not null comment '角色编号',
       `role_name`        VARCHAR(20) comment '角色名称',
       `role_tag`         VARCHAR(50) comment '角色标签',
	  `create_user`  bigint(20) DEFAULT NULL comment '创建人编号',
	  `create_time`  DATETIME DEFAULT NULL comment '创建日期',
	  `update_user`  bigint(20) DEFAULT NULL comment '修改人编号',
	  `update_time`  DATETIME DEFAULT NULL comment '修改日期',
       `status`          tinyint(2) comment '状态 -1已删除 0正常'
);
alter table `s_role` comment= '角色信息表';