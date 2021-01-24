/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  ScheduledJob.java   
 * @Package io.github.junxworks.ep.scheduler   
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
package io.github.junxworks.ep.scheduler;

import java.util.Date;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.alibaba.fastjson.JSON;

import io.github.junxworks.ep.core.utils.SpringContextUtils;
import io.github.junxworks.ep.scheduler.entity.SJob;
import io.github.junxworks.ep.scheduler.entity.SJobLog;
import io.github.junxworks.ep.scheduler.service.ScheduleJobLogService;
import io.github.junxworks.ep.sys.constants.SuccessFail;
import io.github.junxworks.junx.core.util.ExceptionUtils;
import io.github.junxworks.junx.core.util.StringUtils;

/**
 * {类的详细说明}.
 *
 * @ClassName:  ScheduleJob
 * @author: Michael
 * @date:   2020-7-19 12:18:05
 * @since:  v1.0
 */
@DisallowConcurrentExecution
public class ScheduledJob extends QuartzJobBean {

	/** logger. */
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * Execute internal.
	 *
	 * @param context the context
	 * @throws JobExecutionException the job execution exception
	 */
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		String jsonJob = context.getMergedJobDataMap().getString(ScheduleUtils.JOB_PARAM_KEY);
		SJob scheduleJob = JSON.parseObject(jsonJob, SJob.class);
		//获取scheduleJobLogService
		ScheduleJobLogService scheduleJobLogService = (ScheduleJobLogService) SpringContextUtils.getBean("scheduleJobLogService");
		//数据库保存执行记录
		SJobLog log = new SJobLog();
		log.setJobId(scheduleJob.getId());
		log.setBeanName(scheduleJob.getBeanName());
		log.setMethodName(scheduleJob.getMethodName());
		log.setParams(scheduleJob.getParams());
		log.setCreateTime(new Date());
		//任务开始时间
		long startTime = System.currentTimeMillis();
		try {
			//执行任务
			logger.info("任务准备执行，任务ID：" + scheduleJob.getId() + " name:" + scheduleJob.getJobName());
			ScheduleRunnable task = new ScheduleRunnable(scheduleJob.getBeanName(), scheduleJob.getMethodName(), scheduleJob.getParams());
			task.run();
			//任务执行总时长
			long costs = System.currentTimeMillis() - startTime;
			log.setCosts((int) costs);
			log.setStatus(SuccessFail.SUCCESS.getValue());
			logger.info("任务执行完毕，任务ID：" + scheduleJob.getId() + " name:" + scheduleJob.getJobName() + "  总共耗时：" + costs + "毫秒");
		} catch (Exception e) {
			logger.error("任务执行失败，任务ID：" + scheduleJob.getId() + " name:" + scheduleJob.getJobName(), e);
			//任务执行总时长
			long costs = System.currentTimeMillis() - startTime;
			log.setCosts((int) costs);
			log.setStatus(SuccessFail.FAIL.getValue());
			log.setError(StringUtils.substring(ExceptionUtils.getCause(e).toString(), 0, 2000));
		} finally {
			scheduleJobLogService.save(log);
		}
	}
}
