/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  EPSchedulerConfig.java   
 * @Package io.github.junxworks.ep.scheduler.config   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2022年11月15日 上午11:26:59   
 * @version V1.0 
 * @Copyright: 2022 Junxworks. All rights reserved. 
 * 注意：
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.scheduler.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * EP定时任务配置，目前开放部分属性外部配置，后续酌情添加
 *
 * @ClassName:  EPSchedulerConfig
 * @author: Michael
 * @date:   2020-7-19 12:17:48
 * @since:  v1.0
 */
@ConfigurationProperties(prefix = "junx.ep.scheduler")
public class EPSchedulerConfig {
	private boolean enabled = true;

	private boolean autoStartup = true;

	private String instanceName = "QuartzScheduler";

	private String instanceId = "AUTO";

	private int threadCount = 10;

	private int threadPriority = 5;

	private boolean isClustered = true;

	private long clusterCheckinInterval = 15000;

	private int maxMisfiresToHandleAtATime = 1;

	private long misfireThreshold = 60000;

	private String schedulerName = "QuartzScheduler";

	private long StartupDelay = 5;

	private boolean OverwriteExistingJobs = true;

	/**springboot2.5.7之前需要配置，2.5.7及之后版本无需配置，采用默认的org.springframework.scheduling.quartz.LocalDataSourceJobStore */
	private boolean useJobStoreTX;

	public boolean isUseJobStoreTX() {
		return useJobStoreTX;
	}

	public void setUseJobStoreTX(boolean useJobStoreTX) {
		this.useJobStoreTX = useJobStoreTX;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isAutoStartup() {
		return autoStartup;
	}

	public void setAutoStartup(boolean autoStartup) {
		this.autoStartup = autoStartup;
	}

	public int getThreadCount() {
		return threadCount;
	}

	public void setThreadCount(int threadCount) {
		this.threadCount = threadCount;
	}

	public int getThreadPriority() {
		return threadPriority;
	}

	public void setThreadPriority(int threadPriority) {
		this.threadPriority = threadPriority;
	}

	public boolean isClustered() {
		return isClustered;
	}

	public void setClustered(boolean isClustered) {
		this.isClustered = isClustered;
	}

	public long getClusterCheckinInterval() {
		return clusterCheckinInterval;
	}

	public void setClusterCheckinInterval(long clusterCheckinInterval) {
		this.clusterCheckinInterval = clusterCheckinInterval;
	}

	public int getMaxMisfiresToHandleAtATime() {
		return maxMisfiresToHandleAtATime;
	}

	public void setMaxMisfiresToHandleAtATime(int maxMisfiresToHandleAtATime) {
		this.maxMisfiresToHandleAtATime = maxMisfiresToHandleAtATime;
	}

	public long getMisfireThreshold() {
		return misfireThreshold;
	}

	public void setMisfireThreshold(long misfireThreshold) {
		this.misfireThreshold = misfireThreshold;
	}

	public String getSchedulerName() {
		return schedulerName;
	}

	public void setSchedulerName(String schedulerName) {
		this.schedulerName = schedulerName;
	}

	public long getStartupDelay() {
		return StartupDelay;
	}

	public void setStartupDelay(long startupDelay) {
		StartupDelay = startupDelay;
	}

	public boolean isOverwriteExistingJobs() {
		return OverwriteExistingJobs;
	}

	public void setOverwriteExistingJobs(boolean overwriteExistingJobs) {
		OverwriteExistingJobs = overwriteExistingJobs;
	}

	public String getInstanceName() {
		return instanceName;
	}

	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

}
