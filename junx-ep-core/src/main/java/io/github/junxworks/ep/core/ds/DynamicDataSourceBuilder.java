/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  DynamicDataSourceBuilder.java   
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

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

/**
 * {类的详细说明}.
 *
 * @ClassName:  DynamicDataSourceBuilder
 * @author: Michael
 * @date:   2020-7-19 12:18:36
 * @since:  v1.0
 */
public class DynamicDataSourceBuilder {
	
	/**
	 * Creates the.
	 *
	 * @return the dynamic data source object
	 */
	public static DynamicDataSourceObject create() {
		return new DynamicDataSourceObject();
	}

	/**
	 * {类的详细说明}.
	 *
	 * @ClassName:  DynamicDataSourceBuilder
	 * @author: Michael
	 * @date:   2020-7-19 12:18:36
	 * @since:  v1.0
	 */
	public static final class DynamicDataSourceObject {
		
		/** primary source name. */
		private String primarySourceName;

		/** primary source. */
		private DataSource primarySource;

		/** sources. */
		private Map<String, DataSource> sources = new HashMap<>();

		/**
		 * Sets the primary source.
		 *
		 * @param name the name
		 * @param dataSource the data source
		 * @return the dynamic data source object
		 */
		public DynamicDataSourceObject setPrimarySource(String name, DataSource dataSource) {
			primarySourceName = name;
			primarySource = dataSource;
			sources.put(name, dataSource);
			return this;
		}

		/**
		 * Adds the data source.
		 *
		 * @param name the name
		 * @param dataSource the data source
		 * @return the dynamic data source object
		 */
		public DynamicDataSourceObject addDataSource(String name, DataSource dataSource) {
			if (sources.containsKey(name)) {
				throw new DuplicatedDataSourceException(name);
			}
			sources.put(name, dataSource);
			return this;
		}

		/**
		 * Builds the.
		 *
		 * @return the dynamic data source
		 */
		public DynamicDataSource build() {
			DynamicDataSource dynamicDataSource = new DynamicDataSource(primarySource, sources);
			dynamicDataSource.setPrimarySourceName(primarySourceName);
			return dynamicDataSource;
		}
	}
}
