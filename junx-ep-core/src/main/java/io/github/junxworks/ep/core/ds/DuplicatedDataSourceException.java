/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  DuplicatedDataSourceException.java   
 * @Package io.github.junxworks.ep.core.ds   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2020-7-19 12:18:37   
 * @version V1.0 
 * @Copyright: 2020 Junxworks. All rights reserved. 
 * 注意：
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.core.ds;

import io.github.junxworks.junx.core.exception.BaseRuntimeException;

/**
 * 重复的数据源异常
 *
 * @ClassName:  DuplicatedDataSourceException
 * @author: Michael
 * @date:   2020-7-19 12:18:37
 * @since:  v1.0
 */
public class DuplicatedDataSourceException extends BaseRuntimeException {

	/** 常量 serialVersionUID. */
	private static final long serialVersionUID = 5362519994576363996L;

	/**
	 * 构造一个新的 duplicated data source exception 对象.
	 */
	public DuplicatedDataSourceException() {
		super("");
	}

	/**
	 * 构造一个新的 duplicated data source exception 对象.
	 *
	 * @param msg the msg
	 */
	public DuplicatedDataSourceException(String msg) {
		super(msg);
	}

	/**
	 * 构造一个新的 duplicated data source exception 对象.
	 *
	 * @param ex the ex
	 */
	public DuplicatedDataSourceException(Throwable ex) {
		super(ex);
	}

	/**
	 * 构造一个新的 duplicated data source exception 对象.
	 *
	 * @param msg the msg
	 * @param ex the ex
	 */
	public DuplicatedDataSourceException(String msg, Throwable ex) {
		super(msg, ex);
	}

	/**
	 * 构造一个新的 duplicated data source exception 对象.
	 *
	 * @param msg the msg
	 * @param args the args
	 */
	public DuplicatedDataSourceException(String msg, Object... args) {
		super(msg, args);
	}

	/**
	 * 构造一个新的 duplicated data source exception 对象.
	 *
	 * @param msg the msg
	 * @param e the e
	 * @param args the args
	 */
	public DuplicatedDataSourceException(String msg, Exception e, Object... args) {
		super(msg, e, args);
	}
}
