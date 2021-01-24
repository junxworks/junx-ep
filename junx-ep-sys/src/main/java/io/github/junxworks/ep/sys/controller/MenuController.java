/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  MenuController.java   
 * @Package io.github.junxworks.ep.sys.controller   
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
package io.github.junxworks.ep.sys.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.junxworks.ep.auth.model.UserModel;
import io.github.junxworks.ep.core.Result;
import io.github.junxworks.ep.sys.annotations.OpLog;
import io.github.junxworks.ep.sys.constants.MenuType;
import io.github.junxworks.ep.sys.constants.RecordStatus;
import io.github.junxworks.ep.sys.dto.MenuDto;
import io.github.junxworks.ep.sys.dto.MenuPageable;
import io.github.junxworks.ep.sys.service.MenuService;
import io.github.junxworks.ep.sys.vo.MenuInfoVo;

/**
 * {类的详细说明}.
 *
 * @ClassName:  MenuController
 * @author: Michael
 * @date:   2020-7-19 12:17:48
 * @since:  v1.0
 */
@RestController
@RequestMapping("/ep/sys/menus")
public class MenuController {
	
	/** menu service. */
	@Autowired
	private MenuService menuService;

	/**
	 * 菜单列表
	 */
	@GetMapping()
	public Result getMenuList() {
		return Result.ok(menuService.getAllMenuItems());
	}

	/**
	 * 返回 menu role list 属性.
	 *
	 * @param menuId the menu id
	 * @return menu role list 属性
	 */
	@GetMapping(value = "/{menuId}/roles")
	public Result getMenuRoleList(@PathVariable("menuId") Long menuId) {
		return Result.ok(menuService.queryRolesByMenuId(menuId));
	}

	/**
	 * Save menu info.
	 *
	 * @param menuInfo the menu info
	 * @return the result
	 */
	@PostMapping()
	@OpLog("保存菜单信息")
	public Result saveMenuInfo(@RequestBody MenuDto menuInfo) {
		return Result.ok(menuService.saveMenuInfo(menuInfo));
	}

	/**
	 * 返回 menu info by id 属性.
	 *
	 * @param id the id
	 * @return menu info by id 属性
	 */
	@GetMapping("/{id}")
	public Result getMenuInfoById(@PathVariable Long id) {
		return Result.ok(menuService.getMenuInfoById(id));
	}

	/**
	 * Del menu info.
	 *
	 * @param id the id
	 * @return the result
	 */
	@DeleteMapping("/{id}")
	@OpLog("删除菜单信息")
	public Result delMenuInfo(@PathVariable("id") Long id) {
		int childrenCount = menuService.selectChildrenCountById(id);
		if (childrenCount > 0) {
			return Result.warn("该菜单存在下级菜单，不能删除");
		}
		menuService.deleteMenuInfoById(id);
		return Result.ok();
	}

	/**
	 * 返回 for menu 属性.
	 *
	 * @param username the username
	 * @return for menu 属性
	 */
	@GetMapping("/menus-bar")
	public Result getForMenu(String username) {
		MenuPageable pageable = new MenuPageable();
		pageable.setStatus(RecordStatus.NORMAL.getValue());
		Subject subject = SecurityUtils.getSubject();
		UserModel user = (UserModel) subject.getPrincipal();
		if (user == null) {
			return Result.unauthenticated();
		}
		List<MenuInfoVo> userMenuList = null;
		if (user.isAdmin()) {
			userMenuList = menuService.getAllMenuList();
		} else {
			userMenuList = menuService.getAllMenuByUserId(user.getId());
		}
		final Map<Long, MenuInfoVo> root = userMenuList.stream().filter(m -> {
			return m.getParentId() == 0;
		}).collect(Collectors.toMap(MenuInfoVo::getId, m -> m));

		final Map<Long, MenuInfoVo> dirs = userMenuList.stream().filter(m -> {
			return m.getType() == MenuType.DIRECTORY.getValue();
		}).collect(Collectors.toMap(MenuInfoVo::getId, m -> m));

		userMenuList.stream().sorted((m1, m2) -> {
			int s1 = 0, s2 = 0;
			if (m1.getSort() != null)
				s1 = m1.getSort();
			if (m2.getSort() != null)
				s2 = m2.getSort();
			return s1 - s2;
		}).forEach(m -> {
			if (m.getParentId() != null && m.getParentId() > 0) {
				MenuInfoVo parent = dirs.get(m.getParentId());
				if (parent != null) {
					parent.getChildren().add(m);
				}
			}
		});
		return Result.ok(root.values().stream().sorted((m1, m2) -> {
			int s1 = 0, s2 = 0;
			if (m1.getSort() != null)
				s1 = m1.getSort();
			if (m2.getSort() != null)
				s2 = m2.getSort();
			return s1 - s2;
		}).collect(Collectors.toList()));
	}

	/**
	 * Query tree select.
	 *
	 * @return the result
	 */
	@GetMapping("/tree-select")
	public Result queryTreeSelect() {
		return Result.ok(menuService.queryMenuTreeSelect());
	}
}
