/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  UserMapper.java   
 * @Package io.github.junxworks.ep.sys.mapper   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2020-7-19 12:17:47   
 * @version V1.0 
 * @Copyright: 2020 Junxworks. All rights reserved. 
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
import org.apache.ibatis.annotations.Update;

import com.github.pagehelper.Page;
import io.github.junxworks.ep.core.orm.BaseMapper;
import io.github.junxworks.ep.sys.dto.UserPageable;
import io.github.junxworks.ep.sys.entity.SUser;
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
public interface UserMapper extends BaseMapper{

	/**
	 * Update user status.
	 *
	 * @param userId the user id
	 * @param status the status
	 * @return the int
	 */
	@Update(
			"<script>"
					+"update s_user set "
					+ " status=#{status}"
					+ " where id=#{userId}"
					+ "</script>"
	)
	public int updateUserStatus(@Param("userId")Long userId,@Param("status")Byte status);

	/**
	 * Update user pass.
	 *
	 * @param userId the user id
	 * @param password the password
	 * @return the int
	 */
	@Update(
			"<script>"
					+"update s_user set "
					+ " password=#{password}"
					+ " where id=#{userId}"
					+ "</script>"
	)
	int updateUserPass(@Param("userId")Long userId,@Param("password")String password);

	/**
	 * Select by id.
	 *
	 * @param id the id
	 * @return the user info vo
	 */
	@Select("select s.*,o.orgName from s_user s left join s_org o on s.orgNo=o.orgNo and o.status=0 where s.id=#{id}")
	UserInfoVo selectById(Long id);

	/**
	 * Select by user name.
	 *
	 * @param mobile the mobile
	 * @return the s user
	 */
	@Select("select * from s_user where userName=#{userName} and status=0")
	SUser selectByUserName(String mobile);

	/**
	 * Delete by id.
	 *
	 * @param id the id
	 * @return the int
	 */
	@Delete("delete from s_user where id=#{id}")
	int deleteById(Long id);

	/**
	 * Select all.
	 *
	 * @param condition the condition
	 * @return the page
	 */
	@Select("<script>" +
			"SELECT u.*, o.orgName,r.roleName " + 
			"FROM s_user u " + 
			"     LEFT JOIN s_org o ON u.orgNo = o.orgNo AND o.status = 0 " + 
			"     LEFT JOIN (SELECT ur.userId, group_concat(r.roleName) roleName " + 
			"                FROM s_user_role ur, s_role r " + 
			"                WHERE ur.roleId = r.id AND r.status = 0 group by ur.userId) r " + 
			"        ON u.id = r.userId" +
			"<if test='roleIds!=null'> " +
				",s_user_role ur"+
			"</if>"+
			" where 1=1 "+ 
			"<if test='query!=null and query.length>0'> " +
			"     and (u.name like CONCAT('%',#{query},'%') or u.mobile = #{query}) " +
			"</if>" +
			"<if test='orgNo!=null and orgNo.length>0'> " +
			"     and u.orgNo=#{orgNo} " +
			"</if>" +
			"<if test='roleIds!=null'> " +
				" and u.id=ur.userId"+ 
			    " and ur.roleId in " +
				"<foreach collection=\"roleIds\" item=\"roleId\" open=\"(\" close=\")\" separator=\",\">" + 
					"#{roleId}" + 
				"</foreach>"+
			"</if>"+
			" order by u.id asc"+
			"</script>")
	Page<UserInfoVo> selectAll(UserPageable condition);

	/**
	 * 返回 user list by auth 属性.
	 *
	 * @param auth the auth
	 * @return user list by auth 属性
	 */
	@Select("<script>" +
			"SELECT DISTINCT u.* from s_user u,s_user_role ur,s_role r,s_role_menu rm,s_menu m "
			+ " where u.status=0 "
			+ " and r.status=0 "
			+ " and m.status=0 "
			+ " and u.id=ur.userId "
			+ " and ur.roleId = r.id "
			+ " and r.id=rm.roleId "
			+ " and rm.menuId=m.id "+
			"<if test='auth!=null '> " +
			" and m.mark=#{auth} " +
			"</if>" +
			"</script>")
	Page<UserInfoVo> getUserListByAuth(@Param("auth") String auth);
	 
	/**
	 * 返回 user list by role id 属性.
	 *
	 * @param roleId the role id
	 * @return user list by role id 属性
	 */
	@Select({"SELECT DISTINCT u.* from s_user u,s_user_role ur,s_role r where r.id=#{id} and r.status = 0 and r.id=ur.roleId and ur.userId=u.id and u.status=0 order by u.id asc"})
	List<UserInfoVo> getUserListByRoleId(@Param("id")Long roleId);
	
	/**
	 * 返回 user list by role tag 属性.
	 *
	 * @param roleTag the role tag
	 * @return user list by role tag 属性
	 */
	@Select({"SELECT DISTINCT u.* from s_user u,s_user_role ur,s_role r where r.roleTag=#{tag} and r.status = 0 and r.id=ur.roleId and ur.userId=u.id and u.status=0 order by u.id asc"})
	List<UserInfoVo> getUserListByRoleTag(@Param("tag")String roleTag);

}