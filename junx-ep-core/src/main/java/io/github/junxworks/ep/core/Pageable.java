/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  Pageable.java   
 * @Package io.github.junxworks.ep.core   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2020-7-19 12:18:37   
 * @version V1.0 
 * @Copyright: 2020 Junxworks. All rights reserved. 
 * 注意：
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.core;

import io.github.junxworks.ep.core.orm.annotations.NotCondition;

/**
 * {类的详细说明}.
 *
 * @ClassName:  Pageable
 * @author: Michael
 * @date:   2020-7-19 12:18:37
 * @since:  v1.0
 */
public class Pageable {
	
	/** page no. */
	@NotCondition
	private Integer pageNo;

	/** page size. */
	@NotCondition
	private Integer pageSize;

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

}
