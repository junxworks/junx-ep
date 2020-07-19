/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  EPShiroMemCacheManager.java   
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
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

/**
 * {类的详细说明}.
 *
 * @ClassName:  EPShiroMemCacheManager
 * @author: Michael
 * @date:   2020-7-19 12:18:42
 * @since:  v1.0
 */
public class EPShiroMemCacheManager implements CacheManager {

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
		EPShiroMemCache<K, V> cache = new EPShiroMemCache<K, V>();
		return cache;
	}

}
