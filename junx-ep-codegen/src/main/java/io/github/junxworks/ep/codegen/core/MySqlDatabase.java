package io.github.junxworks.ep.codegen.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MySqlDatabase extends DataBseDefault {

	public MySqlDatabase(DatabaseElement de) {
		super(de);
	}

	@Override
	public List<Table> queryTableList(String schema, String tableNamePrefix) {
		String sql = "select table_name,table_comment from information_schema.tables where table_schema=? and lower(table_type)='base table'";
		if (!GenerateHelper.isEmpty(tableNamePrefix)) {
			sql += " and table_name like '%" + tableNamePrefix + "%'";
		}
		List<Table> res = new ArrayList<Table>();
		PreparedStatement psmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConn();
			psmt = con.prepareStatement(sql);
			psmt.setObject(1, schema);
			rs = psmt.executeQuery();
			while (rs.next()) {
				Table table = new Table();
				table.setTableName(rs.getString("table_name"));
				table.setTableComment(rs.getString("table_comment"));
				res.add(table);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeResultSet(rs);
			closeStatement(psmt);
			closeConn(con);
		}
		return res;
	}

	@Override
	public Table queryTable(String schema, String tableName) {
		String sql = "select table_name,table_comment from information_schema.tables where table_schema=? and lower(table_type)='base table'";
		if (!GenerateHelper.isEmpty(tableName)) {
			sql += " and table_name = '" + tableName + "'";
		}
		List<Table> res = new ArrayList<Table>();
		PreparedStatement psmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConn();
			psmt = con.prepareStatement(sql);
			psmt.setObject(1, schema);
			rs = psmt.executeQuery();
			while (rs.next()) {
				Table table = new Table();
				table.setTableName(rs.getString("table_name"));
				table.setTableComment(rs.getString("table_comment"));
				res.add(table);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeResultSet(rs);
			closeStatement(psmt);
			closeConn(con);
		}
		return !res.isEmpty() ? res.get(0) : null;
	}

}
