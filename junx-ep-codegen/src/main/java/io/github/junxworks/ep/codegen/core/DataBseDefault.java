package io.github.junxworks.ep.codegen.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import io.github.junxworks.junx.core.exception.BaseRuntimeException;

public abstract class DataBseDefault implements DataBase {
	TypeMapping typeMapping;

	DatabaseElement de;

	public DataBseDefault(DatabaseElement de) {
		typeMapping = new TypeMapping();
		this.de = de;
	}

	public void closeConn(Connection conn) {
		try {
			if (conn != null) {
				conn.close();
				conn = null;
			}
		} catch (SQLException e) {

		}
	}

	public Connection getConn() throws Exception {
		return GenerateHelper.getConnection(de.getType(), de.getUrl(), de.getUsername(), de.getPassword());
	}

	@Override
	public abstract List<Table> queryTableList(String schema, String tableNamePrefix);

	public abstract Table queryTable(String schema, String tableName);

	@Override
	public Table describeTable(String schema, String tableName) throws Exception {
		try (Connection con = getConn()) {
			return getTable(schema, tableName, con);
		} catch (Exception e) {
			throw new BaseRuntimeException(e);
		}
	}

	private Table getTable(String schema, String tableName, Connection con) {
		ResultSet rs = null;
		Table table = null;
		try {
			table = queryTable(schema, tableName);
			if (table != null) {
				table.setSchema(schema);
				
				introspectPrimaryKeys(table, con);
				introspectColumns(table, con);
				introspectForeignKeys(table, con);
				introspectIndex(table, con);
				introspectGeneratedKeys(table, con);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeResultSet(rs);
		}
		return table;
	}

	@Override
	public void closeResultSet(ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
		} catch (SQLException e) {

		}

	}

	@Override
	public void closeStatement(Statement statement) {
		try {
			if (statement != null) {
				statement.close();
				statement = null;
			}
		} catch (SQLException e) {

		}
	}

	protected void introspectPrimaryKeys(Table table, Connection con) {
		ResultSet rs = null;
		try {
			rs = con.getMetaData().getPrimaryKeys(null, null, table.getTableName());
			while (rs.next()) {
				String columnName = rs.getString("COLUMN_NAME");
				Column column = table.getColumn(columnName);
				if (column == null) {
					column = new Column(columnName);
					table.addPrimaryKey(column);
				}
				column.setPrimaryKey(true);
				column.setUnique(!rs.getBoolean("NON_UNIQUE"));
			}
		} catch (SQLException e) {

		} finally {
			closeResultSet(rs);
		}
	}

	protected void introspectColumns(Table table, Connection con) {
		ResultSet rs = null;
		try {

			rs = con.getMetaData().getColumns(null, null, table.getTableName(), "%");

			while (rs.next()) {

				String columnName = rs.getString("COLUMN_NAME");// 获得字段名称
				if (GenerateHelper.isEmpty(columnName)) {
					continue;
				}

				Column column = table.getColumn(columnName);
				if (column == null) {
					column = new Column(columnName);
					table.addBaseColumn(column);
				}
				column.setJdbcType(rs.getInt("DATA_TYPE"));
				column.setSize(rs.getInt("COLUMN_SIZE"));
				column.setNullable(rs.getInt("NULLABLE") == 1);

				column.setDefaultValue(rs.getString("COLUMN_DEF"));
				column.setDecimalDigits(rs.getInt("DECIMAL_DIGITS"));
				column.setRemarks(rs.getString("REMARKS"));
				column.setJdbcTypeName(JdbcTypeResolver.getJdbcTypeName(column.getJdbcType()));
				column.setJavaType(typeMapping.calculateJavaType(column));
				column.setFullJavaType(typeMapping.calculateFullJavaType(column));
				column.setJavaProperty(GenerateHelper.getCamelCaseString(columnName, false));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResultSet(rs);
		}
	}

	// 获得外键的信息
	protected void introspectForeignKeys(Table table, Connection con) {
		ResultSet rs = null;
		try {
			rs = con.getMetaData().getImportedKeys(null, null, table.getTableName());
			while (rs.next()) {
				String columnName = rs.getString("FKCOLUMN_NAME");
				if (GenerateHelper.isEmpty(columnName)) {
					continue;
				}
				String pkTableName = rs.getString("PKTABLE_NAME");
				String pkColumnName = rs.getString("PKCOLUMN_NAME");
				Column column = table.getColumn(columnName);
				if (column != null) {
					column.setForeignKey(true);
					column.setTargetTableName(pkTableName);
					column.setTargetColumnName(pkColumnName);
				}
			}
		} catch (SQLException e) {

		} finally {
			closeResultSet(rs);
		}
	}

	// 获得索引
	protected void introspectIndex(Table table, Connection con) {
		ResultSet rs = null;
		try {
			rs = con.getMetaData().getIndexInfo(null, null, table.getTableName(), true, true);

			while (rs.next()) {
				String columnName = rs.getString("COLUMN_NAME");
				if (GenerateHelper.isEmpty(columnName)) {
					continue;
				}
				Column column = table.getColumn(columnName);
				if (column != null) {

					column.setUnique(!rs.getBoolean("NON_UNIQUE"));
				}
			}
		} catch (SQLException e) {

		} finally {
			closeResultSet(rs);
		}
	}

	// 获取自动增长列
	protected void introspectGeneratedKeys(Table table, Connection con) {
		try {
			PreparedStatement ps = con.prepareStatement("select * from " + table.getTableName());
			ResultSet rs = ps.executeQuery();
			ResultSetMetaData rsme = rs.getMetaData();

			int columnCount = rsme.getColumnCount();
			for (int i = 1; i < columnCount; i++) {
				String columnName = rsme.getColumnName(i);
				Column column = table.getColumn(columnName);
				if (column != null) {
					column.setAutoIncrement(rsme.isAutoIncrement(i));
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
