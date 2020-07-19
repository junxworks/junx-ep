/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  DynamicDataSource.java   
 * @Package io.github.junxworks.ep.core.ds   
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
package io.github.junxworks.ep.core.ds;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * {类的详细说明}.
 *
 * @ClassName:  DynamicDataSource
 * @author: Michael
 * @date:   2020-7-19 12:18:37
 * @since:  v1.0
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

	/** primary source name. */
	private static String primarySourceName;

	/** 常量 contextHolder. */
	private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

	/** primary data source. */
	private DataSource primaryDataSource;

	/**
	 * 构造一个新的 dynamic data source 对象.
	 */
	public DynamicDataSource() {
	}

	/**
	 * 构造一个新的 dynamic data source 对象.
	 *
	 * @param defaultTargetDataSource the default target data source
	 * @param targetDataSources the target data sources
	 */
	public DynamicDataSource(DataSource defaultTargetDataSource, Map<String, DataSource> targetDataSources) {
		primaryDataSource = defaultTargetDataSource;
		super.setDefaultTargetDataSource(defaultTargetDataSource);
		super.setTargetDataSources(new HashMap<>(targetDataSources));
		super.afterPropertiesSet();
	}

	public void setPrimarySourceName(String primarySourceName) {
		DynamicDataSource.primarySourceName = primarySourceName;
	}

	public DataSource getPrimaryDataSource() {
		return primaryDataSource;
	}

	/**
	 * Determine current lookup key.
	 *
	 * @return the object
	 */
	/* (non-Javadoc)
	 * @see org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource#determineCurrentLookupKey()
	 */
	@Override
	protected Object determineCurrentLookupKey() {
		return getDataSource();
	}

	/**
	 * Use primary source.
	 *
	 * @return the string
	 */
	public static String usePrimarySource() {
		contextHolder.set(primarySourceName);
		return primarySourceName;
	}

	public static void setDataSource(String dataSource) {
		contextHolder.set(dataSource);
	}

	public static String getDataSource() {
		return contextHolder.get();
	}

	/**
	 * Clear data source.
	 */
	public static void clearDataSource() {
		contextHolder.remove();
	}

	public void setPrimaryDataSource(DataSource primaryDataSource) {
		this.primaryDataSource = primaryDataSource;
		setDefaultTargetDataSource(primaryDataSource);
	}

}
