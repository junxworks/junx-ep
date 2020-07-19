/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  TableInitComponent.java   
 * @Package io.github.junxworks.ep.core.svc   
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
package io.github.junxworks.ep.core.svc;

import java.io.IOException;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import io.github.junxworks.ep.core.utils.DBUtils;
import io.github.junxworks.junx.core.util.ByteUtils;
import io.github.junxworks.junx.core.util.StringUtils;

/**
 * {类的详细说明}.
 *
 * @ClassName:  TableInitComponent
 * @author: Michael
 * @date:   2020-7-19 12:18:37
 * @since:  v1.0
 */
public class TableInitComponent {
	
	/** db product name. */
	private static String dbProductName;

	/**
	 * Creates the table validate.
	 *
	 * @param path the path
	 * @param tableName the table name
	 * @param desc the desc
	 * @return the table initializer
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected TableInitializer createTableValidate(String path, String tableName, String desc) throws IOException {
		TableInitializer service = new TableInitializer();
		service.setDescription(desc);
		service.setTableName(tableName);
		Resource resource = new ClassPathResource(getDDLPath(path, tableName));
		String createTableDDL = new String(ByteUtils.inputStreamToBytes(resource.getInputStream()), "UTF-8");
		service.setCreateTableDDL(createTableDDL);
		resource = new ClassPathResource(getDMLPath(path, tableName));
		if (resource != null && resource.exists() && resource.isReadable()) {
			String sqlsTableDDL = new String(ByteUtils.inputStreamToBytes(resource.getInputStream()), "UTF-8");
			service.setSqlsAfterCreateTable(StringUtils.split(sqlsTableDDL, ";"));
		}
		return service;
	}

	/**
	 * 返回 DDL path 属性.
	 *
	 * @param path the path
	 * @param tableName the table name
	 * @return DDL path 属性
	 */
	protected String getDDLPath(String path, String tableName) {
		if (StringUtils.isNull(dbProductName)) {
			dbProductName = DBUtils.getDBProductName();
		}
		return path + "/ddl/" + tableName + "_" + dbProductName + ".sql";
	}

	/**
	 * 返回 DML path 属性.
	 *
	 * @param path the path
	 * @param tableName the table name
	 * @return DML path 属性
	 */
	protected String getDMLPath(String path, String tableName) {
		if (StringUtils.isNull(dbProductName)) {
			dbProductName = DBUtils.getDBProductName();
		}
		return path + "/dml/" + tableName + "_" + dbProductName + ".sql";
	}
}
