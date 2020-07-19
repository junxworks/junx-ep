/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  RecordStatus.java   
 * @Package io.github.junxworks.ep.sys.constants   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2020-7-19 12:17:47   
 * @version V1.0 
 * @Copyright: 2020 Junxworks. All rights reserved. 
 * 注意：
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.sys.constants;

/**
 * {类的详细说明}.
 *
 * @ClassName:  RecordStatus
 * @author: Michael
 * @date:   2020-7-19 12:17:47
 * @since:  v1.0
 */
public enum RecordStatus {

	/** normal. */
	NORMAL((byte) 0, "正常"), /** deleted. */
 DELETED((byte) -1, "已删除");

	/** value. */
	private byte value;

	/** desc. */
	private String desc;

	/**
	 * 构造一个新的 record status 对象.
	 *
	 * @param value the value
	 * @param desc the desc
	 */
	RecordStatus(byte value, String desc) {
		this.value = value;
		this.desc = desc;
	}

	/**
	 * 返回 enum 属性.
	 *
	 * @param value the value
	 * @return enum 属性
	 */
	public static RecordStatus getEnum(byte value) {
		for (RecordStatus obj : RecordStatus.values()) {
			if (obj.value == value) {
				return obj;
			}
		}
		return null;
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