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
package io.github.junxworks.ep.modules.aspect;

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
import io.github.junxworks.ep.modules.annotations.OpLog;
import io.github.junxworks.ep.modules.modules.sys.entity.SOpLog;
import io.github.junxworks.ep.modules.modules.sys.mapper.OpLogMapper;

import io.github.junxworks.junx.core.executor.StandardThreadExecutor;
import io.github.junxworks.junx.core.util.ExceptionUtils;
import io.github.junxworks.junx.core.util.ObjectUtils;

@Aspect
public class OpLogAspect {

	private static final String THREAD_PREFIX = "op-log-";

	private static final int MAX_WORKERS = 10;

	@Autowired
	private OpLogMapper opLogMapper;

	private StandardThreadExecutor executor;

	@Pointcut("@annotation(io.github.junxworks.ep.modules.annotations.OpLog)")
	public void logPointCut() {

	}

	@PostConstruct
	public void init() {
		executor = new StandardThreadExecutor();
		executor.setNamePrefix(THREAD_PREFIX);
		executor.setMaxThreads(MAX_WORKERS);
		executor.setMinSpareThreads(1);
		executor.setDescription("op-log-executor");
		executor.start();
	}

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
				log.setUrl(request.getRequestURI());
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
