/*
 ***************************************************************************************
 * All rights Reserved, Designed By www.cqhyrc.com.cn
 * @Title:  Result.java   
 * @Package io.github.junxworks.ep.core   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2019-1-2 10:12:41   
 * @version V1.0 
 * @Copyright: 2019 重庆华宇集团. All rights reserved. 
 * 注意：本内容仅限于公司内部使用，禁止外泄以及用于其他的商业目
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
 * 请求结果模型
 *
 * @ClassName:  Result
 * @author: 王兴
 * @date:   2019-1-2 10:06:56
 * @since:  v1.0
 */
public class Result implements Serializable {

	/** 常量 serialVersionUID. */
	private static final long serialVersionUID = -5206850679177510996L;

	/**状态码. */
	private int code = Status.OK.getCode();

	/** 提示消息. */
	private String msg = Status.OK.getMessage();

	/** 数据，用map占位. */
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

	public static Result unauthorized() {
		Result res = new Result();
		res.setCode(Status.UNAUTHORIZED.code);
		res.setMsg(Status.UNAUTHORIZED.message);
		return res;
	}

	public static Result unauthenticated() {
		Result res = new Result();
		res.setCode(Status.UNAUTHENTICATED.code);
		res.setMsg(Status.UNAUTHENTICATED.message);
		return res;
	}

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
	 * 未认证.
	 *
	 * @param message the message
	 * @return the result
	 */
	public static Result unauthenticated(String message) {
		return new Result(Status.UNAUTHENTICATED.code, message);
	}

	/**
	 * 权限不足.
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
	 * 应答状态
	 *
	 * @ClassName:  Result
	 * @author: 王兴
	 * @date:   2019-1-2 10:12:41
	 * @since:  v1.0
	 */
	public static enum Status {

		/** 系统异常. */
		ERROR(-1, "error"),
		/** 成功. */
		OK(0, "success"),
		/** 业务告警. */
		WARN(1, "warn"),
		/**未认证 */
		UNAUTHENTICATED(-2, "unauthenticated"),
		/**权限不足*/
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
