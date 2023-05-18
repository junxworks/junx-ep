package io.github.junxworks.ep.auth.simple;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;

import io.github.junxworks.ep.auth.DefaultToken;
import io.github.junxworks.ep.auth.RamConstants;
import io.github.junxworks.ep.core.Result;
import io.github.junxworks.ep.core.utils.HttpUtils;
import io.github.junxworks.junx.core.util.StringUtils;
// TODO: Auto-generated Javadoc
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * The Class EPSimpleAuthenticationFilter.
 */
public class EPSimpleAuthenticationFilter extends AuthenticatingFilter {
	
	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(EPSimpleAuthenticationFilter.class);

	/** The path matcher. */
	private PathMatcher pathMatcher = new AntPathMatcher();

	/** The ram header key name. */
	private String ramHeaderKeyName = RamConstants.RAM_HEADER_ACCESSKEY;

	/** The ram header secret name. */
	private String ramHeaderSecretName = RamConstants.RAM_HEADER_ACCESSSECRET;

	/** The simple accounts. */
	private List<SimpleAccount> simpleAccounts = Lists.newArrayList();

	/** The accounts. */
	private Map<String, SimpleAccount> accounts;

	/**
	 * Gets the ram header key name.
	 *
	 * @return the ram header key name
	 */
	public String getRamHeaderKeyName() {
		return ramHeaderKeyName;
	}

	/**
	 * Sets the ram header key name.
	 *
	 * @param ramHeaderKeyName the new ram header key name
	 */
	public void setRamHeaderKeyName(String ramHeaderKeyName) {
		this.ramHeaderKeyName = ramHeaderKeyName;
	}

	/**
	 * Gets the ram header secret name.
	 *
	 * @return the ram header secret name
	 */
	public String getRamHeaderSecretName() {
		return ramHeaderSecretName;
	}

	/**
	 * Sets the ram header secret name.
	 *
	 * @param ramHeaderSecretName the new ram header secret name
	 */
	public void setRamHeaderSecretName(String ramHeaderSecretName) {
		this.ramHeaderSecretName = ramHeaderSecretName;
	}

	/**
	 * Gets the simple accounts.
	 *
	 * @return the simple accounts
	 */
	public List<SimpleAccount> getSimpleAccounts() {
		return simpleAccounts;
	}

	/**
	 * Sets the simple accounts.
	 *
	 * @param simpleAccounts the new simple accounts
	 */
	public void setSimpleAccounts(List<SimpleAccount> simpleAccounts) {
		this.simpleAccounts = simpleAccounts;
		if (simpleAccounts != null) {
			this.accounts = simpleAccounts.stream().collect(Collectors.toMap(SimpleAccount::getEpRamKey, v -> v));
		}
	}

	/**
	 * On access denied.
	 *
	 * @param request the request
	 * @param response the response
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	/* (non-Javadoc)
	 * @see org.apache.shiro.web.filter.AccessControlFilter#onAccessDenied(javax.servlet.ServletRequest, javax.servlet.ServletResponse)
	 */
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		Result res = Result.unauthenticated();
		if (isRamRequest((HttpServletRequest) request) && accounts != null && !accounts.isEmpty()) {
			HttpServletRequest req = (HttpServletRequest) request;
			String key = HttpUtils.getRequestParam(ramHeaderKeyName, req);
			String secret = HttpUtils.getRequestParam(ramHeaderSecretName, req);
			String uri = req.getRequestURI();
			String path = req.getContextPath();
			if (accounts.containsKey(key)) {
				SimpleAccount acc = accounts.get(key);
				if (secret.equals(acc.getEpRamSecret())) {
					if (acc.getAuthorizes() != null && !acc.getAuthorizes().isEmpty()) {
						for (String auth : acc.getAuthorizes()) {
							if (antMatch(uri, path + auth)) {
								return true;
							}
						}
					}
				}
			}
		}
		if (log.isTraceEnabled()) {
			log.trace("Attempting to access a path which requires authentication.  Forwarding to the " + "Authentication url [" + getLoginUrl() + "]");
		}
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		httpResponse.setContentType("application/json;charset=utf-8");
		String json = JSON.toJSONString(res);
		httpResponse.getWriter().print(json);
		return false;
	}

	/**
	 * 是否是ram帐号控制的请求.
	 *
	 * @param req the req
	 * @return 返回布尔值 ram request
	 */
	protected boolean isRamRequest(HttpServletRequest req) {
		String key = HttpUtils.getRequestParam(ramHeaderKeyName, req);
		String secret = HttpUtils.getRequestParam(ramHeaderSecretName, req);
		return StringUtils.notNull(key) && StringUtils.notNull(secret);
	}

	/**
	 * Ant match.
	 *
	 * @param uri the uri
	 * @param antExps the ant exps
	 * @return true, if successful
	 */
	private boolean antMatch(String uri, String antExps) {
		if (StringUtils.isNull(antExps)) {
			return false;
		}
		String[] exps = antExps.split(";");
		for (String exp : exps) {
			if (pathMatcher.match(exp, uri)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Creates the token.
	 *
	 * @param request the request
	 * @param response the response
	 * @return the authentication token
	 * @throws Exception the exception
	 */
	@Override
	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
		return new DefaultToken();
	}
}
