/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  UserController.java   
 * @Package io.github.junxworks.ep.sys.controller   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2020-7-19 12:17:47   
 * @version V1.0 
 * @Copyright: 2020 Junxworks. All rights reserved. 
 * 注意：
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.sys.controller;

import java.util.Date;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import io.github.junxworks.ep.core.Result;
import io.github.junxworks.ep.core.utils.PageUtils;
import io.github.junxworks.ep.sys.annotations.OpLog;
import io.github.junxworks.ep.sys.constants.EPConstants;
import io.github.junxworks.ep.sys.dto.UserInfoDto;
import io.github.junxworks.ep.sys.dto.UserListConditionDto;
import io.github.junxworks.ep.sys.service.RoleService;
import io.github.junxworks.ep.sys.service.UserService;
import io.github.junxworks.ep.sys.vo.RoleInfoVo;
import io.github.junxworks.ep.sys.vo.UserInfoVo;
import io.github.junxworks.ep.auth.model.UserModel;

/**
 * {类的详细说明}.
 *
 * @ClassName:  UserController
 * @author: Michael
 * @date:   2020-7-19 12:17:47
 * @since:  v1.0
 */
@RestController
@RequestMapping("/ep/sys/users")
public class UserController {

	/** user service. */
	@Autowired
	private UserService userService;

	/** role service. */
	@Autowired
	private RoleService roleService;

	/**
	 * 当前登录用户信息
	 */
	@GetMapping(value = "/current-user")
	public Result getCurrentUser() {
		UserModel user = (UserModel) SecurityUtils.getSubject().getPrincipal();
		UserInfoVo vo = userService.findUserInfoById(user.getId());
		if (EPConstants.DEFAULT_PASSWORD.equals(vo.getPassword())) {
			Result res = new Result(EPConstants.CODE_NEED_RESET_PASSWORD, "请重置密码");
			res.setData(user);
			return res;
		}
		return Result.ok(user);
	}

	/**
	 * 返回 user list 属性.
	 *
	 * @param pageable the pageable
	 * @return user list 属性
	 */
	@GetMapping()
	public Result getUserList(UserListConditionDto condition) {
		PageUtils.setPage(condition);
		return Result.ok(new PageInfo<UserInfoVo>(userService.findUserListByCondition(condition)));
	}

	/**
	 * 返回 user info 属性.
	 *
	 * @param userId the user id
	 * @return user info 属性
	 */
	@GetMapping(value = "/{userId}")
	public Result getUserInfo(@PathVariable Long userId) {
		return Result.ok(userService.findUserInfoById(userId));
	}

	/**
	 * 返回 all role list 属性.
	 *
	 * @param userId the user id
	 * @return all role list 属性
	 */
	@GetMapping(value = "/{userId}/roles")
	public Result getAllRoleList(@PathVariable Long userId) {
		Result result = Result.ok();
		List<RoleInfoVo> roleList = roleService.findAllRoleList();
		result.setData(roleList);
		result.addAttribute("checked", roleService.findRoleIdsByUserId(userId));
		return result;
	}

	/**
	 * Save user info.
	 *
	 * @param dto the dto
	 * @return the result
	 */
	@PostMapping()
	@OpLog("保存用户信息")
	public Result saveUserInfo(@RequestBody UserInfoDto dto) {
		userService.saveUserInfo(dto);
		return Result.ok();
	}

	/**
	 * Update user status.
	 *
	 * @param userId the user id
	 * @param userInfoDto the user info dto
	 * @return the result
	 */
	@PutMapping(value = "/{userId}/status")
	@OpLog("更新用户状态")
	public Result updateUserStatus(@PathVariable Long userId, @RequestBody UserInfoDto userInfoDto) {
		UserModel user = (UserModel) SecurityUtils.getSubject().getPrincipal();
		userInfoDto.setModifierId(user.getId());
		userInfoDto.setModifyDate(new Date());
		userService.updateUserStatus(userId, userInfoDto.getStatus());
		return Result.ok();
	}

	/**
	 * Update user pass.
	 *
	 * @param userId the user id
	 * @param userInfoDto the user info dto
	 * @return the result
	 */
	@PutMapping(value = "/{userId}/pass")
	@OpLog("重置用户密码")
	public Result updateUserPass(@PathVariable Long userId, @RequestBody UserInfoDto userInfoDto) {
		userService.updateUserPass(userId, userInfoDto.getPass());
		return Result.ok();
	}

	/**
	 * 返回 user list by auth 属性.
	 *
	 * @param auth the auth
	 * @return user list by auth 属性
	 */
	@GetMapping(value = "/{auth}/users")
	public Result getUserListByAuth(@PathVariable("auth") String auth) {
		return Result.ok(userService.getUserListByAuth(auth));
	}

	/**
	 * Change user pass.
	 *
	 * @param userId the user id
	 * @param userInfoDto the user info dto
	 * @return the result
	 */
	@PutMapping(value = "/{userId}/change-pass")
	@OpLog("修改用户密码")
	public Result changeUserPass(@PathVariable Long userId, @RequestBody UserInfoDto userInfoDto) {
		userService.changeUserPass(userId, userInfoDto);
		return Result.ok();
	}
}
