/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  ScheduleJobController.java   
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;

import io.github.junxworks.ep.core.Result;
import io.github.junxworks.ep.core.utils.PageUtils;
import io.github.junxworks.ep.scheduler.entity.ScheduleJobEntity;
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
	public Result list(@RequestParam Map<String, Object> params){
		//查询列表数据
		PageUtils.setPage(params);
		List<ScheduleJobEntity> jobList = scheduleJobService.queryList(params);
		PageInfo<ScheduleJobEntity> page = new PageInfo<>(jobList);
		return Result.ok(page);
	}
	
	/**
	 * Info.
	 *
	 * @param jobId the job id
	 * @return the result
	 */
	@GetMapping("/jobs/{jobId}")
	public Result info(@PathVariable("jobId") Long jobId){
		ScheduleJobEntity schedule = scheduleJobService.queryObject(jobId);
		
		return new Result(schedule);
	}
	
	/**
	 * Save.
	 *
	 * @param scheduleJob the schedule job
	 * @return the result
	 */
	@OpLog("保存定时任务")
	@PostMapping("/jobs")
	public Result save(@RequestBody ScheduleJobEntity scheduleJob){
		scheduleJobService.save(scheduleJob);
		return Result.ok();
	}
	
	/**
	 * Update.
	 *
	 * @param scheduleJob the schedule job
	 * @return the result
	 */
	@OpLog("修改定时任务")
	@PutMapping("/jobs")
	public Result update(@RequestBody ScheduleJobEntity scheduleJob){
		scheduleJobService.update(scheduleJob);
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
	public Result delete(@PathVariable("jobId") Long jobId){
		scheduleJobService.deleteBatch(new Long[] {jobId});
		return Result.ok();
	}
	
	/**
	 * Run.
	 *
	 * @param jobId the job id
	 * @return the result
	 */
	@OpLog("立即执行任务")
	@GetMapping("/jobs/{jobId}/run")
	public Result run(@PathVariable("jobId") Long jobId){
		scheduleJobService.run(new Long[] {jobId});
		return Result.ok();
	}
	
	/**
	 * Pause.
	 *
	 * @param jobId the job id
	 * @return the result
	 */
	@OpLog("暂停定时任务")
	@GetMapping("/jobs/{jobId}/pause")
	public Result pause(@PathVariable("jobId") Long jobId){
		scheduleJobService.pause(new Long[] {jobId});
		return Result.ok();
	}
	
	/**
	 * Resume.
	 *
	 * @param jobId the job id
	 * @return the result
	 */
	@OpLog("恢复定时任务")
	@GetMapping("/jobs/{jobId}/resume")
	public Result resume(@PathVariable("jobId") Long jobId){
		scheduleJobService.resume(new Long[] {jobId});
		return Result.ok();
	}

}
