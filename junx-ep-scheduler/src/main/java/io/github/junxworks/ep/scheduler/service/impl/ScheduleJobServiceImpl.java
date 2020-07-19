/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  ScheduleJobServiceImpl.java   
 * @Package io.github.junxworks.ep.scheduler.service.impl   
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
package io.github.junxworks.ep.scheduler.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.junxworks.ep.scheduler.ScheduleStatus;
import io.github.junxworks.ep.scheduler.ScheduleUtils;
import io.github.junxworks.ep.scheduler.entity.ScheduleJobEntity;
import io.github.junxworks.ep.scheduler.mapper.ScheduleJobMapper;
import io.github.junxworks.ep.scheduler.service.ScheduleJobService;

/**
 * {类的详细说明}.
 *
 * @ClassName:  ScheduleJobServiceImpl
 * @author: Michael
 * @date:   2020-7-19 12:18:05
 * @since:  v1.0
 */
@Service("scheduleJobService")
public class ScheduleJobServiceImpl implements ScheduleJobService {
	
	/** scheduler. */
	@Autowired
    private Scheduler scheduler;
	
	/** scheduler job dao. */
	@Autowired
	private ScheduleJobMapper schedulerJobDao;
	
	/**
	 * Inits the.
	 */
	@PostConstruct
	public void init(){
		/*List<ScheduleJobEntity> scheduleJobList = schedulerJobDao.queryList(new HashMap<>());
		for(ScheduleJobEntity scheduleJob : scheduleJobList){
			CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, scheduleJob.getJobId());
            //如果不存在，则创建
            if(cronTrigger == null) {
                ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
            }else {
                ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
            }
		}*/
	}
	
	/**
	 * Query object.
	 *
	 * @param jobId the job id
	 * @return the schedule job entity
	 */
	@Override
	public ScheduleJobEntity queryObject(Long jobId) {
		return schedulerJobDao.queryObject(jobId);
	}

	/**
	 * Query list.
	 *
	 * @param map the map
	 * @return the list
	 */
	@Override
	public List<ScheduleJobEntity> queryList(Map<String, Object> map) {
		return schedulerJobDao.queryList(map);
	}

	/**
	 * Query total.
	 *
	 * @param map the map
	 * @return the int
	 */
	@Override
	public int queryTotal(Map<String, Object> map) {
		return schedulerJobDao.queryTotal(map);
	}

	/**
	 * Save.
	 *
	 * @param scheduleJob the schedule job
	 */
	@Override
	@Transactional
	public void save(ScheduleJobEntity scheduleJob) {
		scheduleJob.setCreateTime(new Date());
		scheduleJob.setStatus(ScheduleStatus.NORMAL.getValue());
        schedulerJobDao.save(scheduleJob);
        ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
    }
	
	/**
	 * Update.
	 *
	 * @param scheduleJob the schedule job
	 */
	@Override
	@Transactional
	public void update(ScheduleJobEntity scheduleJob) {
        schedulerJobDao.update(scheduleJob);
        ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
    }

	/**
	 * Delete batch.
	 *
	 * @param jobIds the job ids
	 */
	@Override
	@Transactional
    public void deleteBatch(Long[] jobIds) {
    	for(Long jobId : jobIds){
    		ScheduleUtils.deleteScheduleJob(scheduler, jobId);
    	}
    	
    	//删除数据
    	schedulerJobDao.deleteBatch(jobIds);
	}

	/**
	 * Update batch.
	 *
	 * @param jobIds the job ids
	 * @param status the status
	 * @return the int
	 */
	@Override
    public int updateBatch(Long[] jobIds, int status){
    	Map<String, Object> map = new HashMap<>();
    	map.put("list", jobIds);
    	map.put("status", status);
    	return schedulerJobDao.updateBatch(map);
    }
    
	/**
	 * Run.
	 *
	 * @param jobIds the job ids
	 */
	@Override
	@Transactional
    public void run(Long[] jobIds) {
    	for(Long jobId : jobIds){
    		ScheduleUtils.run(scheduler, queryObject(jobId));
    	}
    }

	/**
	 * Pause.
	 *
	 * @param jobIds the job ids
	 */
	@Override
	@Transactional
    public void pause(Long[] jobIds) {
        for(Long jobId : jobIds){
    		ScheduleUtils.pauseJob(scheduler, jobId);
    	}
        
    	updateBatch(jobIds, ScheduleStatus.PAUSE.getValue());
    }

	/**
	 * Resume.
	 *
	 * @param jobIds the job ids
	 */
	@Override
	@Transactional
    public void resume(Long[] jobIds) {
    	for(Long jobId : jobIds){
    		ScheduleUtils.resumeJob(scheduler, jobId);
    	}

    	updateBatch(jobIds, ScheduleStatus.NORMAL.getValue());
    }
    
}
