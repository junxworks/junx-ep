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

import java.io.Serializable;
import java.util.Date;

/**
 * {类的详细说明}.
 *
 * @ClassName:  ScheduleJobLogEntity
 * @author: Michael
 * @date:   2020-7-19 12:18:05
 * @since:  v1.0
 */
public class ScheduleJobLogEntity implements Serializable {
	
	/** 常量 serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** log id. */
	private Long logId;
	
	/** job id. */
	private Long jobId;
	
	/** bean name. */
	private String beanName;
	
	/** method name. */
	private String methodName;
	
	/** params. */
	private String params;
	
	/** status. */
	private Integer status;
	
	/** error. */
	private String error;
	
	/** times. */
	private Integer times;
	
	/** create time. */
	private Date createTime;

	public Long getLogId() {
		return logId;
	}

	public void setLogId(Long logId) {
		this.logId = logId;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public Integer getTimes() {
		return times;
	}

	public void setTimes(Integer times) {
		this.times = times;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}