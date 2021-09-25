/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  DataController.java   
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

import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Maps;

import io.github.junxworks.ep.core.Result;
import io.github.junxworks.ep.sys.service.DataService;
import io.github.junxworks.junx.core.util.StringUtils;

/**
 * 基于string template进行字符串解析，可用于直接配置sql进行条件查询操作
 *
 * @ClassName:  DataController
 * @author: Michael
 * @date:   2020-7-19 12:17:48
 * @since:  v1.0
 */
@RestController
@RequestMapping("/ep/data")
public class DataController {

	/** data service. */
	@Autowired
	private DataService dataService;
	
	/**
	 * 根据sqlid以及传入参数，返回ep_s_sql表中对应sql的执行结果，在一些简单的场景可以使用.
	 *
	 * @param sqlid the sqlid
	 * @param request the request
	 * @return the result
	 * @throws Exception the exception
	 */
	@GetMapping(value = "/{sqlid}")
	public Result data(@PathVariable String sqlid, HttpServletRequest request) throws Exception {
		Map<String, String> cond = Maps.newHashMap();
		Enumeration<String> emu_params = request.getParameterNames();
		if (emu_params != null) {
			while (emu_params.hasMoreElements()) {
				String paraName = emu_params.nextElement();
				String value = request.getParameter(paraName);
				if (StringUtils.notNull(value)) {
					cond.put(paraName, value);
				}
			}
		}
		return Result.ok(dataService.getDataBySQLUid(sqlid, cond));
	}
}
