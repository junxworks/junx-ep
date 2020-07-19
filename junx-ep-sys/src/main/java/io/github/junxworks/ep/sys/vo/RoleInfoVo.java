/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  RoleInfoVo.java   
 * @Package io.github.junxworks.ep.sys.vo   
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
package io.github.junxworks.ep.sys.vo;

/**
 * {类的详细说明}.
 *
 * @ClassName:  RoleInfoVo
 * @author: Michael
 * @date:   2020-7-19 12:17:47
 * @since:  v1.0
 */
public class RoleInfoVo {

	/** id. */
	private Long id;

	/** role name. */
	private String roleName;

	/** role tag. */
	private String roleTag;

	/** checked. */
	private boolean checked;

	public String getRoleTag() {
		return roleTag;
	}

	public void setRoleTag(String roleTag) {
		this.roleTag = roleTag;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}
}
