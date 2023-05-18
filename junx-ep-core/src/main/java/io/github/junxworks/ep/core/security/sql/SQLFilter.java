/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  SQLFilter.java   
 * @Package io.github.junxworks.ep.core.security.sql   
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
package io.github.junxworks.ep.core.security.sql;

import java.io.IOException;
import java.util.Enumeration;

import com.alibaba.fastjson.JSON;

import io.github.junxworks.ep.core.Result;
import io.github.junxworks.junx.core.util.StringUtils;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * sql注入过滤器
 *
 * @ClassName:  SQLFilter
 * @author: Michael
 * @date:   2020-7-19 12:18:37
 * @since:  v1.0
 */
public class SQLFilter implements Filter {

	/** 常量 SQL_INJECT_KEYS. */
	private static final String SQL_INJECT_KEYS = "sqlInjectKeys";

	/** 常量 SEPARATOR. */
	private static final String SEPARATOR = "|";

	/** 常量 DEFAULT_SQL_INJECT_KEYS. */
	private static final String DEFAULT_SQL_INJECT_KEYS = "truncate|insert|select|delete|update|declare|alert|drop|xp_cmdshell|/add|net user|exec|execute";

	/** keys. */
	private static String[] keys;

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
		String keyStr = config.getInitParameter(SQL_INJECT_KEYS);
		if (StringUtils.isBlank(keyStr)) {
			keyStr = DEFAULT_SQL_INJECT_KEYS;
		}
		keys = StringUtils.split(keyStr, SEPARATOR);
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
	@SuppressWarnings("rawtypes")
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		//获得所有请求参数名
		Enumeration params = req.getParameterNames();
		StringBuilder param = new StringBuilder();
		while (params.hasMoreElements()) {
			//得到参数名
			String name = params.nextElement().toString();
			//得到参数对应值
			String[] value = req.getParameterValues(name);
			for (int i = 0; i < value.length; i++) {
				param.append(value[i]);
			}
		}
		//有sql关键字，直接返回，不继续执行
		if (vailidate(param.toString())) {
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
	 * @param params the params
	 * @return true, if successful
	 */
	private boolean vailidate(String params) {
		for (String key : keys) {
			if (params.indexOf(key) != -1) {
				return true;
			}
		}
		return false;
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