create table  `s_role_menu`
(
       `id`              bigint(20) auto_increment primary key not null comment '编号',
       `role_id`          bigint(20) comment '角色编号',
       `menu_id`          bigint(20) comment '菜单编号'
);
alter table `s_role_menu` comment= '角色菜单关联表';