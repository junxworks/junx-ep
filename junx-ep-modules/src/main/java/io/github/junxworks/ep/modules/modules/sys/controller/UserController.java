package io.github.junxworks.ep.modules.modules.sys.controller;

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
import io.github.junxworks.ep.modules.annotations.OpLog;
import io.github.junxworks.ep.modules.constants.EPConstants;
import io.github.junxworks.ep.modules.modules.sys.dto.UserInfoDto;
import io.github.junxworks.ep.modules.modules.sys.dto.UserPageable;
import io.github.junxworks.ep.modules.modules.sys.service.RoleService;
import io.github.junxworks.ep.modules.modules.sys.service.UserService;
import io.github.junxworks.ep.modules.modules.sys.vo.RoleInfoVo;
import io.github.junxworks.ep.modules.modules.sys.vo.UserInfoVo;
import io.github.junxworks.ep.auth.model.UserModel;

/**
 * @Description: 用户模板
 * @Author: ChenYang
 * @Date: 2019/6/28 18:01
 */
@RestController
@RequestMapping("/ep/sys")
public class UserController {

	@Autowired
	private UserService userService;

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
			Result res=new Result(EPConstants.CODE_NEED_RESET_PASSWORD, "请重置密码");
			res.setData(user);
			return res;
		}
		return Result.ok(user);
	}

	/**
	 * 用户列表
	 */
	@GetMapping(value = "/users")
	public Result getUserList(UserPageable pageable) {
		Result result = Result.ok();
		PageInfo<UserInfoVo> userPage = userService.findUserListByPage(pageable);
		result.setData(userPage);
		return result;
	}

	/**
	 * 查询用户信息
	 */
	@GetMapping(value = "/users/{userId}")
	public Result getUserInfo(@PathVariable Long userId) {
		Result result = Result.ok();
		UserInfoVo vo = userService.findUserInfoById(userId);
		result.setData(vo);
		return result;
	}

	/**
	 * 获取用户对应所有角色
	 */
	@GetMapping(value = "/users/{userId}/roles")
	public Result getAllRoleList(@PathVariable Long userId) {
		Result result = Result.ok();
		List<RoleInfoVo> roleList = roleService.findAllRoleList();
		if (userId != null) {
			List<RoleInfoVo> userRoles = roleService.findRoleListByUserId(userId);
			for (RoleInfoVo vo : roleList) {
				//默认不勾选
				vo.setChecked(false);
				for (RoleInfoVo userRole : userRoles) {
					if (vo.getId().equals(userRole.getId())) {
						vo.setChecked(true);
					}
				}
			}
		}
		result.setData(roleList);
		return result;
	}

	/**
	 * 保存用户信息
	 */
	@PostMapping(value = "/users")
	@OpLog("保存用户信息")
	public Result saveUserInfo(@RequestBody UserInfoDto dto) {
		UserModel user = (UserModel) SecurityUtils.getSubject().getPrincipal();
		dto.setCreatorId(user.getId());
		dto.setCreateDate(new Date());
		userService.saveUserInfo(dto);
		return Result.ok();
	}

	/**
	 * 更新用户信息
	 */
	@PutMapping(value = "/users/{userId}/status")
	@OpLog("更新用户状态")
	public Result updateUserStatus(@PathVariable Long userId, @RequestBody UserInfoDto userInfoDto) {
		UserModel user = (UserModel) SecurityUtils.getSubject().getPrincipal();
		userInfoDto.setModifierId(user.getId());
		userInfoDto.setModifyDate(new Date());
		userService.updateUserStatus(userId, userInfoDto.getStatus());
		return Result.ok();
	}

	/**
	 * 重置用户密码
	 */
	@PutMapping(value = "/users/{userId}/pass")
	@OpLog("重置用户密码")
	public Result updateUserPass(@PathVariable Long userId, @RequestBody UserInfoDto userInfoDto) {
		userService.updateUserPass(userId, userInfoDto.getPass());
		return Result.ok();
	}

	@GetMapping(value = "/users/{auth}/users")
	public Result getUserListByAuth(@PathVariable("auth") String auth) {
		return Result.ok(userService.getUserListByAuth(auth));
	}

	/**
	 * 修改用户密码
	 */
	@PutMapping(value = "/users/{userId}/change-pass")
	@OpLog("修改用户密码")
	public Result changeUserPass(@PathVariable Long userId, @RequestBody UserInfoDto userInfoDto) {
		try {
			userService.changeUserPass(userId, userInfoDto);
			return Result.ok();
		} catch (Exception e) {
			return Result.error(e.getMessage());
		}
	}
}
