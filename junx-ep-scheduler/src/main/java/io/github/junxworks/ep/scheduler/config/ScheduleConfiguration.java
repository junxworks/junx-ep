/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  ScheduleConfiguration.java   
 * @Package io.github.junxworks.ep.scheduler.config   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2021-1-24 17:50:25   
 * @version V1.0 
 * @Copyright: 2021 Junxworks. All rights reserved. 
 * 注意：
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.scheduler.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import io.github.junxworks.ep.core.pvc.PersistenceVersionController;
import io.github.junxworks.ep.core.utils.SpringContextUtils;

/**
 * 定时任务配置类
 *
 * @ClassName:  ScheduleConfiguration
 * @author: Michael
 * @date:   2020-7-19 12:18:05
 * @since:  v1.0
 */
@Configuration
@EnableConfigurationProperties({ EPSchedulerConfig.class })
@ConditionalOnClass(name = "io.github.junxworks.ep.sys.config.BaseModuleConfiguration")
@ConditionalOnProperty(prefix = "junx.ep.scheduler", name = "enabled", havingValue = "true", matchIfMissing = true)
@ComponentScan(value = "io.github.junxworks.ep.scheduler")
@Import({ SpringContextUtils.class })
public class ScheduleConfiguration {

	/** 常量 PVC_PATH.持久化版本控制路径 */
	private static final String PVC_PATH = "/io/github/junxworks/ep/scheduler/pvc";

	/** 常量 MODULE_NAME.pvc参数，模块名 */
	private static final String MODULE_NAME = "junx_ep_scheduler";

	@Autowired
	private EPSchedulerConfig config;

	/**
	 * Scheduler factory bean.
	 *
	 * @param dataSource the data source
	 * @param transactionManager the transaction manager
	 * @return the scheduler factory bean
	 */
	@Bean
	public SchedulerFactoryBean schedulerFactoryBean(DataSource dataSource, PlatformTransactionManager transactionManager) {
		SchedulerFactoryBean factory = new SchedulerFactoryBean();
		factory.setDataSource(dataSource);

		//quartz参数
		Properties prop = new Properties();
		prop.put("org.quartz.scheduler.instanceName", config.getInstanceName());
		prop.put("org.quartz.scheduler.instanceId", config.getInstanceId());
		//线程池配置
		prop.put("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
		prop.put("org.quartz.threadPool.threadCount", String.valueOf(config.getThreadCount()));
		prop.put("org.quartz.threadPool.threadPriority", String.valueOf(config.getThreadPriority()));
		//JobStore配置
		if (config.isUseJobStoreTX()) {
			// springboot2.5.7之前需要配置，2.5.7及之后版本之后走org.springframework.scheduling.quartz.LocalDataSourceJobStore
			prop.put("org.quartz.jobStore.class", "org.quartz.impl.jdbcjobstore.JobStoreTX");
		}
		//集群配置
		prop.put("org.quartz.jobStore.isClustered", String.valueOf(config.isClustered()));//是否是应用在集群中，当应用在集群中时必须设置为TRUE，否则会出错org.quartz.jobStore.clusterCheckinInterval
		prop.put("org.quartz.jobStore.clusterCheckinInterval", String.valueOf(config.getClusterCheckinInterval())); //#scheduler的checkin时间，时间长短影响failure scheduler的发现速度
		prop.put("org.quartz.jobStore.maxMisfiresToHandleAtATime", String.valueOf(config.getMaxMisfiresToHandleAtATime()));

		prop.put("org.quartz.jobStore.misfireThreshold", String.valueOf(config.getMisfireThreshold()));//最大能忍受的触发超时时间，如果超过则认为“失误”，单位ms
		prop.put("org.quartz.jobStore.tablePrefix", "QRTZ_");
		factory.setQuartzProperties(prop);

		factory.setSchedulerName(config.getSchedulerName());
		//延时启动
		//		factory.setStartupDelay(30);
		factory.setApplicationContextSchedulerContextKey("applicationContextKey");
		//可选，QuartzScheduler 启动时更新己存在的Job，这样就不用每次修改targetObject后删除qrtz_job_details表对应记录了
		factory.setOverwriteExistingJobs(config.isOverwriteExistingJobs());
		//设置自动启动，默认为true
		factory.setAutoStartup(config.isAutoStartup());
		factory.setTransactionManager(transactionManager);
		return factory;
	}

	@DependsOn("JunxEpSpringContextUtils")
	@Bean(name = "junxEpSchedulerPvc", initMethod = "start", destroyMethod = "stop")
	public PersistenceVersionController junxEpSchedulerPvc() {
		PersistenceVersionController pvc = new PersistenceVersionController();
		pvc.setPvcPath(PVC_PATH);
		pvc.setModuleName(MODULE_NAME);
		return pvc;
	}
}
