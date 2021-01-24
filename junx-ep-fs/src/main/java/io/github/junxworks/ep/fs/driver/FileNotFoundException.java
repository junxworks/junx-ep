/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  FileNotFoundException.java   
 * @Package io.github.junxworks.ep.fs.driver   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2020-8-2 13:58:55   
 * @version V1.0 
 * @Copyright: 2020 Junxworks. All rights reserved. 
 * 注意：
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.fs.driver;

import io.github.junxworks.junx.core.exception.BaseRuntimeException;

public class FileNotFoundException extends BaseRuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5362519994576363996L;

	/**
	 * @see BaseRuntimeException#BaseRuntimeException()
	 */
	public FileNotFoundException() {
		super("");
	}

	/**
	 * @see BaseRuntimeException#BaseRuntimeException(String)
	 */
	public FileNotFoundException(String msg) {
		super(msg);
	}

	/**
	 * @see BaseRuntimeException#BaseRuntimeException(Throwable)
	 */
	public FileNotFoundException(Throwable ex) {
		super(ex);
	}

	/**
	 * @see BaseRuntimeException#BaseRuntimeException(String,Throwable)
	 */
	public FileNotFoundException(String msg, Throwable ex) {
		super(msg, ex);
	}

	/**
	 * @see BaseRuntimeException#BaseRuntimeException(String,Object...)
	 */
	public FileNotFoundException(String msg, Object... args) {
		super(msg, args);
	}

	/**
	 *  @see BaseRuntimeException#BaseRuntimeException(String,Exception,Object...)
	 */
	public FileNotFoundException(String msg, Exception e, Object... args) {
		super(msg, e, args);
	}
}
