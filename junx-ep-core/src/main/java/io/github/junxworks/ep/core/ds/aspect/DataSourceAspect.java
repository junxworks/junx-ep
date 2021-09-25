/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  DataSourceAspect.java   
 * @Package io.github.junxworks.ep.core.ds.aspect   
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
package io.github.junxworks.ep.core.ds.aspect;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;

import io.github.junxworks.ep.core.ds.DynamicDataSource;
import io.github.junxworks.ep.core.ds.annotation.DS;

import io.github.junxworks.junx.core.util.StringUtils;

/**
 * 数据源AOP类
 *
 * @ClassName:  DataSourceAspect
 * @author: Michael
 * @date:   2020-7-19 12:18:36
 * @since:  v1.0
 */
@Aspect
public class DataSourceAspect implements Ordered {

	/** logger. */
	protected Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * Data source point cut.
	 */
	@Pointcut("@annotation(io.github.junxworks.ep.core.ds.annotation.DS)")
	public void dataSourcePointCut() {

	}

	/**
	 * 切面设置数据上下文.
	 *
	 * @param point the point
	 * @return the object
	 * @throws Throwable the throwable
	 */
	@Around("dataSourcePointCut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		MethodSignature signature = (MethodSignature) point.getSignature();
		Method method = signature.getMethod();

		DS ds = method.getAnnotation(DS.class);
		if (ds == null) {
			String name = DynamicDataSource.usePrimarySource();
			logger.debug("set datasource " + name);
		} else {
			String dsName = ds.name();
			if (StringUtils.isNull(dsName)) {
				dsName = ds.value();
			}
			DynamicDataSource.setDataSource(dsName);
			logger.debug("set datasource " + dsName);
		}

		try {
			return point.proceed();
		} finally {
			DynamicDataSource.clearDataSource();
			logger.debug("clean datasource");
		}
	}

	@Override
	public int getOrder() {
		return 1;
	}
}
