insert into `s_menu`(`parentId`,`name`,`type`,`mark`,`url`,`icon`,`sort`,`status`,`creatorId`,`createDate`) values (1,'定时任务管理',0,'sys:schedule','/eui/modules/sys/schedule.html','fa fa-clock-o',7,0,-1,'2019-07-03 14:58:21');
INSERT INTO `s_job` (`bean_name`, `method_name`, `params`, `cron_expression`, `status`, `remark`, `create_time`) VALUES ('testTask', 'test', 'renren', '0 0/30 * * * ?', '1', '有参数测试', '2016-12-01 23:16:46');
INSERT INTO `s_job` (`bean_name`, `method_name`, `params`, `cron_expression`, `status`, `remark`, `create_time`) VALUES ('testTask', 'test2', NULL, '0 0/30 * * * ?', '1', '无参数测试', '2016-12-03 14:55:56');