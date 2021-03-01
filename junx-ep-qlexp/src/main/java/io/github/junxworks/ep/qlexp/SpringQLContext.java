/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  SpringQLContext.java   
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

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.ApplicationContext;

import com.google.common.collect.Maps;
import com.ql.util.express.IExpressContext;

/**
 * {类的详细说明}.
 *
 * @ClassName:  SpringQLContext
 * @author: Michael
 * @date:   2021-3-1 21:18:48
 * @since:  v1.0
 */
public class SpringQLContext extends HashMap<String, Object> implements IExpressContext<String, Object> {

	/** 常量 serialVersionUID. */
	private static final long serialVersionUID = 2598389830210276604L;

	/** context. */
	private ApplicationContext context;

	/** params. */
	private Map<String, Object> params;

	/** inner params. */
	private Map<String, Object> innerParams = Maps.newHashMap();

	/**
	 * 构造一个新的 spring QL context 对象.
	 */
	public SpringQLContext() {
		this(null);
	}

	/**
	 * 构造一个新的 spring QL context 对象.
	 *
	 * @param aContext the a context
	 */
	//构造函数，传入context和 ApplicationContext
	public SpringQLContext(ApplicationContext aContext) {
		super(Maps.newHashMap());
		this.context = aContext;
		initInnerParams();
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	/**
	 * Gets the.
	 *
	 * @param name the name
	 * @return the object
	 */
	public Object get(Object name) {
		Object result = null;
		if (params != null) {
			result = params.get(name);
		}
		if (result != null) {
			return result;
		}
		result = innerParams.get(name);
		if (result != null) {
			return result;
		}
		result = super.get(name);
		try {
			if (result == null && this.context != null && this.context.containsBean((String) name)) {
				// 如果在Spring容器中包含bean，则返回String的Bean
				result = this.context.getBean((String) name);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return result;
	}

	/**
	 * Put.
	 *
	 * @param name the name
	 * @param object the object
	 * @return the object
	 */
	/* (non-Javadoc)
	 * @see java.util.HashMap#put(java.lang.Object, java.lang.Object)
	 */
	public Object put(String name, Object object) {
		if (params != null) {
			return params.put(name, object);
		}
		return super.put(name, object);
	}

	public Map<String, Object> getInnerParams() {
		return innerParams;
	}

	/**
	 * Inits the inner params.
	 */
	private void initInnerParams() {
		innerParams.put("现在", new Date());
	}

}
