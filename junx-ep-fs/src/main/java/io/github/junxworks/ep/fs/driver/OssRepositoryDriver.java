/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  OssRepositoryDriver.java   
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

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;

import io.github.junxworks.junx.core.util.ByteUtils;

/**
 * 基于阿里云oss实现的文件存储驱动
 *
 * @ClassName:  OssRepositoryDriver
 * @author: 王兴
 * @date:   2019-1-6 11:46:57
 * @since:  v1.0
 */
public class OssRepositoryDriver extends AbstractFileRepository {

	/** OSS配置. */
	private AliyunOssConfig config;

	/** 阿里云 oss 客户端. */
	//	private OSSClient ossClient;

	public AliyunOssConfig getConfig() {
		return config;
	}

	public void setConfig(AliyunOssConfig config) {
		this.config = config;
	}

	/* (non-Javadoc)
	 * @see io.github.junxworks.junx.core.lifecycle.Service#doStart()
	 */
	@Override
	protected void doStart() throws Throwable {
		//		ossClient = new OSSClient(config.getEndpoint(), config.getAccessKeyId(), config.getAccessKeySecret());
	}

	/* (non-Javadoc)
	 * @see com.yrxd.commons.filerepository.FileRepository#storeFile(java.io.File)
	 */
	@Override
	public String storeFile(File file) throws StoreFailedException {
		String token = createToken();
		OSSClient ossClient = new OSSClient(config.getEndpoint(), config.getAccessKeyId(), config.getAccessKeySecret());
		try {
			ossClient.putObject(config.getBucket(), token, file);
		} finally {
			try {
				ossClient.shutdown();
			} catch (Throwable e) {
			}
		}
		return token;
	}

	/* (non-Javadoc)
	 * @see com.yrxd.commons.filerepository.FileRepository#storeFile(byte[])
	 */
	@Override
	public String storeFile(byte[] bytes) throws StoreFailedException {
		String token = createToken();
		OSSClient ossClient = new OSSClient(config.getEndpoint(), config.getAccessKeyId(), config.getAccessKeySecret());
		try {
			ossClient.putObject(config.getBucket(), token, new ByteArrayInputStream(bytes));
		} finally {
			try {
				ossClient.shutdown();
			} catch (Throwable e) {
			}
		}
		return token;
	}

	/* (non-Javadoc)
	 * @see com.yrxd.commons.filerepository.FileRepository#storeFile(java.io.InputStream)
	 */
	@Override
	public String storeFile(InputStream inputStream) throws StoreFailedException {
		String token = createToken();
		OSSClient ossClient = new OSSClient(config.getEndpoint(), config.getAccessKeyId(), config.getAccessKeySecret());
		try {
			ossClient.putObject(config.getBucket(), token, inputStream);
		} finally {
			try {
				ossClient.shutdown();
			} catch (Throwable e) {
			}
		}
		return token;
	}

	@Override
	public byte[] fetchFileBytes(String fileToken) throws FetchFailedException {
		OSSClient ossClient = new OSSClient(config.getEndpoint(), config.getAccessKeyId(), config.getAccessKeySecret());
		try {
			OSSObject ossObject = ossClient.getObject(config.getBucket(), fileToken);
			if (ossObject == null) {
				throw new FileNotFoundException("File does not exist.");
			}
			try (InputStream in = ossObject.getObjectContent()) {
				return ByteUtils.inputStreamToBytes(in);
			} catch (Throwable t) {
				throw new FetchFailedException("Fetch file failed.", t);
			}
		} finally {
			try {
				ossClient.shutdown();
			} catch (Throwable e) {
			}
		}
	}

	@Override
	public void fetchFileIntoStream(String fileToken, OutputStream output) throws FetchFailedException {
		OSSClient ossClient = new OSSClient(config.getEndpoint(), config.getAccessKeyId(), config.getAccessKeySecret());
		try {
			OSSObject ossObject = ossClient.getObject(config.getBucket(), fileToken);
			if (ossObject == null) {
				throw new FileNotFoundException("File does not exist.");
			}
			try (InputStream in = ossObject.getObjectContent()) {
				intputStreamToOutputStream(in, output);
			} catch (Throwable t) {
				throw new FetchFailedException("Fetch file failed.", t);
			}
		} finally {
			try {
				ossClient.shutdown();
			} catch (Throwable e) {
			}
		}
	}

	@Override
	public InputStream fetchFileAsStream(String fileToken) throws FetchFailedException {
		OSSClient ossClient = new OSSClient(config.getEndpoint(), config.getAccessKeyId(), config.getAccessKeySecret());
		try {
			OSSObject ossObject = ossClient.getObject(config.getBucket(), fileToken);
			if (ossObject == null) {
				throw new FetchFailedException("File does not exist.");
			}
			try (InputStream in = ossObject.getObjectContent()) {
				return new ByteArrayInputStream(ByteUtils.inputStreamToBytes(in));
			} catch (Throwable t) {
				throw new FetchFailedException("Fetch file failed.", t);
			}
			//			return ossObject.getObjectContent(); //防止连接泄漏，把ObjectContent输入流转换成ByteArrayInputStream
		} finally {
			try {
				ossClient.shutdown();
			} catch (Throwable e) {
			}
		}
	}

	/* (non-Javadoc)
	 * @see io.github.junxworks.junx.core.lifecycle.Service#doStop()
	 */
	@Override
	protected void doStop() throws Throwable {
		//		if (ossClient != null) {
		//			ossClient.shutdown();
		//		}
	}
}
