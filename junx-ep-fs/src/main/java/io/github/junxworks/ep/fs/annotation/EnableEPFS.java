/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  OSSRepository.java   
 * @Package io.github.junxworks.ep.fs.annotation   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2020-8-2 13:58:55   
 * @version V1.0 
 * @Copyright: 2020 Junxworks. All rights reserved. 
 * 注意：
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.fs.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

import io.github.junxworks.ep.fs.config.FSConfiguration;

/**
 * 快速开启EP文件服务器服务
 *
 * @ClassName:  EnableEPFS
 * @author: Michael
 * @date:   2019-1-6 14:54:17
 * @since:  v1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({FSConfiguration.class })
@Deprecated
public @interface EnableEPFS {
}
