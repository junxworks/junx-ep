/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  EPRamAuthenticationFilter.java   
 * @Package io.github.junxworks.ep.auth.ram   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2020-7-19 12:18:42   
 * @version V1.0 
 * @Copyright: 2020 Junxworks. All rights reserved. 
 * 注意：
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.auth.ram;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import io.github.junxworks.ep.auth.RamRequest;
import io.github.junxworks.ep.auth.RamRequestHolder;
import io.github.junxworks.ep.core.Result;
import io.github.junxworks.junx.core.util.StringUtils;

/**
 * {类的详细说明}.
 *
 * @ClassName:  EPRamAuthenticationFilter
 * @author: Michael
 * @date:   2020-7-19 12:18:42
 * @since:  v1.0
 */
public abstract class EPRamAuthenticationFilter extends AuthenticatingFilter {
	
	/** 常量 log. */
	private static final Logger log = LoggerFactory.getLogger(EPRamAuthenticationFilter.class);

	/** ram service. */
	private EPRamService ramService = new EPRamService();

	/** ram enabled. */
	private boolean ramEnabled = false;

	/** ram auth center addr. */
	private String ramAuthCenterAddr;

	/** ram auth path. */
	private String ramAuthPath;

	/** ram header key name. */
	private String ramHeaderKeyName;

	/** ram header secret name. */
	private String ramHeaderSecretName;

	public EPRamService getRamService() {
		return ramService;
	}

	public void setRamService(EPRamService ramService) {
		this.ramService = ramService;
	}

	public boolean isRamEnabled() {
		return ramEnabled;
	}

	public void setRamEnabled(boolean ramEnabled) {
		this.ramEnabled = ramEnabled;
	}

	public String getRamAuthCenterAddr() {
		return ramAuthCenterAddr;
	}

	public void setRamAuthCenterAddr(String ramAuthCenterAddr) {
		this.ramAuthCenterAddr = ramAuthCenterAddr;
	}

	public String getRamAuthPath() {
		return ramAuthPath;
	}

	public void setRamAuthPath(String ramAuthPath) {
		this.ramAuthPath = ramAuthPath;
	}

	public String getRamHeaderKeyName() {
		return ramHeaderKeyName;
	}

	public void setRamHeaderKeyName(String ramHeaderKeyName) {
		this.ramHeaderKeyName = ramHeaderKeyName;
	}

	public String getRamHeaderSecretName() {
		return ramHeaderSecretName;
	}

	public void setRamHeaderSecretName(String ramHeaderSecretName) {
		this.ramHeaderSecretName = ramHeaderSecretName;
	}

	/**
	 * On access denied.
	 *
	 * @param request the request
	 * @param response the response
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	/* (non-Javadoc)
	 * @see org.apache.shiro.web.filter.AccessControlFilter#onAccessDenied(javax.servlet.ServletRequest, javax.servlet.ServletResponse)
	 */
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		Result res = Result.unauthenticated();
		if (ramEnabled && isRamRequest((HttpServletRequest) request)) {
			HttpServletRequest req = (HttpServletRequest) request;
			//调用认证中心补充认证
			String key = req.getHeader(ramHeaderKeyName);
			String secret = req.getHeader(ramHeaderSecretName);
			String uri = req.getRequestURI();
			String method = req.getMethod();
			String ctx = req.getContextPath();
			res = ramService.contactAuthCenter(ramAuthCenterAddr + ramAuthPath, key, secret, ctx, uri, method);
			if (res.isOk()) {
				RamRequest rr = new RamRequest();
				rr.setAccessKey(key);
				rr.setContextPath(ctx);
				rr.setRequestUri(uri);
				rr.setRequestMethod(method);
				RamRequestHolder.set(rr);
				return true;
			}
		}
		if (log.isTraceEnabled()) {
			log.trace("Attempting to access a path which requires authentication.  Forwarding to the " + "Authentication url [" + getLoginUrl() + "]");
		}
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		httpResponse.setContentType("application/json;charset=utf-8");
		String json = JSON.toJSONString(res);
		httpResponse.getWriter().print(json);
		return false;
	}

	/**
	 * 返回布尔值 ram request.
	 *
	 * @param req the req
	 * @return 返回布尔值 ram request
	 */
	protected boolean isRamRequest(HttpServletRequest req) {
		String key = req.getHeader(ramHeaderKeyName);
		String secret = req.getHeader(ramHeaderSecretName);
		return StringUtils.notNull(key) && StringUtils.notNull(secret);
	}

}
