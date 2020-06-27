package io.github.junxworks.ep.modules.modules.scheduler.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.junxworks.ep.modules.modules.scheduler.entity.ScheduleJobLogEntity;
import io.github.junxworks.ep.modules.modules.scheduler.mapper.ScheduleJobLogMapper;
import io.github.junxworks.ep.modules.modules.scheduler.service.ScheduleJobLogService;
import io.github.junxworks.ep.modules.modules.scheduler.vo.ScheduleJobLogVo;

@Service("scheduleJobLogService")
public class ScheduleJobLogServiceImpl implements ScheduleJobLogService {
	@Autowired
	private ScheduleJobLogMapper scheduleJobLogDao;
	
	@Override
	public ScheduleJobLogEntity queryObject(Long jobId) {
		return scheduleJobLogDao.queryObject(jobId);
	}

	@Override
	public List<ScheduleJobLogVo> queryList(Map<String, Object> map) {
		return scheduleJobLogDao.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		return scheduleJobLogDao.queryTotal(map);
	}

	@Override
	public void save(ScheduleJobLogEntity log) {
		scheduleJobLogDao.save(log);
	}

}
