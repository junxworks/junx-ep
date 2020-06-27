package io.github.junxworks.ep.modules.modules.sys.vo;

import java.util.List;

import com.google.common.collect.Lists;

public class TreeSelectVo {
	private String id;

	private String parentId;

	private String name;

	private boolean open = false;

	private boolean checked = false;

	private List<TreeSelectVo> children;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public List<TreeSelectVo> getChildren() {
		return children;
	}

	public void setChildren(List<TreeSelectVo> children) {
		this.children = children;
	}

	public void addChild(TreeSelectVo child) {
		if (children == null) {
			children = Lists.newArrayList();
		}
		this.children.add(child);
	}

}
