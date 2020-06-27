/*
 ***************************************************************************************
 * All rights Reserved, Designed By www.cqhyrc.com.cn
 * @Title:  SQLFilter.java   
 * @Package io.github.junxworks.ep.core.security.sql   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2019-1-2 11:14:05   
 * @version V1.0 
 * @Copyright: 2019 重庆华宇集团. All rights reserved. 
 * 注意：本内容仅限于公司内部使用，禁止外泄以及用于其他的商业目
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
 * sql注入过滤
 *
 * @ClassName:  SQLFilter
 * @author: 王兴
 * @date:   2019-1-2 11:14:05
 * @since:  v1.0
 */
public class CSRFFilter implements Filter {

	/** CSRF token的字段名. */
	public static final String DEFAULT_CSRF_TOKEN = "_c_";

	private static final String CSRF_TOKEN_NAME = "csrf_token";

	private String cToken;

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
	 * 校验csrf token是否合法
	 *
	 * @param params the params
	 * @return true, if successful
	 */
	private boolean vailidate(String token) {
		//检查token
		return true;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
	}

}