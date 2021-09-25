package io.github.junxworks.ep.sys.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * <p>Entity Class</p>
 * <p>Table: ep_s_top_item</p>
 *
 * @since 2021年9月9日 下午2:57:04 Generated by JunxPlugin
 */

public class TopItemVo {

	private Long id;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createDate;

	private Long createUser;

	private String createUserName;

	private Date updateDate;

	private Long updateUser;

	private String itemName;

	private Integer itemIndex;

	private String itemInnerHtml;

	private String itemJsPath;

	private String itemCssPath;

	private Byte status;

	private String remark;

	public String getItemCssPath() {
		return itemCssPath;
	}

	public void setItemCssPath(String itemCssPath) {
		this.itemCssPath = itemCssPath;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Long getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(Long createUser) {
		this.createUser = createUser;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Long getUpdateUser() {
		return this.updateUser;
	}

	public void setUpdateUser(Long updateUser) {
		this.updateUser = updateUser;
	}

	public String getItemName() {
		return this.itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Integer getItemIndex() {
		return this.itemIndex;
	}

	public void setItemIndex(Integer itemIndex) {
		this.itemIndex = itemIndex;
	}

	public String getItemInnerHtml() {
		return this.itemInnerHtml;
	}

	public void setItemInnerHtml(String itemInnerHtml) {
		this.itemInnerHtml = itemInnerHtml;
	}

	public String getItemJsPath() {
		return this.itemJsPath;
	}

	public void setItemJsPath(String itemJsPath) {
		this.itemJsPath = itemJsPath;
	}

	public Byte getStatus() {
		return this.status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}
}