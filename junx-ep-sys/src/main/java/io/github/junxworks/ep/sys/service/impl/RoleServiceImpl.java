/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  RoleServiceImpl.java   
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
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;

import io.github.junxworks.ep.auth.model.UserModel;
import io.github.junxworks.ep.core.exception.BusinessException;
import io.github.junxworks.ep.sys.constants.RecordStatus;
import io.github.junxworks.ep.sys.dto.RoleConditionDto;
import io.github.junxworks.ep.sys.dto.RoleInfoDto;
import io.github.junxworks.ep.sys.entity.EpSRole;
import io.github.junxworks.ep.sys.entity.EpSRoleMenu;
import io.github.junxworks.ep.sys.mapper.EpRoleMapper;
import io.github.junxworks.ep.sys.mapper.EpRoleMenuMapper;
import io.github.junxworks.ep.sys.service.RoleService;
import io.github.junxworks.ep.sys.vo.RoleInfoVo;
import io.github.junxworks.ep.sys.vo.UserInfoVo;
import io.github.junxworks.junx.core.util.StringUtils;

/**
 * 角色服务实现类
 *
 * @ClassName:  RoleServiceImpl
 * @author: Michael
 * @date:   2020-7-19 12:17:48
 * @since:  v1.0
 */
public class RoleServiceImpl implements RoleService {

	/** role mapper. */
	@Autowired
	private EpRoleMapper roleMapper;

	/** role menu mapper. */
	@Autowired
	private EpRoleMenuMapper roleMenuMapper;

	/**
	 * Find role list by page.
	 *
	 * @param pageable the pageable
	 * @return the page info
	 */
	@Override
	public List<RoleInfoVo> findRoleListByCondition(RoleConditionDto condition) {
		return roleMapper.selectAll(condition);
	}

	/**
	 * Find all role list.
	 *
	 * @return the list
	 */
	@Override
	public List<RoleInfoVo> findAllRoleList() {
		return roleMapper.selectAll(null);
	}

	/**
	 * Save role info.
	 *
	 * @param dto the dto
	 */
	@Override
	@Transactional
	public void saveRoleInfo(RoleInfoDto dto) {
		//保存角色基本信息
		EpSRole role = new EpSRole();
		BeanUtils.copyProperties(dto, role);
		UserModel user = (UserModel) SecurityUtils.getSubject().getPrincipal();
		EpSRole nameRole = roleMapper.selectByName(dto.getRoleName());
		EpSRole tagRole = null;
		if (StringUtils.notNull(dto.getRoleTag())) {
			tagRole = roleMapper.selectByTag(dto.getRoleTag());
		}
		List<Long> menuIdList = Lists.newArrayList();
		if (StringUtils.notNull(dto.getMenuInfoList())) {
			JSONArray menuInfoVoList = JSON.parseArray(dto.getMenuInfoList());
			menuIdList = this.getMenuIdList(menuInfoVoList);
		} else if (StringUtils.notNull(dto.getSelectedMenus())) {
			menuIdList = Lists.newArrayList(dto.getSelectedMenus().split(",")).stream().flatMap(f -> {
				return Stream.of(Long.valueOf(f));
			}).collect(Collectors.toList());
		}
		if (role.getId() == null) {//新增
			if (nameRole != null) {
				throw new BusinessException("角色名：" + dto.getRoleName() + ",已存在");
			}
			if (tagRole != null) {
				throw new BusinessException("角色标签已经被角色\"" + tagRole.getRoleName() + "\"使用");
			}
			role.setCreateUser(user.getId());
			role.setCreateTime(new Date());
			role.setStatus(RecordStatus.NORMAL.getValue());
			roleMapper.insertWithoutNull(role);
			for (Long menuId : menuIdList) {
				EpSRoleMenu roleMenu = new EpSRoleMenu();
				roleMenu.setRoleId(role.getId());
				roleMenu.setMenuId(menuId);
				roleMenuMapper.insertWithoutNull(roleMenu);
			}
		} else {//修改
			if (nameRole != null && !dto.getId().equals(nameRole.getId())) {
				throw new BusinessException("角色名：" + dto.getRoleName() + ",已存在");
			}
			if (tagRole != null && !dto.getId().equals(tagRole.getId())) {
				throw new BusinessException("角色标签已经被角色\"" + tagRole.getRoleName() + "\"使用");
			}
			Long roleId = role.getId();
			role.setUpdateUser(user.getId());
			role.setUpdateTime(new Date());
			roleMapper.updateWithoutNull(role);
			roleMenuMapper.deleteByMenuIds(roleId, menuIdList);//删除多余菜单
			List<Long> existsMenuIds = roleMenuMapper.queryMenusByRoleId(roleId);
			menuIdList.forEach(m -> {
				if (!existsMenuIds.contains(m)) {
					EpSRoleMenu roleMenu = new EpSRoleMenu();
					roleMenu.setRoleId(roleId);
					roleMenu.setMenuId(m);
					roleMenuMapper.insertWithoutNull(roleMenu);
				}
			});
		}
	}

	/**
	 * 返回 menu id list 属性.
	 *
	 * @param menuInfoVoList the menu info vo list
	 * @return menu id list 属性
	 */
	private List<Long> getMenuIdList(JSONArray menuInfoVoList) {
		List<Long> menuIdList = Lists.newArrayList();
		for (int i = 0; i < menuInfoVoList.size(); i++) {
			JSONObject object = menuInfoVoList.getJSONObject(i);
			menuIdList.add(Long.valueOf(object.get("id").toString()));
			JSONArray ch = (JSONArray) object.get("children");
			if (ch != null && ch.size() > 0) {
				menuIdList.addAll(this.getMenuIdList(ch));
			}
		}
		return menuIdList;
	}

	/**
	 * Find role list by user id.
	 *
	 * @param userId the user id
	 * @return the list
	 */
	@Override
	public List<Long> findRoleIdsByUserId(Long userId) {
		return roleMapper.selectIdsByUserId(userId);
	}

	/**
	 * Delete role by id.
	 *
	 * @param id the id
	 * @return the int
	 */
	@Override
	public int deleteRoleById(Long id) {
		EpSRole role = roleMapper.selectById(id);
		UserModel user = (UserModel) SecurityUtils.getSubject().getPrincipal();
		role.setId(id);
		role.setStatus(RecordStatus.DELETED.getValue());
		role.setUpdateUser(user.getId());
		role.setUpdateTime(new Date());
		return roleMapper.updateWithoutNull(role);
	}

	/**
	 * Find user list by role tag.
	 *
	 * @param roleTag the role tag
	 * @return the list
	 */
	@Override
	public List<UserInfoVo> findUserListByRoleTag(String roleTag) {
		return roleMapper.findUserListByRoleTag(roleTag);
	}
}
