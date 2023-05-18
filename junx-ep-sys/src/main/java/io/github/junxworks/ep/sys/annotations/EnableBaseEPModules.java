/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  EnableBaseEPModules.java   
 * @Package io.github.junxworks.ep.sys.annotations   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2021-1-24 15:35:41   
 * @version V1.0 
 * @Copyright: 2021 Junxworks. All rights reserved. 
 * 注意：
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.sys.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

import io.github.junxworks.ep.sys.config.BaseModuleConfiguration;
import io.github.junxworks.ep.sys.config.EpWebConfig;

/**
 * 开启EP系统支撑基础功能模块
 *
 * @ClassName:  EnableBaseEPModules
 * @author: Michael
 * @date:   2020-7-19 12:17:47
 * @since:  v1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({ BaseModuleConfiguration.class, EpWebConfig.class })
public @interface EnableBaseEPModules {

}
