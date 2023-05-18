/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  Column.java   
 * @Package io.github.junxworks.ep.core.orm.annotations   
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
package io.github.junxworks.ep.core.orm.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数据库列注解，含有此注解的属性将会按此注解进行解析，如果不含，则采用默认逻辑解析。
 *
 * @ClassName:  Column
 * @author: Michael
 * @date:   2020-7-19 12:18:36
 * @since:  v1.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
	
	/**
	 * Name.
	 *
	 * @return the string
	 */
	String name();

	/**
	 * Type.
	 *
	 * @return the string
	 */
	String type();

	/**
	 * Length.
	 *
	 * @return the string
	 */
	String length();

	/**
	 * Nullable.
	 *
	 * @return the string
	 */
	String nullable();

	/**
	 * Comment.
	 *
	 * @return the string
	 */
	String comment();
}
