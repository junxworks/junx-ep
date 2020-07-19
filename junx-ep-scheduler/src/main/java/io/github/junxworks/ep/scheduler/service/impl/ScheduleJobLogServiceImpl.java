/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  ScheduleJobLogServiceImpl.java   
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

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.junxworks.ep.scheduler.entity.ScheduleJobLogEntity;
import io.github.junxworks.ep.scheduler.mapper.ScheduleJobLogMapper;
import io.github.junxworks.ep.scheduler.service.ScheduleJobLogService;
import io.github.junxworks.ep.scheduler.vo.ScheduleJobLogVo;

/**
 * {类的详细说明}.
 *
 * @ClassName:  ScheduleJobLogServiceImpl
 * @author: Michael
 * @date:   2020-7-19 12:18:05
 * @since:  v1.0
 */
@Service("scheduleJobLogService")
public class ScheduleJobLogServiceImpl implements ScheduleJobLogService {
	
	/** schedule job log dao. */
	@Autowired
	private ScheduleJobLogMapper scheduleJobLogDao;
	
	/**
	 * Query object.
	 *
	 * @param jobId the job id
	 * @return the schedule job log entity
	 */
	@Override
	public ScheduleJobLogEntity queryObject(Long jobId) {
		return scheduleJobLogDao.queryObject(jobId);
	}

	/**
	 * Query list.
	 *
	 * @param map the map
	 * @return the list
	 */
	@Override
	public List<ScheduleJobLogVo> queryList(Map<String, Object> map) {
		return scheduleJobLogDao.queryList(map);
	}

	/**
	 * Query total.
	 *
	 * @param map the map
	 * @return the int
	 */
	@Override
	public int queryTotal(Map<String, Object> map) {
		return scheduleJobLogDao.queryTotal(map);
	}

	/**
	 * Save.
	 *
	 * @param log the log
	 */
	@Override
	public void save(ScheduleJobLogEntity log) {
		scheduleJobLogDao.save(log);
	}

}
