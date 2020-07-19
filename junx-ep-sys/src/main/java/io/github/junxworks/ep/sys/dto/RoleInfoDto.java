/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  RoleInfoDto.java   
 * @Package io.github.junxworks.ep.sys.dto   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2020-7-19 12:17:48   
 * @version V1.0 
 * @Copyright: 2020 Junxworks. All rights reserved. 
 * 注意：
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.sys.dto;

/**
 * {类的详细说明}.
 *
 * @ClassName:  RoleInfoDto
 * @author: Michael
 * @date:   2020-7-19 12:17:48
 * @since:  v1.0
 */
public class RoleInfoDto {

	/** id. */
	private Long id;

	/** role name. */
	private String roleName;

	/** role tag. */
	private String roleTag;

	/** menu info list. */
	private String menuInfoList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRoleTag() {
		return roleTag;
	}

	public void setRoleTag(String roleTag) {
		this.roleTag = roleTag;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getMenuInfoList() {
		return menuInfoList;
	}

	public void setMenuInfoList(String menuInfoList) {
		this.menuInfoList = menuInfoList;
	}
}
