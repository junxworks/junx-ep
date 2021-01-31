/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  AliyunOssConfig.java   
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
 * 阿里云的OSS对象存储服务的配置
 *
 * @ClassName:  AliCloudOssConfig
 * @author: Michael
 * @date:   2019-1-6 11:33:42
 * @since:  v1.0
 */
public class AliyunOssConfig {

	/** endpoint. */
	private String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";

	/** bucket. */
	private String bucket;

	/** 一次性给定的key，不能修改. */
	private String accessKeyId;

	/** 一次性给定的密钥，不能修改. */
	private String accessKeySecret;

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public String getBucket() {
		return bucket;
	}

	public void setBucket(String bucket) {
		this.bucket = bucket;
	}

	public String getAccessKeyId() {
		return accessKeyId;
	}

	public void setAccessKeyId(String accessKeyId) {
		this.accessKeyId = accessKeyId;
	}

	public String getAccessKeySecret() {
		return accessKeySecret;
	}

	public void setAccessKeySecret(String accessKeySecret) {
		this.accessKeySecret = accessKeySecret;
	}

}
