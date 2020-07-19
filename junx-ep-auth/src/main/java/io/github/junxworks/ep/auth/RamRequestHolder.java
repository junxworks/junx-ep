/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  RamRequestHolder.java   
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

/**
 * {类的详细说明}.
 *
 * @ClassName:  RamRequestHolder
 * @author: Michael
 * @date:   2020-7-19 12:18:41
 * @since:  v1.0
 */
public class RamRequestHolder {
	
	/** 常量 requests. */
	private static final ThreadLocal<RamRequest> requests = new ThreadLocal<>();

	/**
	 * Sets the.
	 *
	 * @param request the request
	 */
	public static void set(RamRequest request) {
		requests.set(request);
	}

	/**
	 * Gets the.
	 *
	 * @return the ram request
	 */
	public static RamRequest get() {
		return requests.get();
	}

	/**
	 * Removes the.
	 */
	public static void remove() {
		requests.remove();
	}
}
