package io.github.junxworks.ep.codegen.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Import;

import io.github.junxworks.ep.core.pvc.PersistenceVersionController;
import io.github.junxworks.ep.core.utils.SpringContextUtils;

@Configuration
@EnableConfigurationProperties({ EPCodegenConfig.class })
@ConditionalOnClass(name = "io.github.junxworks.ep.sys.config.BaseModuleConfiguration")
@ConditionalOnProperty(prefix = "junx.ep.codegen", name = "enabled", havingValue = "true", matchIfMissing = true)
@ComponentScan(value = "io.github.junxworks.ep.codegen")
@Import({ SpringContextUtils.class })
public class CodegenConfiguration {

	/** 常量 PVC_PATH.持久化版本控制路径 */
	private static final String PVC_PATH = "/io/github/junxworks/ep/codegen/pvc";

	/** 常量 MODULE_NAME.pvc参数，模块名 */
	private static final String MODULE_NAME = "junx_ep_codegen";

	@DependsOn("JunxEpSpringContextUtils")
	@Bean(name = "junxEpCodegenPvc", initMethod = "start", destroyMethod = "stop")
	public PersistenceVersionController junxEpCodegenPvc() {
		PersistenceVersionController pvc = new PersistenceVersionController();
		pvc.setPvcPath(PVC_PATH);
		pvc.setModuleName(MODULE_NAME);
		return pvc;
	}
}
