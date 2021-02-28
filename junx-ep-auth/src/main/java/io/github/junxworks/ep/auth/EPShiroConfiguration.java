/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  EPShiroConfiguration.java   
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

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import com.google.common.collect.Maps;

import io.github.junxworks.ep.auth.cache.EPShiroRedisCacheManager;

/**
 * {类的详细说明}.
 *
 * @ClassName:  EPShiroConfiguration
 * @author: Michael
 * @date:   2020-7-19 12:18:41
 * @since:  v1.0
 */
@Configuration
@ConditionalOnWebApplication
@EnableConfigurationProperties({ EPShiroConfig.class })
@Order
public class EPShiroConfiguration {
	
	/** 常量 AUTHENTICATION_CACHE. */
	private static final String AUTHENTICATION_CACHE = "ep-authen";

	/** 常量 AUTHORIZATION_CACHE. */
	private static final String AUTHORIZATION_CACHE = "ep-author";

	/**
	 * Shiro service.
	 *
	 * @return the EP shiro service
	 */
	@Bean
	public EPShiroService shiroService() {
		return new EPShiroService();
	}

	/**
	 * Shiro filter.
	 *
	 * @param securityManager the security manager
	 * @param shiroConfig the shiro config
	 * @return the shiro filter factory bean
	 */
	@Bean
	public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager, EPShiroConfig shiroConfig) {
		EPTokenAuthenticatingFilter authenticatingFilter = new EPTokenAuthenticatingFilter();
		authenticatingFilter.setLoginUrl(shiroConfig.getLoginUrl());
		authenticatingFilter.setConfig(shiroConfig);
		authenticatingFilter.setRamEnabled(shiroConfig.isRamEnabled());
		authenticatingFilter.setRamAuthCenterAddr(shiroConfig.getRamAuthCenterAddr());
		authenticatingFilter.setRamAuthPath(shiroConfig.getRamAuthPath());
		authenticatingFilter.setRamHeaderKeyName(shiroConfig.getRamHeaderKeyName());
		authenticatingFilter.setRamHeaderSecretName(shiroConfig.getRamHeaderSecretName());
		ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
		shiroFilter.setSecurityManager(securityManager);
		Map<String, Filter> filters = new HashMap<>();
		filters.put("ep", authenticatingFilter);
		shiroFilter.setFilters(filters);
		if (!shiroConfig.getFilters().isEmpty()) {
			shiroFilter.setFilterChainDefinitionMap(Maps.newLinkedHashMap(shiroConfig.getFilters()));
		} else {
			Map<String, String> filterMap = new LinkedHashMap<>();
			filterMap.put("/**/*.css", "anon");
			filterMap.put("/**/*.js", "anon");
			filterMap.put("/**/*.html", "anon");
			filterMap.put("/**/*.jpg", "anon");
			filterMap.put("/**/*.ico", "anon");
			filterMap.put("/fonts/**", "anon");
			filterMap.put("/plugins/**", "anon");
			filterMap.put("/**/login", "anon");
			filterMap.put("/**/system-name", "anon");
			filterMap.put("/**", "ep");
			shiroFilter.setFilterChainDefinitionMap(filterMap);
		}
		return shiroFilter;
	}

	/**
	 * Shiro realm.
	 *
	 * @return the EP shiro realm
	 */
	@Bean
	public EPShiroRealm shiroRealm() {
		return new EPShiroRealm();
	}

	/**
	 * Cache manager.
	 *
	 * @param redisTemplate the redis template
	 * @param config the config
	 * @return the cache manager
	 */
	@Bean
	@ConditionalOnMissingBean(CacheManager.class)
	public CacheManager cacheManager() {
		return new EPShiroRedisCacheManager();
	}

	/**
	 * Session manager.
	 *
	 * @param cacheManager the cache manager
	 * @param config the config
	 * @return the session manager
	 */
	@Bean
	public SessionManager sessionManager(CacheManager cacheManager, EPShiroConfig config) {
		DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
		sessionManager.setSessionValidationSchedulerEnabled(true); //定期检查session，如果是集中缓存，那么会从cachemanager中获取所有活动session过来检查，检查周期默认1小时1次
		sessionManager.setSessionIdCookieEnabled(true);
		sessionManager.setDeleteInvalidSessions(true); //删除过期session
		sessionManager.setCacheManager(cacheManager); //设置缓存管理器
		sessionManager.setSessionDAO(new EnterpriseCacheSessionDAO()); //设置sessiondao
		sessionManager.setGlobalSessionTimeout(config.getGlobalSessionTimeout());
		return sessionManager;
	}

	/**
	 * Authentication cache.
	 *
	 * @param cacheManager the cache manager
	 * @return the cache
	 */
	@Bean
	public Cache<Object, AuthenticationInfo> authenticationCache(CacheManager cacheManager) {
		return cacheManager.getCache(AUTHENTICATION_CACHE);
	}

	/**
	 * Authorization cache.
	 *
	 * @param cacheManager the cache manager
	 * @return the cache
	 */
	@Bean
	public Cache<Object, AuthorizationInfo> authorizationCache(CacheManager cacheManager) {
		return cacheManager.getCache(AUTHORIZATION_CACHE);
	}

	/**
	 * Security manager.
	 *
	 * @param shiroRealm the shiro realm
	 * @param sessionManager the session manager
	 * @param cacheManager the cache manager
	 * @param authenticationCache the authentication cache
	 * @param authorizationCache the authorization cache
	 * @return the security manager
	 */
	@Bean
	public SecurityManager securityManager(AuthorizingRealm shiroRealm, SessionManager sessionManager, CacheManager cacheManager, Cache<Object, AuthenticationInfo> authenticationCache, Cache<Object, AuthorizationInfo> authorizationCache) {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(shiroRealm);
		securityManager.setSessionManager(sessionManager);
		securityManager.setCacheManager(cacheManager);
		shiroRealm.setAuthenticationCachingEnabled(false);//认证
		shiroRealm.setAuthenticationCache(authenticationCache);
		shiroRealm.setAuthorizationCachingEnabled(false);//权限
		shiroRealm.setAuthorizationCache(authorizationCache);
		SecurityUtils.setSecurityManager(securityManager);
		return securityManager;
	}

	/**
	 * Lifecycle bean post processor.
	 *
	 * @return the lifecycle bean post processor
	 */
	@Bean
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	/**
	 * Default advisor auto proxy creator.
	 *
	 * @return the default advisor auto proxy creator
	 */
	@Bean
	public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator proxyCreator = new DefaultAdvisorAutoProxyCreator();
		proxyCreator.setProxyTargetClass(true);
		return proxyCreator;
	}

	/**
	 * Authorization attribute source advisor.
	 *
	 * @param securityManager the security manager
	 * @return the authorization attribute source advisor
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
		advisor.setSecurityManager(securityManager);
		return advisor;
	}

}
