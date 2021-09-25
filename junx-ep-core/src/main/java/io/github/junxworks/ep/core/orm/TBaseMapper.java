package io.github.junxworks.ep.core.orm;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

public interface TBaseMapper<T> extends BaseMapper {

	@SelectProvider(type = MybatisObjectSqlProvider.class, method = "getOneByPK")
	T selectEntityByID(@Param("class") Class<T> entity, @Param("id") Long id);

	@SelectProvider(type = MybatisObjectSqlProvider.class, method = "getOneByPKNameAndValue")
	T selectEntityByPKNameAndValue(@Param("class")Class<T> entity,  @Param("pkName")String pkName, @Param("id") Long id);
}
