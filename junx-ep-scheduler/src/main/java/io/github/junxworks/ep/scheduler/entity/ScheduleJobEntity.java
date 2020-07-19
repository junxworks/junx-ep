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

import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
import java.util.Date;

/**
 * {类的详细说明}.
 *
 * @ClassName:  ScheduleJobEntity
 * @author: Michael
 * @date:   2020-7-19 12:18:05
 * @since:  v1.0
 */
public class ScheduleJobEntity implements Serializable {
	
	/** 常量 serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** 常量 JOB_PARAM_KEY. */
    public static final String JOB_PARAM_KEY = "JOB_PARAM_KEY";
	
	/** job id. */
	private Long jobId;

	/** bean name. */
	@NotBlank(message="bean名称不能为空")
	private String beanName;
	
	/** method name. */
	@NotBlank(message="方法名称不能为空")
	private String methodName;
	
	/** params. */
	private String params;
	
	/** cron expression. */
	@NotBlank(message="cron表达式不能为空")
	private String cronExpression;

	/** status. */
	private Integer status;

	/** remark. */
	private String remark;

	/** create time. */
	private Date createTime;

	/**
	 * 设置：任务id
	 * @param jobId 任务id
	 */
	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	/**
	 * 获取：任务id
	 * @return Long
	 */
	public Long getJobId() {
		return jobId;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 设置：任务状态
	 * @param status 任务状态
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * 获取：任务状态
	 * @return String
	 */
	public Integer getStatus() {
		return status;
	}
	
	/**
	 * 设置：cron表达式
	 * @param cronExpression cron表达式
	 */
	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	/**
	 * 获取：cron表达式
	 * @return String
	 */
	public String getCronExpression() {
		return cronExpression;
	}
	
	/**
	 * 设置：创建时间
	 * @param createTime 创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 获取：创建时间
	 * @return Date
	 */
	public Date getCreateTime() {
		return createTime;
	}
}
