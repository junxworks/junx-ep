/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  ScheduleJobController.java   
 * @Package io.github.junxworks.ep.scheduler.controller   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2021-1-24 17:50:26   
 * @version V1.0 
 * @Copyright: 2021 Junxworks. All rights reserved. 
 * 注意：
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.scheduler.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;

import io.github.junxworks.ep.core.Result;
import io.github.junxworks.ep.core.utils.PageUtils;
import io.github.junxworks.ep.scheduler.dto.SJobListConditionDto;
import io.github.junxworks.ep.scheduler.entity.SJob;
import io.github.junxworks.ep.scheduler.service.ScheduleJobService;
import io.github.junxworks.ep.sys.annotations.OpLog;

/**
 * {类的详细说明}.
 *
 * @ClassName:  ScheduleJobController
 * @author: Michael
 * @date:   2020-7-19 12:18:05
 * @since:  v1.0
 */
@RestController
@RequestMapping("/ep/sys/schedule")
public class ScheduleJobController {

	/** schedule job service. */
	@Autowired
	private ScheduleJobService scheduleJobService;

	/**
	 * List.
	 *
	 * @param params the params
	 * @return the result
	 */
	@GetMapping("/jobs")
	public Result list(SJobListConditionDto condition) {
		//查询列表数据
		PageUtils.setPage(condition);
		return Result.ok(new PageInfo<>(scheduleJobService.queryList(condition)));
	}

	/**
	 * Info.
	 *
	 * @param jobId the job id
	 * @return the result
	 */
	@GetMapping("/jobs/{jobId}")
	public Result info(@PathVariable("jobId") Long jobId) {
		return Result.ok(scheduleJobService.queryObject(jobId));
	}

	/**
	 * Save.
	 *
	 * @param scheduleJob the schedule job
	 * @return the result
	 */
	@OpLog("保存定时任务")
	@PostMapping("/jobs")
	public Result save(@RequestBody SJob scheduleJob) {
		scheduleJobService.save(scheduleJob);
		return Result.ok();
	}

	/**
	 * Delete.
	 *
	 * @param jobId the job id
	 * @return the result
	 */
	@OpLog("删除定时任务")
	@DeleteMapping("/jobs/{jobId}")
	public Result delete(@PathVariable("jobId") Long jobId) {
		scheduleJobService.deleteBatch(new Long[] { jobId });
		return Result.ok();
	}

	/**
	 * Run.
	 *
	 * @param jobId the job id
	 * @return the result
	 */
	@OpLog("立即执行任务")
	@PutMapping("/jobs/{jobId}/run")
	public Result run(@PathVariable("jobId") Long jobId) {
		scheduleJobService.run(new Long[] { jobId });
		return Result.ok();
	}

	/**
	 * Pause.
	 *
	 * @param jobId the job id
	 * @return the result
	 */
	@OpLog("暂停定时任务")
	@PutMapping("/jobs/{jobId}/pause")
	public Result pause(@PathVariable("jobId") Long jobId) {
		scheduleJobService.pause(new Long[] { jobId });
		return Result.ok();
	}

	/**
	 * Resume.
	 *
	 * @param jobId the job id
	 * @return the result
	 */
	@OpLog("恢复定时任务")
	@PutMapping("/jobs/{jobId}/resume")
	public Result resume(@PathVariable("jobId") Long jobId) {
		scheduleJobService.resume(new Long[] { jobId });
		return Result.ok();
	}

}
