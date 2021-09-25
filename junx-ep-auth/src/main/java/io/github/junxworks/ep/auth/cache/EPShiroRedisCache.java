/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  EPShiroRedisCache.java   
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

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * {类的详细说明}.
 *
 * @param <K> the key type
 * @param <V> the value type
 * @ClassName:  EPShiroRedisCache
 * @author: Michael
 * @date:   2020-7-19 12:18:41
 * @since:  v1.0
 */
public class EPShiroRedisCache<K, V> extends EPBaseCache<K, V> {

	/** redis. */
	private RedisTemplate<Object, Object> redis;

	/**
	 * 构造一个新的 EP shiro redis cache 对象.
	 *
	 * @param name the name
	 * @param redis the redis
	 */
	public EPShiroRedisCache(String name, RedisTemplate<Object, Object> redis) {
		this.cacheKeyPrefix = name + "-";
		this.redis = redis;
	}

	/**
	 * Gets the.
	 *
	 * @param key the key
	 * @return the v
	 * @throws CacheException the cache exception
	 */
	/* (non-Javadoc)
	 * @see org.apache.shiro.cache.Cache#get(java.lang.Object)
	 */
	@Override
	public V get(K key) throws CacheException {
		Object value = redis.opsForValue().get(getCacheKey(key));
		if (expire != NOT_EXPIRE) {
			redis.expire(key.toString(), expire, TimeUnit.MILLISECONDS);
		}
		return value == null ? null : (V) value;
	}

	/**
	 * Put.
	 *
	 * @param key the key
	 * @param value the value
	 * @return the v
	 * @throws CacheException the cache exception
	 */
	/* (non-Javadoc)
	 * @see org.apache.shiro.cache.Cache#put(java.lang.Object, java.lang.Object)
	 */
	@Override
	public V put(K key, V value) throws CacheException {
		V old = get(key);
		String k = getCacheKey(key).toString();
		redis.opsForValue().set(k, value);
		if (expire != NOT_EXPIRE) {
			redis.expire(k, expire, TimeUnit.MILLISECONDS);
		}
		return old;
	}

	/**
	 * Removes the.
	 *
	 * @param key the key
	 * @return the v
	 * @throws CacheException the cache exception
	 */
	/* (non-Javadoc)
	 * @see org.apache.shiro.cache.Cache#remove(java.lang.Object)
	 */
	@Override
	public V remove(K key) throws CacheException {
		V old = get(key);
		redis.delete(getCacheKey(key).toString());
		return old;
	}

	/**
	 * Clear.
	 *
	 * @throws CacheException the cache exception
	 */
	/* (non-Javadoc)
	 * @see org.apache.shiro.cache.Cache#clear()
	 */
	@Override
	public void clear() throws CacheException {
		redis.delete(keys());
	}

	/**
	 * Size.
	 *
	 * @return the int
	 */
	/* (non-Javadoc)
	 * @see org.apache.shiro.cache.Cache#size()
	 */
	@Override
	public int size() {
		return keys().size();
	}

	/**
	 * Keys.
	 *
	 * @return the sets the
	 */
	/* (non-Javadoc)
	 * @see org.apache.shiro.cache.Cache#keys()
	 */
	@Override
	public Set<K> keys() {
		return (Set<K>)redis.keys(getCacheKey("*"));
	}

	/**
	 * Values.
	 *
	 * @return the collection
	 */
	/* (non-Javadoc)
	 * @see org.apache.shiro.cache.Cache#values()
	 */
	@Override
	public Collection<V> values() {
		return (List<V>) redis.opsForValue().multiGet((Set<Object>)keys());
	}
}
