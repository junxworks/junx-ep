/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  RoleInfoDto.java   
 * @Package io.github.junxworks.ep.sys.dto   
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

	/** menu info list.是一个menu info对象数组的json串 */
	private String menuInfoList;

	/** 逗号隔开的menu id. */
	private String selectedMenus;

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

	public String getSelectedMenus() {
		return selectedMenus;
	}

	public void setSelectedMenus(String selectedMenus) {
		this.selectedMenus = selectedMenus;
	}

}
