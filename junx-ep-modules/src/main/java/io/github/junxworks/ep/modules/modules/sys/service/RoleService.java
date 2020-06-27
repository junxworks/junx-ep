/*
 ***************************************************************************************
 * All rights Reserved, Designed By www.cqhyrc.com.cn
 * @Title:  RoleService.java   
 * @Package io.github.junxworks.ep.modules.modules.sys.service   
 * @Description: (用一句话描述该文件做什么)   
 * @author: AOC
 * @date:   2019-12-20 18:09:48   
 * @version V1.0 
 * @Copyright: 2019 重庆华宇集团. All rights reserved. 
 * 注意：本内容仅限于公司内部使用，禁止外泄以及用于其他的商业目
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.modules.modules.sys.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import io.github.junxworks.ep.modules.modules.sys.dto.RoleInfoDto;
import io.github.junxworks.ep.modules.modules.sys.dto.RolePageable;
import io.github.junxworks.ep.modules.modules.sys.vo.RoleInfoVo;
import io.github.junxworks.ep.modules.modules.sys.vo.UserInfoVo;

/**
 * @Description: 角色相关接口
 * @Author: ChenYang
 * @Date: 2019/7/2 9:01
 */
public interface RoleService {

	/**
	 * 分页查询角色信息
	 * @param pageable 查询条件
	 * @return 角色列表
	 */
	PageInfo<RoleInfoVo> findRoleListByPage(RolePageable pageable);

	/**
	 * 查询所有角色信息
	 */
	List<RoleInfoVo> findAllRoleList();

	/**
	 * 更新角色信息
	 * @param dto 角色信息
	 */
	void saveRoleInfo(RoleInfoDto dto);

	/**
	 * 查用户角色信息
	 * @param userId 用户ID
	 */
	List<RoleInfoVo> findRoleListByUserId(Long userId);

	/**
	 * 根据角色标签获取人员列表
	 *
	 * @param roleTag the role tag
	 * @return the list
	 */
	List<UserInfoVo> findUserListByRoleTag(String roleTag);

	/**
	 * 删除角色
	 *
	 * @param id the id
	 * @return the int
	 */
	int deleteRoleById(Long id);

}
