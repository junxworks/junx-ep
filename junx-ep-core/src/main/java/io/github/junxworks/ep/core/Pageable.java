package io.github.junxworks.ep.core;

import io.github.junxworks.ep.core.orm.annotations.NotCondition;

public class Pageable {
	@NotCondition
	private Integer pageNo;

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
