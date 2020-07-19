/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  MenuService.java   
 * @Package io.github.junxworks.ep.sys.service   
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
package io.github.junxworks.ep.sys.service;

import java.util.List;

import com.github.pagehelper.PageInfo;

import io.github.junxworks.ep.sys.dto.MenuDto;
import io.github.junxworks.ep.sys.dto.MenuPageable;
import io.github.junxworks.ep.sys.vo.MenuInfoVo;
import io.github.junxworks.ep.sys.vo.RoleInfoVo;
import io.github.junxworks.ep.sys.vo.TreeSelectVo;

/**
 * {类的详细说明}.
 *
 * @ClassName:  MenuService
 * @author: Michael
 * @date:   2020-7-19 12:17:48
 * @since:  v1.0
 */

public interface MenuService {
	
	/**
	 * 返回 all menu items 属性.
	 *
	 * @return all menu items 属性
	 */
	List<MenuInfoVo> getAllMenuItems();
	
	/**
	 * Query roles by menu id.
	 *
	 * @param menuId the menu id
	 * @return the list
	 */
	List<RoleInfoVo> queryRolesByMenuId(Long menuId);
	
    /**
     * 返回 menu list by page 属性.
     *
     * @param pageable the pageable
     * @return menu list by page 属性
     */
    PageInfo<MenuInfoVo> getMenuListByPage(MenuPageable pageable);
    
    /**
     * 返回 menu info by id 属性.
     *
     * @param menuId the menu id
     * @return menu info by id 属性
     */
    MenuInfoVo getMenuInfoById(Long menuId);
    
    /**
     * Save menu info.
     *
     * @param menuInfo the menu info
     * @return the int
     */
    int saveMenuInfo(MenuDto menuInfo);

    /**
     * 返回 menu list by role id 属性.
     *
     * @param roleId the role id
     * @return menu list by role id 属性
     */
    List<MenuInfoVo> getMenuListByRoleId(Long roleId);
    
    /**
     * Delete menu info by id.
     *
     * @param id the id
     * @return the int
     */
    int deleteMenuInfoById(Long id);
    
    /**
     * 返回 all menu list 属性.
     *
     * @return all menu list 属性
     */
    List<MenuInfoVo> getAllMenuList();
    
    /**
     * 返回 all menu by user id 属性.
     *
     * @param userId the user id
     * @return all menu by user id 属性
     */
    List<MenuInfoVo> getAllMenuByUserId(long userId);

    /**
     * Select children count by id.
     *
     * @param id the id
     * @return the int
     */
    int selectChildrenCountById(Long id);
    
    /**
     * Query menu tree select.
     *
     * @return the list
     */
    List<TreeSelectVo> queryMenuTreeSelect();
}
