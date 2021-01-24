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

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.google.common.collect.Maps;

/**
 * {类的详细说明}.
 *
 * @ClassName:  EPShiroConfig
 * @author: Michael
 * @date:   2020-7-19 12:18:41
 * @since:  v1.0
 */
@ConfigurationProperties(prefix = "junx.ep.auth.shiro")
public class EPShiroConfig {

	/** global session timeout. */
	private long globalSessionTimeout = 30 * 60 * 1000;

	/** login url. */
	private String loginUrl = "/ep/sys/login";

	/** filters. */
	private Map<String, String> filters = Maps.newLinkedHashMap();

	/** ram enabled. */
	private boolean ramEnabled = false;

	/** ram header key name. */
	private String ramHeaderKeyName = RamConstants.RAM_HEADER_ACCESSKEY;

	/** ram header secret name. */
	private String ramHeaderSecretName = RamConstants.RAM_HEADER_ACCESSSECRET;

	/** ram auth center addr. */
	private String ramAuthCenterAddr;

	/** ram auth path. */
	private String ramAuthPath;

	/** ram key. */
	private String ramKey;

	/** ram secret. */
	private String ramSecret;

	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public Map<String, String> getFilters() {
		return filters;
	}

	public void setFilters(Map<String, String> filters) {
		this.filters = filters;
	}

	public long getGlobalSessionTimeout() {
		return globalSessionTimeout;
	}

	public void setGlobalSessionTimeout(long globalSessionTimeout) {
		this.globalSessionTimeout = globalSessionTimeout;
	}

	public boolean isRamEnabled() {
		return ramEnabled;
	}

	public void setRamEnabled(boolean ramEnabled) {
		this.ramEnabled = ramEnabled;
	}

	public String getRamAuthCenterAddr() {
		return ramAuthCenterAddr;
	}

	public void setRamAuthCenterAddr(String ramAuthCenterAddr) {
		this.ramAuthCenterAddr = ramAuthCenterAddr;
	}

	public String getRamAuthPath() {
		return ramAuthPath;
	}

	public void setRamAuthPath(String ramAuthPath) {
		this.ramAuthPath = ramAuthPath;
	}

	public String getRamHeaderKeyName() {
		return ramHeaderKeyName;
	}

	public void setRamHeaderKeyName(String ramHeaderKeyName) {
		this.ramHeaderKeyName = ramHeaderKeyName;
	}

	public String getRamHeaderSecretName() {
		return ramHeaderSecretName;
	}

	public void setRamHeaderSecretName(String ramHeaderSecretName) {
		this.ramHeaderSecretName = ramHeaderSecretName;
	}

	public String getRamKey() {
		return ramKey;
	}

	public void setRamKey(String ramKey) {
		this.ramKey = ramKey;
	}

	public String getRamSecret() {
		return ramSecret;
	}

	public void setRamSecret(String ramSecret) {
		this.ramSecret = ramSecret;
	}

}
