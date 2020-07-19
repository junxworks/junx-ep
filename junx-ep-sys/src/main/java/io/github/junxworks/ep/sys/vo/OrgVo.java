/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  OrgVo.java   
 * @Package io.github.junxworks.ep.sys.vo   
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
package io.github.junxworks.ep.sys.vo;

import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;
import io.github.junxworks.ep.core.orm.annotations.Column;
import io.github.junxworks.ep.core.orm.annotations.PrimaryKey;
import io.github.junxworks.ep.core.orm.annotations.Table;

/**
 * {类的详细说明}.
 *
 * @ClassName:  OrgVo
 * @author: Michael
 * @date:   2020-7-19 12:17:48
 * @since:  v1.0
 */

@Table(tableName = "s_org", tableComment = "")
public class OrgVo {

	/** id. */
	@PrimaryKey
	@Column(name = "id", type = "BIGINT", length = "19", nullable = "false", comment = "编号")
	private Long id;

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

	/** org no. */
	@Column(name = "orgNo", type = "VARCHAR", length = "50", nullable = "true", comment = "组织编码")
	private String orgNo;

	/** org name. */
	@Column(name = "orgName", type = "VARCHAR", length = "100", nullable = "true", comment = "组织名称")
	private String orgName;

	/** org type. */
	@Column(name = "orgType", type = "VARCHAR", length = "20", nullable = "true", comment = "机构类型 参考码表机构类型 orgType")
	private String orgType;

	/** parent no. */
	@Column(name = "parentNo", type = "VARCHAR", length = "50", nullable = "true", comment = "直接上级机构编码")
	private String parentNo;

	/** parent name. */
	private String parentName;

	/** top level no. */
	@Column(name = "topLevelNo", type = "VARCHAR", length = "50", nullable = "true", comment = "顶级机构编码")
	private String topLevelNo;

	/** org path. */
	@Column(name = "orgPath", type = "VARCHAR", length = "200", nullable = "true", comment = "组织路径")
	private String orgPath;

	/** remark. */
	@Column(name = "remark", type = "VARCHAR", length = "200", nullable = "true", comment = "备注")
	private String remark;

	/** parent. */
	private OrgVo parent;

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public OrgVo getParent() {
		return parent;
	}

	public void setParent(OrgVo parent) {
		this.parent = parent;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
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

}