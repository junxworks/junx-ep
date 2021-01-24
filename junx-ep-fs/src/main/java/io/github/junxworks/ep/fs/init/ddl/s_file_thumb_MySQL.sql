CREATE TABLE `s_file_thumb` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `fileId` bigint(20) NOT NULL,
  `width` int(10) NOT NULL,
  `height` int(10) NOT NULL,
  `fileSize` bigint(20) NOT NULL COMMENT '文件大小',
  `fileExt` varchar(20) DEFAULT NULL COMMENT '文件扩展名',
  `storageId` varchar(100) NOT NULL COMMENT '文件存储id，通过这个id取获取',
  `storageDriver` varchar(100) NOT NULL COMMENT '存储驱动名，后台程序记录，可用于问题分析',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=715 DEFAULT CHARSET=utf8 COMMENT='缩略图表 系统文件';