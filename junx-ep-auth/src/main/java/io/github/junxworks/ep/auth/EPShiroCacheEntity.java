/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  EPShiroCacheEntity.java   
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

import java.io.Serializable;

/**
 * {类的详细说明}.
 *
 * @param <K> the key type
 * @param <V> the value type
 * @ClassName:  EPShiroCacheEntity
 * @author: Michael
 * @date:   2020-7-19 12:18:41
 * @since:  v1.0
 */
public class EPShiroCacheEntity<K,V> implements Serializable {

	/** 常量 serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** key. */
	private K key;
	
	/** value. */
	private V value;
	public K getKey() {
		return key;
	}
	public void setKey(K key) {
		this.key = key;
	}
	public V getValue() {
		return value;
	}
	public void setValue(V value) {
		this.value = value;
	}
	
	
}
