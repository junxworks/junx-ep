/*
 ***************************************************************************************
 * All rights Reserved, Designed By www.cqhyrc.com.cn
 * @Title:  SystemLogConditionDto.java   
 * @Package io.github.junxworks.ep.modules.modules.sys.dto   
 * @Description: (用一句话描述该文件做什么)   
 * @author: AOC
 * @date:   2019-11-19 17:29:46   
 * @version V1.0 
 * @Copyright: 2019 重庆华宇集团. All rights reserved. 
 * 注意：本内容仅限于公司内部使用，禁止外泄以及用于其他的商业目
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.modules.modules.sys.dto;

import io.github.junxworks.ep.core.Pageable;

/**
 * @Description:
 * @Author: FengYun
 * @Date: 2019/7/2 10:54
 */
public class SystemLogConditionDto extends Pageable {

	/** 操作日期. */
	private String createDate;

	/** 操作员名字. */
	private String name;

	/** 操作名称. */
	private String opName;

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOpName() {
		return opName;
	}

	public void setOpName(String opName) {
		this.opName = opName;
	}

}
