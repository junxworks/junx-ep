/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  CheckResult.java   
 * @Package io.github.junxworks.ep.qlexp   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2021-3-1 21:18:48   
 * @version V1.0 
 * @Copyright: 2021 Junxworks. All rights reserved. 
 * 注意：
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.qlexp;

import io.github.junxworks.junx.core.util.StringUtils;

/**
 * {类的详细说明}.
 *
 * @ClassName:  CheckResult
 * @author: Michael
 * @date:   2021-3-1 21:18:48
 * @since:  v1.0
 */
public class CheckResult {
	
	/** exception. */
	private Exception exception;

	/** exception cause. */
	private String exceptionCause;

	/** message. */
	private String message;

	/** pass. */
	private boolean pass = true;

	public Exception getException() {
		return exception;
	}

	public String getExceptionCause() {
		return exceptionCause;
	}

	public void setExceptionCause(String exceptionCause) {
		this.exceptionCause = exceptionCause;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}

	public String getMessage() {
		return StringUtils.isNull(message) ? exceptionCause : message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isPass() {
		return pass;
	}

	public void setPass(boolean pass) {
		this.pass = pass;
	}
}
