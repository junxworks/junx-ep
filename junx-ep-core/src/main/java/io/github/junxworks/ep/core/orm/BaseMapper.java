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
 * 数据库操作Mapper基类
 *
 * @ClassName:  BaseMapper
 * @author: Michael
 * @date:   2020-7-19 12:18:37
 * @since:  v1.0
 */
public interface BaseMapper {

	/**
	 * 解析实体类并将其写入到数据库中，排除空值字段写入，Object必须含有io.github.junxworks.ep.core.orm.annotations.Table注解。
	 * 写入数据后，系统默认生成ID字段回写，ID字段默认名称为id，如果id不是这个字段的，请不要调用此方法，将本方法复制到自己的mapper中，修改ID字段名即可，如下：
	 * 
	 * 	@InsertProvider(type = MybatisObjectSqlProvider.class, method = "insertWithoutNull")
	 * 	@Options(useGeneratedKeys = true, keyProperty = "xxxId")
	 *  public int insertWithoutNull(Object entity);{
	 *  	......
	 *  
	 *  }
	 *
	 * @param entity 需要写入数据库的实体对象
	 * @return 成功写入记录条数
	 */
	@InsertProvider(type = MybatisObjectSqlProvider.class, method = "insertWithoutNull")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	public int insertWithoutNull(Object entity);

	/**
	 * 将实体类批量写入数据库，会采用第一个实体类的表结构进行sql解析，不支持多种实体类混合写入。
	 *
	 * @param entities 需要写入数据库的实体对象集合
	 * @return 成功写入记录条数
	 */
	@InsertProvider(type = MybatisObjectSqlProvider.class, method = "insertBatch")
	public int insertBatch(List<?> entities);

	/**
	 * 解析实体类并将其写入到数据库中，保留空值字段写入，Object必须含有io.github.junxworks.ep.core.orm.annotations.Table注解。
	 * 写入数据后，系统默认生成ID字段回写，ID字段默认名称为id，如果id不是这个字段的，请不要调用此方法，将本方法复制到自己的mapper中，修改ID字段名即可，如下：
	 * 
	 * 	@InsertProvider(type = MybatisObjectSqlProvider.class, method = "insertWithNull")
	 * 	@Options(useGeneratedKeys = true, keyProperty = "xxxId")
	 *  public int insertWithNull(Object entity);{
	 *  	......
	 *  
	 *  }
	 *
	 * @param entity 需要写入数据库的实体对象
	 * @return 成功写入记录条数
	 */
	@InsertProvider(type = MybatisObjectSqlProvider.class, method = "insertWithNull")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	public int insertWithNull(Object entity);

	/**
	 * 根据主键更新一条记录，排除空值字段写入，实体类必须要包含io.github.junxworks.ep.core.orm.annotations.Table注解，io.github.junxworks.ep.core.orm.annotations.PrimaryKey主键或id属性。
	 *
	 * @param entity 需要更新数据库的实体对象
	 * @return 成功更新的记录条数
	 */
	@UpdateProvider(type = MybatisObjectSqlProvider.class, method = "updateWithoutNull")
	public int updateWithoutNull(Object entity);

	/**
	 * 根据主键更新一条记录，保留排除空值字段写入，实体类必须要包含io.github.junxworks.ep.core.orm.annotations.Table注解，io.github.junxworks.ep.core.orm.annotations.PrimaryKey主键或id属性。
	 *
	 * @param entity 需要更新数据库的实体对象
	 * @return 成功更新的记录条数
	 */
	@UpdateProvider(type = MybatisObjectSqlProvider.class, method = "updateWithNull")
	public int updateWithNull(Object entity);

	/**
	 * 根据主键删除一条记录，实体类必须要包含io.github.junxworks.ep.core.orm.annotations.Table注解，io.github.junxworks.ep.core.orm.annotations.PrimaryKey主键或id属性。
	 *
	 * @param entity 需要删除数据库的实体对象
	 * @return 成功删除的记录条数
	 */
	@DeleteProvider(type = MybatisObjectSqlProvider.class, method = "deleteByPK")
	public int deleteByPK(Object entity);

	/**
	 * 根据实体类class与id，删除一条数据库记录，实体类必须要包含io.github.junxworks.ep.core.orm.annotations.Table注解，io.github.junxworks.ep.core.orm.annotations.PrimaryKey主键或id属性。
	 *
	 * @param entityClass 需要删除数据库的实体对象类
	 * @param id 关键字值
	 * @return 成功删除的记录条数
	 */
	@DeleteProvider(type = MybatisObjectSqlProvider.class, method = "deleteByID")
	public int deleteOneById(@SuppressWarnings("rawtypes") @Param("class")Class entityClass, @Param("id") Long id);

	/**
	 * 根据实体类class与id，查询一条数据库记录，实体类必须要包含io.github.junxworks.ep.core.orm.annotations.Table注解，io.github.junxworks.ep.core.orm.annotations.PrimaryKey主键或id属性。
	 *
	 * @param entity 需要查询数据库的实体对象类
	 * @param id 关键字值
	 * @return 查询结果 
	 */
	@SelectProvider(type = MybatisObjectSqlProvider.class, method = "getOneByPK")
	public Map<String,Object> selectMapByID(@SuppressWarnings("rawtypes") @Param("class")Class entityClass, @Param("id") Long id);

	/**
	 * 根据实体类class与id，查询一条数据库记录，实体类必须要包含io.github.junxworks.ep.core.orm.annotations.Table注解，io.github.junxworks.ep.core.orm.annotations.PrimaryKey主键或id属性。
	 *
	 * @param entity 需要查询数据库的实体对象类
	 * @param pkName 关键属性列名
	 * @param id 关键字值
	 * @return 查询结果
	 */
	@SelectProvider(type = MybatisObjectSqlProvider.class, method = "getOneByPKNameAndValue")
	public Map<String,Object> selectMapByPKNameAndValue(@SuppressWarnings("rawtypes") @Param("class")Class entityClass,  @Param("pkName")String pkName, @Param("id") Long id);
}
