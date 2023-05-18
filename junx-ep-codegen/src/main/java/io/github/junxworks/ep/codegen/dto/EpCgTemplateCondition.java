package io.github.junxworks.ep.codegen.dto;

import io.github.junxworks.ep.core.Pageable;

public class EpCgTemplateCondition extends Pageable{

	private Long id;

	private String tmpId;

	private String tmpDesc;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTmpId() {
		return tmpId;
	}

	public void setTmpId(String tmpId) {
		this.tmpId = tmpId;
	}

	public String getTmpDesc() {
		return tmpDesc;
	}

	public void setTmpDesc(String tmpDesc) {
		this.tmpDesc = tmpDesc;
	}

}