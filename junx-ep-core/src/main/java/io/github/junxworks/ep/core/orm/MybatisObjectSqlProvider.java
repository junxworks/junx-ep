/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  MybatisObjectSqlProvider.java   
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

import java.util.List;
import java.util.Map;

/**
 * {类的详细说明}.
 *
 * @ClassName:  MybatisObjectSqlProvider
 * @author: Michael
 * @date:   2020-7-19 12:18:36
 * @since:  v1.0
 */
public class MybatisObjectSqlProvider {

	/**
	 * Insert without null.
	 *
	 * @param entity the entity
	 * @return the string
	 * @throws Exception the exception
	 */
	public String insertWithoutNull(Object entity) throws Exception {
		return SqlGenerator.insertSQL(entity, false);
	}

	/**
	 * Insert batch.
	 *
	 * @param params the params
	 * @return the string
	 * @throws Exception the exception
	 */
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

	/**
	 * Insert with null.
	 *
	 * @param entity the entity
	 * @return the string
	 * @throws Exception the exception
	 */
	public String insertWithNull(Object entity) throws Exception {
		return SqlGenerator.insertSQL(entity, true);
	}

	/**
	 * Update without null.
	 *
	 * @param entity the entity
	 * @return the string
	 * @throws Exception the exception
	 */
	public String updateWithoutNull(Object entity) throws Exception {
		return SqlGenerator.updateSQL(entity, false);
	}

	/**
	 * Update with null.
	 *
	 * @param entity the entity
	 * @return the string
	 * @throws Exception the exception
	 */
	public String updateWithNull(Object entity) throws Exception {
		return SqlGenerator.updateSQL(entity, true);
	}

	/**
	 * Delete by PK.
	 *
	 * @param entity the entity
	 * @return the string
	 * @throws Exception the exception
	 */
	public String deleteByPK(Object entity) throws Exception {
		return SqlGenerator.deleteSQL(entity);
	}

	@SuppressWarnings("rawtypes")
	public String deleteByID(Map params) throws Exception {
		return SqlGenerator.deleteSQL((Class) params.get("class"), Long.valueOf(params.get("id").toString()));
	}

	@SuppressWarnings("rawtypes")
	public String getOneByPK(Map params) throws Exception {
		return SqlGenerator.getOneSQL((Class) params.get("class"), Long.valueOf(params.get("id").toString()));
	}

	@SuppressWarnings("rawtypes")
	public String getOneByPKNameAndValue(Map params) throws Exception {
		return SqlGenerator.getOneSQL((Class) params.get("class"), String.valueOf(params.get("pkName")), Long.valueOf(params.get("id").toString()));
	}

	/**
	 * Query by condition.
	 *
	 * @param entityDto the entity dto
	 * @return the string
	 * @throws Exception the exception
	 */
	public String queryByCondition(Object entityDto) throws Exception {
		return SqlGenerator.querySQL(entityDto);
	}
}
