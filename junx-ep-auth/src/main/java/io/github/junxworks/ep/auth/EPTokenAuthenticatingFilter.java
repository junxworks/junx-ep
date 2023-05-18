/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  EPTokenAuthenticatingFilter.java   
 * @Package io.github.junxworks.ep.auth   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2020-7-19 12:18:41   
 * @version V1.0 
 * @Copyright: 2020 Junxworks. All rights reserved. 
 * 注意：
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.auth;

import java.io.IOException;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.util.WebUtils;

import com.alibaba.fastjson.JSON;

import io.github.junxworks.ep.auth.simple.EPSimpleAuthenticationFilter;
import io.github.junxworks.ep.core.Result;
import io.github.junxworks.ep.core.utils.HttpUtils;
import io.github.junxworks.junx.core.util.ExceptionUtils;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Shiro 认证filter
 *
 * @ClassName:  EPTokenAuthenticatingFilter
 * @author: Michael
 * @date:   2020-7-19 12:18:41
 * @since:  v1.0
 */
public class EPTokenAuthenticatingFilter extends EPSimpleAuthenticationFilter {

	/** 常量 USERNAME. */
	private static final String USERNAME = "username";

	/** 常量 PASSWORD. */
	private static final String PASSWORD = "password";

	/** config. */
	private EPShiroConfig config;

	public EPShiroConfig getConfig() {
		return config;
	}

	public void setConfig(EPShiroConfig config) {
		this.config = config;
	}

	/**
	 * Creates the token.
	 *
	 * @param request the request
	 * @param response the response
	 * @return the authentication token
	 * @throws Exception the exception
	 */
	/* (non-Javadoc)
	 * @see org.apache.shiro.web.filter.authc.AuthenticatingFilter#createToken(javax.servlet.ServletRequest, javax.servlet.ServletResponse)
	 */
	@Override
	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
		//获取请求token
		DefaultToken dt = new DefaultToken();
		dt.setCredential(HttpUtils.getRequestParam(USERNAME, (HttpServletRequest) request));
		dt.setCredential(HttpUtils.getRequestParam(PASSWORD, (HttpServletRequest) request));
		return dt;
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
		if (isLoginRequest(request, response)) {
			return executeLogin(request, response);
		}
		if (this.isRamRequest((HttpServletRequest) request)) {
			return super.onAccessDenied(request, response);
		}
		Result res = Result.unauthenticated();
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		httpResponse.setContentType("application/json;charset=utf-8");
		String json = JSON.toJSONString(res);
		httpResponse.getWriter().print(json);
		return false;
	}

	/**
	 * 返回布尔值 login submission.
	 *
	 * @param request the request
	 * @param response the response
	 * @return 返回布尔值 login submission
	 */
	protected boolean isLoginSubmission(ServletRequest request, ServletResponse response) {
		return (request instanceof HttpServletRequest) && WebUtils.toHttp(request).getMethod().equalsIgnoreCase(POST_METHOD);
	}

	/**
	 * On login failure.
	 *
	 * @param token the token
	 * @param e the e
	 * @param request the request
	 * @param response the response
	 * @return true, if successful
	 */
	/* (non-Javadoc)
	 * @see org.apache.shiro.web.filter.authc.AuthenticatingFilter#onLoginFailure(org.apache.shiro.authc.AuthenticationToken, org.apache.shiro.authc.AuthenticationException, javax.servlet.ServletRequest, javax.servlet.ServletResponse)
	 */
	@Override
	protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		httpResponse.setContentType("application/json;charset=utf-8");
		try {
			//处理登录失败的异常
			String message = ExceptionUtils.getCauseMessage(e);
			Result r = Result.unauthenticated(message);
			String json = JSON.toJSONString(r);
			httpResponse.getWriter().print(json);
		} catch (IOException e1) {
		}
		return false;
	}
}
