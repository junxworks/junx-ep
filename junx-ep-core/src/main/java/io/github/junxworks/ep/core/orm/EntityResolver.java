package io.github.junxworks.ep.core.orm;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import io.github.junxworks.ep.core.orm.annotations.Column;
import io.github.junxworks.ep.core.orm.annotations.NotColumn;
import io.github.junxworks.ep.core.orm.annotations.PrimaryKey;
import io.github.junxworks.ep.core.orm.annotations.Table;
import io.github.junxworks.junx.core.exception.BaseRuntimeException;
import io.github.junxworks.junx.core.util.ObjectUtils;
import io.github.junxworks.junx.core.util.StringUtils;
import net.vidageek.mirror.list.dsl.MirrorList;

public class EntityResolver {
	public static final String ID = "id";

	public static Map<Class<?>, EntityDescription> entities = Maps.newConcurrentMap();

	public static final EntityDescription resolveClass(Class<?> clazz) throws Exception {
		EntityDescription cached = entities.get(clazz);
		if (cached != null) {
			return cached;
		} else {
			final EntityDescription ed = new EntityDescription();
			Table table = clazz.getAnnotation(Table.class);
			if (table == null) {
				throw new BaseRuntimeException("实体类@Table注解为空");
			}
			ed.setTableName(table.tableName());
			MirrorList<Field> fs = ObjectUtils.mirror().on(clazz).reflectAll().fields();
			boolean hasID = false;
			String idName = "";
			List<String> fields = Lists.newArrayList();
			String fieldName = null;
			for (Field f : fs) {
				NotColumn notColumn = f.getAnnotation(NotColumn.class);
				if (notColumn != null) {
					continue;
				}
				String javaFieldName = f.getName();
				PrimaryKey pk = f.getAnnotation(PrimaryKey.class);
				if (pk != null) {
					ed.setPkName(javaFieldName);
				}
				if (ID.equalsIgnoreCase(javaFieldName)) {
					hasID = true;
					idName = f.getName();
				}
				Column column = f.getAnnotation(Column.class);

				if (column != null) {
					fieldName = column.name();
				} else {
					fieldName = javaFieldName;
				}
				fields.add(fieldName);
				ed.addField(fieldName, f);
			}
			if (StringUtils.isNull(ed.getPkName())) {
				if (hasID) {
					ed.setPkName(idName);
				} else {
					throw new BaseRuntimeException("实体类没有找到主键");
				}
			}
			fields.remove(ed.getPkName());
			ed.setDbFieldsExcludePK(fields);
			entities.put(clazz, ed);
			return ed;
		}
	}
}
