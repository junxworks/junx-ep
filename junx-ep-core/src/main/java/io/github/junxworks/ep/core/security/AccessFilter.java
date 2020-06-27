/*
 ***************************************************************************************
 * All rights Reserved, Designed By www.cqhyrc.com.cn
 * @Title:  CookieFilter.java   
 * @Package com.yrxd.security.app.shiro   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2019-1-18 17:51:35   
 * @version V1.0 
 * @Copyright: 2019 重庆华宇集团. All rights reserved. 
 * 注意：本内容仅限于公司内部使用，禁止外泄以及用于其他的商业目
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.core.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.junxworks.ep.core.utils.IPUtils;
import io.github.junxworks.junx.core.util.ByteUtils;
import io.github.junxworks.junx.core.util.StringUtils;

public class AccessFilter implements Filter {
	private static final Logger log = LoggerFactory.getLogger(AccessFilter.class);

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		BodyReaderHttpServletRequestWrapper wrapperRequest = new BodyReaderHttpServletRequestWrapper(req);
		String payload = new String(ByteUtils.inputStreamToBytes(wrapperRequest.getInputStream()));
		if (StringUtils.notNull(payload)) {
			payload = payload.replaceAll("\\n", "");
		}

		log.info(new StringBuilder()
				.append("remote ip:").append(IPUtils.getIpAddr(req))
				.append(" url:").append(req.getRequestURI())
				.append(" method:").append(req.getMethod())
				.append(" params:").append(req.getQueryString())
				.append(" body:").append(payload).toString());
		chain.doFilter(wrapperRequest, response);
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
	}

}
