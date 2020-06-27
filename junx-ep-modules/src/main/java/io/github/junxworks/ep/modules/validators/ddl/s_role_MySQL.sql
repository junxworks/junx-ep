create table  `s_role`
(
       `id`              bigint(20) auto_increment primary key not null comment '角色编号',
       `roleName`        VARCHAR(20) comment '角色名称',
       `roleTag`         VARCHAR(50) comment '角色标签',
       `creatorId`       bigint(20) comment '创建人编号',
       `createDate`      DATETIME comment '创建日期',
       `modifierId`      bigint(20) comment '修改人编号',
       `modifyDate`      DATETIME comment '修改日期',
       `status`          tinyint(2) comment '状态 -1已删除 0正常'
);
alter table `s_role` comment= '角色信息表';