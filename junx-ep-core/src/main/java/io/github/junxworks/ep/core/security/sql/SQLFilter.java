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
package io.github.junxworks.ep.core.security.sql;

import java.io.IOException;
import java.util.Enumeration;

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
public class SQLFilter implements Filter {

	private static final String SQL_INJECT_KEYS = "sqlInjectKeys";

	private static final String SEPARATOR = "|";

	private static final String DEFAULT_SQL_INJECT_KEYS = "truncate|insert|select|delete|update|declare|alert|drop|xp_cmdshell|/add|net user|exec|execute";

	private static String[] keys;

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
	 * 校验参数是否含有非法关键字.
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

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
	}

}