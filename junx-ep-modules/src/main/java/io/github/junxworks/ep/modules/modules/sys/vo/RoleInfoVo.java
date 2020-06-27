package io.github.junxworks.ep.modules.modules.sys.vo;

/**
 * @Description: 角色信息
 * @Author: ChenYang
 * @Date: 2019/7/2 9:08
 */
public class RoleInfoVo {

	private Long id;

	private String roleName;

	private String roleTag;

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
