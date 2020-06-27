/*
 ***************************************************************************************
 * All rights Reserved, Designed By www.cqhyrc.com.cn
 * @Title:  OAuth2Filter.java   
 * @Package com.yrxd.security.app.shiro   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2019-1-16 17:47:41   
 * @version V1.0 
 * @Copyright: 2019 重庆华宇集团. All rights reserved. 
 * 注意：本内容仅限于公司内部使用，禁止外泄以及用于其他的商业目
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.auth;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.util.WebUtils;

import com.alibaba.fastjson.JSON;

import io.github.junxworks.ep.auth.ram.EPRamAuthenticationFilter;
import io.github.junxworks.ep.core.Result;
import io.github.junxworks.junx.core.util.ExceptionUtils;

/**
 * shiro过滤器
 *
 * @ClassName:  ShiroFilter
 * @author: 王兴
 * @date:   2019-1-16 17:47:41
 * @since:  v1.0
 */
public class EPTokenAuthenticatingFilter extends EPRamAuthenticationFilter {

	private static final String USERNAME = "username";

	private static final String PASSWORD = "password";

	private EPShiroConfig config;

	public EPShiroConfig getConfig() {
		return config;
	}

	public void setConfig(EPShiroConfig config) {
		this.config = config;
	}
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

	/* (non-Javadoc)
	 * @see org.apache.shiro.web.filter.AccessControlFilter#onAccessDenied(javax.servlet.ServletRequest, javax.servlet.ServletResponse)
	 */
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		if (isLoginRequest(request, response)) {
			return executeLogin(request, response);
		}
		return super.onAccessDenied(request, response);
	}

	protected boolean isLoginSubmission(ServletRequest request, ServletResponse response) {
		return (request instanceof HttpServletRequest) && WebUtils.toHttp(request).getMethod().equalsIgnoreCase(POST_METHOD);
	}

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
			Result r = Result.error(message);
			String json = JSON.toJSONString(r);
			httpResponse.getWriter().print(json);
		} catch (IOException e1) {
		}

		return false;
	}
}
