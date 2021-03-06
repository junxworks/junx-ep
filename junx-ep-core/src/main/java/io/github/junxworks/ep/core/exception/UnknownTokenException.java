/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  UnknownTokenException.java   
 * @Package io.github.junxworks.ep.core.exception   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2020-7-19 12:18:36   
 * @version V1.0 
 * @Copyright: 2020 Junxworks. All rights reserved. 
 * 注意：
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.core.exception;

import io.github.junxworks.junx.core.exception.BaseRuntimeException;

/**
 * {类的详细说明}.
 *
 * @ClassName:  UnknownTokenException
 * @author: Michael
 * @date:   2020-7-19 12:18:36
 * @since:  v1.0
 */
public class UnknownTokenException extends BaseRuntimeException {

	/** 常量 serialVersionUID. */
	private static final long serialVersionUID = 5362519994576363996L;

	/**
	 * 构造一个新的 unknown token exception 对象.
	 */
	public UnknownTokenException() {
		super("");
	}

	/**
	 * 构造一个新的 unknown token exception 对象.
	 *
	 * @param msg the msg
	 */
	public UnknownTokenException(String msg) {
		super(msg);
	}

	/**
	 * 构造一个新的 unknown token exception 对象.
	 *
	 * @param ex the ex
	 */
	public UnknownTokenException(Throwable ex) {
		super(ex);
	}

	/**
	 * 构造一个新的 unknown token exception 对象.
	 *
	 * @param msg the msg
	 * @param ex the ex
	 */
	public UnknownTokenException(String msg, Throwable ex) {
		super(msg, ex);
	}

	/**
	 * 构造一个新的 unknown token exception 对象.
	 *
	 * @param msg the msg
	 * @param args the args
	 */
	public UnknownTokenException(String msg, Object... args) {
		super(msg, args);
	}

	/**
	 * 构造一个新的 unknown token exception 对象.
	 *
	 * @param msg the msg
	 * @param e the e
	 * @param args the args
	 */
	public UnknownTokenException(String msg, Exception e, Object... args) {
		super(msg, e, args);
	}
}
