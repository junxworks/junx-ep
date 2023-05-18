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
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Import;

import io.github.junxworks.ep.core.pvc.PersistenceVersionController;
import io.github.junxworks.ep.core.utils.SpringContextUtils;
import io.github.junxworks.ep.fs.constants.FsConstants;
import io.github.junxworks.ep.fs.driver.FileRepository;
import io.github.junxworks.ep.fs.driver.FileRepositoryFactory;
import io.github.junxworks.ep.fs.driver.LocalFileSystemDriver;
import io.github.junxworks.ep.fs.driver.OssRepositoryDriver;

@Configuration
@EnableConfigurationProperties({ FSConfig.class })
@ComponentScan("io.github.junxworks.ep.fs")
@Import({ SpringContextUtils.class })
@ConditionalOnProperty(prefix = "junx.ep.fs", name = "enabled", havingValue = "true", matchIfMissing = true)
public class FSConfiguration {

	/** 常量 PVC_PATH.持久化版本控制路径 */
	private static final String PVC_PATH = "/io/github/junxworks/ep/fs/pvc";

	/** 常量 MODULE_NAME.pvc参数，模块名 */
	private static final String MODULE_NAME = "junx_ep_fs";

	/**
	 * 阿里云OSS远程存储
	 *
	 * @param config the config
	 * @return the file repository
	 */
	@ConditionalOnProperty(prefix = "junx.ep.fs", value = "mode", havingValue = FsConstants.MODE_OSS)
	@Bean(name = "ossRepository", initMethod = "start", destroyMethod = "stop")
	public FileRepository ossRepository(FSConfig config) {
		OssRepositoryDriver ossRepository = new OssRepositoryDriver();
		ossRepository.setConfig(config.getOss());
		return ossRepository;
	}

	@Bean(name = "fileRepositoryFactory", initMethod = "initialize", destroyMethod = "destroy")
	public FileRepositoryFactory fileRepositoryFactory() {
		FileRepositoryFactory factory = new FileRepositoryFactory();
		return factory;
	}

	/**
	 * 默认本地存储
	 *
	 * @param config the config
	 * @return the file repository
	 */
	@ConditionalOnProperty(prefix = "junx.ep.fs", value = "mode", havingValue = FsConstants.MODE_LOCAL, matchIfMissing = true)
	@Bean(name = "localFileSystemRepository", initMethod = "start", destroyMethod = "stop")
	public FileRepository localFileSystemRepository(FSConfig config) {
		LocalFileSystemDriver localFileSystemRepository = new LocalFileSystemDriver();
		localFileSystemRepository.setConfig(config.getLocal());
		return localFileSystemRepository;
	}

	@DependsOn("JunxEpSpringContextUtils")
	@Bean(name = "junxEpFsPvc", initMethod = "start", destroyMethod = "stop")
	public PersistenceVersionController junxEpFsPvc() {
		PersistenceVersionController pvc = new PersistenceVersionController();
		pvc.setPvcPath(PVC_PATH);
		pvc.setModuleName(MODULE_NAME);
		return pvc;
	}
}
