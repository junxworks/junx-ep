/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  LocalFSConfig.java   
 * @Package io.github.junxworks.ep.fs.driver   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2020-8-2 13:58:55   
 * @version V1.0 
 * @Copyright: 2020 Junxworks. All rights reserved. 
 * 注意：
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.fs.driver;

/**
 * 本地文件系统存储的配置
 *
 * @ClassName:  LocalFileSystemStorage
 * @author: 王兴
 * @date:   2019-1-6 11:36:13
 * @since:  v1.0
 */
public class LocalFSConfig {

	/** 文件存储的路径. */
	private String dataDir = "/tmp/ep-fs";

	public String getDataDir() {
		return dataDir;
	}

	public void setDataDir(String dataDir) {
		this.dataDir = dataDir;
	}

}
