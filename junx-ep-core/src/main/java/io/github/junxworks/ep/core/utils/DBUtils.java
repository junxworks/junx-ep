/*
 ***************************************************************************************
 * All rights Reserved, Designed By www.higinet.com.cn
 * @Title:  DBUtils.java   
 * @Package cn.com.higinet.common.web.utils   
 * @Description: (用一句话描述该文件做什么)   
 * @author: 王兴
 * @date:   2018-1-31 10:58:38   
 * @version V1.0 
 * @Copyright: 2018 北京宏基恒信科技有限责任公司. All rights reserved. 
 * 注意：本内容仅限于公司内部使用，禁止外泄以及用于其他的商业目
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
 * BD工具类
 *
 * @ClassName:  DBUtils
 * @author: 王兴
 * @date:   2018-1-31 10:58:40
 * @since:  v4.4
 */
public class DBUtils {

	private static final String TEST_SQL_ORACLE = "select 1 from dual";

	private static final String TEST_SQL_MYSQL = "select 1";

	private static final Logger logger = LoggerFactory.getLogger(DBUtils.class);

	/** 常量 DB_ORACLE. */
	public static final String DB_ORACLE = "Oracle";

	/** 常量 DB_MYSQL. */
	public static final String DB_MYSQL = "MySQL";

	public static final String DB_SQLite = "SQLite";

	/** 数据库名 */
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
