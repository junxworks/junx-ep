/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  ScheduleJobLogEntity.java   
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
 * @ClassName:  ScheduleJobLogEntity
 * @author: Michael
 * @date:   2020-7-19 12:18:05
 * @since:  v1.0
 */
@Table(tableName = "s_job_log")
public class SJobLog {

	@PrimaryKey
	@Column(name = "id", type = "BIGINT", length = "19", nullable = "false", comment = "任务日志id")
	private Long id;

	@Column(name = "jobId", type = "BIGINT", length = "19", nullable = "false", comment = "任务id")
	private Long jobId;

	@Column(name = "beanName", type = "VARCHAR", length = "200", nullable = "true", comment = "spring bean名称")
	private String beanName;

	@Column(name = "methodName", type = "VARCHAR", length = "100", nullable = "true", comment = "方法名")
	private String methodName;

	@Column(name = "params", type = "VARCHAR", length = "2,000", nullable = "true", comment = "参数")
	private String params;

	@Column(name = "status", type = "TINYINT", length = "3", nullable = "false", comment = "任务状态    0：成功    1：失败")
	private Byte status;

	@Column(name = "error", type = "VARCHAR", length = "2,000", nullable = "true", comment = "失败信息")
	private String error;

	@Column(name = "costs", type = "INTEGER", length = "10", nullable = "false", comment = "耗时(单位：毫秒)")
	private Integer costs;

	@Column(name = "createTime", type = "TIMESTAMP", length = "19", nullable = "true", comment = "创建时间")
	private Date createTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
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

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public Integer getCosts() {
		return costs;
	}

	public void setCosts(Integer costs) {
		this.costs = costs;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}