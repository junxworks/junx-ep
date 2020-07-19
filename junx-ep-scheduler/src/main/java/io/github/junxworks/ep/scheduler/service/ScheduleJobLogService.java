/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  ScheduleJobLogService.java   
 * @Package io.github.junxworks.ep.scheduler.service   
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
package io.github.junxworks.ep.scheduler.service;

import java.util.List;
import java.util.Map;

import io.github.junxworks.ep.scheduler.entity.ScheduleJobLogEntity;
import io.github.junxworks.ep.scheduler.vo.ScheduleJobLogVo;

/**
 * {类的详细说明}.
 *
 * @ClassName:  ScheduleJobLogService
 * @author: Michael
 * @date:   2020-7-19 12:18:05
 * @since:  v1.0
 */
public interface ScheduleJobLogService {

	/**
	 * Query object.
	 *
	 * @param jobId the job id
	 * @return the schedule job log entity
	 */
	ScheduleJobLogEntity queryObject(Long jobId);
	
	/**
	 * Query list.
	 *
	 * @param map the map
	 * @return the list
	 */
	List<ScheduleJobLogVo> queryList(Map<String, Object> map);
	
	/**
	 * Query total.
	 *
	 * @param map the map
	 * @return the int
	 */
	int queryTotal(Map<String, Object> map);
	
	/**
	 * Save.
	 *
	 * @param log the log
	 */
	void save(ScheduleJobLogEntity log);
	
}
