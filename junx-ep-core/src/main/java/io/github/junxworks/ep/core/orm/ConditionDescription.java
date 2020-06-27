package io.github.junxworks.ep.core.orm;

import java.util.Map;

import com.google.common.collect.Maps;

public class ConditionDescription {
	private String tableName;

	private Map<String, String> java2DBFeildMap = Maps.newHashMap();

	private Map<String, String> compareMap = Maps.newHashMap();

	private Map<String, String> expressionMap = Maps.newHashMap();

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Map<String, String> getJava2DBFeildMap() {
		return java2DBFeildMap;
	}

	public void setJava2DBFeildMap(Map<String, String> java2dbFeildMap) {
		java2DBFeildMap = java2dbFeildMap;
	}

	public void addField(String javaFieldName, String dbFieldName) {
		java2DBFeildMap.put(javaFieldName, dbFieldName);
	}

	public void addCompare(String javaFieldName, String compare) {
		compareMap.put(javaFieldName, compare);
	}

	public void addExpression(String javaFieldName, String expression) {
		expressionMap.put(javaFieldName, expression);
	}

	public String javaFieldNameToDBFieldName(String javaFieldName) {
		return java2DBFeildMap.get(javaFieldName);
	}

	public String getCompare(String javaFieldName) {
		return compareMap.getOrDefault(javaFieldName, CompareOperators.EQUAL);
	}

	public String getExpression(String javaFieldName) {
		return expressionMap.get(javaFieldName);
	}

}
