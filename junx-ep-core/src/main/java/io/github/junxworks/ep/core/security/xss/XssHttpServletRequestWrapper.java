/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  XssHttpServletRequestWrapper.java   
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

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * {类的详细说明}.
 *
 * @ClassName:  XssHttpServletRequestWrapper
 * @author: Michael
 * @date:   2020-7-19 12:18:36
 * @since:  v1.0
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

	/** ori request. */
	//没被包装过的HttpServletRequest（特殊场景，需要自己过滤）
	HttpServletRequest oriRequest;

	/** 常量 htmlFilter. */
	//html过滤
	private final static HTMLFilter htmlFilter = new HTMLFilter();

	/**
	 * 构造一个新的 xss http servlet request wrapper 对象.
	 *
	 * @param request the request
	 */
	public XssHttpServletRequestWrapper(HttpServletRequest request) {
		super(request);
		oriRequest = request;
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		//非json类型，直接返回
		String type = super.getHeader(HttpHeaders.CONTENT_TYPE);
		if (StringUtils.isBlank(type) || !type.contains(MediaType.APPLICATION_JSON_VALUE)) {
			return super.getInputStream();
		}

		//为空，直接返回
		String json = IOUtils.toString(super.getInputStream(), "utf-8");
		if (StringUtils.isBlank(json)) {
			return super.getInputStream();
		}

		//xss过滤
		json = xssEncode(json);
		final ByteArrayInputStream bis = new ByteArrayInputStream(json.getBytes("utf-8"));
		return new ServletInputStream() {
			@Override
			public boolean isFinished() {
				return true;
			}

			@Override
			public boolean isReady() {
				return true;
			}

			@Override
			public void setReadListener(ReadListener readListener) {

			}

			@Override
			public int read() throws IOException {
				return bis.read();
			}
		};
	}

	/**
	 * 返回 parameter 属性.
	 *
	 * @param name the name
	 * @return parameter 属性
	 */
	@Override
	public String getParameter(String name) {
		String value = super.getParameter(xssEncode(name));
		if (StringUtils.isNotBlank(value)) {
			value = xssEncode(value);
		}
		return value;
	}

	/**
	 * 返回 parameter values 属性.
	 *
	 * @param name the name
	 * @return parameter values 属性
	 */
	@Override
	public String[] getParameterValues(String name) {
		String[] parameters = super.getParameterValues(name);
		if (parameters == null || parameters.length == 0) {
			return null;
		}

		for (int i = 0; i < parameters.length; i++) {
			parameters[i] = xssEncode(parameters[i]);
		}
		return parameters;
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		Map<String, String[]> map = new LinkedHashMap<>();
		Map<String, String[]> parameters = super.getParameterMap();
		for (String key : parameters.keySet()) {
			String[] values = parameters.get(key);
			for (int i = 0; i < values.length; i++) {
				values[i] = xssEncode(values[i]);
			}
			map.put(key, values);
		}
		return map;
	}

	/**
	 * 返回 header 属性.
	 *
	 * @param name the name
	 * @return header 属性
	 */
	@Override
	public String getHeader(String name) {
		String value = super.getHeader(xssEncode(name));
		if (StringUtils.isNotBlank(value)) {
			value = xssEncode(value);
		}
		return value;
	}

	/**
	 * Xss encode.
	 *
	 * @param input the input
	 * @return the string
	 */
	private String xssEncode(String input) {
		return htmlFilter.filter(input);
	}

	/**
	 * 获取最原始的request
	 */
	public HttpServletRequest getOriginalRequest() {
		return oriRequest;
	}

	/**
	 * 返回 original request 属性.
	 *
	 * @param request the request
	 * @return original request 属性
	 */
	public static HttpServletRequest getOriginalRequest(HttpServletRequest request) {
		if (request instanceof XssHttpServletRequestWrapper) {
			return ((XssHttpServletRequestWrapper) request).getOriginalRequest();
		}

		return request;
	}

}
