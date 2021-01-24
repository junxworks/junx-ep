/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  SUser.java   
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
 * @ClassName:  SUser
 * @author: Michael
 * @date:   2020-7-19 12:17:47
 * @since:  v1.0
 */

@Table(tableName = "s_user", tableComment = "")
public class SUser {

	/** id. */
	@PrimaryKey
	@Column(name = "id", type = "BIGINT", length = "19", nullable = "false", comment = "用户编号")
	private Long id;

	/** name. */
	@Column(name = "name", type = "VARCHAR", length = "50", nullable = "false", comment = "用户状态")
	private String name;

	/** user name. */
	@Column(name = "userName", type = "VARCHAR", length = "50", nullable = "true", comment = "账号")
	private String userName;

	/** password. */
	@Column(name = "password", type = "VARCHAR", length = "100", nullable = "true", comment = "登录密码")
	private String password;

	/** mobile. */
	@Column(name = "mobile", type = "VARCHAR", length = "11", nullable = "true", comment = "手机号码")
	private String mobile;

	/** id card. */
	@Column(name = "idCard", type = "VARCHAR", length = "20", nullable = "true", comment = "身份证号码")
	private String idCard;

	/** email. */
	@Column(name = "email", type = "VARCHAR", length = "30", nullable = "true", comment = "邮箱")
	private String email;

	/** org no. */
	@Column(name = "orgNo", type = "VARCHAR", length = "50", nullable = "true", comment = "所属组织")
	private String orgNo;

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
	@Column(name = "status", type = "TINYINT", length = "3", nullable = "true", comment = "状态 0：正常，1：冻结，2：锁定，3：离职 ")
	private Byte status;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getIdCard() {
		return this.idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getOrgNo() {
		return orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

}