package io.github.junxworks.ep.modules.modules.sys.dto;

import java.util.List;

import io.github.junxworks.ep.core.Pageable;

/**
 * @Description: 用户列表过滤条件
 * @Author: ChenYang
 * @Date: 2019/7/1 10:02
 */
public class UserPageable extends Pageable {

	/**
	 * 搜索关键字
	 */
	private String query;

	private String orgNo;

	private String roles;

	private List<Long> roleIds;

	public String getOrgNo() {
		return orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public List<Long> getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(List<Long> roleIds) {
		this.roleIds = roleIds;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}
}
