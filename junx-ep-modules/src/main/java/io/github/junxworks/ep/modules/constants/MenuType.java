package io.github.junxworks.ep.modules.constants;

public enum MenuType {

	MENU((byte) 0, "菜单"), AUTH((byte) 1, "权限"), DIRECTORY((byte) 2, "目录");

	private byte value;

	private String desc;

	private MenuType(byte type, String desc) {
		this.value = type;
		this.desc = desc;
	}

	public byte getValue() {
		return value;
	}

	public void setValue(byte value) {
		this.value = value;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
