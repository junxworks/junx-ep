package io.github.junxworks.ep.modules.modules.sys.dto;

/**
 * @Description: 角色信息
 * @Author: ChenYang
 * @Date: 2019/7/2 9:26
 */
public class RoleInfoDto {

	private Long id;

	private String roleName;

	private String roleTag;

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
