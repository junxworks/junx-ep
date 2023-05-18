/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  OrgDto.java   
 * @Package io.github.junxworks.ep.sys.dto   
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
package io.github.junxworks.ep.sys.dto;

/**
 * {类的详细说明}.
 *
 * @ClassName:  OrgDto
 * @author: Michael
 * @date:   2020-7-19 12:17:48
 * @since:  v1.0
 */

public class OrgDto {

	/** id. */
	private Long id;

	/** status. */
	private Byte status;

	/** org no. */
	private String orgNo;

	/** org name. */
	private String orgName;

	/** org type. */
	private String orgType;

	/** parent no. */
	private String parentNo;

	/** top level no. */
	private String topLevelNo;

	/** org path. */
	private String orgPath;

	/** remark. */
	private String remark;

	private String rootNo;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Byte getStatus() {
		return this.status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public String getOrgNo() {
		return this.orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public String getOrgName() {
		return this.orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgType() {
		return this.orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	public String getParentNo() {
		return this.parentNo;
	}

	public void setParentNo(String parentNo) {
		this.parentNo = parentNo;
	}

	public String getTopLevelNo() {
		return this.topLevelNo;
	}

	public void setTopLevelNo(String topLevelNo) {
		this.topLevelNo = topLevelNo;
	}

	public String getOrgPath() {
		return this.orgPath;
	}

	public void setOrgPath(String orgPath) {
		this.orgPath = orgPath;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRootNo() {
		return rootNo;
	}

	public void setRootNo(String rootNo) {
		this.rootNo = rootNo;
	}

}