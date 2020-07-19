/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  ConditionDescription.java   
 * @Package io.github.junxworks.ep.core.orm   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2020-7-19 12:18:36   
 * @version V1.0 
 * @Copyright: 2020 Junxworks. All rights reserved. 
 * 注意：
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.core.orm;

import java.util.Map;

import com.google.common.collect.Maps;

/**
 * {类的详细说明}.
 *
 * @ClassName:  ConditionDescription
 * @author: Michael
 * @date:   2020-7-19 12:18:36
 * @since:  v1.0
 */
public class ConditionDescription {
	
	/** table name. */
	private String tableName;

	/** java 2 DB feild map. */
	private Map<String, String> java2DBFeildMap = Maps.newHashMap();

	/** compare map. */
	private Map<String, String> compareMap = Maps.newHashMap();

	/** expression map. */
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

	/**
	 * Adds the field.
	 *
	 * @param javaFieldName the java field name
	 * @param dbFieldName the db field name
	 */
	public void addField(String javaFieldName, String dbFieldName) {
		java2DBFeildMap.put(javaFieldName, dbFieldName);
	}

	/**
	 * Adds the compare.
	 *
	 * @param javaFieldName the java field name
	 * @param compare the compare
	 */
	public void addCompare(String javaFieldName, String compare) {
		compareMap.put(javaFieldName, compare);
	}

	/**
	 * Adds the expression.
	 *
	 * @param javaFieldName the java field name
	 * @param expression the expression
	 */
	public void addExpression(String javaFieldName, String expression) {
		expressionMap.put(javaFieldName, expression);
	}

	/**
	 * Java field name to DB field name.
	 *
	 * @param javaFieldName the java field name
	 * @return the string
	 */
	public String javaFieldNameToDBFieldName(String javaFieldName) {
		return java2DBFeildMap.get(javaFieldName);
	}

	/**
	 * 返回 compare 属性.
	 *
	 * @param javaFieldName the java field name
	 * @return compare 属性
	 */
	public String getCompare(String javaFieldName) {
		return compareMap.getOrDefault(javaFieldName, CompareOperators.EQUAL);
	}

	/**
	 * 返回 expression 属性.
	 *
	 * @param javaFieldName the java field name
	 * @return expression 属性
	 */
	public String getExpression(String javaFieldName) {
		return expressionMap.get(javaFieldName);
	}

}
