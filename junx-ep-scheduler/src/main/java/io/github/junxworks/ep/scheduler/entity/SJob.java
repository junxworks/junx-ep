/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  ScheduleJobEntity.java   
 * @Package io.github.junxworks.ep.scheduler.entity   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2020-7-19 12:18:05   
 * @version V1.0 
 * @Copyright: 2020 Junxworks. All rights reserved. 
 * 注意：
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.scheduler.entity;

import java.util.Date;

import io.github.junxworks.ep.core.orm.annotations.Column;
import io.github.junxworks.ep.core.orm.annotations.PrimaryKey;
import io.github.junxworks.ep.core.orm.annotations.Table;

/**
 * {类的详细说明}.
 *
 * @ClassName:  ScheduleJobEntity
 * @author: Michael
 * @date:   2020-7-19 12:18:05
 * @since:  v1.0
 */
@Table(tableName = "s_job")
public class SJob {
	@PrimaryKey
	@Column(name = "id", type = "BIGINT", length = "19", nullable = "false", comment = "任务id")
	private Long id;

	@Column(name = "jobName", type = "VARCHAR", length = "100", nullable = "true", comment = "任务名称")
	private String jobName;

	@Column(name = "beanName", type = "VARCHAR", length = "200", nullable = "true", comment = "spring bean名称")
	private String beanName;

	@Column(name = "methodName", type = "VARCHAR", length = "100", nullable = "true", comment = "方法名")
	private String methodName;

	@Column(name = "params", type = "VARCHAR", length = "2,000", nullable = "true", comment = "参数")
	private String params;

	@Column(name = "cronExpression", type = "VARCHAR", length = "100", nullable = "true", comment = "cron表达式")
	private String cronExpression;

	@Column(name = "status", type = "TINYINT", length = "3", nullable = "true", comment = "任务状态  0：正常  1：暂停")
	private Byte status;

	@Column(name = "remark", type = "VARCHAR", length = "255", nullable = "true", comment = "任务备注")
	private String remark;

	@Column(name = "creatorId", type = "BIGINT", length = "19", nullable = "true", comment = "创建人编号")
	private Long creatorId;

	@Column(name = "createDate", type = "TIMESTAMP", length = "19", nullable = "true", comment = "创建日期")
	private Date createDate;

	@Column(name = "modifierId", type = "BIGINT", length = "19", nullable = "true", comment = "修改人编号")
	private Long modifierId;

	@Column(name = "modifyDate", type = "TIMESTAMP", length = "19", nullable = "true", comment = "修改日期")
	private Date modifyDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
