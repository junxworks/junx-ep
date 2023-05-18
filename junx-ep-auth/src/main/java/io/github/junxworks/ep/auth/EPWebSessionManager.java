package io.github.junxworks.ep.auth;

import java.io.Serializable;

import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.servlet.ShiroHttpSession;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

public class EPWebSessionManager extends DefaultWebSessionManager {

	private String headerTokenName;

	public String getHeaderTokenName() {
		return headerTokenName;
	}

	public void setHeaderTokenName(String headerTokenName) {
		this.headerTokenName = headerTokenName;
	}

	/**
	 * 补充从header获取sessionid.
	 *
	 * @param request the request
	 * @param response the response
	 * @return the session id
	 */
	@Override
	protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
		Serializable id = getSessionIdFromHeader((HttpServletRequest) request);
		if (id == null) {
			id = getUriPathSegmentParamValue(request, headerTokenName);
			if (id == null && request instanceof HttpServletRequest) {
				//not a URI path segment parameter, try the query parameters:
				HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
				String queryString = httpServletRequest.getQueryString();
				if (queryString != null && queryString.contains(headerTokenName)) {
					id = request.getParameter(headerTokenName);
				}
				if (id == null && queryString != null && queryString.contains(headerTokenName.toLowerCase())) {
					//try lowercase:
					id = request.getParameter(headerTokenName.toLowerCase());
				}
			}
			if (id != null) {
				request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, ShiroHttpServletRequest.URL_SESSION_ID_SOURCE);
			}
		}
		if (id == null) {
			id = super.getSessionId(request, response);
		}
		if (id != null) {
			request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, id);
			request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
		}
		return id;
	}

	protected Serializable getSessionIdFromHeader(HttpServletRequest request) {
		return request.getHeader(headerTokenName);
	}

	protected String getSessionIdName() {
		String name = getSessionIdCookie() != null ? getSessionIdCookie().getName() : null;
		if (name == null) {
			name = ShiroHttpSession.DEFAULT_SESSION_ID_NAME;
		}
		return name;
	}

	private String getUriPathSegmentParamValue(ServletRequest servletRequest, String paramName) {

		if (!(servletRequest instanceof HttpServletRequest)) {
			return null;
		}
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		String uri = request.getRequestURI();
		if (uri == null) {
			return null;
		}

		int queryStartIndex = uri.indexOf('?');
		if (queryStartIndex >= 0) { //get rid of the query string
			uri = uri.substring(0, queryStartIndex);
		}

		int index = uri.indexOf(';'); //now check for path segment parameters:
		if (index < 0) {
			//no path segment params - return:
			return null;
		}

		//there are path segment params, let's get the last one that may exist:

		final String TOKEN = paramName + "=";

		uri = uri.substring(index + 1); //uri now contains only the path segment params

		//we only care about the last JSESSIONID param:
		index = uri.lastIndexOf(TOKEN);
		if (index < 0) {
			//no segment param:
			return null;
		}

		uri = uri.substring(index + TOKEN.length());

		index = uri.indexOf(';'); //strip off any remaining segment params:
		if (index >= 0) {
			uri = uri.substring(0, index);
		}

		return uri; //what remains is the value
	}
}
