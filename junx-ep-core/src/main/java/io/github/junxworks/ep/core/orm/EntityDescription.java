package io.github.junxworks.ep.core.orm;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;

public class EntityDescription {
	private String tableName;
	private String pkName;
	private List<String> dbFieldsExcludePK;
	private Map<String, Field> dbFeildsMap = Maps.newHashMap();

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getPkName() {
		return pkName;
	}

	public void setPkName(String pkName) {
		this.pkName = pkName;
	}

	public List<String> getDbFieldsExcludePK() {
		return dbFieldsExcludePK;
	}

	public void setDbFieldsExcludePK(List<String> dbFieldsExcludePK) {
		this.dbFieldsExcludePK = dbFieldsExcludePK;
	}

	public Map<String, Field> getDbFeildsMap() {
		return dbFeildsMap;
	}

	public void setDbFeildsMap(Map<String, Field> dbFeildsMap) {
		this.dbFeildsMap = dbFeildsMap;
	}

	public void addField(String dbFieldName, Field field) {
		dbFeildsMap.put(dbFieldName, field);
	}

	public String dbFieldNameToJavaFieldName(String dbFieldName) {
		return dbFeildsMap.get(dbFieldName).getName();
	}

	public Field dbFieldNameToJavaField(String dbFieldName) {
		return dbFeildsMap.get(dbFieldName);
	}
}
