/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  EPConfig.java   
 * @Package io.github.junxworks.ep.sys.config   
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
package io.github.junxworks.ep.sys.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * {类的详细说明}.
 *
 * @ClassName:  EPConfig
 * @author: Michael
 * @date:   2020-7-19 12:17:48
 * @since:  v1.0
 */
@ConfigurationProperties(prefix = "junx.ep")
public class EPConfig {

	/** main page. */
	private String mainPage = "/eui/pages/main/main.html";

	/** system name. */
	private String systemName = "Junx-EP开发平台";

	/** system short name. */
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
