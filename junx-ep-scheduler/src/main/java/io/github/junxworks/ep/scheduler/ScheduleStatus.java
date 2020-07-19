/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  ScheduleStatus.java   
 * @Package io.github.junxworks.ep.scheduler   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2020-7-19 12:18:05   
 * @version V1.0 
 * @Copyright: 2020 Junxworks. All rights reserved. 
 * 注意：
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.scheduler;

/**
 * {类的详细说明}.
 *
 * @ClassName:  ScheduleStatus
 * @author: Michael
 * @date:   2020-7-19 12:18:05
 * @since:  v1.0
 */
public enum ScheduleStatus {
	
	/** normal. */
	NORMAL(0),
	
	/** pause. */
	PAUSE(1);

	/** value. */
	private int value;

	/**
	 * 构造一个新的 schedule status 对象.
	 *
	 * @param value the value
	 */
	ScheduleStatus(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
