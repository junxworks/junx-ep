/*
 ***************************************************************************************
 * All rights Reserved, Designed By www.higinet.com.cn
 * @Title:  ShiroProperties.java   
 * @Package cn.com.higinet.platform.modules.shiro   
 * @Description: (用一句话描述该文件做什么)   
 * @author: 王兴
 * @date:   2018-3-22 14:01:35   
 * @version V1.0 
 * @Copyright: 2018 北京宏基恒信科技有限责任公司. All rights reserved. 
 * 注意：本内容仅限于公司内部使用，禁止外泄以及用于其他的商业目
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.modules.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * EP平台配置
 *
 * @ClassName:  EPConfig
 * @author: 王兴
 * @date:   2019-7-4 9:11:17
 * @since:  v1.0
 */
@ConfigurationProperties(prefix = "ep")
public class EPConfig {

	/** 管理系统首页. */
	private String mainPage = "/eui/pages/main/main.html";

	/** 系统名称. */
	private String systemName = "Junx-EP开发平台";

	/** 系统名称. */
	private String systemShortName = "EP";

	public String getMainPage() {
		return mainPage;
	}

	public void setMainPage(String mainPage) {
		this.mainPage = mainPage;
	}

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public String getSystemShortName() {
		return systemShortName;
	}

	public void setSystemShortName(String systemShortName) {
		this.systemShortName = systemShortName;
	}

}
