/*
 ***************************************************************************************
 * All rights Reserved, Designed By www.higinet.com.cn
 * @Title:  DataSourceAspect.java   
 * @Package cn.com.higinet.common.web.datasource.aspect   
 * @Description: (用一句话描述该文件做什么)   
 * @author: 王兴
 * @date:   2018-1-26 14:03:10   
 * @version V1.0 
 * @Copyright: 2018 北京宏基恒信科技有限责任公司. All rights reserved. 
 * 注意：本内容仅限于公司内部使用，禁止外泄以及用于其他的商业目
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
	 * 设置线程绑定数据源.
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
