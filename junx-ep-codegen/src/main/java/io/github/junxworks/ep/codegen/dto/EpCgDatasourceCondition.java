package io.github.junxworks.ep.codegen.dto;

import io.github.junxworks.ep.core.Pageable;

public class EpCgDatasourceCondition extends Pageable {

	private String dsId;

	private String dsDesc;

	private String dbType;

	public String getDsId() {
		return dsId;
	}

	public void setDsId(String dsId) {
		this.dsId = dsId;
	}

	public String getDsDesc() {
		return dsDesc;
	}

	public void setDsDesc(String dsDesc) {
		this.dsDesc = dsDesc;
	}

	public String getDbType() {
		return dbType;
	}

	public void setDbType(String dbType) {
		this.dbType = dbType;
	}

}