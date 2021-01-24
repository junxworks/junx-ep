/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  WebConfig.java   
 * @Package io.github.junxworks.ep.sys.config   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2021-1-24 15:35:41   
 * @version V1.0 
 * @Copyright: 2021 Junxworks. All rights reserved. 
 * 注意：
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.sys.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * {类的详细说明}.
 *
 * @ClassName:  WebConfig
 * @author: Michael
 * @date:   2020-7-19 12:17:47
 * @since:  v1.0
 */
@EnableWebMvc
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

	/**
	 * Adds the resource handlers.
	 *
	 * @param registry the registry
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations("classpath:/static/","classpath:/public/","classpath:/resources/","classpath:/META-INF/resources/");
		registry.addResourceHandler("/eui/**").addResourceLocations("classpath:/META-INF/resources/eui/");
	}

}