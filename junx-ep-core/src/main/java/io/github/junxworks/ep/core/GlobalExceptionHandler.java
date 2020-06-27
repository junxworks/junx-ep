package io.github.junxworks.ep.core;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
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

@RestControllerAdvice
public class GlobalExceptionHandler {
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 处理自定义异常
	 */
	@ExceptionHandler(BaseRuntimeException.class)
	public Result handleRRException(BaseRuntimeException e) {
		return handleException(e);
	}

	/**
	 * 业务提示
	 */
	@ExceptionHandler(BusinessException.class)
	public Result handleBusinessException(BusinessException e) {
		String msg = ExceptionUtils.getCauseMessage(e);
		logger.warn(msg);
		return Result.warn(msg);
	}

	@ExceptionHandler(DuplicateKeyException.class)
	public Result handleDuplicateKeyException(DuplicateKeyException e) {
		logger.error(e.getMessage(), e);
		return Result.error("Duplicate key");
	}

	@ExceptionHandler(UnknownTokenException.class)
	public Result handleUnknownTokenException(UnknownTokenException e) {
		logger.warn(e.getMessage(), e);
		return Result.error("token unavailable");
	}

	@ExceptionHandler(AuthorizationException.class)
	public Result handleAuthorizationException(AuthorizationException e) {
		logger.warn(e.getMessage(), e);
		return Result.error("Insufficient authority");
	}

	@ExceptionHandler(AuthenticationException.class)
	public Result handleAuthorizationException(AuthenticationException e) {
		logger.error(e.getMessage(), e);
		String message = ExceptionUtils.getCauseMessage(e);
		return Result.error(StringUtils.isNull(message) ? "Authentication failed" : message);
	}

	@ExceptionHandler(IncorrectCredentialsException.class)
	public Result handleAuthorizationException(IncorrectCredentialsException e) {
		logger.error(e.getMessage(), e);
		return Result.error("Incorrect username or password");
	}

	@ExceptionHandler(UnknownAccountException.class)
	public Result handleUnknownAccountException(UnknownAccountException e) {
		logger.error(e.getMessage(), e);
		return Result.error("Incorrect username or password");
	}

	@ExceptionHandler(LockedAccountException.class)
	public Result handleLockedAccountException(LockedAccountException e) {
		logger.warn(e.getMessage(), e);
		return Result.error("Account locked");
	}

	@ExceptionHandler(BindException.class)
	public Result bindException(BindException e) {
		BindingResult res = e.getBindingResult();
		return Result.error(bindingResult2String(res));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Result methodArgumentNotValidException(MethodArgumentNotValidException e) {
		BindingResult res = e.getBindingResult();
		return Result.error(bindingResult2String(res));
	}

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

	@ExceptionHandler(NullPointerException.class)
	public Result handleNullPointerException(NullPointerException e) {
		logger.error(e.getMessage(), e);
		return Result.error("Null pointer exception");
	}

	@ExceptionHandler(Exception.class)
	public Result handleException(Exception e) {
		logger.error(e.getMessage(), e);
		String exception = ExceptionUtils.getCauseMessage(e);
		return Result.error(StringUtils.isNull(exception) ? "System error." : exception);
	}

}
