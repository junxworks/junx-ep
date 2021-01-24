/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  BaseModuleConfiguration.java   
 * @Package io.github.junxworks.ep.sys.config   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2021-1-24 15:35:42   
 * @version V1.0 
 * @Copyright: 2021 Junxworks. All rights reserved. 
 * 注意：
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.sys.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import io.github.junxworks.ep.sys.aspect.OpLogAspect;
import io.github.junxworks.ep.sys.controller.DataController;
import io.github.junxworks.ep.sys.controller.DictionaryController;
import io.github.junxworks.ep.sys.controller.MenuController;
import io.github.junxworks.ep.sys.controller.OrgController;
import io.github.junxworks.ep.sys.controller.ParamController;
import io.github.junxworks.ep.sys.controller.RoleController;
import io.github.junxworks.ep.sys.controller.SysController;
import io.github.junxworks.ep.sys.controller.SystemLogController;
import io.github.junxworks.ep.sys.controller.UserController;
import io.github.junxworks.ep.sys.service.impl.DataServiceImpl;
import io.github.junxworks.ep.sys.service.impl.DictionaryServiceImpl;
import io.github.junxworks.ep.sys.service.impl.MenuServiceImpl;
import io.github.junxworks.ep.sys.service.impl.OrgServiceImpl;
import io.github.junxworks.ep.sys.service.impl.ParamServiceImpl;
import io.github.junxworks.ep.sys.service.impl.RoleServiceImpl;
import io.github.junxworks.ep.sys.service.impl.SystemLogServiceImpl;
import io.github.junxworks.ep.sys.service.impl.UserServiceImpl;

/**
 * {类的详细说明}.
 *
 * @ClassName:  BaseModuleConfiguration
 * @author: Michael
 * @date:   2020-7-19 12:17:48
 * @since:  v1.0
 */
@Configuration
@EnableConfigurationProperties({ EPConfig.class })
@Import({ OpLogAspect.class, UserController.class, UserServiceImpl.class, MenuController.class, MenuServiceImpl.class, OrgController.class, OrgServiceImpl.class, RoleController.class, RoleServiceImpl.class, SystemLogController.class, SystemLogServiceImpl.class, SysController.class, DictionaryController.class, DictionaryServiceImpl.class, DataController.class, DataServiceImpl.class,
		ParamController.class, ParamServiceImpl.class })
public class BaseModuleConfiguration {

}
