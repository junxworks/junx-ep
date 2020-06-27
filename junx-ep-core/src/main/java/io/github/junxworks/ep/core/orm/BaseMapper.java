/*
 ***************************************************************************************
 * All rights Reserved, Designed By www.cqhyrc.com.cn
 * @Title:  BaseMapper.java   
 * @Package io.github.junxworks.ep.core.mybatis   
 * @Description: (用一句话描述该文件做什么)   
 * @author: AOC
 * @date:   2019-9-2 9:50:49   
 * @version V1.0 
 * @Copyright: 2019 重庆华宇集团. All rights reserved. 
 * 注意：本内容仅限于公司内部使用，禁止外泄以及用于其他的商业目
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.core.orm;

import java.util.List;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.UpdateProvider;

/**
 * 基础mapper类
 * 单表查询接口可以按下面样例：
 *  {@code  
	    @SelectProvider(type = MybatisObjectSqlProvider.class, method = "queryByCondition")
	    public VoObject queryByCondition(Object entityDto);
 *  }
 *  
 * @ClassName:  BaseMapper
 * @author: 王兴
 * @date:   2019-9-2 9:50:49
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
	 * Insert entities without null.
	 *
	 * @param entity the entity
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
	public int deleteByPK(Object entity);

	/*	@SelectProvider(type = MybatisObjectSqlProvider.class, method = "getOneByPK")
		public Map<String, Object> getOneByPK(Object entity);*/
}
