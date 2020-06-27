/*
 ***************************************************************************************
 * All rights Reserved, Designed By www.cqhyrc.com.cn
 * @Title:  BaseModuleConfiguration.java   
 * @Package io.github.junxworks.ep.modules.config   
 * @Description: (用一句话描述该文件做什么)   
 * @author: AOC
 * @date:   2019-6-26 19:14:22   
 * @version V1.0 
 * @Copyright: 2019 重庆华宇集团. All rights reserved. 
 * 注意：本内容仅限于公司内部使用，禁止外泄以及用于其他的商业目
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.modules.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import io.github.junxworks.ep.modules.aspect.OpLogAspect;
import io.github.junxworks.ep.modules.modules.sys.controller.DataController;
import io.github.junxworks.ep.modules.modules.sys.controller.DictionaryController;
import io.github.junxworks.ep.modules.modules.sys.controller.SysController;
import io.github.junxworks.ep.modules.modules.sys.controller.MenuController;
import io.github.junxworks.ep.modules.modules.sys.controller.OrgController;
import io.github.junxworks.ep.modules.modules.sys.controller.RoleController;
import io.github.junxworks.ep.modules.modules.sys.controller.SystemLogController;
import io.github.junxworks.ep.modules.modules.sys.controller.UserController;
import io.github.junxworks.ep.modules.modules.sys.service.impl.DataServiceImpl;
import io.github.junxworks.ep.modules.modules.sys.service.impl.DictionaryServiceImpl;
import io.github.junxworks.ep.modules.modules.sys.service.impl.MenuServiceImpl;
import io.github.junxworks.ep.modules.modules.sys.service.impl.OrgServiceImpl;
import io.github.junxworks.ep.modules.modules.sys.service.impl.RoleServiceImpl;
import io.github.junxworks.ep.modules.modules.sys.service.impl.SystemLogServiceImpl;
import io.github.junxworks.ep.modules.modules.sys.service.impl.UserServiceImpl;

/**
 * 基础模块配置
 *
 * @ClassName:  BaseModuleConfiguration
 * @author: 王兴
 * @date:   2019-6-26 19:14:22
 * @since:  v1.0
 */
@Configuration
@EnableConfigurationProperties({ EPConfig.class })
@Import({ OpLogAspect.class, UserController.class, UserServiceImpl.class, MenuController.class, MenuServiceImpl.class, OrgController.class, OrgServiceImpl.class, RoleController.class, RoleServiceImpl.class, SystemLogController.class, SystemLogServiceImpl.class, SysController.class, DictionaryController.class, DictionaryServiceImpl.class, DataController.class, DataServiceImpl.class })
public class BaseModuleConfiguration {

}
