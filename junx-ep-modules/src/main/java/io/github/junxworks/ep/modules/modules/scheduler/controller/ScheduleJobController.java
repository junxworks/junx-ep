package io.github.junxworks.ep.modules.modules.scheduler.controller;

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
import io.github.junxworks.ep.modules.annotations.OpLog;
import io.github.junxworks.ep.modules.modules.scheduler.entity.ScheduleJobEntity;
import io.github.junxworks.ep.modules.modules.scheduler.service.ScheduleJobService;

/**
 * 定时任务
 * 
 */
@RestController
@RequestMapping("/ep/sys/schedule")
public class ScheduleJobController {
	@Autowired
	private ScheduleJobService scheduleJobService;
	
	/**
	 * 定时任务列表
	 */
	@GetMapping("/jobs")
//	@RequiresPermissions("sys:schedule:list")
	public Result list(@RequestParam Map<String, Object> params){
		//查询列表数据
		PageUtils.setPage(params);
		List<ScheduleJobEntity> jobList = scheduleJobService.queryList(params);
		PageInfo<ScheduleJobEntity> page = new PageInfo<>(jobList);
		return Result.ok(page);
	}
	
	/**
	 * 定时任务信息
	 */
	@GetMapping("/jobs/{jobId}")
//	@RequiresPermissions("sys:schedule:info")
	public Result info(@PathVariable("jobId") Long jobId){
		ScheduleJobEntity schedule = scheduleJobService.queryObject(jobId);
		
		return new Result(schedule);
	}
	
	/**
	 * 保存定时任务
	 */
	@OpLog("保存定时任务")
	@PostMapping("/jobs")
//	@RequiresPermissions("sys:schedule:save")
	public Result save(@RequestBody ScheduleJobEntity scheduleJob){
		scheduleJobService.save(scheduleJob);
		return Result.ok();
	}
	
	/**
	 * 修改定时任务
	 */
	@OpLog("修改定时任务")
	@PutMapping("/jobs")
//	@RequiresPermissions("sys:schedule:update")
	public Result update(@RequestBody ScheduleJobEntity scheduleJob){
		scheduleJobService.update(scheduleJob);
		return Result.ok();
	}
	
	/**
	 * 删除定时任务
	 */
	@OpLog("删除定时任务")
	@DeleteMapping("/jobs/{jobId}")
//	@RequiresPermissions("sys:schedule:delete")
	public Result delete(@PathVariable("jobId") Long jobId){
		scheduleJobService.deleteBatch(new Long[] {jobId});
		return Result.ok();
	}
	
	/**
	 * 立即执行任务
	 */
	@OpLog("立即执行任务")
	@GetMapping("/jobs/{jobId}/run")
//	@RequiresPermissions("sys:schedule:run")
	public Result run(@PathVariable("jobId") Long jobId){
		scheduleJobService.run(new Long[] {jobId});
		return Result.ok();
	}
	
	/**
	 * 暂停定时任务
	 */
	@OpLog("暂停定时任务")
	@GetMapping("/jobs/{jobId}/pause")
//	@RequiresPermissions("sys:schedule:pause")
	public Result pause(@PathVariable("jobId") Long jobId){
		scheduleJobService.pause(new Long[] {jobId});
		return Result.ok();
	}
	
	/**
	 * 恢复定时任务
	 */
	@OpLog("恢复定时任务")
	@GetMapping("/jobs/{jobId}/resume")
//	@RequiresPermissions("sys:schedule:resume")
	public Result resume(@PathVariable("jobId") Long jobId){
		scheduleJobService.resume(new Long[] {jobId});
		return Result.ok();
	}

}
