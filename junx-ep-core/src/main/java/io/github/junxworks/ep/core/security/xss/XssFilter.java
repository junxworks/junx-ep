/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  XssFilter.java   
 * @Package io.github.junxworks.ep.core.security.xss   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2020-7-19 12:18:36   
 * @version V1.0 
 * @Copyright: 2020 Junxworks. All rights reserved. 
 * 注意：
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.core.security.xss;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * {类的详细说明}.
 *
 * @ClassName:  XssFilter
 * @author: Michael
 * @date:   2020-7-19 12:18:36
 * @since:  v1.0
 */
public class XssFilter implements Filter {

	/**
	 * Inits the.
	 *
	 * @param config the config
	 * @throws ServletException the servlet exception
	 */
	@Override
	public void init(FilterConfig config) throws ServletException {
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
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
		XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper(
				(HttpServletRequest) request);
		chain.doFilter(xssRequest, response);
	}

	/**
	 * Destroy.
	 */
	@Override
	public void destroy() {
	}

}