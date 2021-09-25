/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  GlobalExceptionHandler.java   
 * @Package io.github.junxworks.ep.core   
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
package io.github.junxworks.ep.core;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.github.junxworks.ep.core.exception.BusinessException;
import io.github.junxworks.ep.core.exception.UnknownTokenException;
import io.github.junxworks.junx.core.exception.BaseRuntimeException;
import io.github.junxworks.junx.core.util.ExceptionUtils;
import io.github.junxworks.junx.core.util.StringUtils;

/**
 * {类的详细说明}.
 *
 * @ClassName:  GlobalExceptionHandler
 * @author: Michael
 * @date:   2020-7-19 12:18:36
 * @since:  v1.0
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
	
	/** logger. */
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * Handle RR exception.
	 *
	 * @param e the e
	 * @return the result
	 */
	@ExceptionHandler(BaseRuntimeException.class)
	public Result handleRRException(BaseRuntimeException e) {
		return handleException(e);
	}

	/**
	 * Handle business exception.
	 *
	 * @param e the e
	 * @return the result
	 */
	@ExceptionHandler(BusinessException.class)
	public Result handleBusinessException(BusinessException e) {
		String msg = ExceptionUtils.getCauseMessage(e);
		logger.warn(msg);
		return Result.warn(msg);
	}

	/**
	 * Handle unknown token exception.
	 *
	 * @param e the e
	 * @return the result
	 */
	@ExceptionHandler(UnknownTokenException.class)
	public Result handleUnknownTokenException(UnknownTokenException e) {
		logger.warn(e.getMessage(), e);
		return Result.error("token unavailable");
	}

	/**
	 * Handle authorization exception.
	 *
	 * @param e the e
	 * @return the result
	 */
	@ExceptionHandler(AuthorizationException.class)
	public Result handleAuthorizationException(AuthorizationException e) {
		logger.warn(e.getMessage(), e);
		return Result.error("Insufficient authority");
	}

	/**
	 * Handle authorization exception.
	 *
	 * @param e the e
	 * @return the result
	 */
	@ExceptionHandler(AuthenticationException.class)
	public Result handleAuthorizationException(AuthenticationException e) {
		logger.error(e.getMessage(), e);
		String message = ExceptionUtils.getCauseMessage(e);
		return Result.error(StringUtils.isNull(message) ? "Authentication failed" : message);
	}

	/**
	 * Bind exception.
	 *
	 * @param e the e
	 * @return the result
	 */
	@ExceptionHandler(BindException.class)
	public Result bindException(BindException e) {
		BindingResult res = e.getBindingResult();
		return Result.error(bindingResult2String(res));
	}

	/**
	 * Method argument not valid exception.
	 *
	 * @param e the e
	 * @return the result
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Result methodArgumentNotValidException(MethodArgumentNotValidException e) {
		BindingResult res = e.getBindingResult();
		return Result.error(bindingResult2String(res));
	}

	/**
	 * Binding result 2 string.
	 *
	 * @param res the res
	 * @return the string
	 */
	private String bindingResult2String(BindingResult res) {
		StringBuilder msg = new StringBuilder();
		res.getFieldErrors().forEach(fe -> {
			if (msg.length() > 0) {
				msg.append(",");
			}
			msg.append(fe.getDefaultMessage());
		});
		return msg.toString();
	}

	/**
	 * Handle null pointer exception.
	 *
	 * @param e the e
	 * @return the result
	 */
	@ExceptionHandler(NullPointerException.class)
	public Result handleNullPointerException(NullPointerException e) {
		logger.error(e.getMessage(), e);
		return Result.error("Null pointer exception");
	}

	/**
	 * Handle exception.
	 *
	 * @param e the e
	 * @return the result
	 */
	@ExceptionHandler(Exception.class)
	public Result handleException(Exception e) {
		logger.error(e.getMessage(), e);
		String exception = ExceptionUtils.getCauseMessage(e);
		return Result.error(StringUtils.isNull(exception) ? "System error." : exception);
	}

}
