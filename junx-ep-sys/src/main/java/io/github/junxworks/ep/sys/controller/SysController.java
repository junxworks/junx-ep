/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  SysController.java   
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

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.google.code.kaptcha.impl.DefaultKaptcha;

import io.github.junxworks.ep.auth.DefaultToken;
import io.github.junxworks.ep.auth.AuthConstants;
import io.github.junxworks.ep.auth.model.UserModel;
import io.github.junxworks.ep.core.Result;
import io.github.junxworks.ep.core.utils.IPUtils;
import io.github.junxworks.ep.sys.config.EPConfig;
import io.github.junxworks.ep.sys.entity.EpSLog;
import io.github.junxworks.ep.sys.service.SysLogService;
import io.github.junxworks.junx.core.util.StringUtils;

/**
 * 系统基础控制器
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

	@Autowired
	@Qualifier("JunxEPSystemLogService")
	private SysLogService sysLog;

	@Autowired
	private DefaultKaptcha producer;

	/**
	 * Login.
	 *
	 * @param map the map
	 * @return the result
	 */
	@PostMapping(value = "/login", consumes = "application/json;charset=UTF-8")
	public Result login(@RequestBody Map<String, String> map, HttpServletRequest request) {
		long begin = System.currentTimeMillis();
		Subject subject = SecurityUtils.getSubject();
		Object checkVf = subject.getSession().getAttribute(AuthConstants.CHECK_VERIFICATION_CODE);
		if (checkVf != null && Boolean.valueOf(checkVf.toString())) {
			String vf = map.get(AuthConstants.VERIFICATION_CODE);
			if (StringUtils.isNull(vf)) {
				return Result.unauthenticated("无效的验证码");
			}
			String code = subject.getSession().getAttribute(AuthConstants.VERIFICATION_CODE).toString();
			if (!code.equalsIgnoreCase(vf)) {
				return Result.unauthenticated("无效的验证码");
			}
		}
		DefaultToken token = new DefaultToken();
		//进行验证，这里可以捕获异常，然后返回对应信息
		String username = map.get(AuthConstants.USERNAME);
		String password = map.get(AuthConstants.PASSWD);
		token.setPrincipal(username);
		token.setCredential(password);
		try {
			subject.login(token);
		} catch (IncorrectCredentialsException e) {
			return Result.unauthenticated("用户名或密码错误");
		}
		subject.getSession().setAttribute(AuthConstants.CHECK_VERIFICATION_CODE, false); //登录成功，取消验证码限制
		UserModel user = (UserModel) SecurityUtils.getSubject().getPrincipal();
		EpSLog log = new EpSLog();
		log.setOperation("EP-系统支撑-系统登录");
		log.setUrl("/login");
		log.setData(JSON.toJSONString(map));
		log.setUserId(user.getId());
		log.setCreateTime(new Date());
		log.setCost(System.currentTimeMillis() - begin);
		log.setMethod("io.github.junxworks.ep.sys.controller.SysController.login(Map<String, String>,HttpServletRequest)");
		log.setIp(IPUtils.getIpAddr(request));
		sysLog.saveSystemLog(log);
		return Result.ok(subject.getSession().getId());
	}

	@GetMapping(value = "/verification-codes")
	public void generateVerificationCode(HttpServletResponse response) throws IOException {
		String code = String.valueOf(1000 + new Random().nextInt(8999));
		BufferedImage image = producer.createImage(code);
		SecurityUtils.getSubject().getSession().setAttribute(AuthConstants.VERIFICATION_CODE, code);
		response.setHeader("Cache-Control", "no-store, no-cache");
		response.setContentType("image/jpeg");
		try (ServletOutputStream out = response.getOutputStream();) {
			ImageIO.write(image, "jpg", out);
		}
	}

	/**
	 * Logout.
	 *
	 * @return the result
	 */
	@PostMapping(value = "/logout")
	public Result logout(HttpServletRequest request) {
		long begin = System.currentTimeMillis();
		UserModel user = (UserModel) SecurityUtils.getSubject().getPrincipal();
		EpSLog log = new EpSLog();
		log.setOperation("EP-系统支撑-系统登出");
		log.setUrl("/logout");
		log.setUserId(user.getId());
		log.setCreateTime(new Date());
		log.setCost(System.currentTimeMillis() - begin);
		log.setMethod("io.github.junxworks.ep.sys.controller.SysController.logout(HttpServletRequest)");
		log.setIp(IPUtils.getIpAddr(request));
		sysLog.saveSystemLog(log);
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
