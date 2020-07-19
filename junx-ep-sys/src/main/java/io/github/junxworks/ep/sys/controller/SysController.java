/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  SysController.java   
 * @Package io.github.junxworks.ep.sys.controller   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2020-7-19 12:17:48   
 * @version V1.0 
 * @Copyright: 2020 Junxworks. All rights reserved. 
 * 注意：
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.sys.controller;

import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.junxworks.ep.auth.DefaultToken;
import io.github.junxworks.ep.core.Result;
import io.github.junxworks.ep.sys.config.EPConfig;

/**
 * {类的详细说明}.
 *
 * @ClassName:  SysController
 * @author: Michael
 * @date:   2020-7-19 12:17:48
 * @since:  v1.0
 */
@RestController
@RequestMapping("/ep/sys")
public class SysController {

	/** config. */
	@Autowired
	private EPConfig config;

	/**
	 * Login.
	 *
	 * @param map the map
	 * @return the result
	 */
	@PostMapping(value = "/login", consumes = "application/json;charset=UTF-8")
	public Result login(@RequestBody Map<String, String> map) {
		Subject subject = SecurityUtils.getSubject();
		DefaultToken token = new DefaultToken();
		//进行验证，这里可以捕获异常，然后返回对应信息
		String username = map.get("username");
		String password = map.get("password");
		token.setPrincipal(username);
		token.setCredential(password);
		subject.login(token);
		return Result.ok(subject.getSession().getId());
	}

	/**
	 * Logout.
	 *
	 * @return the result
	 */
	@PostMapping(value = "/logout")
	public Result logout() {
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		return Result.ok();
	}

	/**
	 * Main page.
	 *
	 * @return the result
	 */
	@GetMapping(value = "/main-page")
	public Result mainPage() {
		return Result.ok(config.getMainPage());
	}

	/**
	 * System name.
	 *
	 * @return the result
	 */
	@GetMapping(value = "/system-name")
	public Result systemName() {
		return Result.ok(config.getSystemName());
	}

	/**
	 * System short name.
	 *
	 * @return the result
	 */
	@GetMapping(value = "/system-short-name")
	public Result systemShortName() {
		return Result.ok(config.getSystemShortName());
	}

	/**
	 * Ep config.
	 *
	 * @return the result
	 */
	@GetMapping(value = "/ep-config")
	public Result epConfig() {
		return Result.ok(config);
	}
}
