/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  FSConfig.java   
 * @Package io.github.junxworks.ep.fs.config   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2021-1-31 16:47:25   
 * @version V1.0 
 * @Copyright: 2021 Junxworks. All rights reserved. 
 * 注意：
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.fs.config;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.google.common.collect.Maps;

import io.github.junxworks.ep.fs.driver.AliyunOssConfig;
import io.github.junxworks.ep.fs.driver.LocalFSConfig;

/**
 * 文件存储服务的配置
 *
 * @ClassName:  FileRepositoryConfig
 * @author: Michael
 * @date:   2019-1-6 11:34:29
 * @since:  v1.0
 */
@ConfigurationProperties(prefix = "junx.ep.fs")
public class FSConfig {

	/** 文件服务器模式，local：本地,oss：阿里云OSS. */
	private String mode;

	/** 阿里云的OSS存储配置. */
	private AliyunOssConfig oss = new AliyunOssConfig();

	/** 本地存储. */
	private LocalFSConfig local = new LocalFSConfig();

	/** mime types. */
	private Map<String, String> mimeTypes = Maps.newHashMap();

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public Map<String, String> getMimeTypes() {
		return mimeTypes;
	}

	public void setMimeTypes(Map<String, String> mimeTypes) {
		this.mimeTypes = mimeTypes;
	}

	public AliyunOssConfig getOss() {
		return oss;
	}

	public void setOss(AliyunOssConfig oss) {
		this.oss = oss;
	}

	public LocalFSConfig getLocal() {
		return local;
	}

	public void setLocal(LocalFSConfig local) {
		this.local = local;
	}

}
