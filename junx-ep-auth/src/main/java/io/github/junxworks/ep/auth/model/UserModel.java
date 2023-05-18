/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  UserModel.java   
 * @Package io.github.junxworks.ep.auth.model   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2020-7-19 12:18:41   
 * @version V1.0 
 * @Copyright: 2020 Junxworks. All rights reserved. 
 * 注意：
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.auth.model;

import java.io.Serializable;
import java.util.List;

/**
 * {类的详细说明}.
 *
 * @ClassName:  UserModel
 * @author: Michael
 * @date:   2020-7-19 12:18:41
 * @since:  v1.0
 */
public class UserModel implements Serializable {

	/** 常量 SUPER_ADMIN. */
	public static final long SUPER_ADMIN = -1;

	/** 常量 serialVersionUID. */
	private static final long serialVersionUID = 7941789731039560137L;

	/** id. */
	private Long id;

	/** nick. */
	private String nick;

	/** name. */
	private String name;

	/** username. */
	private String username;

	/** id card. */
	private String idCard;

	/** email. */
	private String email;

	/** mobile. */
	private String mobile;

	/** user type. */
	private int userType;

	/** org no. */
	private String orgNo;

	/** org name. */
	private String orgName;

	/** org type. */
	private String orgType;

	/** status. */
	private int status;

	/** authorizations. */
	private List<String> authorizations;

	/** roles. */
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
		return id != null && SUPER_ADMIN == id;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
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
	 * Auth.
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
	 * Checks for role.
	 *
	 * @param roleTag the role tag
	 * @return true, if successful
	 */
	public boolean hasRole(String roleTag) {
		return roles != null && roles.contains(roleTag);
	}
}
