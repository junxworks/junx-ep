package io.github.junxworks.ep.modules.modules.scheduler.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;

import io.github.junxworks.ep.core.Result;
import io.github.junxworks.ep.core.utils.PageUtils;
import io.github.junxworks.ep.modules.modules.scheduler.entity.ScheduleJobLogEntity;
import io.github.junxworks.ep.modules.modules.scheduler.service.ScheduleJobLogService;
import io.github.junxworks.ep.modules.modules.scheduler.vo.ScheduleJobLogVo;

/**
 * 定时任务日志
 * 
 * @author wangxing
 * @email 219710904@163.com
 * @date 2016年12月1日 下午10:39:52
 */
@RestController
@RequestMapping("/ep/sys/schedule")
public class ScheduleJobLogController {
	@Autowired
	private ScheduleJobLogService scheduleJobLogService;

	/**
	 * 定时任务日志列表
	 */
	@GetMapping("/logs")
//	@RequiresPermissions("sys:schedule:log")
	public Result list(@RequestParam Map<String, Object> params) {
		//查询列表数据
		PageUtils.setPage(params);
		List<ScheduleJobLogVo> jobList = scheduleJobLogService.queryList(params);
		PageInfo<ScheduleJobLogVo> page = new PageInfo<>(jobList);
		return Result.ok(page);
	}

	/**
	 * 定时任务日志信息
	 */
	@GetMapping("/logs/{logId}")
	public Result info(@PathVariable("logId") Long logId) {
		ScheduleJobLogEntity log = scheduleJobLogService.queryObject(logId);
		return Result.ok(log);
	}
}
