package io.github.junxworks.ep.codegen.dto;

import io.github.junxworks.ep.core.Pageable;

public class EpCgGeneratorCondition extends Pageable {

	private String genName;

	public String getGenName() {
		return genName;
	}

	public void setGenName(String genName) {
		this.genName = genName;
	}

}