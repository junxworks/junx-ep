/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  RoleMapper.java   
 * @Package io.github.junxworks.ep.sys.mapper   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2021-1-24 15:35:42   
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
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import io.github.junxworks.ep.core.orm.BaseMapper;
import io.github.junxworks.ep.sys.dto.RoleConditionDto;
import io.github.junxworks.ep.sys.entity.EpSRole;
import io.github.junxworks.ep.sys.vo.RoleInfoVo;
import io.github.junxworks.ep.sys.vo.UserInfoVo;

/**
 * {类的详细说明}.
 *
 * @ClassName:  RoleMapper
 * @author: Michael
 * @date:   2020-7-19 12:17:48
 * @since:  v1.0
 */
@Mapper
public interface RoleMapper extends BaseMapper{

	/**
	 * Select by id.
	 *
	 * @param id the id
	 * @return the s role
	 */
	@Select("select * from ep_s_role where id=#{id} and status=0")
	EpSRole selectById(Long id);

	/**
	 * Select by user id.
	 *
	 * @param userId the user id
	 * @return the list
	 */
	@Select("select r.* from ep_s_role r inner join ep_s_user_role ur on ur.role_id = r.id where r.status=0 and ur.user_id=#{userId}")
	List<RoleInfoVo> selectByUserId(Long userId);

	/**
	 * Select ids by user id.
	 *
	 * @param userId the user id
	 * @return the list
	 */
	@Select("select r.id from ep_s_role r inner join ep_s_user_role ur on ur.role_id = r.id where r.status=0 and ur.user_id=#{userId}")
	List<Long> selectIdsByUserId(Long userId);
	
	/**
	 * Select by name.
	 *
	 * @param roleName the role name
	 * @return the s role
	 */
	@Select("select * from ep_s_role where role_name=#{roleName} and status=0")
	EpSRole selectByName(String roleName);

	/**
	 * Select by tag.
	 *
	 * @param roleTag the role tag
	 * @return the s role
	 */
	@Select("select * from ep_s_role where role_tag=#{roleTag} and status=0")
	EpSRole selectByTag(String roleTag);
	
	/**
	 * Delete by id.
	 *
	 * @param id the id
	 * @return the int
	 */
	@Delete("update ep_s_role set status=-1 where id=#{id}")
	int deleteById(Long id);

	/**
	 * Select all.
	 *
	 * @param query the query
	 * @return the page
	 */
	@Select("<script>" +
			"select * from ep_s_role where status=0 " +
			"<if test='roleName!=null and roleName.length>0'> " +
				" and role_name like CONCAT('%',#{roleName},'%') " +
			"</if>" +
			"</script>")
	List<RoleInfoVo> selectAll(RoleConditionDto condition);

	/**
	 * Find user list by role tag.
	 *
	 * @param roleTag the role tag
	 * @return the list
	 */
	@Select("select distinct u.* from ep_s_role r,ep_s_user_role ur,ep_s_user u where ur.role_id = r.id and r.status=0 and ur.user_id=u.id and u.status=0 and r.role_tag=#{roleTag}")
	List<UserInfoVo> findUserListByRoleTag(@Param("roleTag")String roleTag);
}