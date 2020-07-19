/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  ConditionResolver.java   
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
import java.util.Map;

import com.google.common.collect.Maps;

import io.github.junxworks.ep.core.orm.annotations.Condition;
import io.github.junxworks.ep.core.orm.annotations.NotCondition;
import io.github.junxworks.ep.core.orm.annotations.Table;
import io.github.junxworks.junx.core.exception.BaseRuntimeException;
import io.github.junxworks.junx.core.util.ObjectUtils;
import io.github.junxworks.junx.core.util.StringUtils;
import net.vidageek.mirror.list.dsl.MirrorList;

/**
 * {类的详细说明}.
 *
 * @ClassName:  ConditionResolver
 * @author: Michael
 * @date:   2020-7-19 12:18:37
 * @since:  v1.0
 */
public class ConditionResolver {
	
	/** conditions. */
	public static Map<Class<?>, ConditionDescription> conditions = Maps.newConcurrentMap();

	/**
	 * Resolve class.
	 *
	 * @param clazz the clazz
	 * @return the condition description
	 * @throws Exception the exception
	 */
	public static final ConditionDescription resolveClass(Class<?> clazz) throws Exception {
		ConditionDescription cached = conditions.get(clazz);
		if (cached != null) {
			return cached;
		} else {
			final ConditionDescription cd = new ConditionDescription();
			Table table = clazz.getAnnotation(Table.class);
			if (table == null) {
				throw new BaseRuntimeException("Condition类@Table注解为空");
			}
			cd.setTableName(table.tableName());
			MirrorList<Field> fs = ObjectUtils.mirror().on(clazz).reflectAll().fields();
			String fieldName = null;
			String javaField = null;
			for (Field f : fs) {
				NotCondition notCondition = f.getAnnotation(NotCondition.class);
				if (notCondition != null) {
					continue;
				}
				javaField = f.getName();
				Condition condition = f.getAnnotation(Condition.class);
				if (condition != null) {
					fieldName = condition.field();
					cd.addCompare(javaField, condition.compare());
					if (StringUtils.notNull(condition.expression())) {
						cd.addExpression(javaField, condition.expression());
					}
				} else {
					fieldName = f.getName();
					cd.addCompare(javaField, CompareOperators.EQUAL);
				}
				cd.addField(javaField, fieldName);
			}
			conditions.put(clazz, cd);
			return cd;
		}
	}
}
