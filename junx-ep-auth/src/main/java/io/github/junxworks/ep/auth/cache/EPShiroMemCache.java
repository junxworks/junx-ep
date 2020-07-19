/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  EPShiroMemCache.java   
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
import java.util.Map;
import java.util.Set;

import org.apache.shiro.cache.CacheException;

import com.google.common.collect.Maps;

/**
 * {类的详细说明}.
 *
 * @param <K> the key type
 * @param <V> the value type
 * @ClassName:  EPShiroMemCache
 * @author: Michael
 * @date:   2020-7-19 12:18:41
 * @since:  v1.0
 */
public class EPShiroMemCache<K, V> extends EPBaseCache<K, V> {

	/** mem cache. */
	private Map<K, V> memCache = Maps.newConcurrentMap();

	/**
	 * Clear.
	 *
	 * @throws CacheException the cache exception
	 */
	@Override
	public void clear() throws CacheException {
		memCache.clear();
	}

	/**
	 * Gets the.
	 *
	 * @param key the key
	 * @return the v
	 * @throws CacheException the cache exception
	 */
	@Override
	public V get(K key) throws CacheException {
		if (key == null) {
			return null;
		}
		return memCache.get(key);
	}

	/**
	 * Keys.
	 *
	 * @return the sets the
	 */
	@Override
	public Set<K> keys() {
		return memCache.keySet();
	}

	/**
	 * Put.
	 *
	 * @param key the key
	 * @param value the value
	 * @return the v
	 * @throws CacheException the cache exception
	 */
	@Override
	public V put(K key, V value) throws CacheException {
		return memCache.put(key, value);
	}

	/**
	 * Removes the.
	 *
	 * @param key the key
	 * @return the v
	 * @throws CacheException the cache exception
	 */
	@Override
	public V remove(K key) throws CacheException {
		return memCache.remove(key);
	}

	/**
	 * Size.
	 *
	 * @return the int
	 */
	@Override
	public int size() {
		return memCache.size();
	}

	/**
	 * Values.
	 *
	 * @return the collection
	 */
	@Override
	public Collection<V> values() {
		return memCache.values();
	}

}
