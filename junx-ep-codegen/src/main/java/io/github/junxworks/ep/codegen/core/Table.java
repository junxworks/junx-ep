package io.github.junxworks.ep.codegen.core;

import java.util.ArrayList;
import java.util.List;

public class Table implements java.io.Serializable, Cloneable {

	private static final long serialVersionUID = -7246043091254837124L;

	private String tableName;

	private String tableAlias;

	private String tableComment;

	private String className;

	private String javaFieldName;

	private String schema = null;

	private List<Column> baseColumns = new ArrayList<Column>();

	private List<Column> primaryKeys = new ArrayList<Column>();

	public Table() {

	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
		this.tableAlias = tableName;
		this.className = GenerateHelper.getCamelCaseString(tableAlias, true);
		this.javaFieldName = GenerateHelper.getCamelCaseString(tableAlias, false);
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public Column getColumn(String columnName) {
		for (Column column : primaryKeys) {
			if (column.getColumnName().equals(columnName)) {
				return column;
			}
		}
		for (Column column : baseColumns) {
			if (column.getColumnName().equals(columnName)) {
				return column;
			}
		}
		return null;
	}

	public List<Column> getColumns() {
		List<Column> allColumns = new ArrayList<Column>();
		allColumns.addAll(primaryKeys);
		allColumns.addAll(baseColumns);
		return allColumns;
	}

	public List<Column> getBaseColumns() {
		return baseColumns;
	}

	public void addBaseColumn(Column column) {
		this.baseColumns.add(column);
	}

	public List<Column> getPrimaryKeys() {
		return primaryKeys;
	}

	public void addPrimaryKey(Column primaryKey) {
		this.primaryKeys.add(primaryKey);
	}

	public String getJavaFieldName() {
		return javaFieldName;
	}

	public void setJavaFieldName(String javaFieldName) {
		this.javaFieldName = javaFieldName;
	}

	public String getTableAlias() {
		return tableAlias;
	}

	public void setTableAlias(String tableAlias) {
		this.tableAlias = tableAlias;
		this.className = GenerateHelper.getCamelCaseString(tableAlias, true);
		this.javaFieldName = GenerateHelper.getCamelCaseString(tableAlias, false);
	}

	public boolean isHasDateColumn() {
		for (Column column : getColumns()) {
			if (column.isDate()) {
				return true;
			}
		}
		return false;
	}

	public boolean isHasBigDecimalColumn() {
		for (Column column : getColumns()) {
			if (column.isBigDecimal()) {
				return true;
			}
		}
		return false;
	}

	public String getTableComment() {
		return tableComment;
	}

	public void setTableComment(String tableComment) {
		this.tableComment = tableComment;
	}
}
