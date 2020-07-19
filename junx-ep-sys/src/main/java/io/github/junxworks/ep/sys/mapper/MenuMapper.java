/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  MenuMapper.java   
 * @Package io.github.junxworks.ep.sys.mapper   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2020-7-19 12:17:48   
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
	@Select("select s.*,case when parentId!=0 then (select name from s_menu where id=s.parentId) else '根目录' end `parentName` from s_menu s where s.id=#{id}")
	public MenuInfoVo selectById(Long id);

	/**
	 * Delete by id.
	 *
	 * @param id the id
	 * @return the int
	 */
	@Delete("delete from s_menu where id=#{id}")
	public int deleteById(Long id);

	/**
	 * Select all.
	 *
	 * @param entity the entity
	 * @return the page
	 */
	@Select("<script>" +
			"select * from s_menu where 1=1" +
			"<if test='status!=null '> " +
			"and status = #{status} " +
			"</if>" +
			"<if test='name!=null and name.length>0 '> " +
			"and name like CONCAT('%',#{name},'%') " +
			"</if>" +
			"<if test='parentId!=null and parentId.length>0 '> " +
			"and parentId = #{parentId} " +
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
	@Select("select * from s_menu where status=0 and type!=1")
	public List<MenuInfoVo> selectAllMenus();
	
	/**
	 * Select all menus items.
	 *
	 * @return the list
	 */
	@Select("select * from s_menu where status=0 order by sort asc")
	public List<MenuInfoVo> selectAllMenusItems();
	
	/**
	 * Select by role id.
	 *
	 * @param roleId the role id
	 * @return the list
	 */
	@Select("select DISTINCT m.* from s_menu m,s_role_menu rm,s_role r where rm.roleId=#{roleId} and rm.menuId = m.id and rm.roleId=r.id and r.status=0")
	List<MenuInfoVo> selectByRoleId(Long roleId);

	/**
	 * Select children count by id.
	 *
	 * @param id the id
	 * @return the int
	 */
	@Select({"<script>",
			"select count(1) from s_menu where status=0 and parentId=#{id}",
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
			+ "(SELECT DISTINCT rm.menuId FROM s_role_menu rm,s_user_role ur,s_role r WHERE r.status=0 and r.id=rm.roleId and rm.roleId = ur.roleId and ur.userId =#{userId}) menus" +
			",s_menu m WHERE menus.menuId = m.id and m.type!=1 AND  m.`status`=0"+
			"</script>")
	public List<MenuInfoVo> getAllMenuByUserId(long userId);
	
	/**
	 * Query roles by menu id.
	 *
	 * @param menuId the menu id
	 * @return the list
	 */
	@Select("select r.* from s_role_menu rm,s_role r where r.status=0 and r.id=rm.roleId and rm.menuId = #{menuId}")
	List<RoleInfoVo> queryRolesByMenuId(@Param("menuId") Long menuId);
}