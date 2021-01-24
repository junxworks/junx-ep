create table  `s_menu`
(
       `id`              bigint(20) auto_increment primary key not null comment '菜单编号',
       `parentId`        bigint(20) comment '上级菜单编号',
       `name`            text(64) comment '菜单名称',
       `type`            TINYINT comment '类型 0菜单 1按钮 2目录 ',
       `mark`            text(100) comment '菜单标记',
       `url`             text(500) comment '菜单链接',
       `icon`            text(50) comment '菜单图标',
       `sort`            tinyint comment '排序',
       `status`          tinyint(2) comment '状态 -1已删除 0正常',
	  `createUser`  bigint(20) DEFAULT NULL comment '创建人编号',
	  `createTime`  DATETIME DEFAULT NULL comment '创建日期',
	  `updateUser`  bigint(20) DEFAULT NULL comment '修改人编号',
	  `updateTime`  DATETIME DEFAULT NULL comment '修改日期'
);
alter table `s_menu` comment= '菜单管理';