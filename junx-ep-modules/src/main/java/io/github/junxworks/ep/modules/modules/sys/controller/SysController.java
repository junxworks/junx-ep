package io.github.junxworks.ep.modules.modules.sys.controller;

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
import io.github.junxworks.ep.modules.config.EPConfig;

@RestController
@RequestMapping("/ep/sys")
public class SysController {

	@Autowired
	private EPConfig config;

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

	@PostMapping(value = "/logout")
	public Result logout() {
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		return Result.ok();
	}

	@GetMapping(value = "/main-page")
	public Result mainPage() {
		return Result.ok(config.getMainPage());
	}

	@GetMapping(value = "/system-name")
	public Result systemName() {
		return Result.ok(config.getSystemName());
	}

	@GetMapping(value = "/system-short-name")
	public Result systemShortName() {
		return Result.ok(config.getSystemShortName());
	}

	@GetMapping(value = "/ep-config")
	public Result epConfig() {
		return Result.ok(config);
	}
}
