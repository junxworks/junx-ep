/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  FSConfiguration.java   
 * @Package io.github.junxworks.ep.fs.config   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2021-1-31 16:49:31   
 * @version V1.0 
 * @Copyright: 2021 Junxworks. All rights reserved. 
 * 注意：
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.fs.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import io.github.junxworks.ep.fs.controller.FileController;
import io.github.junxworks.ep.fs.driver.FileRepository;
import io.github.junxworks.ep.fs.driver.LocalFileSystemDriver;
import io.github.junxworks.ep.fs.driver.OssRepositoryDriver;
import io.github.junxworks.ep.fs.service.impl.FileServiceImpl;

@Configuration
@EnableConfigurationProperties({ FSConfig.class })
@Import({ DBInitConfiguration.class, FileController.class, FileServiceImpl.class })
public class FSConfiguration {

	/**
	 * 阿里云OSS远程存储
	 *
	 * @param config the config
	 * @return the file repository
	 */
	@ConditionalOnProperty(prefix = "junx.ep.fs", value = "mode", havingValue = "oss")
	@Bean(name = "ossRepository", initMethod = "start", destroyMethod = "stop")
	public FileRepository ossRepository(FSConfig config) {
		OssRepositoryDriver ossRepository = new OssRepositoryDriver();
		ossRepository.setConfig(config.getOss());
		return ossRepository;
	}

	/**
	 * 默认本地存储
	 *
	 * @param config the config
	 * @return the file repository
	 */
	@ConditionalOnProperty(prefix = "junx.ep.fs", value = "mode", havingValue = "local", matchIfMissing = true)
	@Bean(name = "localFileSystemRepository", initMethod = "start", destroyMethod = "stop")
	public FileRepository localFileSystemRepository(FSConfig config) {
		LocalFileSystemDriver localFileSystemRepository = new LocalFileSystemDriver();
		localFileSystemRepository.setConfig(config.getLocal());
		return localFileSystemRepository;
	}
}
