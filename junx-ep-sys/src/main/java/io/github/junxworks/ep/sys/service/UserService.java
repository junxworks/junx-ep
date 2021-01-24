/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  UserService.java   
 * @Package io.github.junxworks.ep.sys.service   
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
package io.github.junxworks.ep.sys.service;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;

import io.github.junxworks.ep.sys.dto.UserInfoDto;
import io.github.junxworks.ep.sys.dto.UserListConditionDto;
import io.github.junxworks.ep.sys.vo.UserInfoVo;

/**
 * {类的详细说明}.
 *
 * @ClassName:  UserService
 * @author: Michael
 * @date:   2020-7-19 12:17:48
 * @since:  v1.0
 */
public interface UserService {

	/**
	 * Find user list by page.
	 *
	 * @param pageable the pageable
	 * @return the page info
	 */
	List<UserInfoVo> findUserListByCondition(UserListConditionDto cond);

	/**
	 * Find user info by id.
	 *
	 * @param userId the user id
	 * @return the user info vo
	 */
	UserInfoVo findUserInfoById(Long userId);

	/**
	 * Save user info.
	 *
	 * @param dto the dto
	 */
	void saveUserInfo(UserInfoDto dto);

	/**
	 * Update user status.
	 *
	 * @param userId the user id
	 * @param status the status
	 */
	int updateUserStatus(UserInfoDto userInfoDto);

	/**
	 * Update user pass.
	 *
	 * @param userId the user id
	 * @param pass the pass
	 */
	int updateUserPass(Long userId, String pass);

	/**
	 * 返回 user list by auth 属性.
	 *
	 * @param auth the auth
	 * @return user list by auth 属性
	 */
	List<UserInfoVo> getUserListByAuth(String auth);

	/**
	 * Change user pass.
	 *
	 * @param userId the user id
	 * @param userInfoDto the user info dto
	 */
	void changeUserPass(Long userId, @RequestBody UserInfoDto userInfoDto);

	/**
	 * 返回 user list by role tag 属性.
	 *
	 * @param roleTag the role tag
	 * @return user list by role tag 属性
	 */
	List<UserInfoVo> getUserListByRoleTag(String roleTag);

	/**
	 * 返回 user list by role id 属性.
	 *
	 * @param roleId the role id
	 * @return user list by role id 属性
	 */
	List<UserInfoVo> getUserListByRoleId(Long roleId);

}
