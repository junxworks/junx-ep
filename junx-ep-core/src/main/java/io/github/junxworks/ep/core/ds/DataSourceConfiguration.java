/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  DataSourceConfiguration.java   
 * @Package io.github.junxworks.ep.core.ds   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2020-7-19 12:18:36   
 * @version V1.0 
 * @Copyright: 2020 Junxworks. All rights reserved. 
 * 注意：
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.core.ds;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.junxworks.ep.core.ds.aspect.DataSourceAspect;

/**
 * 数据源配置类
 *
 * @ClassName:  DataSourceConfiguration
 * @author: Michael
 * @date:   2020-7-19 12:18:36
 * @since:  v1.0
 */
@Configuration
public class DataSourceConfiguration {

	/**
	 * Data source aspect.
	 *
	 * @return the data source aspect
	 */
	@Bean
	public DataSourceAspect dataSourceAspect() {
		return new DataSourceAspect();
	}

}
