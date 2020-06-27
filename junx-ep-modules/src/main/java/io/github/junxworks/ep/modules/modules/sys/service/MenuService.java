/*
 ***************************************************************************************
 * All rights Reserved, Designed By www.cqhyrc.com.cn
 * @Title:  MenuService.java   
 * @Package io.github.junxworks.ep.modules.modules.sys.service   
 * @Description: (用一句话描述该文件做什么)   
 * @author: AOC
 * @date:   2019-7-3 10:00:34   
 * @version V1.0 
 * @Copyright: 2019 重庆华宇集团. All rights reserved. 
 * 注意：本内容仅限于公司内部使用，禁止外泄以及用于其他的商业目
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.modules.modules.sys.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import io.github.junxworks.ep.modules.modules.sys.dto.MenuPageable;
import io.github.junxworks.ep.modules.modules.sys.vo.MenuInfoVo;
import io.github.junxworks.ep.modules.modules.sys.vo.RoleInfoVo;

/**
 * @Description: 菜单信息
 * @Author: FengYun
 * @Date: 2019/7/1 10:40
 */

public interface MenuService {
	
	/**
	 * 查询所有菜单项
	 *
	 * @return all menu 属性
	 */
	List<MenuInfoVo> getAllMenuItems();
	
	/**
	 * 根据菜单ID查询关联角色信息
	 *
	 * @param menuId the menu id
	 * @return the list
	 */
	List<RoleInfoVo> queryRolesByMenuId(Long menuId);
	
    /**
     * 分页查询用户信息
     * @param pageable 分页条件
     * @return 菜单列表
     */
    PageInfo<MenuInfoVo> getMenuListByPage(MenuPageable pageable);
    /**
     * 通过id查询菜单信息
     */
    MenuInfoVo getMenuInfoById(Long menuId);
    /**
     * 新增菜单信息
     */
    int postMenuInfo(MenuInfoVo menuInfoVo);
    /**
     * 修改菜单信息
     */
    int putMenuInfo(MenuInfoVo menuInfoVo);

    /**
     * 根据角色ID获取菜单列表
     */
    List<MenuInfoVo> getMenuListByRoleId(Long roleId);
    /**
     * 删除菜单信息
     */
    int deleteMenuInfo(MenuInfoVo menuInfoVo);
    
    /**
     * 查询所有菜单
     *
     * @return all menu list 属性
     */
    List<MenuInfoVo> getAllMenuList();
    
    /**
     * 获取用户关联的菜单信息
     *
     * @param userId the user id
     * @return all menu by user id 属性
     */
    List<MenuInfoVo> getAllMenuByUserId(long userId);

    int selectChildrenCountById(Long id);
}
