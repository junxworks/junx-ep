/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  EPShiroRedisCacheManager.java   
 * @Package io.github.junxworks.ep.auth.cache   
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
package io.github.junxworks.ep.auth.cache;

import javax.annotation.Resource;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import io.github.junxworks.ep.auth.EPShiroConfig;

/**
 * {类的详细说明}.
 *
 * @ClassName:  EPShiroRedisCacheManager
 * @author: Michael
 * @date:   2020-7-19 12:18:41
 * @since:  v1.0
 */
public class EPShiroRedisCacheManager implements CacheManager {

	/** redis. */
	@Resource
	private RedisTemplate<Object, Object> redis;

	/** config. */
	@Autowired
	private EPShiroConfig config;

	/**
	 * 返回 cache 属性.
	 *
	 * @param <K> the key type
	 * @param <V> the value type
	 * @param name the name
	 * @return cache 属性
	 * @throws CacheException the cache exception
	 */
	/* (non-Javadoc)
	 * @see org.apache.shiro.cache.CacheManager#getCache(java.lang.String)
	 */
	@Override
	public <K, V> Cache<K, V> getCache(String name) throws CacheException {
		EPShiroRedisCache<K, V> cache = new EPShiroRedisCache<K, V>(name, redis);
		cache.setExpire(config.getGlobalSessionTimeout());
		return cache;
	}

	public EPShiroConfig getConfig() {
		return config;
	}

	public void setConfig(EPShiroConfig config) {
		this.config = config;
	}

}
