/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  EnableEPSys.java   
 * @Package io.github.junxworks.ep.sys.annotations   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2021-3-28 17:06:52   
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

import io.github.junxworks.ep.auth.EnableEPShiroProxy;

/**
 * 开启EP基础系统模块，含系统支撑以及EP认证
 *
 * @ClassName:  EnableEPSys
 * @author: Michael
 * @date:   2020-7-19 12:17:47
 * @since:  v1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@EnableBaseEPModules //EP基础系统模块
@EnableEPShiroProxy //开启EP认证
public @interface EnableEPSys {
}
