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
package io.github.junxworks.ep.sys.constants;

/**
 * 菜单类型枚举类
 *
 * @ClassName:  MenuType
 * @author: Michael
 * @date:   2020-7-26 14:02:23
 * @since:  v1.0
 */
public enum MenuType {

	/** menu. */
	MENU((byte) 0, "菜单"),
	/** auth. */
	AUTH((byte) 1, "权限"),
	/** directory. */
	DIRECTORY((byte) 2, "目录");

	/** value. */
	private byte value;

	/** desc. */
	private String desc;

	/**
	 * 构造一个新的 menu type 对象.
	 *
	 * @param type the type
	 * @param desc the desc
	 */
	private MenuType(byte type, String desc) {
		this.value = type;
		this.desc = desc;
	}

	public byte getValue() {
		return value;
	}

	public void setValue(byte value) {
		this.value = value;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
