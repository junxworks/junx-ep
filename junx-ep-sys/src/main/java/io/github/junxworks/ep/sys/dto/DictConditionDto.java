/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  DictConditionDto.java   
 * @Package io.github.junxworks.ep.sys.dto   
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
package io.github.junxworks.ep.sys.dto;

import io.github.junxworks.ep.core.Pageable;

/**
 * {类的详细说明}.
 *
 * @ClassName:  DictionaryPageable
 * @author: Michael
 * @date:   2020-7-19 12:17:48
 * @since:  v1.0
 */
public class DictConditionDto extends Pageable {

	/** status. */
	private byte status;

	/** parent code. */
	private String parentCode;

	/** data value. */
	private String dataLabel;

	/** data code. */
	private String dataCode;

	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public String getDataLabel() {
		return dataLabel;
	}

	public void setDataLabel(String dataLabel) {
		this.dataLabel = dataLabel;
	}

	public String getDataCode() {
		return dataCode;
	}

	public void setDataCode(String dataCode) {
		this.dataCode = dataCode;
	}
}
