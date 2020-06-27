/*
 ***************************************************************************************
 * All rights Reserved, Designed By www.cqhyrc.com.cn
 * @Title:  RedisOper.java   
 * @Package io.github.junxworks.ep.core   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2019-1-16 17:10:04   
 * @version V1.0 
 * @Copyright: 2019 重庆华宇集团. All rights reserved. 
 * 注意：本内容仅限于公司内部使用，禁止外泄以及用于其他的商业目
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.core;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

/**
 * Redis的template操作门面类
 *
 * @ClassName:  RedisOper
 * @author: 王兴
 * @date:   2019-1-16 17:10:04
 * @since:  v1.0
 */
public class RedisOper {

	/** redis template. */
	private RedisTemplate<Object, Object> redisTemplate;

	/** value operations. */
	private ValueOperations<Object, Object> valueOperations;

	/**  默认过期时长，单位：秒 */
	public final static long DEFAULT_EXPIRE = 24 * 60 * 60 * 1000;

	/**  不设置过期时长 */
	public final static long NOT_EXPIRE = -1;

	public void setRedisTemplate(RedisTemplate<Object, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	public void setValueOperations(ValueOperations<Object, Object> valueOperations) {
		this.valueOperations = valueOperations;
	}

	/**
	 * Checks for key.
	 *
	 * @param key the key
	 * @return true, if successful
	 */
	public boolean hasKey(Object key) {
		return redisTemplate.hasKey(key);
	}

	/**
	 * Sets the.
	 *
	 * @param key the key
	 * @param value the value
	 * @param expire the expire
	 */
	public void set(Object key, Object value, long expire) {
		//		valueOperations.set(key, toJson(value));
		valueOperations.set(key, value);
		if (expire != NOT_EXPIRE) {
			redisTemplate.expire(key, expire, TimeUnit.MILLISECONDS);
		}
	}

	/**
	 * Sets the.
	 *
	 * @param key the key
	 * @param value the value
	 */
	public void set(Object key, Object value) {
		set(key, value, NOT_EXPIRE);
	}

	/**
	 * Gets the.
	 *
	 * @param <T> the generic type
	 * @param key the key
	 * @param clazz the clazz
	 * @param expire the expire
	 * @return the t
	 */
	public <T> T get(Object key, Class<T> clazz, long expire) {
		Object value = valueOperations.get(key);
		if (expire != NOT_EXPIRE) {
			redisTemplate.expire(key, expire, TimeUnit.MILLISECONDS);
		}
		return value == null ? null : (T) value;
	}

	/**
	 * Gets the.
	 *
	 * @param <T> the generic type
	 * @param key the key
	 * @param clazz the clazz
	 * @return the t
	 */
	public <T> T get(Object key, Class<T> clazz) {
		return get(key, clazz, NOT_EXPIRE);
	}

	/**
	 * Gets the.
	 *
	 * @param key the key
	 * @param expire the expire
	 * @return the object
	 */
	public Object get(Object key, long expire) {
		Object value = valueOperations.get(key);
		if (expire != NOT_EXPIRE) {
			redisTemplate.expire(key, expire, TimeUnit.MILLISECONDS);
		}
		return value;
	}

	/**
	 * Gets the.
	 *
	 * @param key the key
	 * @return the object
	 */
	public Object get(Object key) {
		return get(key, NOT_EXPIRE);
	}

	/**
	 * Multi get.
	 *
	 * @param keys the keys
	 * @return the list
	 */
	public List<Object> multiGet(Collection<Object> keys) {
		return valueOperations.multiGet(keys);
	}

	/**
	 * Delete.
	 *
	 * @param key the key
	 */
	public void delete(Object key) {
		redisTemplate.delete(key);
	}

	/**
	 * Delete.
	 *
	 * @param keys the keys
	 */
	public void delete(Set<Object> keys) {
		redisTemplate.delete(keys);
	}

	/**
	 * Expire.
	 *
	 * @param key the key
	 * @param timeout the timeout
	 */
	public void expire(Object key, long timeout) {
		redisTemplate.expire(key, timeout, TimeUnit.MILLISECONDS);
	}

	/**
	 * Keys.
	 *
	 * @param prefix the prefix
	 * @return the sets the
	 */
	public Set<Object> keys(String prefix) {
		return redisTemplate.keys(prefix);
	}

	public RedisTemplate<Object, Object> getRedisTemplate() {
		return redisTemplate;
	}

	public ValueOperations<Object, Object> getValueOperations() {
		return valueOperations;
	}

}
