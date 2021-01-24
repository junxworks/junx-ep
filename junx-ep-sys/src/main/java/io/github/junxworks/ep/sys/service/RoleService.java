/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  RoleService.java   
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

import io.github.junxworks.ep.sys.dto.RoleConditionDto;
import io.github.junxworks.ep.sys.dto.RoleInfoDto;
import io.github.junxworks.ep.sys.vo.RoleInfoVo;
import io.github.junxworks.ep.sys.vo.UserInfoVo;

/**
 * {类的详细说明}.
 *
 * @ClassName:  RoleService
 * @author: Michael
 * @date:   2020-7-19 12:17:47
 * @since:  v1.0
 */
public interface RoleService {

	/**
	 * Find role list by page.
	 *
	 * @param pageable the pageable
	 * @return the page info
	 */
	List<RoleInfoVo> findRoleListByCondition(RoleConditionDto condition);

	/**
	 * Find all role list.
	 *
	 * @return the list
	 */
	List<RoleInfoVo> findAllRoleList();

	/**
	 * Save role info.
	 *
	 * @param dto the dto
	 */
	void saveRoleInfo(RoleInfoDto dto);

	/**
	 * Find role list by user id.
	 *
	 * @param userId the user id
	 * @return the list
	 */
	List<Long> findRoleIdsByUserId(Long userId);

	/**
	 * Find user list by role tag.
	 *
	 * @param roleTag the role tag
	 * @return the list
	 */
	List<UserInfoVo> findUserListByRoleTag(String roleTag);

	/**
	 * Delete role by id.
	 *
	 * @param id the id
	 * @return the int
	 */
	int deleteRoleById(Long id);

}
