/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  LocalFileSystemRepositoryConfiguration.java   
 * @Package io.github.junxworks.ep.fs.config   
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
package io.github.junxworks.ep.fs.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.junxworks.ep.fs.driver.FileRepository;
import io.github.junxworks.ep.fs.driver.FSConfig;
import io.github.junxworks.ep.fs.driver.LocalFileSystemDriver;

@Configuration
@EnableConfigurationProperties({ FSConfig.class })
public class LocalFSConfiguration {

	@Bean(name = "localFileSystemRepository", initMethod = "start", destroyMethod = "stop")
	public FileRepository localFileSystemRepository(FSConfig config) {
		LocalFileSystemDriver localFileSystemRepository = new LocalFileSystemDriver();
		localFileSystemRepository.setConfig(config.getLocal());
		return localFileSystemRepository;
	}

}