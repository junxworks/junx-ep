/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  EPShiroConfig.java   
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
package io.github.junxworks.ep.auth;

import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import io.github.junxworks.ep.auth.simple.SimpleAccount;

// TODO: Auto-generated Javadoc
/**
 * EP的Shiro配置.
 *
 * @ClassName:  EPShiroConfig
 * @author: Michael
 * @date:   2020-7-19 12:18:41
 * @since:  v1.0
 */
@ConfigurationProperties(prefix = "junx.ep.auth.shiro")
public class EPShiroConfig {

	/** The header token name. */
	private String headerTokenName = "_ht_";

	/** global session timeout. */
	private long globalSessionTimeout = 30 * 60 * 1000;

	/** login url. */
	private String loginUrl = "/ep/sys/login";

	/**  登录失败次数限制，等于这个次数的时候帐号就会被锁定. */
	private int loginFailThreshold = 5;

	/** 认证是否缓存，开启后登录首先从缓存匹配，默认true. */
	private boolean authenticationCachingEnabled = true;

	/** 鉴权是否缓存，默认true. */
	private boolean authorizationCachingEnabled = true;

	/** filters. */
	private Map<String, String> filters = Maps.newLinkedHashMap();

	private List<SimpleAccount> simpleAccounts = Lists.newArrayList();

	public String getHeaderTokenName() {
		return headerTokenName;
	}

	public void setHeaderTokenName(String headerTokenName) {
		this.headerTokenName = headerTokenName;
	}

	public boolean isAuthenticationCachingEnabled() {
		return authenticationCachingEnabled;
	}

	public void setAuthenticationCachingEnabled(boolean authenticationCachingEnabled) {
		this.authenticationCachingEnabled = authenticationCachingEnabled;
	}

	public boolean isAuthorizationCachingEnabled() {
		return authorizationCachingEnabled;
	}

	public void setAuthorizationCachingEnabled(boolean authorizationCachingEnabled) {
		this.authorizationCachingEnabled = authorizationCachingEnabled;
	}

	/**
	 * Gets the login url.
	 *
	 * @return the login url
	 */
	public String getLoginUrl() {
		return loginUrl;
	}

	/**
	 * Sets the login url.
	 *
	 * @param loginUrl the new login url
	 */
	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	/**
	 * Gets the filters.
	 *
	 * @return the filters
	 */
	public Map<String, String> getFilters() {
		return filters;
	}

	/**
	 * Sets the filters.
	 *
	 * @param filters the filters
	 */
	public void setFilters(Map<String, String> filters) {
		this.filters = filters;
	}

	/**
	 * Gets the global session timeout.
	 *
	 * @return the global session timeout
	 */
	public long getGlobalSessionTimeout() {
		return globalSessionTimeout;
	}

	/**
	 * Sets the global session timeout.
	 *
	 * @param globalSessionTimeout the new global session timeout
	 */
	public void setGlobalSessionTimeout(long globalSessionTimeout) {
		this.globalSessionTimeout = globalSessionTimeout;
	}

	/**
	 * Gets the login fail threshold.
	 *
	 * @return the login fail threshold
	 */
	public int getLoginFailThreshold() {
		return loginFailThreshold;
	}

	/**
	 * Sets the login fail threshold.
	 *
	 * @param loginFailThreshold the new login fail threshold
	 */
	public void setLoginFailThreshold(int loginFailThreshold) {
		this.loginFailThreshold = loginFailThreshold;
	}

	public List<SimpleAccount> getSimpleAccounts() {
		return simpleAccounts;
	}

	public void setSimpleAccounts(List<SimpleAccount> simpleAccounts) {
		this.simpleAccounts = simpleAccounts;
	}

}
