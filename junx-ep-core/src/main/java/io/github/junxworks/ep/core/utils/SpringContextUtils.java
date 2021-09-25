/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  SpringContextUtils.java   
 * @Package io.github.junxworks.ep.core.utils   
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
package io.github.junxworks.ep.core.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * {类的详细说明}.
 *
 * @ClassName:  SpringContextUtils
 * @author: Michael
 * @date:   2020-7-19 12:18:37
 * @since:  v1.0
 */
@Component("JunxEpSpringContextUtils")
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SpringContextUtils implements ApplicationContextAware {
	
	/** application context. */
	public static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringContextUtils.applicationContext = applicationContext;
	}

	/**
	 * 返回 bean 属性.
	 *
	 * @param name the name
	 * @return bean 属性
	 */
	public static Object getBean(String name) {
		return applicationContext.getBean(name);
	}

	/**
	 * 返回 bean 属性.
	 *
	 * @param <T> the generic type
	 * @param requiredType the required type
	 * @return bean 属性
	 */
	public static <T> T getBean(Class<T> requiredType) {
		return applicationContext.getBean(requiredType);
	}

	/**
	 * 返回 bean 属性.
	 *
	 * @param <T> the generic type
	 * @param name the name
	 * @param requiredType the required type
	 * @return bean 属性
	 */
	public static <T> T getBean(String name, Class<T> requiredType) {
		return applicationContext.getBean(name, requiredType);
	}

	/**
	 * Contains bean.
	 *
	 * @param name the name
	 * @return true, if successful
	 */
	public static boolean containsBean(String name) {
		return applicationContext.containsBean(name);
	}

	/**
	 * 返回布尔值 singleton.
	 *
	 * @param name the name
	 * @return 返回布尔值 singleton
	 */
	public static boolean isSingleton(String name) {
		return applicationContext.isSingleton(name);
	}

	/**
	 * Gets the type.
	 *
	 * @param name the name
	 * @return the type
	 * @Title: 
	 */
	public static Class<? extends Object> getType(String name) {
		return applicationContext.getType(name);
	}

}