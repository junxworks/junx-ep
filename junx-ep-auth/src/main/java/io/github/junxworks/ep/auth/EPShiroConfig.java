/*
 ***************************************************************************************
 * All rights Reserved, Designed By www.higinet.com.cn
 * @Title:  ShiroProperties.java   
 * @Package cn.com.higinet.platform.modules.shiro   
 * @Description: (用一句话描述该文件做什么)   
 * @author: 王兴
 * @date:   2018-3-22 14:01:35   
 * @version V1.0 
 * @Copyright: 2018 北京宏基恒信科技有限责任公司. All rights reserved. 
 * 注意：本内容仅限于公司内部使用，禁止外泄以及用于其他的商业目
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
 * shiro的配置
 *
 * @ClassName:  ShiroProperties
 * @author: 王兴
 * @date:   2018-3-22 14:01:35
 * @since:  v4.4
 */
@ConfigurationProperties(prefix = "ep.auth.shiro")
public class EPShiroConfig {

	/** 全局session过期时间配置，默认半小时. */
	private long globalSessionTimeout = 30 * 60 * 1000;

	private String loginUrl = "/ep/sys/login";

	/** 权限过滤. */
	private Map<String, String> filters = Maps.newLinkedHashMap();

	/** 启用ram控制，启用后会优先判断ram参数进行认证和鉴权. */
	private boolean ramEnabled = false;

	private String ramHeaderKeyName = RamConstants.RAM_HEADER_ACCESSKEY;

	private String ramHeaderSecretName = RamConstants.RAM_HEADER_ACCESSSECRET;

	/** RAM认证中心地址，后期采用集群注册的方式，前期先写死一个ip地址,如：localhost:8890. */
	private String ramAuthCenterAddr;

	/** RAM认证URL请求路径. */
	private String ramAuthPath;

	/** ram认证的 key. */
	private String ramKey;

	/** ram认证的 secret. */
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
