create table  `s_user_role`
(
       `id`              bigint(20) auto_increment primary key not null comment '编号',
       `userId`          bigint(20) not null comment '用户编号',
       `roleId`          bigint(20) not null comment '角色编号'
);
alter table `s_user_role` comment= '用户角色关联表';