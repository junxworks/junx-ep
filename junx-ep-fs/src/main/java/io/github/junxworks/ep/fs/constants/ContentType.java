package io.github.junxworks.ep.fs.constants;

public enum ContentType {
	ATTACHMENT("attachment", "附件"), INLINE("inline", "页内展示");

	private String value;

	private String desc;

	private ContentType(String value, String desc) {
		this.value = value;
		this.desc = desc;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
