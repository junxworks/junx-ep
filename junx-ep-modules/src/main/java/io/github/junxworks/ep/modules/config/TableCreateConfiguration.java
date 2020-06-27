/*
 ***************************************************************************************
 * All rights Reserved, Designed By www.cqhyrc.com.cn
 * @Title:  TableCreateConfiguration.java   
 * @Package io.github.junxworks.ep.modules.config   
 * @Description: (用一句话描述该文件做什么)   
 * @author: AOC
 * @date:   2019-6-26 19:13:47   
 * @version V1.0 
 * @Copyright: 2019 重庆华宇集团. All rights reserved. 
 * 注意：本内容仅限于公司内部使用，禁止外泄以及用于其他的商业目
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.modules.config;

import java.io.IOException;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import io.github.junxworks.ep.core.svc.InitDBTableService;
import io.github.junxworks.ep.core.utils.DBUtils;
import io.github.junxworks.junx.core.util.ByteUtils;
import io.github.junxworks.junx.core.util.StringUtils;

/**
 * 构造所需表结构
 *
 * @ClassName:  TableCreateConfiguration
 * @author: 王兴
 * @date:   2019-6-26 19:13:47
 * @since:  v1.0
 */
public class TableCreateConfiguration {
	private static String dbProductName;
	
	protected InitDBTableService createTableValidate(String tableName, String desc) throws IOException {
		InitDBTableService service = new InitDBTableService();
		service.setDescription(desc);
		service.setTableName(tableName);
		Resource resource = new ClassPathResource(getDDLPath(tableName));
		String createTableDDL = new String(ByteUtils.inputStreamToBytes(resource.getInputStream()), "UTF-8");
		service.setCreateTableDDL(createTableDDL);
		resource = new ClassPathResource(getDMLPath(tableName));
		if (resource != null && resource.exists() && resource.isReadable()) {
			String sqlsTableDDL = new String(ByteUtils.inputStreamToBytes(resource.getInputStream()), "UTF-8");
			service.setSqlsAfterCreateTable(StringUtils.split(sqlsTableDDL, ";"));
		}
		return service;
	}

	protected String getDDLPath(String tableName) {
		if (StringUtils.isNull(dbProductName)) {
			dbProductName = DBUtils.getDBProductName();
		}
		return "/io/github/junxworks/ep/modules/validators/ddl/" + tableName + "_" + dbProductName + ".sql";
	}

	protected String getDMLPath(String tableName) {
		if (StringUtils.isNull(dbProductName)) {
			dbProductName = DBUtils.getDBProductName();
		}
		return "/io/github/junxworks/ep/modules/validators/dml/" + tableName + "_" + dbProductName + ".sql";
	}
}
