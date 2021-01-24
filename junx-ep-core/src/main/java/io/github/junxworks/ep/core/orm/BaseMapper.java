/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  BaseMapper.java   
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

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

/**
 * {类的详细说明}.
 *
 * @ClassName:  BaseMapper
 * @author: Michael
 * @date:   2020-7-19 12:18:37
 * @since:  v1.0
 */
public interface BaseMapper {

	/**
	 * Insert without null.
	 *
	 * @param entity the entity
	 * @return the int
	 */
	@InsertProvider(type = MybatisObjectSqlProvider.class, method = "insertWithoutNull")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	public int insertWithoutNull(Object entity);

	/**
	 * Insert batch.
	 *
	 * @param entities the entities
	 * @return the int
	 */
	@InsertProvider(type = MybatisObjectSqlProvider.class, method = "insertBatch")
	public int insertBatch(List<?> entities);

	/**
	 * Insert with null.
	 *
	 * @param entity the entity
	 * @return the int
	 */
	@InsertProvider(type = MybatisObjectSqlProvider.class, method = "insertWithNull")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	public int insertWithNull(Object entity);

	/**
	 * Update without null.
	 *
	 * @param entity the entity
	 * @return the int
	 */
	@UpdateProvider(type = MybatisObjectSqlProvider.class, method = "updateWithoutNull")
	public int updateWithoutNull(Object entity);

	/**
	 * Update with null.
	 *
	 * @param entity the entity
	 * @return the int
	 */
	@UpdateProvider(type = MybatisObjectSqlProvider.class, method = "updateWithNull")
	public int updateWithNull(Object entity);

	/**
	 * Delete by PK.
	 *
	 * @param entity the entity
	 * @return the int
	 */
	@DeleteProvider(type = MybatisObjectSqlProvider.class, method = "deleteByPK")
	public int deleteByID(Object entity);

	/**
	 * Delete by PK.
	 *
	 * @param <T> the generic type
	 * @param entity the entity
	 * @param id the id
	 * @return the int
	 */
	@DeleteProvider(type = MybatisObjectSqlProvider.class, method = "deleteByID")
	public int deleteOneById(@Param("class")Class entity, @Param("id") Long id);

	/**
	 * 返回 one by PK 属性.
	 *
	 * @param <T> the generic type
	 * @param entity the entity
	 * @param id the id
	 * @return one by PK 属性
	 */
	@SelectProvider(type = MybatisObjectSqlProvider.class, method = "getOneByPK")
	public Map<String,Object> selectMapByID(@Param("class")Class entity, @Param("id") Long id);

	/**
	 * 指定pkName获取实体
	 *
	 * @param <T> the generic type
	 * @param entity the entity
	 * @param pkName the pk name
	 * @param id the id
	 * @return one by PK 属性
	 */
	@SelectProvider(type = MybatisObjectSqlProvider.class, method = "getOneByPKNameAndValue")
	public Map<String,Object> selectMapByPKNameAndValue(@Param("class")Class entity,  @Param("pkName")String pkName, @Param("id") Long id);
}
