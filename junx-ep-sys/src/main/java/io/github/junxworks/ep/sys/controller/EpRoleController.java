/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  RoleController.java   
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import io.github.junxworks.ep.core.Result;
import io.github.junxworks.ep.core.utils.PageUtils;
import io.github.junxworks.ep.sys.annotations.EpLog;
import io.github.junxworks.ep.sys.dto.MenuPageable;
import io.github.junxworks.ep.sys.dto.RoleInfoDto;
import io.github.junxworks.ep.sys.dto.RoleConditionDto;
import io.github.junxworks.ep.sys.service.MenuService;
import io.github.junxworks.ep.sys.service.RoleService;
import io.github.junxworks.ep.sys.vo.MenuInfoVo;
import io.github.junxworks.ep.sys.vo.MenuTreeVo;
import io.github.junxworks.ep.sys.vo.RoleInfoVo;

/**
 * {类的详细说明}.
 *
 * @ClassName:  RoleController
 * @author: Michael
 * @date:   2020-7-19 12:17:47
 * @since:  v1.0
 */
@RestController
@RequestMapping("/ep/sys/roles")
public class EpRoleController {

	/** role service. */
	@Autowired
	private RoleService roleService;

	/** menu service. */
	@Autowired
	private MenuService menuService;

	/**
	 * 返回 role list 属性.
	 *
	 * @param pageable the pageable
	 * @return role list 属性
	 */
	@GetMapping()
	public Result getRoleList(RoleConditionDto condition) {
		PageUtils.setPage(condition);
		return Result.ok(new PageInfo<RoleInfoVo>(roleService.findRoleListByCondition(condition)));
	}

	@GetMapping(value = "/{roleId}/checked-menus")
	public Result getCheckedMenus(@PathVariable("roleId") Long roleId) {
		//角色对应菜单
		List<MenuInfoVo> roleMenus = menuService.getMenuListByRoleId(roleId);
		Set<Long> roleMenuIds = roleMenus.stream().flatMap(v -> {
			return Stream.of(v.getId());
		}).collect(Collectors.toSet());
		return Result.ok(roleMenuIds);
	}

	/**
	 * 返回 all menu list 属性.
	 *
	 * @param roleId the role id
	 * @return all menu list 属性
	 */
	@GetMapping(value = "/{roleId}/menus")
	public Result getAllMenuList(@PathVariable Long roleId) {
		Result result = Result.ok();
		MenuPageable pageable = new MenuPageable();
		pageable.setStatus(Byte.valueOf("0"));
		pageable.setPageNo(1);
		pageable.setPageSize(Integer.MAX_VALUE);
		//全局总菜单
		List<MenuInfoVo> allMenus = menuService.getMenuListByPage(pageable).getList();
		//角色对应菜单
		List<MenuInfoVo> roleMenus = menuService.getMenuListByRoleId(roleId);
		Set<Long> roleMenuIds = roleMenus.stream().flatMap(v -> {
			return Stream.of(v.getId());
		}).collect(Collectors.toSet());
		//加工成树节点后的总菜单
		List<MenuTreeVo> treeList = Lists.newArrayList();
		//根节点集
		List<MenuTreeVo> rootList = Lists.newArrayList();
		//父节点集
		Set<Long> parents = Sets.newHashSet();
		for (MenuInfoVo menuInfo : allMenus) {
			MenuTreeVo treeVo = new MenuTreeVo();
			treeVo.setId(menuInfo.getId());
			treeVo.setParentId(menuInfo.getParentId());
			treeVo.setTitle(menuInfo.getName());
			treeVo.setSort(menuInfo.getSort());
			treeList.add(treeVo);
		}
		for (MenuTreeVo node1 : treeList) {
			if (roleMenuIds.contains(node1.getId())) {
				node1.setChecked(true);
			}
			boolean isRoot = true;
			for (MenuTreeVo node2 : treeList) {
				if (node1.getParentId() != null && node1.getParentId().equals(node2.getId())) {
					isRoot = false;
					if (node2.getChildren() == null) {
						node2.setChildren(new ArrayList<MenuTreeVo>());
					}
					node2.getChildren().add(node1);
					parents.add(node2.getId());
					break;
				}
			}
			if (node1.getChildren() != null && node1.getChildren().size() > 0) {
				Collections.sort(node1.getChildren(), (MenuTreeVo h1, MenuTreeVo h2) -> h1.getSort() - h2.getSort());
			}
			if (isRoot) {
				rootList.add(node1);
			}
		}
		treeList.forEach(v -> {
			if (parents.contains(v.getId())) {
				v.setChecked(false);
			}
		});
		Collections.sort(rootList, (MenuTreeVo h1, MenuTreeVo h2) -> h1.getSort() - h2.getSort());
		result.setData(rootList);
		return result;
	}

	/*private void checkRoleMenu(List<MenuTreeVo> allList, List<MenuInfoVo> roleMenus) {
		for (MenuTreeVo vo : allList) {
			for (MenuInfoVo roleMenu : roleMenus) {
				if (vo.getId().equals(roleMenu.getId())) {
					vo.setChecked(true);
					for (MenuInfoVo roleMenuChild : roleMenus) {
						if (roleMenuChild.getParentId().equals(roleMenu.getId())) {
							vo.setChecked(false);
						}
					}
				}
			}
			if (CollectionUtils.isNotEmpty(vo.getChildren())) {
				this.checkRoleMenu(vo.getChildren(), roleMenus);
			}
		}
	}*/

	/**
	 * Save role info.
	 *
	 * @param dto the dto
	 * @return the result
	 */
	@PostMapping()
	@EpLog("EP-系统支撑-保存角色")
	public Result saveRoleInfo(@RequestBody RoleInfoDto dto) {
		roleService.saveRoleInfo(dto);
		return Result.ok();
	}

	/**
	 * Delete role.
	 *
	 * @param roleId the role id
	 * @return the result
	 */
	@DeleteMapping(value = "/{roleId}")
	@EpLog("EP-系统支撑-删除角色")
	public Result deleteRole(@PathVariable("roleId") Long roleId) {
		return Result.ok(roleService.deleteRoleById(roleId));
	}

	/**
	 * 返回 user list by rot tag 属性.
	 *
	 * @param roleTag the role tag
	 * @return user list by rot tag 属性
	 */
	@GetMapping(value = "/{rotTag}/users")
	public Result getUserListByRotTag(@PathVariable("rotTag") String roleTag) {
		return Result.ok(roleService.findUserListByRoleTag(roleTag));
	}
}
