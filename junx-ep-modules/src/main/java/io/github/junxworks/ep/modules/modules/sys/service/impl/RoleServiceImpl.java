package io.github.junxworks.ep.modules.modules.sys.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;

import io.github.junxworks.ep.auth.model.UserModel;
import io.github.junxworks.ep.core.exception.BusinessException;
import io.github.junxworks.ep.core.utils.PageUtils;
import io.github.junxworks.ep.modules.constants.RecordStatus;
import io.github.junxworks.ep.modules.modules.sys.dto.RoleInfoDto;
import io.github.junxworks.ep.modules.modules.sys.dto.RolePageable;
import io.github.junxworks.ep.modules.modules.sys.entity.SRole;
import io.github.junxworks.ep.modules.modules.sys.entity.SRoleMenu;
import io.github.junxworks.ep.modules.modules.sys.mapper.RoleMapper;
import io.github.junxworks.ep.modules.modules.sys.mapper.RoleMenuMapper;
import io.github.junxworks.ep.modules.modules.sys.service.RoleService;
import io.github.junxworks.ep.modules.modules.sys.vo.RoleInfoVo;
import io.github.junxworks.ep.modules.modules.sys.vo.UserInfoVo;
import io.github.junxworks.junx.core.util.StringUtils;

/**
 * @Description: 角色接口实现类
 * @Author: ChenYang
 * @Date: 2019/7/2 9:01
 */
@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleMapper roleMapper;

	@Autowired
	private RoleMenuMapper roleMenuMapper;

	/**
	 * 分页查询角色信息
	 */
	@Override
	public PageInfo<RoleInfoVo> findRoleListByPage(RolePageable pageable) {
		PageUtils.setPage(pageable);
		Page<RoleInfoVo> roleList = roleMapper.selectAll(pageable.getQuery());
		PageInfo<RoleInfoVo> voPageInfo = new PageInfo<>(roleList);
		return voPageInfo;
	}

	/**
	 * 查询所有角色
	 */
	@Override
	public List<RoleInfoVo> findAllRoleList() {
		List<RoleInfoVo> roleList = roleMapper.selectAll(null);
		return roleList;
	}

	/**
	 * 更新角色信息
	 */
	@Override
	@Transactional
	public void saveRoleInfo(RoleInfoDto dto) {
		//保存角色基本信息
		SRole role = new SRole();
		BeanUtils.copyProperties(dto, role);
		UserModel user = (UserModel) SecurityUtils.getSubject().getPrincipal();
		SRole nameRole = roleMapper.selectByName(dto.getRoleName());
		SRole tagRole = null;
		if (StringUtils.notNull(dto.getRoleTag())) {
			tagRole = roleMapper.selectByTag(dto.getRoleTag());
		}
		JSONArray menuInfoVoList = JSON.parseArray(dto.getMenuInfoList());
		List<Long> menuIdList = this.getMenuIdList(menuInfoVoList);
		if (role.getId() == null) {//新增
			if (nameRole != null) {
				throw new BusinessException("角色名：" + dto.getRoleName() + ",已存在");
			}
			if (tagRole != null) {
				throw new BusinessException("角色标签已经被角色\"" + tagRole.getRoleName() + "\"使用");
			}
			role.setCreatorId(user.getId());
			role.setCreateDate(new Date());
			role.setStatus(RecordStatus.NORMAL.getValue());
			roleMapper.insertWithoutNull(role);
			for (Long menuId : menuIdList) {
				SRoleMenu roleMenu = new SRoleMenu();
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
			role.setModifierId(user.getId());
			role.setModifyDate(new Date());
			roleMapper.updateWithoutNull(role);
			roleMenuMapper.deleteByMenuIds(roleId, menuIdList);//删除多余菜单
			List<Long> existsMenuIds = roleMenuMapper.queryMenusByRoleId(roleId);
			menuIdList.forEach(m -> {
				if (!existsMenuIds.contains(m)) {
					SRoleMenu roleMenu = new SRoleMenu();
					roleMenu.setRoleId(roleId);
					roleMenu.setMenuId(m);
					roleMenuMapper.insertWithoutNull(roleMenu);
				}
			});
		}
	}

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
	 * 查用户角色信息
	 */
	@Override
	public List<RoleInfoVo> findRoleListByUserId(Long userId) {
		return roleMapper.selectByUserId(userId);
	}

	@Override
	public int deleteRoleById(Long id) {
		SRole role = roleMapper.selectById(id);
		UserModel user = (UserModel) SecurityUtils.getSubject().getPrincipal();
		role.setId(id);
		role.setStatus(RecordStatus.DELETED.getValue());
		role.setModifierId(user.getId());
		role.setModifyDate(new Date());
		return roleMapper.updateWithoutNull(role);
	}

	@Override
	public List<UserInfoVo> findUserListByRoleTag(String roleTag) {
		return roleMapper.findUserListByRoleTag(roleTag);
	}
}
