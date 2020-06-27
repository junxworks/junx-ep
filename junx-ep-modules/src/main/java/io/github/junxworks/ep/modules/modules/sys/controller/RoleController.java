package io.github.junxworks.ep.modules.modules.sys.controller;

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
import io.github.junxworks.ep.modules.annotations.OpLog;
import io.github.junxworks.ep.modules.modules.sys.dto.MenuPageable;
import io.github.junxworks.ep.modules.modules.sys.dto.RoleInfoDto;
import io.github.junxworks.ep.modules.modules.sys.dto.RolePageable;
import io.github.junxworks.ep.modules.modules.sys.service.MenuService;
import io.github.junxworks.ep.modules.modules.sys.service.RoleService;
import io.github.junxworks.ep.modules.modules.sys.vo.MenuInfoVo;
import io.github.junxworks.ep.modules.modules.sys.vo.MenuTreeVo;
import io.github.junxworks.ep.modules.modules.sys.vo.RoleInfoVo;

/**
 * @Description: 角色管理模块
 * @Author: ChenYang
 * @Date: 2019/7/2 9:00
 */
@RestController
@RequestMapping("/ep/sys")
public class RoleController {

	@Autowired
	private RoleService roleService;

	@Autowired
	private MenuService menuService;

	/**
	 * 角色列表
	 */
	@GetMapping(value = "/roles")
	public Result getRoleList(RolePageable pageable) {
		Result result = Result.ok();
		PageInfo<RoleInfoVo> rolePage = roleService.findRoleListByPage(pageable);
		result.setData(rolePage);
		return result;
	}

	/**
	 * 获取角色对应的菜单项
	 */
	@GetMapping(value = "/roles/{roleId}/menus")
	public Result getAllMenuList(@PathVariable Long roleId) {
		Result result = Result.ok();
		MenuPageable pageable = new MenuPageable();
		pageable.setStatus(Byte.valueOf("0"));
		pageable.setPageNo(0);
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
	 * 保存角色信息
	 */
	@PostMapping(value = "/roles")
	@OpLog("保存角色信息")
	public Result saveRoleInfo(@RequestBody RoleInfoDto dto) {
		roleService.saveRoleInfo(dto);
		return Result.ok();
	}

	/**
	 * 删除角色信息
	 * 关联删除所有人员关联
	 */
	@DeleteMapping(value = "/roles/{roleId}")
	@OpLog("删除角色信息")
	public Result deleteRole(@PathVariable("roleId") Long roleId) {
		return Result.ok(roleService.deleteRoleById(roleId));
	}
	
	@GetMapping(value = "/roles/{rotTag}/users")
	public Result getUserListByRotTag(@PathVariable("rotTag") String roleTag) {
		return Result.ok(roleService.findUserListByRoleTag(roleTag));
	}
}
