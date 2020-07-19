/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  CSRFFilter.java   
 * @Package io.github.junxworks.ep.core.security.csrf   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2020-7-19 12:18:37   
 * @version V1.0 
 * @Copyright: 2020 Junxworks. All rights reserved. 
 * 注意：
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.core.security.csrf;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import io.github.junxworks.ep.core.Result;

import io.github.junxworks.junx.core.util.StringUtils;

/**
 * {类的详细说明}.
 *
 * @ClassName:  CSRFFilter
 * @author: Michael
 * @date:   2020-7-19 12:18:37
 * @since:  v1.0
 */
public class CSRFFilter implements Filter {

	/** 常量 DEFAULT_CSRF_TOKEN. */
	public static final String DEFAULT_CSRF_TOKEN = "_c_";

	/** 常量 CSRF_TOKEN_NAME. */
	private static final String CSRF_TOKEN_NAME = "csrf_token";

	/** c token. */
	private String cToken;

	/**
	 * Inits the.
	 *
	 * @param config the config
	 * @throws ServletException the servlet exception
	 */
	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig config) throws ServletException {
		String cToken = config.getInitParameter(CSRF_TOKEN_NAME);
		if (StringUtils.isBlank(cToken)) {
			cToken = DEFAULT_CSRF_TOKEN;
		}
	}

	/**
	 * Do filter.
	 *
	 * @param request the request
	 * @param response the response
	 * @param chain the chain
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ServletException the servlet exception
	 */
	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String ctoken = req.getParameter(cToken);
		//有sql关键字，直接返回，不继续执行
		if (vailidate(ctoken)) {
			res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			String json = JSON.toJSONString(Result.error("Bad request paramters."));
			res.getWriter().print(json);
			return;
		} else {
			chain.doFilter(req, res);
		}
	}

	/**
	 * Vailidate.
	 *
	 * @param token the token
	 * @return true, if successful
	 */
	private boolean vailidate(String token) {
		//检查token
		return true;
	}

	/**
	 * Destroy.
	 */
	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
	}

}