/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  DataMapper.java   
 * @Package io.github.junxworks.ep.sys.mapper   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2021-1-24 15:35:41   
 * @version V1.0 
 * @Copyright: 2021 Junxworks. All rights reserved. 
 * 注意：
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.sys.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import io.github.junxworks.ep.core.orm.BaseMapper;
import io.github.junxworks.ep.sys.entity.EpSSql;

/**
 * {类的详细说明}.
 *
 * @ClassName:  DataMapper
 * @author: Michael
 * @date:   2020-7-19 12:17:48
 * @since:  v1.0
 */
@Mapper
public interface DataMapper extends BaseMapper {
	
	/**
	 * 返回 sql by uid 属性.
	 *
	 * @param uid the uid
	 * @return sql by uid 属性
	 */
	@Select("select * from ep_s_sql where status=0 and uid=#{uid}")
	EpSSql getSqlByUid(@Param("uid") String uid);
}