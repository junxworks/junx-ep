/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  EnableAccessFilter.java   
 * @Package io.github.junxworks.ep.core.security.access   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2021-6-19 15:33:22   
 * @version V1.0 
 * @Copyright: 2021 Junxworks. All rights reserved. 
 * 注意：
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.core.security.access;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;


/**
 * 开启访问日志
 *
 * @ClassName:  EnableAccessFilter
 * @author: Michael
 * @date:   2021-6-19 15:33:22
 * @since:  v1.0
 * @see {@link io.github.junxworks.ep.core.security.access.EnableAccessLog}
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({AccessConfiguration.class})
@Deprecated
public @interface EnableAccessFilter {

}
