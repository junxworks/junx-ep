/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  MenuMapper.java   
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

import com.github.pagehelper.Page;
import io.github.junxworks.ep.core.orm.BaseMapper;
import io.github.junxworks.ep.sys.dto.MenuPageable;
import io.github.junxworks.ep.sys.vo.MenuInfoVo;
import io.github.junxworks.ep.sys.vo.RoleInfoVo;

/**
 * {类的详细说明}.
 *
 * @ClassName:  MenuMapper
 * @author: Michael
 * @date:   2020-7-19 12:17:48
 * @since:  v1.0
 */
@Mapper
public interface MenuMapper extends BaseMapper{

	/**
	 * Select by id.
	 *
	 * @param id the id
	 * @return the menu info vo
	 */
	@Select("select s.*,case when parent_id!=0 then (select name from ep_s_menu where id=s.parent_id) else '根目录' end `parentName` from ep_s_menu s where s.id=#{id}")
	public MenuInfoVo selectById(Long id);

	@Select("select count(1) from ep_s_menu where status=0 and mark=#{mark}")
	public int queryCountByMenuMark(String mark);
	
	/**
	 * Delete by id.
	 *
	 * @param id the id
	 * @return the int
	 */
	@Delete("delete from ep_s_menu where id=#{id}")
	public int deleteById(Long id);

	/**
	 * Select all.
	 *
	 * @param entity the entity
	 * @return the page
	 */
	@Select("<script>" +
			"select * from ep_s_menu where 1=1" +
			"<if test='status!=null '> " +
			"and status = #{status} " +
			"</if>" +
			"<if test='name!=null and name.length>0 '> " +
			"and name like CONCAT('%',#{name},'%') " +
			"</if>" +
			"<if test='parentId!=null and parentId.length>0 '> " +
			"and parent_id = #{parentId} " +
			"</if>" +
			"<if test='type!=null and type.length>0 '> " +
			"and type = #{type} " +
			"</if>" +
			" order by sort asc"+
			"</script>")
	public Page<MenuInfoVo> selectAll(MenuPageable entity);
	
	/**
	 * Select all menus.
	 *
	 * @return the list
	 */
	@Select("select * from ep_s_menu where status=0 and type!=1")
	public List<MenuInfoVo> selectAllMenus();
	
	/**
	 * Select all menus items.
	 *
	 * @return the list
	 */
	@Select("select * from ep_s_menu where status=0 order by sort asc")
	public List<MenuInfoVo> selectAllMenusItems();
	
	/**
	 * Select by role id.
	 *
	 * @param roleId the role id
	 * @return the list
	 */
	@Select("select DISTINCT m.* from ep_s_menu m,ep_s_role_menu rm,ep_s_role r where rm.role_id=#{roleId} and rm.menu_id = m.id and rm.role_id=r.id and r.status=0")
	List<MenuInfoVo> selectByRoleId(Long roleId);

	/**
	 * Select children count by id.
	 *
	 * @param id the id
	 * @return the int
	 */
	@Select({"<script>",
			"select count(1) from ep_s_menu where status=0 and parent_id=#{id}",
			"</script>"})
	public int selectChildrenCountById(long id);

	/**
	 * 返回 all menu by user id 属性.
	 *
	 * @param userId the user id
	 * @return all menu by user id 属性
	 */
	@Select("<script> "+
			"SELECT m.* FROM "
			+ "(SELECT DISTINCT rm.menu_id FROM ep_s_role_menu rm,ep_s_user_role ur,ep_s_role r WHERE r.status=0 and r.id=rm.role_id and rm.role_id = ur.role_id and ur.user_id =#{userId}) menus" +
			",ep_s_menu m WHERE menus.menu_id = m.id and m.type!=1 AND  m.`status`=0"+
			"</script>")
	public List<MenuInfoVo> getAllMenuByUserId(long userId);
	
	/**
	 * Query roles by menu id.
	 *
	 * @param menuId the menu id
	 * @return the list
	 */
	@Select("select r.* from ep_s_role_menu rm,ep_s_role r where r.status=0 and r.id=rm.role_id and rm.menu_id = #{menuId}")
	List<RoleInfoVo> queryRolesByMenuId(@Param("menuId") Long menuId);
}