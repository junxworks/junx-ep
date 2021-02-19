/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  RoleMenuMapper.java   
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

/**
 * {类的详细说明}.
 *
 * @ClassName:  RoleMenuMapper
 * @author: Michael
 * @date:   2020-7-19 12:17:48
 * @since:  v1.0
 */
@Mapper
public interface RoleMenuMapper extends BaseMapper {

	/**
	 * Delete by role id.
	 *
	 * @param roleId the role id
	 * @return the int
	 */
	@Delete("delete from s_role_menu where role_id=#{roleId}")
	public int deleteByRoleId(Long roleId);

	/**
	 * Delete by menu ids.
	 *
	 * @param roleId the role id
	 * @param menuIds the menu ids
	 * @return the int
	 */
	@Delete({"<script>",
		"delete from s_role_menu where role_id=#{roleId} ",
			"<if test='menuIds!=null'> " +
				" and  menu_id not in " +
				"<foreach collection=\"menuIds\" item=\"menuId\" open=\"(\" close=\")\" separator=\",\">" + 
					"#{menuId}" + 
				"</foreach>"+
			"</if>",
		"</script>"})
	public int deleteByMenuIds(@Param("roleId") Long roleId, @Param("menuIds") List<Long> menuIds);
	

	/**
	 * Query menus by role id.
	 *
	 * @param roleId the role id
	 * @return the list
	 */
	@Select("select menuId from s_role_menu where role_id=#{roleId}")
	public List<Long> queryMenusByRoleId(@Param("roleId") Long roleId);

}