/*
 ***************************************************************************************
 * All rights Reserved, Designed By www.cqhyrc.com.cn
 * @Title:  DataSource.java   
 * @Package io.github.junxworks.ep.core.ds.annotation   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2019-1-2 9:19:10   
 * @version V1.0 
 * @Copyright: 2019 重庆华宇集团. All rights reserved. 
 * 注意：本内容仅限于公司内部使用，禁止外泄以及用于其他的商业目
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.core.ds.annotation;

import java.lang.annotation.*;

import org.springframework.core.annotation.AliasFor;

/**
 * 多数据源注解  Data Source
 *
 * @ClassName:  DS
 * @author: 王兴
 * @date:   2019-1-2 9:19:10
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
