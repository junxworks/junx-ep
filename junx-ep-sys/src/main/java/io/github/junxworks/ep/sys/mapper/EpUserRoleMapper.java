/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  UserRoleMapper.java   
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

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import io.github.junxworks.ep.core.orm.BaseMapper;
import io.github.junxworks.ep.sys.entity.EpSUserRole;

/**
 * {类的详细说明}.
 *
 * @ClassName:  UserRoleMapper
 * @author: Michael
 * @date:   2020-7-19 12:17:47
 * @since:  v1.0
 */
@Mapper
public interface EpUserRoleMapper extends BaseMapper{

	/**
	 * Select by id.
	 *
	 * @param id the id
	 * @return the s user role
	 */
	@Select("select * from ep_s_user_role where id=#{id}")
	public EpSUserRole selectById(Long id);

	/**
	 * Select by user id.
	 *
	 * @param userId the user id
	 * @return the list
	 */
	@Select("select * from ep_s_user_role where user_id=#{userId}")
	List<EpSUserRole> selectByUserId(Long userId);

	/**
	 * Delete by id.
	 *
	 * @param id the id
	 * @return the int
	 */
	@Delete("delete from ep_s_user_role where id=#{id}")
	public int deleteById(Long id);

	/**
	 * Delete by user id.
	 *
	 * @param userId the user id
	 * @return the int
	 */
	@Delete("delete from ep_s_user_role where user_id=#{userId}")
	int deleteByUserId(Long userId);


}