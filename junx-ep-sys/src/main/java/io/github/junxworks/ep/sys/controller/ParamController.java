/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  ParamController.java   
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.junxworks.ep.core.Result;
import io.github.junxworks.ep.sys.entity.SParam;
import io.github.junxworks.ep.sys.service.ParamService;

/**
 * 通用数据查询
 *
 * @ClassName:  DataController
 * @author: Michael
 * @date:   2020-3-2 13:23:58
 * @since:  v1.0
 */
@RestController
@RequestMapping("/ep/params")
public class ParamController {

	@Autowired
	private ParamService paramService;

	@GetMapping(value = "/{group}/{paramName}")
	public Result queryParam(@PathVariable("group") String group, @PathVariable("paramName") String paramName) throws Exception {
		return Result.ok(paramService.queryParamByGroupAndName(group, paramName));
	}

	@PostMapping
	public Result updateParam(@RequestBody SParam param) throws Exception {
		return Result.ok(paramService.updateParam(param));
	}
}
