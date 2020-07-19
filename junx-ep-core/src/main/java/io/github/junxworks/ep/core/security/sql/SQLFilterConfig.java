/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  SQLFilterConfig.java   
 * @Package io.github.junxworks.ep.core.security.sql   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2020-7-19 12:18:37   
 * @version V1.0 
 * @Copyright: 2020 Junxworks. All rights reserved. 
 * 注意：
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.core.security.sql;

import javax.servlet.DispatcherType;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * {类的详细说明}.
 *
 * @ClassName:  SQLFilterConfig
 * @author: Michael
 * @date:   2020-7-19 12:18:37
 * @since:  v1.0
 */
@Configuration
public class SQLFilterConfig {
	
	/**
	 * Sql filter registration.
	 *
	 * @return the filter registration bean
	 */
	@Bean
	public FilterRegistrationBean sqlFilterRegistration() {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setDispatcherTypes(DispatcherType.REQUEST);
		registration.setFilter(new SQLFilter());
		registration.addUrlPatterns("/*");
		registration.setName("sqlFilter");
		registration.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return registration;
	}
}
