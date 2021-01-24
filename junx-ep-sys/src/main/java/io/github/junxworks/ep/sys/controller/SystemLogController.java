/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  SystemLogController.java   
 * @Package io.github.junxworks.ep.sys.controller   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2021-1-24 15:35:41   
 * @version V1.0 
 * @Copyright: 2021 Junxworks. All rights reserved. 
 * 注意：
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.sys.controller;

import com.github.pagehelper.PageInfo;
import io.github.junxworks.ep.core.Result;
import io.github.junxworks.ep.core.utils.PageUtils;
import io.github.junxworks.ep.sys.dto.SystemLogConditionDto;
import io.github.junxworks.ep.sys.service.SystemLogService;
import io.github.junxworks.ep.sys.vo.SystemLogInfoVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * {类的详细说明}.
 *
 * @ClassName:  SystemLogController
 * @author: Michael
 * @date:   2020-7-19 12:17:48
 * @since:  v1.0
 */
@RestController
@RequestMapping("/ep/sys")
public class SystemLogController {

	/** system log service. */
	@Autowired
	private SystemLogService systemLogService;

	/**
	 * 返回 system log list 属性.
	 *
	 * @param condition the condition
	 * @return system log list 属性
	 */
	@GetMapping(value = "/system-logs")
	public Result getSystemLogList(SystemLogConditionDto condition) {
		PageUtils.setPage(condition);
		return Result.ok(new PageInfo<SystemLogInfoVo>(systemLogService.getSystemLogListByCondition(condition)));
	}

	/**
	 * 返回 system log info by id 属性.
	 *
	 * @param id the id
	 * @return system log info by id 属性
	 */
	@GetMapping("/system-logs/{id}")
	public Result getSystemLogInfoById(@PathVariable Long id) {
		return Result.ok(systemLogService.getSystemLogInfoById(id));
	}
}
