/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  ScheduleJobLogController.java   
 * @Package io.github.junxworks.ep.scheduler.controller   
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
package io.github.junxworks.ep.scheduler.controller;

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
import io.github.junxworks.ep.scheduler.entity.ScheduleJobLogEntity;
import io.github.junxworks.ep.scheduler.service.ScheduleJobLogService;
import io.github.junxworks.ep.scheduler.vo.ScheduleJobLogVo;

/**
 * {类的详细说明}.
 *
 * @ClassName:  ScheduleJobLogController
 * @author: Michael
 * @date:   2020-7-19 12:18:05
 * @since:  v1.0
 */
@RestController
@RequestMapping("/ep/sys/schedule")
public class ScheduleJobLogController {
	
	/** schedule job log service. */
	@Autowired
	private ScheduleJobLogService scheduleJobLogService;

	/**
	 * List.
	 *
	 * @param params the params
	 * @return the result
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
	 * Info.
	 *
	 * @param logId the log id
	 * @return the result
	 */
	@GetMapping("/logs/{logId}")
	public Result info(@PathVariable("logId") Long logId) {
		ScheduleJobLogEntity log = scheduleJobLogService.queryObject(logId);
		return Result.ok(log);
	}
}
