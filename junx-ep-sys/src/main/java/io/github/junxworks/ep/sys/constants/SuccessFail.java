/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  SuccessFail.java   
 * @Package io.github.junxworks.ep.sys.constants   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2020-7-26 14:02:23   
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
 * @ClassName:  SuccessFail
 * @author: Michael
 * @date:   2020-7-26 14:02:23
 * @since:  v1.0
 */
public enum SuccessFail {

	/** success. */
	SUCCESS((byte) 1, "成功"),
	/** fail. */
	FAIL((byte) 0, "失败");

	/** value. */
	private byte value;

	/** desc. */
	private String desc;

	/**
	 * 构造一个新的 success fail 对象.
	 *
	 * @param value the value
	 * @param desc the desc
	 */
	SuccessFail(byte value, String desc) {
		this.value = value;
		this.desc = desc;
	}

	/**
	 * 返回 enum 属性.
	 *
	 * @param value the value
	 * @return enum 属性
	 */
	public static SuccessFail getEnum(byte value) {
		for (SuccessFail SUCCESS : SuccessFail.values()) {
			if (SUCCESS.value == value) {
				return SUCCESS;
			}
		}
		return SuccessFail.FAIL;
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
