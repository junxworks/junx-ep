package io.github.junxworks.ep.codegen.core;

public class DatabaseElement {

	private static final long serialVersionUID = -4793412674735445680L;
	private String type;
	private String url;
	private String username;
	private String password;
	private String schema;

	public DatabaseElement(){
		
	}
	public DatabaseElement(String type, String url, String username, String password, String schema) {
		this.type = type;
		this.url = url;
		this.username = username;
		this.password = password;
		this.schema = schema;

	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
