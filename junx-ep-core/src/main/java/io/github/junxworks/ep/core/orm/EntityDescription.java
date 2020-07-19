/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  EntityDescription.java   
 * @Package io.github.junxworks.ep.core.orm   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2020-7-19 12:18:37   
 * @version V1.0 
 * @Copyright: 2020 Junxworks. All rights reserved. 
 * 注意：
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.core.orm;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;

/**
 * {类的详细说明}.
 *
 * @ClassName:  EntityDescription
 * @author: Michael
 * @date:   2020-7-19 12:18:37
 * @since:  v1.0
 */
public class EntityDescription {
	
	/** table name. */
	private String tableName;
	
	/** pk name. */
	private String pkName;
	
	/** db fields exclude PK. */
	private List<String> dbFieldsExcludePK;
	
	/** db feilds map. */
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

	/**
	 * Adds the field.
	 *
	 * @param dbFieldName the db field name
	 * @param field the field
	 */
	public void addField(String dbFieldName, Field field) {
		dbFeildsMap.put(dbFieldName, field);
	}

	/**
	 * Db field name to java field name.
	 *
	 * @param dbFieldName the db field name
	 * @return the string
	 */
	public String dbFieldNameToJavaFieldName(String dbFieldName) {
		return dbFeildsMap.get(dbFieldName).getName();
	}

	/**
	 * Db field name to java field.
	 *
	 * @param dbFieldName the db field name
	 * @return the field
	 */
	public Field dbFieldNameToJavaField(String dbFieldName) {
		return dbFeildsMap.get(dbFieldName);
	}
}
