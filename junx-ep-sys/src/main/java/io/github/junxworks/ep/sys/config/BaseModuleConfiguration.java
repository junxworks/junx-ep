/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  BaseModuleConfiguration.java   
 * @Package io.github.junxworks.ep.sys.config   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2021-6-20 16:34:18   
 * @version V1.0 
 * @Copyright: 2021 Junxworks. All rights reserved. 
 * 注意：
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.sys.config;

import java.util.Properties;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Import;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;

import io.github.junxworks.ep.core.pvc.PersistenceVersionController;
import io.github.junxworks.ep.core.utils.SpringContextUtils;
import io.github.junxworks.ep.sys.aspect.EpLogAspect;

/**
 * EP基础系统模块配置
 *
 * @ClassName:  BaseModuleConfiguration
 * @author: Michael
 * @date:   2020-7-19 12:17:48
 * @since:  v1.0
 */
@Import({ SpringContextUtils.class, EpLogAspect.class})
@Configuration("JunxEPBaseModuleConfiguration")
@EnableConfigurationProperties({ EPConfig.class })
@ComponentScan("io.github.junxworks.ep.sys")
public class BaseModuleConfiguration {

	/** 常量 PVC_PATH.持久化版本控制路径 */
	private static final String PVC_PATH = "/io/github/junxworks/ep/sys/pvc";

	/** 常量 MODULE_NAME.pvc参数，模块名 */
	private static final String MODULE_NAME = "junx_ep_sys";

	@Bean
	public DefaultKaptcha producer() {
		Properties properties = new Properties();
		properties.put("kaptcha.border", "no");
		properties.put("kaptcha.textproducer.font.color", "black");
		properties.put("kaptcha.textproducer.char.space", "5");
		properties.put("kaptcha.background.clear.from", "gray");
		properties.put("kaptcha.background.clear.to", "white");
		Config config = new Config(properties);
		DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
		defaultKaptcha.setConfig(config);
		return defaultKaptcha;
	}

	@DependsOn("JunxEpSpringContextUtils")
	@Bean(name = "junxEpSysPvc", initMethod = "start", destroyMethod = "stop")
	public PersistenceVersionController junxEpSysPvc() {
		PersistenceVersionController pvc = new PersistenceVersionController();
		pvc.setPvcPath(PVC_PATH);
		pvc.setModuleName(MODULE_NAME);
		return pvc;
	}
}
