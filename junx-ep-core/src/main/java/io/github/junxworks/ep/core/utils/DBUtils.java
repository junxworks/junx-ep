/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  DBUtils.java   
 * @Package io.github.junxworks.ep.core.utils   
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
package io.github.junxworks.ep.core.utils;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.junxworks.ep.core.ds.DynamicDataSource;

/**
 * DB工具类
 *
 * @ClassName:  DBUtils
 * @author: Michael
 * @date:   2020-7-19 12:18:36
 * @since:  v1.0
 */
public class DBUtils {

	/** 常量 TEST_SQL_ORACLE. */
	private static final String TEST_SQL_ORACLE = "select 1 from dual";

	/** 常量 TEST_SQL_MYSQL. */
	private static final String TEST_SQL_MYSQL = "select 1";

	/** 常量 logger. */
	private static final Logger logger = LoggerFactory.getLogger(DBUtils.class);

	/** 常量 DB_ORACLE. */
	public static final String DB_ORACLE = "Oracle";

	/** 常量 DB_MYSQL. */
	public static final String DB_MYSQL = "MySQL";

	/** 常量 DB_SQLite. */
	public static final String DB_SQLite = "SQLite";

	/** db product name. */
	private static String DB_PRODUCT_NAME;

	/**
	 * 返回是哪种数据库，必须初始化Spring后才能使用此方法
	 *
	 * @return DB type 属性
	 */
	public static String getDBProductName() {
		if (DB_PRODUCT_NAME == null) {
			DataSource ds = SpringContextUtils.getBean(DataSource.class);
			Connection conn = null;
			try {
				if (ds instanceof DynamicDataSource) {
					conn = ((DynamicDataSource) ds).getPrimaryDataSource().getConnection();
				} else {
					conn = ds.getConnection();
				}
				DB_PRODUCT_NAME = conn.getMetaData().getDatabaseProductName();
			} catch (SQLException e) {
				logger.error("Get DB oroduct name failed.", e);
			} finally {
				if (conn != null) {
					try {
						conn.close();
					} catch (SQLException e) {
					}
				}
			}
		}
		return DB_PRODUCT_NAME;
	}

	/**
	 * 获取测试数据库连接有效性的sql
	 *
	 * @return test SQL 属性
	 */
	public static String getTestSQL() {
		if (DB_PRODUCT_NAME == null) {
			getDBProductName();
		}
		switch (DB_PRODUCT_NAME) {
			case DB_ORACLE:
				return TEST_SQL_ORACLE;
			default:
				return TEST_SQL_MYSQL;
		}
	}

}
