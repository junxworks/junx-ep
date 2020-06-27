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

public class ConditionResolver {
	public static Map<Class<?>, ConditionDescription> conditions = Maps.newConcurrentMap();

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
