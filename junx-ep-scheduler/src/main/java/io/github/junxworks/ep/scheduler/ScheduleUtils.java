/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  ScheduleUtils.java   
 * @Package io.github.junxworks.ep.scheduler   
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
package io.github.junxworks.ep.scheduler;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;

import com.alibaba.fastjson.JSON;

import io.github.junxworks.ep.scheduler.entity.ScheduleJobEntity;
import io.github.junxworks.junx.core.exception.BaseRuntimeException;

/**
 * {类的详细说明}.
 *
 * @ClassName:  ScheduleUtils
 * @author: Michael
 * @date:   2020-7-19 12:18:05
 * @since:  v1.0
 */
public class ScheduleUtils {
    
    /** 常量 JOB_NAME. */
    private final static String JOB_NAME = "TASK_";
    
    /**
     * 返回 trigger key 属性.
     *
     * @param jobId the job id
     * @return trigger key 属性
     */
    private static TriggerKey getTriggerKey(Long jobId) {
        return TriggerKey.triggerKey(JOB_NAME + jobId);
    }
    
    /**
     * 返回 job key 属性.
     *
     * @param jobId the job id
     * @return job key 属性
     */
    private static JobKey getJobKey(Long jobId) {
        return JobKey.jobKey(JOB_NAME + jobId);
    }

    /**
     * 返回 cron trigger 属性.
     *
     * @param scheduler the scheduler
     * @param jobId the job id
     * @return cron trigger 属性
     */
    public static CronTrigger getCronTrigger(Scheduler scheduler, Long jobId) {
        try {
            return (CronTrigger) scheduler.getTrigger(getTriggerKey(jobId));
        } catch (SchedulerException e) {
            throw new BaseRuntimeException("getCronTrigger异常，请检查qrtz开头的表，是否有脏数据", e);
        }
    }

    /**
     * Creates the schedule job.
     *
     * @param scheduler the scheduler
     * @param scheduleJob the schedule job
     */
    public static void createScheduleJob(Scheduler scheduler, ScheduleJobEntity scheduleJob) {
        try {
        	//构建job
            JobDetail jobDetail = JobBuilder.newJob(ScheduleJob.class).withIdentity(getJobKey(scheduleJob.getJobId())).build();

            //构建cron
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression())
            		.withMisfireHandlingInstructionDoNothing();

            //根据cron，构建一个CronTrigger
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(getTriggerKey(scheduleJob.getJobId())).
                    withSchedule(scheduleBuilder).build();

            //放入参数，运行时的方法可以获取
            jobDetail.getJobDataMap().put(ScheduleJobEntity.JOB_PARAM_KEY, JSON.toJSONString(scheduleJob));

            scheduler.scheduleJob(jobDetail, trigger);
            
            //暂停任务
            if(scheduleJob.getStatus() == ScheduleStatus.PAUSE.getValue()){
            	pauseJob(scheduler, scheduleJob.getJobId());
            }
        } catch (SchedulerException e) {
            throw new BaseRuntimeException("创建定时任务失败", e);
        }
    }
    
    /**
     * Update schedule job.
     *
     * @param scheduler the scheduler
     * @param scheduleJob the schedule job
     */
    public static void updateScheduleJob(Scheduler scheduler, ScheduleJobEntity scheduleJob) {
        try {
            TriggerKey triggerKey = getTriggerKey(scheduleJob.getJobId());

            //表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression())
            		.withMisfireHandlingInstructionDoNothing();

            CronTrigger trigger = getCronTrigger(scheduler, scheduleJob.getJobId());
            
            //按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
            
            //参数
            trigger.getJobDataMap().put(ScheduleJobEntity.JOB_PARAM_KEY, JSON.toJSONString(scheduleJob));
            
            scheduler.rescheduleJob(triggerKey, trigger);
            
            //暂停任务
            if(scheduleJob.getStatus() == ScheduleStatus.PAUSE.getValue()){
            	pauseJob(scheduler, scheduleJob.getJobId());
            }
            
        } catch (SchedulerException e) {
            throw new BaseRuntimeException("更新定时任务失败", e);
        }
    }

    /**
     * Run.
     *
     * @param scheduler the scheduler
     * @param scheduleJob the schedule job
     */
    public static void run(Scheduler scheduler, ScheduleJobEntity scheduleJob) {
        try {
        	//参数
        	JobDataMap dataMap = new JobDataMap();
        	dataMap.put(ScheduleJobEntity.JOB_PARAM_KEY, JSON.toJSONString(scheduleJob));
        	
            scheduler.triggerJob(getJobKey(scheduleJob.getJobId()), dataMap);
        } catch (SchedulerException e) {
            throw new BaseRuntimeException("立即执行定时任务失败", e);
        }
    }

    /**
     * Pause job.
     *
     * @param scheduler the scheduler
     * @param jobId the job id
     */
    public static void pauseJob(Scheduler scheduler, Long jobId) {
        try {
            scheduler.pauseJob(getJobKey(jobId));
        } catch (SchedulerException e) {
            throw new BaseRuntimeException("暂停定时任务失败", e);
        }
    }

    /**
     * Resume job.
     *
     * @param scheduler the scheduler
     * @param jobId the job id
     */
    public static void resumeJob(Scheduler scheduler, Long jobId) {
        try {
            scheduler.resumeJob(getJobKey(jobId));
        } catch (SchedulerException e) {
            throw new BaseRuntimeException("暂停定时任务失败", e);
        }
    }

    /**
     * Delete schedule job.
     *
     * @param scheduler the scheduler
     * @param jobId the job id
     */
    public static void deleteScheduleJob(Scheduler scheduler, Long jobId) {
        try {
            scheduler.deleteJob(getJobKey(jobId));
        } catch (SchedulerException e) {
            throw new BaseRuntimeException("删除定时任务失败", e);
        }
    }
}
