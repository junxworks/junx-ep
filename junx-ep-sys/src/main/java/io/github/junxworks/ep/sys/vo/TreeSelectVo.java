/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  TreeSelectVo.java   
 * @Package io.github.junxworks.ep.sys.vo   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2020-7-19 12:17:48   
 * @version V1.0 
 * @Copyright: 2020 Junxworks. All rights reserved. 
 * 注意：
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.sys.vo;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * {类的详细说明}.
 *
 * @ClassName:  TreeSelectVo
 * @author: Michael
 * @date:   2020-7-19 12:17:48
 * @since:  v1.0
 */
public class TreeSelectVo {
	
	/** id. */
	private String id;

	/** parent id. */
	private String parentId;

	/** name. */
	private String name;

	/** value. */
	private String value;

	/** open. */
	private boolean open = false;

	/** checked. */
	private boolean checked = false;

	/** selected. */
	private boolean selected = false;

	/** disabled. */
	private boolean disabled = false;

	/** children. */
	private List<TreeSelectVo> children;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
		this.id = value;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
		this.checked = selected;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
		this.value = id;
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
		this.selected = checked;
	}

	public List<TreeSelectVo> getChildren() {
		return children;
	}

	public void setChildren(List<TreeSelectVo> children) {
		this.children = children;
	}

	/**
	 * Adds the child.
	 *
	 * @param child the child
	 */
	public void addChild(TreeSelectVo child) {
		if (children == null) {
			children = Lists.newArrayList();
		}
		this.children.add(child);
	}

}
