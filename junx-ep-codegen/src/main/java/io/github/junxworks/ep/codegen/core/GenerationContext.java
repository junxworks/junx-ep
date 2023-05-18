package io.github.junxworks.ep.codegen.core;

import java.io.File;
import java.util.Map;

import com.google.common.collect.Maps;

public class GenerationContext {
	private DatabaseElement database;

	private File fileDir;

	private String fileNameExp;

	private String template;

	private String tableName;

	private Map<String, String> attr = Maps.newHashMap();

	public DatabaseElement getDatabase() {
		return database;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public void setDatabase(DatabaseElement database) {
		this.database = database;
	}

	public File getFileDir() {
		return fileDir;
	}

	public void setFileDir(File fileDir) {
		this.fileDir = fileDir;
	}

	public String getFileNameExp() {
		return fileNameExp;
	}

	public void setFileNameExp(String fileNameExp) {
		this.fileNameExp = fileNameExp;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public Map<String, String> getAttr() {
		return attr;
	}

	public void setAttr(Map<String, String> attr) {
		this.attr = attr;
	}

}
