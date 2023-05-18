package io.github.junxworks.ep.codegen.core;

public class Column implements java.io.Serializable, Cloneable {

	private static final long serialVersionUID = 241462432312500261L;

	private String columnName;

	private boolean isPrimaryKey;

	private boolean isForeignKey;

	private String targetTableName;

	private String targetColumnName;

	private int size;

	private int decimalDigits;

	private boolean nullable;

	private boolean unique;

	private String defaultValue;

	private String remarks;

	protected int jdbcType;

	protected String jdbcTypeName;

	private String javaProperty;

	private String javaType;

	private String fullJavaType;

	private String editor;
	
	private boolean autoIncrement;

	

	public Column(String columnName) {
		this.columnName = columnName;
		this.javaProperty = GenerateHelper.getCamelCaseString(columnName, false);
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getDecimalDigits() {
		return decimalDigits;
	}

	public void setDecimalDigits(int decimalDigits) {
		this.decimalDigits = decimalDigits;
	}

	public boolean isNullable() {
		return nullable;
	}

	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}

	public String getDefaultValue() {
		return defaultValue == null ? "" : defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getRemarks() {
		return remarks == null ? "" : remarks;
	}

	public boolean isHasRemarks() {
		return GenerateHelper.isNotEmpty(remarks);
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getRemarksUnicode() {
		return GenerateHelper.toUnicodeString(getRemarks());
	}

	public String getJavaType() {
		return javaType;
	}

	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}

	public String getJavaProperty() {
		return javaProperty;
	}

	public int getJdbcType() {
		return jdbcType;
	}

	public void setJdbcType(int jdbcType) {
		this.jdbcType = jdbcType;
	}

	public String getJdbcTypeName() {
		return jdbcTypeName;
	}

	public void setJdbcTypeName(String jdbcTypeName) {
		this.jdbcTypeName = jdbcTypeName;
	}

	public boolean isString() {
		return JavaTypeResolver.isString(javaType);
	}

	public boolean isFloat() {
		return JavaTypeResolver.isFloat(javaType);
	}

	public boolean isInteger() {
		return JavaTypeResolver.isInteger(javaType);
	}

	public boolean isBigDecimal() {
		return JavaTypeResolver.isBigDecimal(javaType);
	}

	public boolean isBoolean() {
		return JavaTypeResolver.isBoolean(javaType);
	}

	public boolean isDate() {
		return JavaTypeResolver.isDate(javaType);
	}

	public boolean isBLOB() {
		String typeName = getJdbcTypeName();

		return "BINARY".equals(typeName) || "BLOB".equals(typeName) //$NON-NLS-1$ //$NON-NLS-2$
				|| "CLOB".equals(typeName) || "LONGVARBINARY".equals(typeName) //$NON-NLS-1$ //$NON-NLS-2$
				|| "LONGVARCHAR".equals(typeName) || "VARBINARY".equals(typeName); //$NON-NLS-1$ //$NON-NLS-2$
	}

	public boolean isUnique() {
		return unique;
	}

	public void setUnique(boolean unique) {
		this.unique = unique;
	}

	public boolean isPrimaryKey() {
		return isPrimaryKey;
	}

	public void setPrimaryKey(boolean isPrimaryKey) {
		this.isPrimaryKey = isPrimaryKey;
	}

	public boolean isForeignKey() {
		return isForeignKey;
	}

	public void setForeignKey(boolean isForeignKey) {
		this.isForeignKey = isForeignKey;
	}

	public void setJavaProperty(String javaProperty) {
		this.javaProperty = javaProperty;
	}

	public String getGetterMethodCamelName() {
		if (JavaTypeResolver.isBoolean(javaType)) {
			return "is" + GenerateHelper.getCamelCaseString(columnName, true);
		} else {
			return "get" + GenerateHelper.getCamelCaseString(columnName, true);
		}
	}

	public String getSetterMethodCamelName() {
		return "set" + GenerateHelper.getCamelCaseString(columnName, true);
	}

	public String getGetterMethodName() {
		if (JavaTypeResolver.isBoolean(javaType)) {
			return "is" + GenerateHelper.capitalize(columnName);
		} else {
			return "get" + GenerateHelper.capitalize(columnName);
		}
	}

	public String getSetterMethodName() {
		return "set" + GenerateHelper.capitalize(columnName);
	}

	public String getFullJavaType() {
		return fullJavaType;
	}

	public void setFullJavaType(String fullJavaType) {
		this.fullJavaType = fullJavaType;
	}

	public String getEditor() {
		return editor;
	}

	public void setEditor(String editor) {
		this.editor = editor;
	}

	public String getTargetTableName() {
		return targetTableName;
	}

	public void setTargetTableName(String targetTableName) {
		this.targetTableName = targetTableName;
	}

	public String getTargetColumnName() {
		return targetColumnName;
	}

	public void setTargetColumnName(String targetColumnName) {
		this.targetColumnName = targetColumnName;
	}
	
	public boolean isAutoIncrement() {
		return autoIncrement;
	}

	public void setAutoIncrement(boolean autoIncrement) {
		this.autoIncrement = autoIncrement;
	}

}
