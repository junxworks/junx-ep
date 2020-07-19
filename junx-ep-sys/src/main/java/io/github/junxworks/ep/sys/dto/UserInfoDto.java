/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  UserInfoDto.java   
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

import java.util.Date;
import java.util.List;

/**
 * {类的详细说明}.
 *
 * @ClassName:  UserInfoDto
 * @author: Michael
 * @date:   2020-7-19 12:17:48
 * @since:  v1.0
 */
public class UserInfoDto {

	/** id. */
	private Long id;

	/** name. */
	private String name;

	/** user name. */
	private String userName;

	/** password. */
	private String password;

	/** mobile. */
	private String mobile;

	/** id card. */
	private String idCard;

	/** email. */
	private String email;

	/** creator id. */
	private Long creatorId;

	/** create date. */
	private Date createDate;

	/** modifier id. */
	private Long modifierId;

	/** modify date. */
	private Date modifyDate;

	/** status. */
	private Byte status;

	/** role. */
	private String role;

	/** pass. */
	private String pass;

	/** org no. */
	private String orgNo;

	public String getOrgNo() {
		return orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
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

	public Long getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Long getModifierId() {
		return modifierId;
	}

	public void setModifierId(Long modifierId) {
		this.modifierId = modifierId;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}
}
