/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  Result.java   
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

import java.io.Serializable;

import org.springframework.ui.ModelMap;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;

/**
 * {类的详细说明}.
 *
 * @ClassName:  Result
 * @author: Michael
 * @date:   2020-7-19 12:18:36
 * @since:  v1.0
 */
public class Result implements Serializable {

	/** 常量 serialVersionUID. */
	private static final long serialVersionUID = -5206850679177510996L;

	/** code. */
	private int code = Status.OK.getCode();

	/** msg. */
	private String msg = Status.OK.getMessage();

	/** data. */
	private Object data = Maps.newHashMap();

	/** attr. */
	private ModelMap attr = new ModelMap();

	/** ok. */
	private boolean ok = true;

	/**
	 * 构造一个新的 result 对象.
	 */
	public Result() {
		code = Status.OK.getCode();
	}

	/**
	 * 构造一个新的 result 对象.
	 *
	 * @param data the data
	 */
	public Result(Object data) {
		this.code = Status.OK.getCode();
		this.msg = Status.OK.getMessage();
		this.data = data;
	}

	/**
	 * 构造一个新的 result 对象.
	 *
	 * @param code the code
	 * @param message the message
	 */
	public Result(Integer code, String message) {
		this.code = code;
		this.msg = message;
		ok = Status.OK.getCode() == code;
	}

	/**
	 * Ok.
	 *
	 * @return the result
	 */
	public static Result ok() {
		return new Result();
	}

	/**
	 * Unauthorized.
	 *
	 * @return the result
	 */
	public static Result unauthorized() {
		Result res = new Result();
		res.setCode(Status.UNAUTHORIZED.code);
		res.setMsg(Status.UNAUTHORIZED.message);
		return res;
	}

	/**
	 * Unauthenticated.
	 *
	 * @return the result
	 */
	public static Result unauthenticated() {
		Result res = new Result();
		res.setCode(Status.UNAUTHENTICATED.code);
		res.setMsg(Status.UNAUTHENTICATED.message);
		return res;
	}

	/**
	 * 返回 data 属性.
	 *
	 * @param <T> the generic type
	 * @param clazz the clazz
	 * @return data 属性
	 */
	@SuppressWarnings("unchecked")
	public <T> T getData(Class<T> clazz) {
		return (T) data;
	}

	/**
	 * Ok.
	 *
	 * @param body the body
	 * @return the result
	 */
	public static Result ok(Object body) {
		return new Result(body);
	}

	/**
	 * Error.
	 *
	 * @param message the message
	 * @return the result
	 */
	public static Result error(String message) {
		return new Result(Status.ERROR.code, message);
	}

	/**
	 * Warn.
	 *
	 * @param message the message
	 * @return the result
	 */
	public static Result warn(String message) {
		return new Result(Status.WARN.code, message);
	}

	/**
	 * Unauthenticated.
	 *
	 * @param message the message
	 * @return the result
	 */
	public static Result unauthenticated(String message) {
		return new Result(Status.UNAUTHENTICATED.code, message);
	}

	/**
	 * Unauthorized.
	 *
	 * @param message the message
	 * @return the result
	 */
	public static Result unauthorized(String message) {
		return new Result(Status.UNAUTHORIZED.code, message);
	}

	/**
	 * Adds the attribute.
	 *
	 * @param name the name
	 * @param value the value
	 */
	public void addAttribute(String name, Object value) {
		this.attr.addAttribute(name, value);
	}

	public ModelMap getAttr() {
		return attr;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
		ok = Status.OK.getCode() == code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}


	/**
	 * To json.
	 *
	 * @return the string
	 */
	public String toJson() {
		return JSON.toJSONString(this);
	}

	public boolean isOk() {
		return ok;
	}
	
	/**
	 * {类的详细说明}.
	 *
	 * @ClassName:  Result
	 * @author: Michael
	 * @date:   2020-7-19 12:18:36
	 * @since:  v1.0
	 */
	public static enum Status {

		/** error. */
		ERROR(-1, "error"),
		
		/** ok. */
		OK(0, "success"),
		
		/** warn. */
		WARN(1, "warn"),
		
		/** unauthenticated. */
		UNAUTHENTICATED(-2, "unauthenticated"),
		
		/** unauthorized. */
		UNAUTHORIZED(-3, "unauthorized");

		/** code. */
		private Integer code;

		/** message. */
		private String message;

		/**
		 * 构造一个新的 status 对象.
		 *
		 * @param code the code
		 * @param message the message
		 */
		Status(Integer code, String message) {
			this.code = code;
			this.message = message;
		}

		public Integer getCode() {
			return code;
		}

		public String getMessage() {
			return message;
		}
	}
}
