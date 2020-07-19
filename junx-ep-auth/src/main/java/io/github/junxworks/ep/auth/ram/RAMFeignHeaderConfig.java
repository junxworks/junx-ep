/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  RAMFeignHeaderConfig.java   
 * @Package io.github.junxworks.ep.auth.ram   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2020-7-19 12:18:42   
 * @version V1.0 
 * @Copyright: 2020 Junxworks. All rights reserved. 
 * 注意：
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.auth.ram;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import io.github.junxworks.ep.auth.EPShiroConfig;
import io.github.junxworks.ep.auth.RamConstants;

/**
 * {类的详细说明}.
 *
 * @ClassName:  RAMFeignHeaderConfig
 * @author: Michael
 * @date:   2020-7-19 12:18:42
 * @since:  v1.0
 */
@Configuration
@EnableConfigurationProperties({ EPShiroConfig.class })
public class RAMFeignHeaderConfig {

	/** config. */
	@Autowired
	private EPShiroConfig config;
	
	/**
	 * Header interceptor.
	 *
	 * @return the request interceptor
	 */
	@Bean
	public RequestInterceptor headerInterceptor() {
		return new RequestInterceptor() {

			@Override
			public void apply(RequestTemplate template) {
				Map<String, Collection<String>> headers = ImmutableMap.of(RamConstants.RAM_HEADER_ACCESSKEY, Lists.newArrayList(config.getRamKey()), RamConstants.RAM_HEADER_ACCESSSECRET, Lists.newArrayList(config.getRamSecret()));
				template.headers(headers);
			}
		};
	}
}
