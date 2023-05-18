/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  UserMapper.java   
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
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import io.github.junxworks.ep.core.orm.BaseMapper;
import io.github.junxworks.ep.sys.dto.UserListConditionDto;
import io.github.junxworks.ep.sys.entity.EpSUser;
import io.github.junxworks.ep.sys.vo.UserInfoVo;

/**
 * {类的详细说明}.
 *
 * @ClassName:  UserMapper
 * @author: Michael
 * @date:   2020-7-19 12:17:47
 * @since:  v1.0
 */
@Mapper
public interface EpUserMapper extends BaseMapper{

	/**
	 * Select by id.
	 *
	 * @param id the id
	 * @return the user info vo
	 */
	@Select("select s.*,o.org_name from ep_s_user s left join ep_s_org o on s.org_no=o.org_no where s.id=#{id}")
	UserInfoVo selectById(Long id);

	/**
	 * Select by user name.
	 *
	 * @param mobile the mobile
	 * @return the s user
	 */
	@Select("select * from ep_s_user where username=#{username} and status=0")
	EpSUser selectByUsername(String username);

	/**
	 * Delete by id.
	 *
	 * @param id the id
	 * @return the int
	 */
	@Delete("delete from ep_s_user where id=#{id}")
	int deleteById(Long id);

	/**
	 * Select all.
	 *
	 * @param condition the condition
	 * @return the page
	 */
	@Select("<script>" +
			"SELECT u.*, o.org_name,r.role_name " + 
			"FROM ep_s_user u " + 
			"     LEFT JOIN ep_s_org o ON u.org_no = o.org_no " + 
			"     LEFT JOIN (SELECT ur.user_id, group_concat(r.role_name) role_name " + 
			"                FROM ep_s_user_role ur, ep_s_role r " + 
			"                WHERE ur.role_id = r.id AND r.status = 0 group by ur.user_id) r " + 
			"        ON u.id = r.user_id" +
			" where 1=1 "+ 
			"<if test='query!=null and query.length>0'> " +
			"     and (u.name like CONCAT('%',#{query},'%') or u.mobile = #{query}) " +
			"</if>" +
			"<if test='orgNo!=null and orgNo.length>0'> " +
			"     and u.org_no=#{orgNo} " +
			"</if>" + 
			"<if test='status!=null'> " +
			"     and u.status=#{status} " +
			"</if>" +
			"<if test='roleIds!=null'> " +
				" and exists (select 1 from ep_s_user_role ur where u.id=ur.user_id and ur.role_id IN "+ 
				"<foreach collection=\"roleIds\" item=\"roleId\" open=\"(\" close=\")\" separator=\",\">" + 
					"#{roleId}" + 
				"</foreach>"+ 
				")"+
			"</if>"+
			" order by u.id asc"+
			"</script>")
	List<UserInfoVo> selectAll(UserListConditionDto condition);

	/**
	 * 返回 user list by auth 属性.
	 *
	 * @param auth the auth
	 * @return user list by auth 属性
	 */
	@Select("<script>" +
			"SELECT DISTINCT u.* from ep_s_user u,ep_s_user_role ur,ep_s_role r,ep_s_role_menu rm,ep_s_menu m "
			+ " where u.status=0 "
			+ " and r.status=0 "
			+ " and m.status=0 "
			+ " and u.id=ur.user_id "
			+ " and ur.role_id = r.id "
			+ " and r.id=rm.role_id "
			+ " and rm.menu_id=m.id "+
			"<if test='auth!=null '> " +
			" and m.mark=#{auth} " +
			"</if>" +
			"</script>")
	List<UserInfoVo> getUserListByAuth(@Param("auth") String auth);
	 
	/**
	 * 返回 user list by role id 属性.
	 *
	 * @param roleId the role id
	 * @return user list by role id 属性
	 */
	@Select({"SELECT DISTINCT u.* from ep_s_user u,ep_s_user_role ur,ep_s_role r where r.id=#{id} and r.status = 0 and r.id=ur.role_id and ur.user_id=u.id and u.status=0 order by u.id asc"})
	List<UserInfoVo> getUserListByRoleId(@Param("id")Long roleId);
	
	/**
	 * 返回 user list by role tag 属性.
	 *
	 * @param roleTag the role tag
	 * @return user list by role tag 属性
	 */
	@Select({"SELECT DISTINCT u.* from ep_s_user u,ep_s_user_role ur,ep_s_role r where r.role_tag=#{tag} and r.status = 0 and r.id=ur.role_id and ur.user_id=u.id and u.status=0 order by u.id asc"})
	List<UserInfoVo> getUserListByRoleTag(@Param("tag")String roleTag);

}