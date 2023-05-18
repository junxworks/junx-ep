/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  EPCodegenConfig.java   
 * @Package io.github.junxworks.ep.codegen.config   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2021年10月31日 下午3:06:18   
 * @version V1.0 
 * @Copyright: 2021 Junxworks. All rights reserved. 
 * 注意：
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.codegen.config;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.google.common.collect.Maps;

/**
 * 代码生成模块配置
 *
 * @ClassName:  EPCodegenConfig
 * @author: Michael
 * @date:   2021年10月31日 下午3:06:18
 * @since:  v1.0
 */
@ConfigurationProperties(prefix = "junx.ep.codegen")
public class EPCodegenConfig {

	/** 是否启用代码生成模块 */
	private boolean enabled = true;

	/** freemarker模板生成代码时候用到的一些属性配置 */
	private Map<String, String> templateAttr = Maps.newHashMap();

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Map<String, String> getTemplateAttr() {
		return templateAttr;
	}

	public void setTemplateAttr(Map<String, String> templateAttr) {
		this.templateAttr = templateAttr;
	}

}
