/*
 ***************************************************************************************
 * All rights Reserved, Designed By www.cqhyrc.com.cn
 * @Title:  ShiroRedisCacheManager.java   
 * @Package com.yrxd.commons.web.security.authentication.shiro   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2019-1-16 16:57:50   
 * @version V1.0 
 * @Copyright: 2019 重庆华宇集团. All rights reserved. 
 * 注意：本内容仅限于公司内部使用，禁止外泄以及用于其他的商业目
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.auth;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

import com.yrxd.commons.web.RedisOper;

/**
 * {类的详细说明}.
 *
 * @ClassName:  ShiroRedisCacheManager
 * @author: 王兴
 * @date:   2019-1-16 16:57:50
 * @since:  v1.0
 */
public class EPShiroRedisCacheManager implements CacheManager {

	/** redis. */
	private RedisOper redis;

	private EPShiroConfig config;

	/* (non-Javadoc)
	 * @see org.apache.shiro.cache.CacheManager#getCache(java.lang.String)
	 */
	@Override
	public <K, V> Cache<K, V> getCache(String name) throws CacheException {
		EPShiroRedisCache<K, V> cache = new EPShiroRedisCache<K, V>(name, redis);
		cache.setExpire(config.getGlobalSessionTimeout());
		return cache;
	}

	public RedisOper getRedis() {
		return redis;
	}

	public void setRedis(RedisOper redis) {
		this.redis = redis;
	}

	public EPShiroConfig getConfig() {
		return config;
	}

	public void setConfig(EPShiroConfig config) {
		this.config = config;
	}

}
