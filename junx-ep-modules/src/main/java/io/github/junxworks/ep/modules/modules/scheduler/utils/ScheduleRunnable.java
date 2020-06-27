package io.github.junxworks.ep.modules.modules.scheduler.utils;

import java.lang.reflect.Method;

import org.springframework.util.ReflectionUtils;

import io.github.junxworks.ep.core.utils.SpringContextUtils;
import io.github.junxworks.junx.core.exception.BaseRuntimeException;
import io.github.junxworks.junx.core.util.StringUtils;

/**
 * 执行定时任务
 * 
 * @author wangxing
 * @email 219710904@163.com
 * @date 2016年11月30日 下午12:49:33
 */
public class ScheduleRunnable implements Runnable {
	private Object target;
	private Method method;
	private String params;
	
	public ScheduleRunnable(String beanName, String methodName, String params) throws NoSuchMethodException, SecurityException {
		this.target = SpringContextUtils.getBean(beanName);
		this.params = params;
		
		if(StringUtils.isNotBlank(params)){
			this.method = target.getClass().getDeclaredMethod(methodName, String.class);
		}else{
			this.method = target.getClass().getDeclaredMethod(methodName);
		}
	}

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
