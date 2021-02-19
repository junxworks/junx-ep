CREATE TABLE `s_file_thumb` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `file_id` varchar(50) NOT NULL,
  `width` int(10) NOT NULL,
  `height` int(10) NOT NULL,
  `file_size` bigint(20) NOT NULL COMMENT '文件大小',
  `file_ext` varchar(20) DEFAULT NULL COMMENT '文件扩展名',
  `storage_id` varchar(100) NOT NULL COMMENT '文件存储id，通过这个id取获取',
  `storage_driver` varchar(100) NOT NULL COMMENT '存储驱动名，后台程序记录，可用于问题分析',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `fwh` (`file_id`,`width`,`height`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='缩略图表 系统文件';