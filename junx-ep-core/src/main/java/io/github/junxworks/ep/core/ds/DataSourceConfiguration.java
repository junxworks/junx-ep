/*
 ***************************************************************************************
 * All rights Reserved, Designed By www.cqhyrc.com.cn
 * @Title:  DataSourceConfiguration.java   
 * @Package io.github.junxworks.ep.core.ds   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2019-1-2 9:55:54   
 * @version V1.0 
 * @Copyright: 2019 重庆华宇集团. All rights reserved. 
 * 注意：本内容仅限于公司内部使用，禁止外泄以及用于其他的商业目
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.core.ds;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.junxworks.ep.core.ds.aspect.DataSourceAspect;

@Configuration
public class DataSourceConfiguration {

	/**
	 * AOP拦截注入
	 *
	 * @return the data source aspect
	 */
	@Bean
	public DataSourceAspect dataSourceAspect() {
		return new DataSourceAspect();
	}

}
