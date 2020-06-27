package io.github.junxworks.ep.modules.modules.sys.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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

import io.github.junxworks.ep.core.Result;
import io.github.junxworks.ep.modules.annotations.OpLog;
import io.github.junxworks.ep.modules.constants.MenuType;
import io.github.junxworks.ep.modules.constants.RecordStatus;
import io.github.junxworks.ep.modules.modules.sys.dto.MenuPageable;
import io.github.junxworks.ep.modules.modules.sys.service.MenuService;
import io.github.junxworks.ep.modules.modules.sys.vo.MenuInfoVo;
import io.github.junxworks.ep.modules.modules.sys.vo.MenuTreeVo;
import io.github.junxworks.ep.auth.model.UserModel;

/**
 * @Description: 菜单
 * @Author: FengYun
 * @Date: 2019/7/1 10:34
 */
@RestController
@RequestMapping("/ep/sys")
public class MenuController {
	@Autowired
	private MenuService menuService;

	/**
	 * 菜单列表
	 */
	@GetMapping(value = "/menus")
	public Result getMenuList() {
		return Result.ok(menuService.getAllMenuItems());
	}

	@GetMapping(value = "/menus/{menuId}/roles")
	public Result getMenuRoleList(@PathVariable("menuId") Long menuId) {
		return Result.ok(menuService.queryRolesByMenuId(menuId));
	}

	/**
	 * 保存菜单
	 */
	@PostMapping(value = "/menus", consumes = "application/json")
	@OpLog("保存菜单信息")
	public Result saveMenuInfo(@RequestBody MenuInfoVo menuInfo) {
		Result result = Result.ok();
		try {
			Subject sub = SecurityUtils.getSubject();
			UserModel user = (UserModel) sub.getPrincipal();
			menuInfo.setCreatorId(user.getId());
			menuInfo.setCreateDate(new Date());
			//			if(menuInfo.getType()== MenuType.DIRECTORY.getValue()){
			//				menuInfo.setParentId(0L);
			//			}
			if (menuInfo.getId() == null) {
				menuInfo.setStatus(RecordStatus.NORMAL.getValue());
				menuService.postMenuInfo(menuInfo);
			} else {
				MenuInfoVo newdata = menuService.getMenuInfoById(menuInfo.getId());
				newdata.setName(menuInfo.getName());
				newdata.setType(menuInfo.getType());
				newdata.setParentId(menuInfo.getParentId());
				newdata.setMark(menuInfo.getMark());
				newdata.setUrl(menuInfo.getUrl());
				newdata.setIcon(menuInfo.getIcon());
				newdata.setSort(menuInfo.getSort());
				newdata.setModifierId(user.getId());
				newdata.setModifyDate(new Date());
				menuService.putMenuInfo(newdata);
			}
		} catch (Exception e) {
			return Result.error(e.getMessage());
		}
		return result;
	}

	@GetMapping("/menus/{id}")
	public Result getMenuInfoById(@PathVariable Long id) {
		Result result = new Result();
		MenuInfoVo condition = new MenuInfoVo();
		try {
			condition = menuService.getMenuInfoById(id);
			result.setData(condition);
		} catch (Exception e) {
			//log.error("查询还款方式配置异常", e);
			result.setMsg("查询异常");
			result.setCode(Result.Status.ERROR.getCode());
		}
		return result;
	}

	@DeleteMapping("/menus/{id}")
	@OpLog("删除菜单信息")
	public Result putMenuInfo(@PathVariable("id") Long id) {
		try {
			MenuInfoVo menuInfo = new MenuInfoVo();
			menuInfo.setId(id);
			int childrenCount = menuService.selectChildrenCountById(menuInfo.getId());
			if (childrenCount > 0) {
				return Result.warn("该菜单存在下级菜单，不能删除");
			}
			menuInfo.setStatus(RecordStatus.DELETED.getValue());
			menuService.deleteMenuInfo(menuInfo);
		} catch (Exception e) {
			return Result.error(e.getMessage());
		}
		return Result.ok();
	}

	/****
	 * 组合用户权限菜单
	 * @param username String
	 * @return List
	 */
	@GetMapping("/menus/menus-bar")
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

	@GetMapping(value = "/menus/all-menus")
	public Result getAllMenuList() {
		Result result = Result.ok();
		MenuPageable pageable = new MenuPageable();
		pageable.setStatus(Byte.valueOf("0"));
		List<MenuInfoVo> sessionList = menuService.getAllMenuList();
		List<MenuTreeVo> allList = new ArrayList<>();
		List<MenuTreeVo> treeList = new ArrayList<>();

		for (MenuInfoVo menuInfo : sessionList) {
			MenuTreeVo treeVo = new MenuTreeVo();
			treeVo.setId(menuInfo.getId());
			treeVo.setParentId(menuInfo.getParentId());
			treeVo.setTitle(menuInfo.getName());
			treeVo.setSort(menuInfo.getSort());
			treeList.add(treeVo);
		}
		for (MenuTreeVo node1 : treeList) {
			boolean mark = false;
			for (MenuTreeVo node2 : treeList) {
				if (node1.getParentId() != null && node1.getParentId().equals(node2.getId())) {
					mark = true;
					if (node2.getChildren() == null)
						node2.setChildren(new ArrayList<MenuTreeVo>());
					node2.getChildren().add(node1);
					break;
				}
			}
			if (node1.getChildren() != null && node1.getChildren().size() > 0) {
				Collections.sort(node1.getChildren(), (MenuTreeVo h1, MenuTreeVo h2) -> h1.getSort() - h2.getSort());
			}
			if (!mark) {
				allList.add(node1);
			}
		}
		Collections.sort(allList, (MenuTreeVo h1, MenuTreeVo h2) -> h1.getSort() - h2.getSort());
		result.setData(allList);
		return result;
	}
}
