/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  ScheduleJobService.java   
 * @Package io.github.junxworks.ep.scheduler.service   
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
package io.github.junxworks.ep.scheduler.service;

import java.util.List;
import java.util.Map;

import io.github.junxworks.ep.scheduler.dto.SJobListConditionDto;
import io.github.junxworks.ep.scheduler.entity.SJob;
import io.github.junxworks.ep.scheduler.entity.SJob;
import io.github.junxworks.ep.scheduler.vo.ScheduleJobVo;

/**
 * {类的详细说明}.
 *
 * @ClassName:  ScheduleJobService
 * @author: Michael
 * @date:   2020-7-19 12:18:05
 * @since:  v1.0
 */
public interface ScheduleJobService {

	/**
	 * Query object.
	 *
	 * @param jobId the job id
	 * @return the schedule job entity
	 */
	SJob queryObject(Long jobId);

	/**
	 * Query list.
	 *
	 * @param map the map
	 * @return the list
	 */
	List<ScheduleJobVo> queryList(SJobListConditionDto condition);

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
	 * @param scheduleJob the schedule job
	 */
	void save(SJob scheduleJob);

	/**
	 * Delete batch.
	 *
	 * @param jobIds the job ids
	 */
	void deleteBatch(Long[] jobIds);

	/**
	 * Update batch.
	 *
	 * @param jobIds the job ids
	 * @param status the status
	 * @return the int
	 */
	int updateBatch(Long[] jobIds, int status);

	/**
	 * Run.
	 *
	 * @param jobIds the job ids
	 */
	void run(Long[] jobIds);

	/**
	 * Pause.
	 *
	 * @param jobIds the job ids
	 */
	void pause(Long[] jobIds);

	/**
	 * Resume.
	 *
	 * @param jobIds the job ids
	 */
	void resume(Long[] jobIds);
}
