create table  `s_op_log`
(
       `id`              bigint(20) auto_increment primary key not null comment '编号',
       `userId`          bigint(20) comment '用户编号',
       `operation`       text(16) comment '操作名称',
       `url`             text(200) comment '日志url',
       `ip`              text(30) comment '日志ip',
       `data`            text(1000) comment '请求数据',
       `method`          text(200) comment '请求方法',
       `cost`            bigint comment '执行耗时',
       `createDate`      DATETIME comment '创建日期'
);
alter table `s_op_log` comment= '系统日志表';