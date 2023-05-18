/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  SqlGenerator.java   
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

import java.text.MessageFormat;
import java.util.List;

import org.apache.ibatis.jdbc.SQL;

import com.google.common.collect.Lists;

import io.github.junxworks.junx.core.exception.BaseRuntimeException;
import io.github.junxworks.junx.core.util.ObjectUtils;
import io.github.junxworks.junx.core.util.StringUtils;
import net.vidageek.mirror.get.dsl.GetterHandler;
import net.vidageek.mirror.reflect.dsl.ReflectionHandler;

/**
 * {类的详细说明}.
 *
 * @ClassName:  SqlGenerator
 * @author: Michael
 * @date:   2020-7-19 12:18:37
 * @since:  v1.0
 */
public class SqlGenerator {

	/**
	 * Insert SQL.
	 *
	 * @param entity the entity
	 * @param withNull the with null
	 * @return the string
	 * @throws Exception the exception
	 */
	public static final String insertSQL(Object entity, boolean withNull) throws Exception {
		EntityDescription ed = EntityResolver.resolveClass(entity.getClass());
		SQL sql = new SQL();
		sql.INSERT_INTO(ed.getTableName());
		String pkName = ed.getPkName();
		String pkJavaFieldName = ed.dbFieldNameToJavaFieldName(pkName);
		// 首先判断主键是否有传值
		GetterHandler getter = ObjectUtils.mirror().on(entity).get();
		Object pkValue = getter.field(pkJavaFieldName);
		if (pkValue != null) {
			sql.VALUES(pkName, "#{" + pkJavaFieldName + "}");
		}
		ed.getDbFieldsExcludePK().stream().forEach(f -> {
			String javaFieldName = ed.dbFieldNameToJavaFieldName(f);
			Object fValue = getter.field(javaFieldName);
			boolean append = false;
			if (fValue == null) {
				if (withNull) {
					append = true;
				}
			} else {
				append = true;
			}
			if (append) {
				sql.VALUES(f, "#{" + ed.dbFieldNameToJavaFieldName(f) + "}");
			}
		});
		return sql.toString();
	}

	public static final String insertBatchSQL(List<?> entities) throws Exception {
		Object entity = entities.get(0);
		EntityDescription ed = EntityResolver.resolveClass(entity.getClass());
		String pkName = ed.getPkName();
		String pkJavaFieldName = ed.dbFieldNameToJavaFieldName(pkName);
		//通过传值判断是否要写入主键
		GetterHandler getter = ObjectUtils.mirror().on(entity).get();
		Object pkValue = getter.field(pkJavaFieldName);
		boolean writePrimaryKey = false;
		if (pkValue != null) {
			writePrimaryKey = true;
		}
		List<String> columns = ed.getDbFieldsExcludePK();
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO ").append(ed.getTableName());
		sb.append(" (`");
		if (writePrimaryKey) {
			sb.append(pkName).append("`,`");
		}
		sb.append(StringUtils.join("`,`", columns.toArray(new String[0]))).append("`) ");
		sb.append(" VALUES ");
		List<String> v = Lists.newArrayList();
		if (writePrimaryKey) {
			v.add("#'{'list[{0}]." + pkJavaFieldName + "}");
		}
		for (int i = 0, len = columns.size(); i < len; i++) {
			String f = columns.get(i);
			v.add("#'{'list[{0}]." + ed.dbFieldNameToJavaFieldName(f) + "}");
		}
		MessageFormat mf = new MessageFormat("(" + StringUtils.join(",", v.toArray(new String[0])) + ")");
		for (int i = 0, len = entities.size(); i < len; i++) {
			sb.append(mf.format(new Object[] { i }));
			if (i < len - 1) {
				sb.append(",");
			}
		}
		return sb.toString();
	}

	/**
	 * Update SQL.
	 *
	 * @param entity the entity
	 * @param withNull the with null
	 * @return the string
	 * @throws Exception the exception
	 */
	public static final String updateSQL(Object entity, boolean withNull) throws Exception {
		EntityDescription ed = EntityResolver.resolveClass(entity.getClass());
		SQL sql = new SQL();
		sql.UPDATE(ed.getTableName());
		String pkName = ed.getPkName();
		String pkJavaFieldName = ed.dbFieldNameToJavaFieldName(pkName);
		GetterHandler getter = ObjectUtils.mirror().on(entity).get();
		Object pkValue = getter.field(pkJavaFieldName);
		if (pkValue == null) {
			throw new BaseRuntimeException("Null primary key value when update entity.");
		}
		StringBuilder sets = new StringBuilder();
		ed.getDbFieldsExcludePK().stream().forEach(f -> {
			String javaFieldName = ed.dbFieldNameToJavaFieldName(f);
			Object fValue = getter.field(javaFieldName);
			boolean append = false;
			if (fValue == null) {
				if (withNull) {
					append = true;
				}
			} else {
				append = true;
			}
			if (append) {
				if (sets.length() > 0) {
					sets.append(",");
				}
				sets.append(f).append("=").append("#{").append(ed.dbFieldNameToJavaFieldName(f)).append("}");
			}
		});
		sql.SET(sets.toString());
		sql.WHERE(pkName + "= #{" + pkJavaFieldName + "}");
		return sql.toString();
	}

