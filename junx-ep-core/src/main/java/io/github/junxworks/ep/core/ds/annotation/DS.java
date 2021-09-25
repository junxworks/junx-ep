/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  DS.java   
 * @Package io.github.junxworks.ep.core.ds.annotation   
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
package io.github.junxworks.ep.core.ds.annotation;

import java.lang.annotation.*;

import org.springframework.core.annotation.AliasFor;

/**
 * DataSouce数据源，指定当前线程上下文中的数据源用哪个
 * 注意：目前不支持嵌套数据源
 *
 * @ClassName:  DS
 * @author: Michael
 * @date:   2020-7-19 12:18:36
 * @since:  v1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DS {
	
	/**
	 * Value.
	 *
	 * @return the string
	 */
	@AliasFor("name")
	String value() default "";

	/**
	 * Name.
	 *
	 * @return the string
	 */
	@AliasFor("value")
	String name() default "";
}
