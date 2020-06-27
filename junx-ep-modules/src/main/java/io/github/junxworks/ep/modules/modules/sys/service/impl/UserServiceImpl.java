package io.github.junxworks.ep.modules.modules.sys.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;

import io.github.junxworks.ep.core.exception.BusinessException;
import io.github.junxworks.ep.core.utils.PageUtils;
import io.github.junxworks.ep.modules.constants.EPConstants;
import io.github.junxworks.ep.modules.modules.sys.dto.UserInfoDto;
import io.github.junxworks.ep.modules.modules.sys.dto.UserPageable;
import io.github.junxworks.ep.modules.modules.sys.entity.SUser;
import io.github.junxworks.ep.modules.modules.sys.entity.SUserRole;
import io.github.junxworks.ep.modules.modules.sys.mapper.UserMapper;
import io.github.junxworks.ep.modules.modules.sys.mapper.UserRoleMapper;
import io.github.junxworks.ep.modules.modules.sys.service.UserService;
import io.github.junxworks.ep.modules.modules.sys.vo.UserInfoVo;
import io.github.junxworks.junx.core.util.StringUtils;

/**
 * @Description: 用户服务实现类
 * @Author: ChenYang
 * @Date: 2019/7/1 9:08
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private UserRoleMapper userRoleMapper;

	/**
	 * 页查询用户信息
	 */
	@Override
	public PageInfo<UserInfoVo> findUserListByPage(UserPageable cond) {
		PageUtils.setPage(cond);
		if (StringUtils.notNull(cond.getRoles())) {
			cond.setRoleIds(Lists.newArrayList(cond.getRoles().split(",")).stream().flatMap(l -> {
				return Stream.of(Long.valueOf(l));
			}).collect(Collectors.toList()));
		}
		return new PageInfo<>(userMapper.selectAll(cond));
	}

	/**
	 * 根据用户ID查询用户信息
	 */
	@Override
	public UserInfoVo findUserInfoById(Long userId) {
		return userMapper.selectById(userId);
	}

	/**
	 * 更新用户信息
	 */
	@Override
	@Transactional
	public void saveUserInfo(UserInfoDto dto) {
		//更新用户基本信息
		SUser user = new SUser();
		BeanUtils.copyProperties(dto, user);
		SUser existsUser = null;
		if (StringUtils.notNull(dto.getUserName())) {
			existsUser = userMapper.selectByUserName(dto.getUserName());
		}
		if (user.getId() == null) {//新增
			if (existsUser != null) {
				throw new BusinessException("用户名已被用户：" + existsUser.getName() + "注册");
			}
			user.setPassword(EPConstants.DEFAULT_PASSWORD);//设置默认密码
			userMapper.insertWithoutNull(user);
		} else {//修改
			if (existsUser != null && !dto.getId().equals(existsUser.getId())) {
				throw new BusinessException("用户名被用户：" + existsUser.getName() + "注册");
			}
			userMapper.updateWithoutNull(user);
		}
		//更新用户角色信息
		//删除历史角色，然后全量更新
		userRoleMapper.deleteByUserId(user.getId());

		if (StringUtils.isNotBlank(dto.getRole())) {
			String[] roleArr = dto.getRole().split(",");
			for (String role : roleArr) {
				SUserRole userRole = new SUserRole();
				userRole.setRoleId(Long.valueOf(role));
				userRole.setUserId(user.getId());
				userRoleMapper.insertWithoutNull(userRole);
			}
		}

	}

	/**
	 * 更新用户状态
	 */
	@Override
	public void updateUserStatus(Long userId, Byte status) {
		userMapper.updateUserStatus(userId, status);
	}

	/**
	 * 更新用户密码
	 */
	@Override
	public void updateUserPass(Long userId, String pass) {
		userMapper.updateUserPass(userId, pass);
	}

	@Override
	public List<UserInfoVo> getUserListByAuth(String auth) {
		List<UserInfoVo> list = userMapper.getUserListByAuth(auth);
		return list;
	}

	@Override
	public void changeUserPass(Long userId, UserInfoDto userInfoDto) {
		SUser userName = userMapper.selectByUserName(userInfoDto.getUserName());
		if (userName != null && userInfoDto.getPass().equals(userName.getPassword())) {
			userMapper.updateUserPass(userId, userInfoDto.getPassword());
		} else {
			throw new BusinessException("旧密码输入错误");
		}
	}

	@Override
	public List<UserInfoVo> getUserListByRoleTag(String roleTag) {
		return userMapper.getUserListByRoleTag(roleTag);
	}

	@Override
	public List<UserInfoVo> getUserListByRoleId(Long roleId) {
		return userMapper.getUserListByRoleId(roleId);
	}
}
