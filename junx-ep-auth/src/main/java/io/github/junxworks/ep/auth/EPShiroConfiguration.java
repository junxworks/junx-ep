/*
 ***************************************************************************************
 * All rights Reserved, Designed By www.higinet.com.cn
 * @Title:  ShiroConfig.java   
 * @Package cn.com.higinet.platform.config   
 * @Description: (用一句话描述该文件做什么)   
 * @author: 王兴
 * @date:   2018-2-7 20:32:17   
 * @version V1.0 
 * @Copyright: 2018 北京宏基恒信科技有限责任公司. All rights reserved. 
 * 注意：本内容仅限于公司内部使用，禁止外泄以及用于其他的商业目
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
import org.springframework.data.redis.core.RedisTemplate;

import com.google.common.collect.Maps;
import com.yrxd.commons.web.RedisOper;

/**
 * shiro的注入配置类
 *
 * @ClassName:  ShiroConfiguration
 * @author: 王兴
 * @date:   2019-1-16 16:57:51
 * @since:  v1.0
 */
@Configuration
@ConditionalOnWebApplication
@EnableConfigurationProperties({ EPShiroConfig.class })
public class EPShiroConfiguration {
	private static final String AUTHENTICATION_CACHE = "ep-authen";

	private static final String AUTHORIZATION_CACHE = "ep-author";

	@Bean
	public EPShiroService shiroService() {
		return new EPShiroService();
	}

	/**
	 * 必须注入
	 *
	 * @param redisTemplate the redis template
	 * @return the redis oper
	 */
	@Bean
	@ConditionalOnMissingBean(RedisOper.class)
	public RedisOper redisOper(RedisTemplate<Object, Object> redisTemplate) {
		RedisOper redisOper = new RedisOper();
		redisOper.setRedisTemplate(redisTemplate);
		redisOper.setValueOperations(redisTemplate.opsForValue());
		return redisOper;
	}

	/**
	 * Shiro filter.
	 *
	 * @param securityManager the security manager
	 * @param shiroProerties the shiro proerties
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
			filterMap.put("/**", "ep");
			shiroFilter.setFilterChainDefinitionMap(filterMap);
		}
		return shiroFilter;
	}

	@Bean
	public EPShiroRealm shiroRealm() {
		return new EPShiroRealm();
	}

	@Bean
	public CacheManager cacheManager(RedisOper redis, EPShiroConfig config) {
		EPShiroRedisCacheManager cacheManager = new EPShiroRedisCacheManager();
		cacheManager.setRedis(redis);
		cacheManager.setConfig(config);
		return cacheManager;
	}

	/**
	 * Session manager.
	 *
	 * @param shiroCacheProvider the shiro cache provider
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
		sessionManager.setGlobalSessionTimeout(config.getGlobalSessionTimeout()); //半小时
		return sessionManager;
	}

	@Bean
	public Cache<Object, AuthenticationInfo> authenticationCache(EPShiroRedisCacheManager cacheManager) {
		return cacheManager.getCache(AUTHENTICATION_CACHE);
	}

	@Bean
	public Cache<Object, AuthorizationInfo> authorizationCache(EPShiroRedisCacheManager cacheManager) {
		return cacheManager.getCache(AUTHORIZATION_CACHE);
	}

	/**
	 * Security manager.
	 *
	 * @param oAuth2Realm the o auth 2 realm
	 * @param sessionManager the session manager
	 * @return the security manager
	 */
	@Bean
	public SecurityManager securityManager(AuthorizingRealm shiroRealm, SessionManager sessionManager, CacheManager cacheManager, Cache<Object, AuthenticationInfo> authenticationCache, Cache<Object, AuthorizationInfo> authorizationCache) {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(shiroRealm);
		securityManager.setSessionManager(sessionManager);
		securityManager.setCacheManager(cacheManager);
		shiroRealm.setAuthenticationCachingEnabled(true);//认证
		shiroRealm.setAuthenticationCache(authenticationCache);
		shiroRealm.setAuthorizationCachingEnabled(true);//权限
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
