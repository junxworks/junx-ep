package io.github.junxworks.ep.modules.modules.scheduler.service;

import java.util.List;
import java.util.Map;

import io.github.junxworks.ep.modules.modules.scheduler.entity.ScheduleJobLogEntity;
import io.github.junxworks.ep.modules.modules.scheduler.vo.ScheduleJobLogVo;

/**
 * 定时任务日志
 * 
 * @author wangxing
 * @email 219710904@163.com
 * @date 2016年12月1日 下午10:34:48
 */
public interface ScheduleJobLogService {

	/**
	 * 根据ID，查询定时任务日志
	 */
	ScheduleJobLogEntity queryObject(Long jobId);
	
	/**
	 * 查询定时任务日志列表
	 */
	List<ScheduleJobLogVo> queryList(Map<String, Object> map);
	
	/**
	 * 查询总数
	 */
	int queryTotal(Map<String, Object> map);
	
	/**
	 * 保存定时任务日志
	 */
	void save(ScheduleJobLogEntity log);
	
}
