/*
 ***************************************************************************************
 * All rights Reserved, Designed By www.cqhyrc.com.cn
 * @Title:  DynamicDataSourceBuilder.java   
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

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

public class DynamicDataSourceBuilder {
	public static DynamicDataSourceObject create() {
		return new DynamicDataSourceObject();
	}

	public static final class DynamicDataSourceObject {
		private String primarySourceName;

		private DataSource primarySource;

		private Map<String, DataSource> sources = new HashMap<>();

		public DynamicDataSourceObject setPrimarySource(String name, DataSource dataSource) {
			primarySourceName = name;
			primarySource = dataSource;
			sources.put(name, dataSource);
			return this;
		}

		public DynamicDataSourceObject addDataSource(String name, DataSource dataSource) {
			if (sources.containsKey(name)) {
				throw new DuplicatedDataSourceException(name);
			}
			sources.put(name, dataSource);
			return this;
		}

		public DynamicDataSource build() {
			DynamicDataSource dynamicDataSource = new DynamicDataSource(primarySource, sources);
			dynamicDataSource.setPrimarySourceName(primarySourceName);
			return dynamicDataSource;
		}
	}
}
