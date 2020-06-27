package io.github.junxworks.ep.auth.model;

import java.io.Serializable;
import java.util.List;

public class UserModel implements Serializable {

	/** 超级管理员的ID */
	public static final long SUPER_ADMIN = -1;

	private static final long serialVersionUID = 7941789731039560137L;

	/** id. */
	private Long id;

	/** 昵称. */
	private String nick;

	/** 姓名. */
	private String name;

	/** 用户名. */
	private String username;

	/** 身份证. */
	private String idCard;

	/** 邮箱. */
	private String email;

	/** 手机号. */
	private String mobile;

	/** 用户类型. */
	private int userType;

	/**组织编码 */
	private String orgNo;

	/**组织名称. */
	private String orgName;

	/**组织类型. */
	private String orgType;

	private int status;

	/** 权限. */
	private List<String> authorizations;

	private List<String> roles;

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getOrgNo() {
		return orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public boolean isAdmin() {
		return SUPER_ADMIN == id;
	}

	/* shiro删除认证cache的时候会用到
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return username;
	}

	public List<String> getAuthorizations() {
		return authorizations;
	}

	public void setAuthorizations(List<String> authorizations) {
		this.authorizations = authorizations;
	}

	/**
	 * 是否有权限.
	 *
	 * @param authorize the authorize
	 * @return true, if successful
	 */
	public boolean auth(String authorize) {
		return authorizations != null && authorizations.contains(authorize);
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	/**
	 * 是否有角色标签
	 *
	 * @param role the role
	 * @return true, if successful
	 */
	public boolean hasRole(String roleTag) {
		return roles != null && roles.contains(roleTag);
	}
}
