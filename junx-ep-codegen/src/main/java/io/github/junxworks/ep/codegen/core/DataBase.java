package io.github.junxworks.ep.codegen.core;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

/**
 * 数据库操作类
 * 
 * @author levovo
 *
 */
public interface DataBase {
	/**
	 * 查询所有数据库表
	 * 
	 * @return
	 */
	public List<Table> queryTableList(String schema, String tableNamePrefix);

	/**
	 * 根据表名查询表详细信息
	 * 
	 * @param names
	 * 
	 * @return 表名
	 */
	public Table describeTable(String schema, String tableName) throws Exception;

	/**
	 * 关闭结果集
	 */
	public void closeResultSet(ResultSet rs);

	public void closeStatement(Statement statement);
}
