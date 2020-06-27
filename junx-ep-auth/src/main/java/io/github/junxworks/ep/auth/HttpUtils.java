package io.github.junxworks.ep.auth;

import java.util.Arrays;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import io.github.junxworks.junx.core.util.ObjectUtils;

public class HttpUtils {

	public static String getRequestParam(String paramName, HttpServletRequest httpRequest) {
		//从header中获取param
		String param = httpRequest.getHeader(paramName);
		//如果header中不存在param，则从参数中获取param
		if (StringUtils.isBlank(param)) {
			param = httpRequest.getParameter(paramName);
		}
		return param;
	}

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

	public static boolean pathMatches(String cookiePath, String requestPath) {
		if (!requestPath.startsWith(cookiePath)) {
			return false;
		}

		return requestPath.length() == cookiePath.length() || cookiePath.charAt(cookiePath.length() - 1) == '/' || requestPath.charAt(cookiePath.length()) == '/';
	}

	public static javax.servlet.http.Cookie getCookie(HttpServletRequest request, String cookieName) {
		javax.servlet.http.Cookie cookies[] = request.getCookies();
		if (cookies != null) {
			for (javax.servlet.http.Cookie cookie : cookies) {
				if (cookie.getName().equals(cookieName)) {
					return cookie;
				}
			}
		}
		return null;
	}
}
