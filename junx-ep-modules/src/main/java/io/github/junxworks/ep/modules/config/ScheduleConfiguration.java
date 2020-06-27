package io.github.junxworks.ep.modules.config;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import com.google.common.collect.Lists;

import io.github.junxworks.ep.core.svc.InitDBTableService;
import io.github.junxworks.ep.core.utils.SpringContextUtils;
import io.github.junxworks.ep.modules.modules.scheduler.controller.ScheduleJobController;
import io.github.junxworks.ep.modules.modules.scheduler.controller.ScheduleJobLogController;
import io.github.junxworks.ep.modules.modules.scheduler.service.impl.ScheduleJobLogServiceImpl;
import io.github.junxworks.ep.modules.modules.scheduler.service.impl.ScheduleJobServiceImpl;
import io.github.junxworks.ep.modules.modules.scheduler.task.TestTask;
import io.github.junxworks.junx.core.lang.Initializable;

/**
 * 定时任务配置
 *
 * @author wangxing
 * @email 29710904@163.com
 * @date 2017-04-20 23:38
 */
@Configuration
@Import({ SpringContextUtils.class, ScheduleJobController.class, ScheduleJobLogController.class, ScheduleJobLogServiceImpl.class, ScheduleJobServiceImpl.class, TestTask.class })
public class ScheduleConfiguration extends TableCreateConfiguration {

	public static final List<String> tables = Lists.newArrayList("schedule_job", "schedule_job_log", "QRTZ_JOB_DETAILS", "QRTZ_TRIGGERS", "QRTZ_SIMPLE_TRIGGERS", "QRTZ_CRON_TRIGGERS", "QRTZ_SIMPROP_TRIGGERS", "QRTZ_BLOB_TRIGGERS", "QRTZ_CALENDARS", "QRTZ_PAUSED_TRIGGER_GRPS", "QRTZ_FIRED_TRIGGERS", "QRTZ_SCHEDULER_STATE", "QRTZ_LOCKS");

	@Bean(name = "ScheduleDBInit", initMethod = "initialize", destroyMethod = "destroy")
	@Order(Ordered.HIGHEST_PRECEDENCE)
	public Initializable ScheduleDBInit(final JdbcTemplate jdbcTemplate) throws IOException {
		return new Initializable() {
			@Override
			public void initialize() throws Exception {
				for (String table : tables) {
					InitDBTableService service = createTableValidate(table, table);
					service.setJdbcTemplate(jdbcTemplate);
					service.start();
				}
			}

			@Override
			public void destroy() throws Exception {
			}
		};

	}

	@Bean
	@DependsOn("ScheduleDBInit")
	public SchedulerFactoryBean schedulerFactoryBean(DataSource dataSource,PlatformTransactionManager transactionManager) {
		SchedulerFactoryBean factory = new SchedulerFactoryBean();
		factory.setDataSource(dataSource);

		//quartz参数
		Properties prop = new Properties();
		prop.put("org.quartz.scheduler.instanceName", "QuartzScheduler");
		prop.put("org.quartz.scheduler.instanceId", "AUTO");
		//线程池配置
		prop.put("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
		prop.put("org.quartz.threadPool.threadCount", "10");
		prop.put("org.quartz.threadPool.threadPriority", "5");
		//JobStore配置
		prop.put("org.quartz.jobStore.class", "org.quartz.impl.jdbcjobstore.JobStoreTX");
		//集群配置
		prop.put("org.quartz.jobStore.isClustered", "true");//是否是应用在集群中，当应用在集群中时必须设置为TRUE，否则会出错org.quartz.jobStore.clusterCheckinInterval
		prop.put("org.quartz.jobStore.clusterCheckinInterval", "15000");  //#scheduler的checkin时间，时间长短影响failure scheduler的发现速度
		prop.put("org.quartz.jobStore.maxMisfiresToHandleAtATime", "1");

		prop.put("org.quartz.jobStore.misfireThreshold", "60000");//最大能忍受的触发超时时间，如果超过则认为“失误”，单位ms
		prop.put("org.quartz.jobStore.tablePrefix", "QRTZ_");
		factory.setQuartzProperties(prop);

		factory.setSchedulerName("QuartzScheduler");
		//延时启动
		//		factory.setStartupDelay(30);
		factory.setApplicationContextSchedulerContextKey("applicationContextKey");
		//可选，QuartzScheduler 启动时更新己存在的Job，这样就不用每次修改targetObject后删除qrtz_job_details表对应记录了
		factory.setOverwriteExistingJobs(true);
		//设置自动启动，默认为true
		factory.setAutoStartup(true);
		factory.setTransactionManager(transactionManager);
		return factory;
	}
}
