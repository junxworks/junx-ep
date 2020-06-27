package io.github.junxworks.ep.modules.modules.scheduler.utils;

import java.util.Date;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.google.gson.Gson;

import io.github.junxworks.ep.core.utils.SpringContextUtils;
import io.github.junxworks.ep.modules.modules.scheduler.entity.ScheduleJobEntity;
import io.github.junxworks.ep.modules.modules.scheduler.entity.ScheduleJobLogEntity;
import io.github.junxworks.ep.modules.modules.scheduler.entity.ScheduleObj;
import io.github.junxworks.ep.modules.modules.scheduler.service.ScheduleJobLogService;
import io.github.junxworks.junx.core.util.ExceptionUtils;
import io.github.junxworks.junx.core.util.StringUtils;

/**
 * 定时任务
 * 
 * @author wangxing
 * @email 219710904@163.com
 * @date 2016年11月30日 下午12:44:21
 */
@DisallowConcurrentExecution
public class ScheduleJob extends QuartzJobBean {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		String jsonJob = context.getMergedJobDataMap().getString(ScheduleJobEntity.JOB_PARAM_KEY);
		ScheduleObj scheduleJob = new Gson().fromJson(jsonJob, ScheduleObj.class);

		//获取scheduleJobLogService
		ScheduleJobLogService scheduleJobLogService = (ScheduleJobLogService) SpringContextUtils.getBean("scheduleJobLogService");

		//数据库保存执行记录
		ScheduleJobLogEntity log = new ScheduleJobLogEntity();
		log.setJobId(scheduleJob.getJobId());
		log.setBeanName(scheduleJob.getBeanName());
		log.setMethodName(scheduleJob.getMethodName());
		log.setParams(scheduleJob.getParams());
		log.setCreateTime(new Date());

		//任务开始时间
		long startTime = System.currentTimeMillis();

		try {
			//执行任务
			logger.info("任务准备执行，任务ID：" + scheduleJob.getJobId() + " remark:" + scheduleJob.getRemark());
			ScheduleRunnable task = new ScheduleRunnable(scheduleJob.getBeanName(), scheduleJob.getMethodName(), scheduleJob.getParams());
			task.run();

			//任务执行总时长
			long times = System.currentTimeMillis() - startTime;
			log.setTimes((int) times);
			//任务状态    0：成功    1：失败
			log.setStatus(0);

			logger.info("任务执行完毕，任务ID：" + scheduleJob.getJobId() + " remark:" + scheduleJob.getRemark() + "  总共耗时：" + times + "毫秒");
		} catch (Exception e) {
			logger.error("任务执行失败，任务ID：" + scheduleJob.getJobId() + " remark:" + scheduleJob.getRemark(), e);

			//任务执行总时长
			long times = System.currentTimeMillis() - startTime;
			log.setTimes((int) times);

			//任务状态    0：成功    1：失败
			log.setStatus(1);
			log.setError(StringUtils.substring(ExceptionUtils.getCause(e).toString(), 0, 2000));
		} finally {
			scheduleJobLogService.save(log);
		}
	}
}
