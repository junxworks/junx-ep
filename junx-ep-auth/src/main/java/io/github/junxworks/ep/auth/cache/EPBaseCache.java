/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  EPBaseCache.java   
 * @Package io.github.junxworks.ep.auth.cache   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2020-7-19 12:18:42   
 * @version V1.0 
 * @Copyright: 2020 Junxworks. All rights reserved. 
 * 注意：
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.auth.cache;

import org.apache.shiro.cache.Cache;

/**
 * {类的详细说明}.
 *
 * @param <K> the key type
 * @param <V> the value type
 * @ClassName:  EPBaseCache
 * @author: Michael
 * @date:   2020-7-19 12:18:42
 * @since:  v1.0
 */
public abstract class EPBaseCache<K, V> implements Cache<K, V> {

	/** cache key prefix. */
	protected String cacheKeyPrefix;

	/** expire. */
	protected long expire;

	/** 常量 NOT_EXPIRE. */
	protected final static long NOT_EXPIRE = -1;

	public long getExpire() {
		return expire;
	}

	public void setExpire(long expire) {
		this.expire = expire;
	}

	/**
	 * 返回 cache key 属性.
	 *
	 * @param k the k
	 * @return cache key 属性
	 */
	@SuppressWarnings("unchecked")
	protected K getCacheKey(Object k) {
		return (K) (this.cacheKeyPrefix + k.toString());
	}
}
