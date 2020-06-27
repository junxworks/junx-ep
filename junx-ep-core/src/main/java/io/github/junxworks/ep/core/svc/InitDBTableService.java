/*
 ***************************************************************************************
 * All rights Reserved, Designed By www.cqhyrc.com.cn
 * @Title:  InitDBTableService.java   
 * @Package io.github.junxworks.ep.core.svc   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2019-1-9 15:48:31   
 * @version V1.0 
 * @Copyright: 2019 重庆华宇集团. All rights reserved. 
 * 注意：本内容仅限于公司内部使用，禁止外泄以及用于其他的商业目
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */

package io.github.junxworks.ep.core.svc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import io.github.junxworks.ep.core.utils.DBUtils;

import io.github.junxworks.junx.core.exception.FatalException;
import io.github.junxworks.junx.core.lifecycle.Service;
import io.github.junxworks.junx.core.util.StringUtils;


/**
 * 根据脚本初始化数据库表的服务
 *
 * @ClassName:  InitDBTableService
 * @author: 王兴
 * @date:   2019-1-9 15:48:33
 * @since:  v1.0
 */
public class InitDBTableService extends Service {

	/** 数据库表名. */

	private String tableName;

	/** 创建数据库的语句 */
	private String createTableDDL;

	/** 创建完表后的执行语句. */
	private String[] sqlsAfterCreateTable;

	/** jdbc template. */
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getCreateTableDDL() {
		return createTableDDL;
	}

	public void setCreateTableDDL(String createTableDDL) {
		this.createTableDDL = createTableDDL;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/* (non-Javadoc)
	 * @see cn.com.higinet.common.lifecycle.Service#doStart()
	 */
	public String[] getSqlsAfterCreateTable() {
		return sqlsAfterCreateTable;
	}

	public void setSqlsAfterCreateTable(String[] sqlsAfterCreateTable) {
		this.sqlsAfterCreateTable = sqlsAfterCreateTable;
	}

	/* (non-Javadoc)
	 * @see io.github.junxworks.junx.core.lifecycle.Service#doStart()
	 */
	@Override
	protected void doStart() throws Throwable {
		boolean needToCreateTable = false;
		try {
			jdbcTemplate.execute(getValidateSql());
		} catch (Exception e) {
			needToCreateTable = true;
			try {
				jdbcTemplate.execute(DBUtils.getTestSQL()); //防止数据库连接问题，此处进行二次校验
			} catch (Exception ee) {
				needToCreateTable = false;
			}
		}
		if (needToCreateTable) {
			createTable();
		}
	}

	/**
	 * Creates the table.
	 */
	private void createTable() {
		try {
			jdbcTemplate.execute(createTableDDL);
			if (sqlsAfterCreateTable != null) {
				for (int i = 0, len = sqlsAfterCreateTable.length; i < len; i++) {
					String sql = sqlsAfterCreateTable[i];
					if (StringUtils.notNull(sql))
						jdbcTemplate.execute(sql);
				}
			}
		} catch (Exception e) {
			logger.error("Create table " + tableName + " failed.", e);
			throw new FatalException(e);
		}
	}

	private String getValidateSql() {
		return "select 1 from " + tableName + " where 1!=1";
	}

	/* (non-Javadoc)
	 * @see cn.com.higinet.common.lifecycle.Service#doStop()
	 */
	@Override
	protected void doStop() throws Throwable {

	}

}
