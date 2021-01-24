/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  SRole.java   
 * @Package io.github.junxworks.ep.sys.entity   
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
package io.github.junxworks.ep.sys.entity;

import io.github.junxworks.ep.core.orm.annotations.Table;
import io.github.junxworks.ep.core.orm.annotations.PrimaryKey;
import io.github.junxworks.ep.core.orm.annotations.Column;
import java.util.Date;

/**
 * {类的详细说明}.
 *
 * @ClassName:  SRole
 * @author: Michael
 * @date:   2020-7-19 12:17:47
 * @since:  v1.0
 */

@Table(tableName = "s_role", tableComment = "")
public class SRole {

	/** id. */
	@PrimaryKey
	@Column(name = "id", type = "BIGINT", length = "19", nullable = "false", comment = "角色编号")
	private Long id;

	/** role name. */
	@Column(name = "roleName", type = "VARCHAR", length = "20", nullable = "true", comment = "角色名称")
	private String roleName;

	/** role tag. */
	@Column(name = "roleTag", type = "VARCHAR", length = "50", nullable = "true", comment = "角色标签")
	private String roleTag;

	/** creator id. */
	@Column(name = "creatorId", type = "BIGINT", length = "19", nullable = "true", comment = "创建人编号")
	private Long creatorId;

	/** create date. */
	@Column(name = "createDate", type = "TIMESTAMP", length = "19", nullable = "true", comment = "创建日期")
	private Date createDate;

	/** modifier id. */
	@Column(name = "modifierId", type = "BIGINT", length = "19", nullable = "true", comment = "修改人编号")
	private Long modifierId;

	/** modify date. */
	@Column(name = "modifyDate", type = "TIMESTAMP", length = "19", nullable = "true", comment = "修改日期")
	private Date modifyDate;

	/** status. */
	@Column(name = "status", type = "TINYINT", length = "3", nullable = "true", comment = "状态 -1已删除 0正常")
	private Byte status;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleTag() {
		return roleTag;
	}

	public void setRoleTag(String roleTag) {
		this.roleTag = roleTag;
	}

	public Long getCreatorId() {
		return this.creatorId;
	}

	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Long getModifierId() {
		return this.modifierId;
	}

	public void setModifierId(Long modifierId) {
		this.modifierId = modifierId;
	}

	public Date getModifyDate() {
		return this.modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public Byte getStatus() {
		return this.status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}
}