/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  EnableCSRFFilter.java   
 * @Package io.github.junxworks.ep.core.security.csrf   
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
package io.github.junxworks.ep.core.security.csrf;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

/**
 * {类的详细说明}.
 *
 * @ClassName:  EnableCSRFFilter
 * @author: Michael
 * @date:   2020-7-19 12:18:36
 * @since:  v1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({CSRFFilterConfig.class})
public @interface EnableCSRFFilter {

}
