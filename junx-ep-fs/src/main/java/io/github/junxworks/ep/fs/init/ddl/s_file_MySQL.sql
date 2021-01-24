CREATE TABLE `s_file` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `fileName` varchar(100) NOT NULL COMMENT '上传文件名，可人为指定',
  `fileGroup` varchar(50) NOT NULL DEFAULT 'default' COMMENT '文件分组，默认为default',
  `orgNo` varchar(50) NOT NULL COMMENT '组织编号，由于文件服务是公用的，加上组织编号可以区分文件是属于哪个组织',
  `fileSize` int(11) NOT NULL COMMENT '文件大小',
  `fileExt` varchar(20) DEFAULT NULL COMMENT '文件扩展名',
  `storageId` varchar(100) NOT NULL COMMENT '文件存储id，通过这个id取获取',
  `oraginalName` varchar(300) NOT NULL COMMENT '文件原名',
  `storageDriver` varchar(100) NOT NULL COMMENT '存储驱动名，后台程序记录，可用于问题分析',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11416 DEFAULT CHARSET=utf8 COMMENT='系统文件';