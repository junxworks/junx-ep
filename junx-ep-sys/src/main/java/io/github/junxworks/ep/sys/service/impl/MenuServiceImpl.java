/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  MenuServiceImpl.java   
 * @Package io.github.junxworks.ep.sys.service.impl   
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
package io.github.junxworks.ep.sys.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

import io.github.junxworks.ep.auth.model.UserModel;
import io.github.junxworks.ep.core.utils.PageUtils;
import io.github.junxworks.ep.sys.constants.RecordStatus;
import io.github.junxworks.ep.sys.dto.MenuDto;
import io.github.junxworks.ep.sys.dto.MenuPageable;
import io.github.junxworks.ep.sys.entity.EpSMenu;
import io.github.junxworks.ep.sys.exception.DuplicateMenuMarkException;
import io.github.junxworks.ep.sys.mapper.MenuMapper;
import io.github.junxworks.ep.sys.service.MenuService;
import io.github.junxworks.ep.sys.vo.MenuInfoVo;
import io.github.junxworks.ep.sys.vo.RoleInfoVo;
import io.github.junxworks.ep.sys.vo.TreeSelectVo;
import io.github.junxworks.junx.core.util.StringUtils;

/**
 * {类的详细说明}.
 *
 * @ClassName: MenuServiceImpl
 * @author: Michael
 * @date: 2020-7-19 12:17:47
 * @since: v1.0
 */
@Service("JunxEPMenuService")
public class MenuServiceImpl implements MenuService {

	/** 常量 ROOT. */
	private static final String ROOT = "0";

	/** menu mapper. */
	@Autowired
	private MenuMapper menuMapper;

	/**
	 * 分页查询菜单信息
	 */
	@Override
	public List<MenuInfoVo> getAllMenuItems() {
		return menuMapper.selectAllMenusItems();
	}

	/**
	 * 返回 menu list by page 属性.
	 *
	 * @param pageable the pageable
	 * @return menu list by page 属性
	 */
	@Override
	public PageInfo<MenuInfoVo> getMenuListByPage(MenuPageable pageable) {
		PageUtils.setPage(pageable);
		Page<MenuInfoVo> menuList = menuMapper.selectAll(pageable);
		PageInfo<MenuInfoVo> voPageInfo = new PageInfo<MenuInfoVo>(menuList);
		return voPageInfo;
	}

	/**
	 * 分页查询菜单信息
	 */
	@Override
	public List<MenuInfoVo> getAllMenuList() {
		return menuMapper.selectAllMenus();
	}

	/**
	 * 返回 all menu by user id 属性.
	 *
	 * @param userId the user id
	 * @return all menu by user id 属性
	 */
	@Override
	public List<MenuInfoVo> getAllMenuByUserId(long userId) {
		return menuMapper.getAllMenuByUserId(userId);
	}

	/**
	 * 返回 menu list by role id 属性.
	 *
	 * @param roleId the role id
	 * @return menu list by role id 属性
	 */
	@Override
	public List<MenuInfoVo> getMenuListByRoleId(Long roleId) {
		return menuMapper.selectByRoleId(roleId);
	}

	/**
	 * 返回 menu info by id 属性.
	 *
	 * @param menuId the menu id
	 * @return menu info by id 属性
	 */
	@Override
	public MenuInfoVo getMenuInfoById(Long menuId) {
		return menuMapper.selectById(menuId);
	}

	/**
	 * Save menu info.
	 *
	 * @param menuInfo the menu info
	 * @return the int
	 */
	@Override
	public int saveMenuInfo(MenuDto menuInfo) {
		if (StringUtils.notNull(menuInfo.getMark())) {
			if (menuMapper.queryCountByMenuMark(menuInfo.getMark()) > 0) {
				throw new DuplicateMenuMarkException("重复的菜单标识");
			}
		}
		EpSMenu menu = new EpSMenu();
		BeanUtils.copyProperties(menuInfo, menu);
		UserModel user = (UserModel) SecurityUtils.getSubject().getPrincipal();
		if (menuInfo.getId() == null) {
			if (menu.getParentId() == null) {
				menu.setParentId(0L);
			}
			menu.setCreateUser(user.getId());
			menu.setCreateTime(new Date());
			menu.setStatus(RecordStatus.NORMAL.getValue());
			return menuMapper.insertWithoutNull(menu);
		} else {
			menu.setUpdateUser(user.getId());
			menu.setUpdateTime(new Date());
			return menuMapper.updateWithoutNull(menu);
		}
	}

	/**
	 * Delete menu info by id.
	 *
	 * @param id the id
	 * @return the int
	 */
	public int deleteMenuInfoById(Long id) {
		EpSMenu menu = new EpSMenu();
		menu.setId(id);
		menu.setStatus(RecordStatus.DELETED.getValue());
		return menuMapper.updateWithoutNull(menu);
	}

	/**
	 * Select children count by id.
	 *
	 * @param id the id
	 * @return the int
	 */
	@Override
	public int selectChildrenCountById(Long id) {
		return menuMapper.selectChildrenCountById(id);
	}

	/**
	 * Query roles by menu id.
	 *
	 * @param menuId the menu id
	 * @return the list
	 */
	@Override
	public List<RoleInfoVo> queryRolesByMenuId(Long menuId) {
		return menuMapper.queryRolesByMenuId(menuId);
	}

	/**
	 * Query menu tree select.
	 *
	 * @return the list
	 */
	@Override
	public List<TreeSelectVo> queryMenuTreeSelect() {
		List<MenuInfoVo> menusItems = menuMapper.selectAllMenusItems();
		List<TreeSelectVo> treeNodes = menusItems.stream().flatMap(o -> {
			TreeSelectVo node = new TreeSelectVo();
			node.setId(String.valueOf(o.getId()));
			node.setName(o.getName());
			node.setOpen(true);
			node.setParentId(String.valueOf(o.getParentId()));
			return Stream.of(node);
		}).collect(Collectors.toList());
		Map<String, TreeSelectVo> menuMap = treeNodes.stream().collect(Collectors.toMap(TreeSelectVo::getId, v -> v));
		treeNodes.forEach(v -> {
			if (v.getParentId() != null && !ROOT.equals(v.getParentId())) {
				menuMap.get(v.getParentId()).addChild(v);
			}
		});
		return treeNodes.stream().filter(t -> {
			return ROOT.equals(t.getParentId());
		}).collect(Collectors.toList());
	}
}
