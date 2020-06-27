/*
 ***************************************************************************************
 * All rights Reserved, Designed By www.cqhyrc.com.cn
 * @Title:  DuplicatedDataSourceException.java   
 * @Package io.github.junxworks.ep.core.ds   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2019-1-9 15:57:00   
 * @version V1.0 
 * @Copyright: 2019 重庆华宇集团. All rights reserved. 
 * 注意：本内容仅限于公司内部使用，禁止外泄以及用于其他的商业目
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.core.exception;

import io.github.junxworks.junx.core.exception.BaseRuntimeException;

/**
 * 业务异常
 *
 * @author: 王兴
 * @date:   2017-5-7 17:32:52
 * @since:  v4.3
 */
public class BusinessException extends BaseRuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5362519994576363996L;

	/**
	 * @see BaseRuntimeException#BaseRuntimeException()
	 */
	public BusinessException() {
		super("");
	}

	/**
	 * @see BaseRuntimeException#BaseRuntimeException(String)
	 */
	public BusinessException(String msg) {
		super(msg);
	}

	/**
	 * @see BaseRuntimeException#BaseRuntimeException(Throwable)
	 */
	public BusinessException(Throwable ex) {
		super(ex);
	}

	/**
	 * @see BaseRuntimeException#BaseRuntimeException(String,Throwable)
	 */
	public BusinessException(String msg, Throwable ex) {
		super(msg, ex);
	}

	/**
	 * @see BaseRuntimeException#BaseRuntimeException(String,Object...)
	 */
	public BusinessException(String msg, Object... args) {
		super(msg, args);
	}

	/**
	 *  @see BaseRuntimeException#BaseRuntimeException(String,Exception,Object...)
	 */
	public BusinessException(String msg, Exception e, Object... args) {
		super(msg, e, args);
	}
}
