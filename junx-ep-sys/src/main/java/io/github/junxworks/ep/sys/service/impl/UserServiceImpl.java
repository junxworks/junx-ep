/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  UserServiceImpl.java   
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import io.github.junxworks.ep.auth.model.UserModel;
import io.github.junxworks.ep.core.exception.BusinessException;
import io.github.junxworks.ep.sys.constants.EPConstants;
import io.github.junxworks.ep.sys.dto.TransferDto;
import io.github.junxworks.ep.sys.dto.UserInfoDto;
import io.github.junxworks.ep.sys.dto.UserListConditionDto;
import io.github.junxworks.ep.sys.entity.SUser;
import io.github.junxworks.ep.sys.entity.SUserRole;
import io.github.junxworks.ep.sys.mapper.UserMapper;
import io.github.junxworks.ep.sys.mapper.UserRoleMapper;
import io.github.junxworks.ep.sys.service.UserService;
import io.github.junxworks.ep.sys.vo.UserInfoVo;
import io.github.junxworks.junx.core.util.StringUtils;

/**
 * {类的详细说明}.
 *
 * @ClassName:  UserServiceImpl
 * @author: Michael
 * @date:   2020-7-19 12:17:47
 * @since:  v1.0
 */
@Service
public class UserServiceImpl implements UserService {

	/** user mapper. */
	@Autowired
	private UserMapper userMapper;

	/** user role mapper. */
	@Autowired
	private UserRoleMapper userRoleMapper;

	/**
	 * Find user list by page.
	 *
	 * @param cond the cond
	 * @return the page info
	 */
	@Override
	public List<UserInfoVo> findUserListByCondition(UserListConditionDto cond) {
		if (StringUtils.notNull(cond.getRoles())) {
			cond.setRoleIds(Lists.newArrayList(cond.getRoles().split(",")).stream().flatMap(l -> {
				return Stream.of(Long.valueOf(l));
			}).collect(Collectors.toList()));
		}
		return userMapper.selectAll(cond);
	}

	/**
	 * Find user info by id.
	 *
	 * @param userId the user id
	 * @return the user info vo
	 */
	@Override
	public UserInfoVo findUserInfoById(Long userId) {
		return userMapper.selectById(userId);
	}

	/**
	 * Save user info.
	 *
	 * @param dto the dto
	 */
	@Override
	@Transactional
	public void saveUserInfo(UserInfoDto dto) {
		//更新用户基本信息
		SUser user = new SUser();
		BeanUtils.copyProperties(dto, user);
		UserModel operator = (UserModel) SecurityUtils.getSubject().getPrincipal();
		Long operatorId = operator.getId();
		if (user.getId() == null) {//新增
			SUser existsUser = userMapper.selectByUserName(dto.getUserName());
			if (existsUser != null) {
				throw new BusinessException("用户名已被用户：" + existsUser.getName() + "注册");
			}
			user.setCreatorId(operatorId);
			user.setCreateDate(new Date());
			user.setPassword(EPConstants.DEFAULT_PASSWORD);//设置默认密码
			userMapper.insertWithoutNull(user);
		} else {//修改
			user.setModifierId(operatorId);
			user.setModifyDate(new Date());
			userMapper.updateWithoutNull(user);
		}
		Long userId = user.getId();
		//更新用户角色信息
		//删除历史角色，然后全量更新
		userRoleMapper.deleteByUserId(userId);
		if (dto.getRoles() != null) {
			List<SUserRole> roles = Lists.newArrayList();
			for (TransferDto role : dto.getRoles()) {
				SUserRole userRole = new SUserRole();
				userRole.setRoleId(Long.valueOf(role.getValue()));
				userRole.setUserId(userId);
				roles.add(userRole);
			}
			userRoleMapper.insertBatch(roles);
		}
	}

	/**
	 * Update user status.
	 *
	 * @param userId the user id
	 * @param status the status
	 */
	@Override
	public void updateUserStatus(Long userId, Byte status) {
		userMapper.updateUserStatus(userId, status);
	}

	/**
	 * Update user pass.
	 *
	 * @param userId the user id
	 * @param pass the pass
	 */
	@Override
	public void updateUserPass(Long userId, String pass) {
		userMapper.updateUserPass(userId, pass);
	}

	/**
	 * 返回 user list by auth 属性.
	 *
	 * @param auth the auth
	 * @return user list by auth 属性
	 */
	@Override
	public List<UserInfoVo> getUserListByAuth(String auth) {
		return userMapper.getUserListByAuth(auth);
	}

	/**
	 * Change user pass.
	 *
	 * @param userId the user id
	 * @param userInfoDto the user info dto
	 */
	@Override
	public void changeUserPass(Long userId, UserInfoDto userInfoDto) {
		SUser userName = userMapper.selectByUserName(userInfoDto.getUserName());
		if (userName != null && userInfoDto.getPass().equals(userName.getPassword())) {
			userMapper.updateUserPass(userId, userInfoDto.getPassword());
		} else {
			throw new BusinessException("旧密码输入错误");
		}
	}

	/**
	 * 返回 user list by role tag 属性.
	 *
	 * @param roleTag the role tag
	 * @return user list by role tag 属性
	 */
	@Override
	public List<UserInfoVo> getUserListByRoleTag(String roleTag) {
		return userMapper.getUserListByRoleTag(roleTag);
	}

	/**
	 * 返回 user list by role id 属性.
	 *
	 * @param roleId the role id
	 * @return user list by role id 属性
	 */
	@Override
	public List<UserInfoVo> getUserListByRoleId(Long roleId) {
		return userMapper.getUserListByRoleId(roleId);
	}
}
