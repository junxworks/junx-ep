/*
 ***************************************************************************************
 * All rights Reserved, Designed By www.cqhyrc.com.cn
 * @Title:  ShiroRedisCache.java   
 * @Package com.yrxd.commons.web.security.authentication.shiro   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2019-1-16 16:57:51   
 * @version V1.0 
 * @Copyright: 2019 重庆华宇集团. All rights reserved. 
 * 注意：本内容仅限于公司内部使用，禁止外泄以及用于其他的商业目
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.auth;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;

/**
 * {类的详细说明}.
 *
 * @param <K> the key type
 * @param <V> the value type
 * @ClassName:  ShiroRedisCache
 * @author: 王兴
 * @date:   2019-1-16 16:57:51
 * @since:  v1.0
 */
public class EPShiroRedisCache<K, V> implements Cache<K, V> {

	/** 常量 REDIS_SHIRO_CACHE. */
	private static final String REDIS_SHIRO_CACHE = "shiro-cache";

	/** cache key prefix. */
	private String cacheKeyPrefix;

	/** redis. */
	private RedisOper redis;

	/** glob expire. */
	private long expire;

	/**
	 * 构造一个新的 shiro redis cache 对象.
	 *
	 * @param name the name
	 * @param redis the redis
	 */
	public EPShiroRedisCache(String name, RedisOper redis) {
		this.cacheKeyPrefix = REDIS_SHIRO_CACHE + "-" + name + ":";
		this.redis = redis;
	}

	/* (non-Javadoc)
	 * @see org.apache.shiro.cache.Cache#get(java.lang.Object)
	 */
	@Override
	public V get(K key) throws CacheException {
		return (V) redis.get(getCacheKey(key), expire);
	}

	/* (non-Javadoc)
	 * @see org.apache.shiro.cache.Cache#put(java.lang.Object, java.lang.Object)
	 */
	@Override
	public V put(K key, V value) throws CacheException {
		V old = get(key);
		redis.set(getCacheKey(key), value, expire);
		return old;
	}

	/* (non-Javadoc)
	 * @see org.apache.shiro.cache.Cache#remove(java.lang.Object)
	 */
	@Override
	public V remove(K key) throws CacheException {
		V old = get(key);
		redis.delete(getCacheKey(key));
		return old;
	}

	/* (non-Javadoc)
	 * @see org.apache.shiro.cache.Cache#clear()
	 */
	@Override
	public void clear() throws CacheException {
		redis.delete((Set<K>) keys());
	}

	/* (non-Javadoc)
	 * @see org.apache.shiro.cache.Cache#size()
	 */
	@Override
	public int size() {
		return keys().size();
	}

	/* (non-Javadoc)
	 * @see org.apache.shiro.cache.Cache#keys()
	 */
	@Override
	public Set<K> keys() {
		return (Set<K>) redis.keys(String.valueOf(getCacheKey("*")));
	}

	/* (non-Javadoc)
	 * @see org.apache.shiro.cache.Cache#values()
	 */
	@Override
	public Collection<V> values() {
		Set<K> keys = keys();
		List<V> list = (List<V>) redis.multiGet((Collection<Object>) keys);
		return list;
	}

	/**
	 * 返回 cache key 属性.
	 *
	 * @param k the k
	 * @return cache key 属性
	 */
	private K getCacheKey(Object k) {
		return (K) (this.cacheKeyPrefix + k);
	}

	public long getExpire() {
		return expire;
	}

	public void setExpire(long expire) {
		this.expire = expire;
	}

}
