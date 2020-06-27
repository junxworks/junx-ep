/*
 ***************************************************************************************
 * All rights Reserved, Designed By www.cqhyrc.com.cn
 * @Title:  ShiroCacheEntity.java   
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

import java.io.Serializable;

/**
 * {类的详细说明}.
 *
 * @param <K> the key type
 * @param <V> the value type
 * @ClassName:  ShiroCacheEntity
 * @author: 王兴
 * @date:   2019-1-16 16:57:50
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
