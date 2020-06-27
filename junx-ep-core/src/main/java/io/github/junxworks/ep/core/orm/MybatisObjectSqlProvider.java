package io.github.junxworks.ep.core.orm;

import java.util.List;
import java.util.Map;

public class MybatisObjectSqlProvider {

	public String insertWithoutNull(Object entity) throws Exception {
		return SqlGenerator.insertSQL(entity, false);
	}

	@SuppressWarnings("rawtypes")
	public String insertBatch(Map params) throws Exception {
		if (params == null) {
			return "select 0";
		}
		List<?> entities = (List<?>) params.get("list");
		if (entities == null || entities.isEmpty()) {
			return "select 0";
		}
		return SqlGenerator.insertBatchSQL(entities);
	}

	public String insertWithNull(Object entity) throws Exception {
		return SqlGenerator.insertSQL(entity, true);
	}

	public String updateWithoutNull(Object entity) throws Exception {
		return SqlGenerator.updateSQL(entity, false);
	}

	public String updateWithNull(Object entity) throws Exception {
		return SqlGenerator.updateSQL(entity, true);
	}

	public String deleteByPK(Object entity) throws Exception {
		return SqlGenerator.deleteSQL(entity);
	}

	public String getOneByPK(Object entity) throws Exception {
		return SqlGenerator.getOneSQL(entity);
	}

	public String queryByCondition(Object entityDto) throws Exception {
		return SqlGenerator.querySQL(entityDto);
	}
}
