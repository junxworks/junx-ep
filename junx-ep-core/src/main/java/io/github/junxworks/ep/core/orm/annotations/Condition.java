/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  Condition.java   
 * @Package io.github.junxworks.ep.core.orm.annotations   
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
package io.github.junxworks.ep.core.orm.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import io.github.junxworks.ep.core.orm.CompareOperators;

/**
 * 条件注解，在解析sql条件的时候，会根据此条件进行解析
 *
 * @ClassName:  Condition
 * @author: Michael
 * @date:   2020-7-19 12:18:37
 * @since:  v1.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Condition {

	/**
	 * Field.
	 *
	 * @return the string
	 */
	String field();

	/**
	 * Compare.
	 *
	 * @return the string
	 */
	String compare() default CompareOperators.EQUAL;

	/**
	 * Expression.
	 *
	 * @return the string
	 */
	String expression() default "";
}
