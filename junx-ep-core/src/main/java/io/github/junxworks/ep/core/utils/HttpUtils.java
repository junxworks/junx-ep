/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  HttpUtils.java   
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
package io.github.junxworks.ep.core.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import io.github.junxworks.junx.core.util.ObjectUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Http工具类
 *
 * @ClassName:  HttpUtils
 * @author: Michael
 * @date:   2020-7-19 12:18:41
 * @since:  v1.0
 */
public class HttpUtils {

	/**
	 * 返回 request param 属性.
	 *
	 * @param paramName the param name
	 * @param httpRequest the http request
	 * @return request param 属性
	 */
	public static String getRequestParam(String paramName, HttpServletRequest httpRequest) {
		//从header中获取param
		String param = httpRequest.getHeader(paramName);
		//如果header中不存在param，则从参数中获取param
		if (StringUtils.isBlank(param)) {
			param = httpRequest.getParameter(paramName);
		}
		return param;
	}

	/**
	 * Sets the cookie.
	 *
	 * @param httpRequest the http request
	 * @param name the name
	 * @param value the value
	 */
	public static void setCookie(HttpServletRequest httpRequest, String name, String value) {
		Cookie cookie = new Cookie(name, value);
		Cookie[] newCookies = null;
		Cookie[] cookies = httpRequest.getCookies();
		if (cookies != null) {
			newCookies = Arrays.copyOf(cookies, cookies.length + 1);
		} else {
			newCookies = new Cookie[1];
		}
		newCookies[newCookies.length - 1] = cookie;
		Object realRequest = ObjectUtils.mirror().on(httpRequest).get().field("request");
		ObjectUtils.mirror().on(realRequest).set().field("cookies").withValue(newCookies);
	}

	/**
	 * Path matches.
	 *
	 * @param cookiePath the cookie path
	 * @param requestPath the request path
	 * @return true, if successful
	 */
	public static boolean pathMatches(String cookiePath, String requestPath) {
		if (!requestPath.startsWith(cookiePath)) {
			return false;
		}

		return requestPath.length() == cookiePath.length() || cookiePath.charAt(cookiePath.length() - 1) == '/' || requestPath.charAt(cookiePath.length()) == '/';
	}

	/**
	 * 返回 cookie 属性.
	 *
	 * @param request the request
	 * @param cookieName the cookie name
	 * @return cookie 属性
	 */
	public static Cookie getCookie(HttpServletRequest request, String cookieName) {
		Cookie cookies[] = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(cookieName)) {
					return cookie;
				}
			}
		}
		return null;
	}
	
	/**
	 * 从URL下载文件到指定file.
	 *
	 * @param downloadUrl the download url
	 * @param target the target
	 * @throws Exception the exception
	 */
	public static void download(String downloadUrl, File target) throws Exception {
		URL url = null;
		try {
			url = new URL(downloadUrl);
		} catch (MalformedURLException e) {
			throw e;
		}

		URLConnection conn = null;
		try {
			conn = url.openConnection();
			conn.setConnectTimeout(10 * 1000);
		} catch (IOException e) {
			throw e;
		}
		try (InputStream is = conn.getInputStream()) {
			FileUtils.copyInputStreamToFile(is, target);
		}
	}
}
