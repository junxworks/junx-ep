/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  OpLogAspect.java   
 * @Package io.github.junxworks.ep.sys.aspect   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2021-1-24 15:35:41   
 * @version V1.0 
 * @Copyright: 2021 Junxworks. All rights reserved. 
 * 注意：
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.sys.aspect;

import java.lang.reflect.Method;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;

import io.github.junxworks.ep.core.utils.IPUtils;
import io.github.junxworks.ep.sys.annotations.OpLog;
import io.github.junxworks.ep.sys.entity.SOpLog;
import io.github.junxworks.ep.sys.mapper.OpLogMapper;
import io.github.junxworks.junx.core.executor.StandardThreadExecutor;
import io.github.junxworks.junx.core.util.ExceptionUtils;
import io.github.junxworks.junx.core.util.ObjectUtils;

/**
 * {类的详细说明}.
 *
 * @ClassName:  OpLogAspect
 * @author: Michael
 * @date:   2020-7-19 12:17:47
 * @since:  v1.0
 */
@Aspect
public class OpLogAspect {

	private static final int MAX_URL_LEN = 500;

	private static final int MAX_DATA_LEN = 2000;

	/** 常量 THREAD_PREFIX. */
	private static final String THREAD_PREFIX = "op-log-";

	/** 常量 MAX_WORKERS. */
	private static final int MAX_WORKERS = 10;

	/** op log mapper. */
	@Autowired
	private OpLogMapper opLogMapper;

	/** executor. */
	private StandardThreadExecutor executor;

	/**
	 * Log point cut.
	 */
	@Pointcut("@annotation(io.github.junxworks.ep.sys.annotations.OpLog)")
	public void logPointCut() {

	}

	/**
	 * Inits the.
	 */
	@PostConstruct
	public void init() {
		executor = new StandardThreadExecutor();
		executor.setNamePrefix(THREAD_PREFIX);
		executor.setMaxThreads(MAX_WORKERS);
		executor.setMinSpareThreads(1);
		executor.setDescription("op-log-executor");
		executor.start();
	}

	/**
	 * Around.
	 *
	 * @param point the point
	 * @return the object
	 * @throws Throwable the throwable
	 */
	@Around("logPointCut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		long beginTime = System.currentTimeMillis();
		Object result = null;
		//执行方法
		try {
			result = point.proceed();
		} finally {
			//执行时长(毫秒)
			long time = System.currentTimeMillis() - beginTime;
			//保存日志
			saveSysLog(point, time);
		}
		return result;
	}

	/**
	 * Save sys log.
	 *
	 * @param joinPoint the join point
	 * @param time the time
	 */
	private void saveSysLog(ProceedingJoinPoint joinPoint, long time) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		final SOpLog log = new SOpLog();
		OpLog syslog = method.getAnnotation(OpLog.class);
		if (syslog != null) {
			//注解上的描述
			log.setOperation(syslog.value());
		}

		//请求的方法名
		String className = joinPoint.getTarget().getClass().getName();
		String methodName = signature.getName();
		log.setMethod(className + "." + methodName + "()");

		//请求的参数
		Object[] args = joinPoint.getArgs();
		try {
			String params = JSON.toJSONString(args[0]);
			if (params.length() > MAX_DATA_LEN) {
				params = params.substring(0, MAX_DATA_LEN);
			}
			log.setData(params);
		} catch (Exception e) {
			log.setData(ExceptionUtils.getCauseMessage(e));
		}
		try {
			//用户名
			if (SecurityUtils.getSubject() != null) {
				Object id = ObjectUtils.mirror().on(SecurityUtils.getSubject().getPrincipal()).get().field("id");
				if (id != null) {
					log.setUserId(Long.valueOf(id.toString()));
				}
			}
		} catch (Exception e) {
		}
		log.setCost(time);
		log.setCreateDate(new Date());
		ServletRequestAttributes ra = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
		if (ra != null) {
			HttpServletRequest request = ra.getRequest();
			if (request != null) {
				log.setIp(IPUtils.getIpAddr(request));
				String url = request.getRequestURI();
				if (url.length() > MAX_URL_LEN) {
					url = url.substring(0, MAX_URL_LEN);
				}
				log.setUrl(url);
			}
		}
		executor.submit(new Runnable() {
			@Override
			public void run() {
				opLogMapper.insertWithNull(log);
			}
		});
	}
}
