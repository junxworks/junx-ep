/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  ScheduleRunnable.java   
 * @Package io.github.junxworks.ep.scheduler   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2021-1-24 17:50:26   
 * @version V1.0 
 * @Copyright: 2021 Junxworks. All rights reserved. 
 * 注意：
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.scheduler;

import java.lang.reflect.Method;

import org.springframework.util.ReflectionUtils;

import io.github.junxworks.ep.core.utils.SpringContextUtils;
import io.github.junxworks.junx.core.exception.BaseRuntimeException;
import io.github.junxworks.junx.core.util.StringUtils;

/**
 * {类的详细说明}.
 *
 * @ClassName:  ScheduleRunnable
 * @author: Michael
 * @date:   2020-7-19 12:18:05
 * @since:  v1.0
 */
public class ScheduleRunnable implements Runnable {
	
	/** target. */
	private Object target;
	
	/** method. */
	private Method method;
	
	/** params. */
	private String params;
	
	/**
	 * 构造一个新的 schedule runnable 对象.
	 *
	 * @param beanName the bean name
	 * @param methodName the method name
	 * @param params the params
	 * @throws NoSuchMethodException the no such method exception
	 * @throws SecurityException the security exception
	 */
	public ScheduleRunnable(String beanName, String methodName, String params) throws NoSuchMethodException, SecurityException {
		this.target = SpringContextUtils.getBean(beanName);
		this.params = params;
		
		if(StringUtils.isNotBlank(params)){
			this.method = target.getClass().getDeclaredMethod(methodName, String.class);
		}else{
			this.method = target.getClass().getDeclaredMethod(methodName);
		}
	}

	/**
	 * Run.
	 */
	@Override
	public void run() {
		try {
			ReflectionUtils.makeAccessible(method);
			if(StringUtils.isNotBlank(params)){
				method.invoke(target, params);
			}else{
				method.invoke(target);
			}
		}catch (Exception e) {
			throw new BaseRuntimeException("执行定时任务失败", e);
		}
	}

}
