/*
 ***************************************************************************************
 * All rights Reserved, Designed By www.cqhyrc.com.cn
 * @Title:  UserService.java   
 * @Package io.github.junxworks.ep.modules.modules.sys.service   
 * @Description: (用一句话描述该文件做什么)   
 * @author: AOC
 * @date:   2020-3-5 14:49:04   
 * @version V1.0 
 * @Copyright: 2020 重庆华宇集团. All rights reserved. 
 * 注意：本内容仅限于公司内部使用，禁止外泄以及用于其他的商业目
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.modules.modules.sys.service;

import com.github.pagehelper.PageInfo;
import io.github.junxworks.ep.modules.modules.sys.dto.UserInfoDto;
import io.github.junxworks.ep.modules.modules.sys.dto.UserPageable;
import io.github.junxworks.ep.modules.modules.sys.vo.UserInfoVo;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @Description: 用户服务
 * @Author: ChenYang
 * @Date: 2019/7/1 9:08
 */
public interface UserService {

	/**
	 * 分页查询用户信息
	 * @param pageable 分页条件
	 * @return 用户列表
	 */
	PageInfo<UserInfoVo> findUserListByPage(UserPageable pageable);

	/**
	 * 根据用户ID查询用户信息
	 * @param userId 用户ID
	 * @return 用户信息
	 */
	UserInfoVo findUserInfoById(Long userId);

	/**
	 * 更新用户信息
	 * @param dto 用户信息
	 */
	void saveUserInfo(UserInfoDto dto);

	/**
	 * 更新用户状态
	 * @param userId 用户ID
	 * @param status 状态
	 */
	void updateUserStatus(Long userId, Byte status);

	/**
	 * 更新用户密码
	 * @param userId 用户ID
	 * @param pass 密码(MD5)
	 */
	void updateUserPass(Long userId, String pass);

	List<UserInfoVo> getUserListByAuth(String auth);

	void changeUserPass(Long userId, @RequestBody UserInfoDto userInfoDto);

	/**
	 * 根据角色标签查询人员列表
	 *
	 * @param roleTag the role tag
	 * @return user list by role tag 属性
	 */
	List<UserInfoVo> getUserListByRoleTag(String roleTag);

	/**
	 * 根据角色ID查询人员列表
	 *
	 * @param roleId the role id
	 * @return user list by role id 属性
	 */
	List<UserInfoVo> getUserListByRoleId(Long roleId);

}
