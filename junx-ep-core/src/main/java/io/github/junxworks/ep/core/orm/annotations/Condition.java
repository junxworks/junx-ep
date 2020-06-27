/*
 ***************************************************************************************
 * All rights Reserved, Designed By www.cqhyrc.com.cn
 * @Title:  Condition.java   
 * @Package io.github.junxworks.ep.core.annotations   
 * @Description: (用一句话描述该文件做什么)   
 * @author: AOC
 * @date:   2019-9-2 10:48:13   
 * @version V1.0 
 * @Copyright: 2019 重庆华宇集团. All rights reserved. 
 * 注意：本内容仅限于公司内部使用，禁止外泄以及用于其他的商业目
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
 * {类的详细说明}.
 *
 * @ClassName:  Condition
 * @author: 王兴
 * @date:   2019-9-2 10:48:13
 * @since:  v1.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Condition {

	/**
	 * 数据库字段名字.
	 * 默认java field本身名字
	 *
	 * @return the string
	 */
	String field();

	/**
	 * 比较方式
	 * {@link io.github.junxworks.ep.core.orm.CompareOperators}.
	 * 默认 =
	 * @return the string
	 */
	String compare() default CompareOperators.EQUAL;

	/**
	 * 直接写表达式.如果有表达式，那么以表达式为主
	 * 默认无
	 * @return the string
	 */
	String expression() default "";
}
