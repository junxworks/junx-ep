/*
 ***************************************************************************************
 * All rights Reserved, Designed By www.cqhyrc.com.cn
 * @Title:  DataController.java   
 * @Package io.github.junxworks.ep.modules.modules.sys.controller   
 * @Description: (用一句话描述该文件做什么)   
 * @author: AOC
 * @date:   2020-3-2 13:23:58   
 * @version V1.0 
 * @Copyright: 2020 重庆华宇集团. All rights reserved. 
 * 注意：本内容仅限于公司内部使用，禁止外泄以及用于其他的商业目
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.modules.modules.sys.controller;

import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Maps;
import io.github.junxworks.ep.core.Result;
import io.github.junxworks.ep.modules.modules.sys.service.DataService;

/**
 * 通用数据查询
 *
 * @ClassName:  DataController
 * @author: 王兴
 * @date:   2020-3-2 13:23:58
 * @since:  v1.0
 */
@RestController
@RequestMapping("/ep/data")
public class DataController {

	@Autowired
	private DataService dataService;
	
	@GetMapping(value = "/{sqlid}")
	public Result data(@PathVariable String sqlid, HttpServletRequest request) throws Exception {
		Map<String, String> cond = Maps.newHashMap();
		Enumeration<String> emu_params = request.getParameterNames();
		if (emu_params != null) {
			while (emu_params.hasMoreElements()) {
				String paraName = emu_params.nextElement();
				String value = request.getParameter(paraName);
				if (!StringUtils.isEmpty(value)) {
					cond.put(paraName, value);
				}
			}
		}
		return Result.ok(dataService.getDataBySQLUid(sqlid, cond));
	}
}
