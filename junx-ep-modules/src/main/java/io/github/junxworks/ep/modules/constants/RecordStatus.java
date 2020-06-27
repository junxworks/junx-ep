/*
 ***************************************************************************************
 * All rights Reserved, Designed By www.cqhyrc.com.cn
 * @Title:  YesNo.java   
 * @Package com.yrxd.constants.global   
 * @Description: (用一句话描述该文件做什么)   
 * @author: AOC
 * @date:   2019-5-16 16:56:50   
 * @version V1.0 
 * @Copyright: 2019 重庆华宇集团. All rights reserved. 
 * 注意：本内容仅限于公司内部使用，禁止外泄以及用于其他的商业目
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.modules.constants;

/**
 * 数据库记录状态
 *
 * @ClassName:  YesNo
 * @author: 王兴
 * @date:   2019-5-16 16:56:50
 * @since:  v1.0
 */
public enum RecordStatus {

	NORMAL((byte) 0, "正常"), DELETED((byte) -1, "已删除");

	/** value. */
	private byte value;

	/** desc. */
	private String desc;

	/**
	 * 构造一个新的 yes no 对象.
	 *
	 * @param value the value
	 * @param desc the desc
	 */
	RecordStatus(byte value, String desc) {
		this.value = value;
		this.desc = desc;
	}

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
