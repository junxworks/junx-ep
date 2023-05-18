/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  MenuType.java   
 * @Package io.github.junxworks.ep.sys.constants   
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
package io.github.junxworks.ep.codegen.constants;

public enum DBType {

	MYSQL("mysql", "com.mysql.cj.jdbc.Driver");

	private String type;

	private String driver;

	private DBType(String type, String driver) {
		this.type = type;
		this.driver = driver;
	}

	public static DBType getEnum(String type) {
		for (DBType obj : DBType.values()) {
			if (obj.type == type) {
				return obj;
			}
		}
		return null;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

}
