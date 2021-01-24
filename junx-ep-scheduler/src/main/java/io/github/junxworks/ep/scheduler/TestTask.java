/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  TestTask.java   
 * @Package io.github.junxworks.ep.scheduler   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2021-1-24 17:50:25   
 * @version V1.0 
 * @Copyright: 2021 Junxworks. All rights reserved. 
 * 注意：
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * {类的详细说明}.
 *
 * @ClassName:  TestTask
 * @author: Michael
 * @date:   2020-7-19 12:18:05
 * @since:  v1.0
 */
@Component("testTask")
public class TestTask {
	
	/** logger. */
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * Test.
	 *
	 * @param params the params
	 */
	public void test(String params){
		logger.info("我是带参数的test方法，正在被执行，参数为：" + params);
		
		System.out.println("定时任务测试");
		
	}
	
	
	/**
	 * Test 2.
	 */
	public void test2(){
		logger.info("我是不带参数的test2方法，正在被执行");
	}
}