	public static final String deleteSQL(Object entity) throws Exception {
		EntityDescription ed = EntityResolver.resolveClass(entity.getClass());
		SQL sql = new SQL();
		sql.DELETE_FROM(ed.getTableName());
		String pkName = ed.getPkName();
		String pkJavaFieldName = ed.dbFieldNameToJavaFieldName(pkName);
		GetterHandler getter = ObjectUtils.mirror().on(entity).get();
		Object pkValue = getter.field(pkJavaFieldName);
		if (pkValue == null) {
			throw new BaseRuntimeException("Null primary key value when delete entity.");
		}
		sql.WHERE(pkName + "= #{" + pkJavaFieldName + "}");
		return sql.toString();
	}

	@SuppressWarnings("rawtypes")
	public static final String deleteSQL(Class clazz, Long id) throws Exception {
		EntityDescription ed = EntityResolver.resolveClass(clazz);
		SQL sql = new SQL();
		sql.DELETE_FROM(ed.getTableName());
		String pkName = ed.getPkName();
		if (id == null) {
			throw new BaseRuntimeException("Null primary key value when delete entity.");
		}
		sql.WHERE(pkName + "= #{id}");
		return sql.toString();
	}

	@SuppressWarnings("rawtypes")
	public static final String getOneSQL(Class clazz, Long id) throws Exception {
		EntityDescription ed = EntityResolver.resolveClass(clazz);
		SQL sql = new SQL();
		sql.SELECT("*").FROM(ed.getTableName());
		String pkName = ed.getPkName();
		if (id == null) {
			throw new BaseRuntimeException("Null primary key value when get one entity.");
		}
		sql.WHERE(pkName + "= #{id}");
		return sql.toString();
	}

	@SuppressWarnings("rawtypes")
	public static final String getOneSQL(Class clazz, String pkName, Object pkValue) throws Exception {
		EntityDescription ed = EntityResolver.resolveClass(clazz);
		SQL sql = new SQL();
		sql.SELECT("*").FROM(ed.getTableName());
		if (pkValue == null) {
			throw new BaseRuntimeException("Null primary key value when get one entity.");
		}
		sql.WHERE(pkName + "= #{pkValue}");
		return sql.toString();
	}

	/**
	 * Query SQL.
	 *
	 * @param entityDto the entity dto
	 * @return the string
	 * @throws Exception the exception
	 */
	public static final String querySQL(Object entityDto) throws Exception {
		final ConditionDescription cd = ConditionResolver.resolveClass(entityDto.getClass());
		SQL sql = new SQL();
		sql.SELECT("*");
		sql.FROM(cd.getTableName());
		final GetterHandler getter = ObjectUtils.mirror().on(entityDto).get();
		ReflectionHandler<?> reflectHandler = ObjectUtils.mirror().on(entityDto.getClass()).reflect();
		final List<String> conditions = Lists.newArrayList();
		cd.getJava2DBFeildMap().entrySet().stream().forEach(en -> {
			String javaFieldName = en.getKey();
			Object value = getter.field(javaFieldName);
			if (value != null) {
				if (String.class == reflectHandler.field(javaFieldName).getType()) {
					if (StringUtils.isNull(String.valueOf(value))) {
						return;
					}
				}
				StringBuilder condition = new StringBuilder();
				String dbFieldName = en.getValue();
				String expression = cd.getExpression(javaFieldName);
				String compare = cd.getCompare(javaFieldName);
				if (CompareOperators.GRATER.equals(compare) || CompareOperators.GRATER_EQUAL.equals(compare) || CompareOperators.LESS.equals(compare) || CompareOperators.LESS_EQUAL.equals(compare)) {
					compare = concatCDATA(compare);
				}
				condition.append(dbFieldName).append(" ");
				condition.append(compare).append(" ");
				if (StringUtils.notNull(expression)) {
					condition.append(expression);
				} else {
					if (CompareOperators.LIKE.equalsIgnoreCase(cd.getCompare(javaFieldName))) {
						condition.append(" concat('%',#{").append(javaFieldName).append("},'%') ");
					} else if (CompareOperators.LIKE_R.equalsIgnoreCase(cd.getCompare(javaFieldName))) {
						condition.append(" concat(#{").append(javaFieldName).append("},'%') ");
					} else if (CompareOperators.LIKE_L.equalsIgnoreCase(cd.getCompare(javaFieldName))) {
						condition.append(" concat('%',#{").append(javaFieldName).append("}) ");
					} else {
						condition.append(" #{").append(javaFieldName).append("} ");
					}
				}
				conditions.add(condition.toString());
			}
		});
		sql.WHERE(conditions.toArray(new String[0]));
		return sql.toString();
	}

	/**
	 * Concat CDATA.
	 *
	 * @param str the str
	 * @return the string
	 */
	private static String concatCDATA(String str) {
		return "<![CDATA[" + str + "]]>";
	}
}
