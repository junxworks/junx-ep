/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  TreeNodeVo.java   
 * @Package io.github.junxworks.ep.sys.vo   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2020-7-19 12:17:47   
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
 * @ClassName:  TreeNodeVo
 * @author: Michael
 * @date:   2020-7-19 12:17:47
 * @since:  v1.0
 */
public class TreeNodeVo {

	/** id. */
	private String id;

	/** parent id. */
	private String parentId;

	/** title. */
	private String title;

	/** field. */
	private String field;

	/** children. */
	private List<Object> children = Lists.newArrayList();

	/** href. */
	private String href;

	/** spread. */
	private boolean spread = false;

	/** checked. */
	private boolean checked = false;

	/** disabled. */
	private boolean disabled = false;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public List<Object> getChildren() {
		return children;
	}

	public void setChildren(List<Object> children) {
		this.children = children;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public boolean isSpread() {
		return spread;
	}

	public void setSpread(boolean spread) {
		this.spread = spread;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	/**
	 * Adds the children.
	 *
	 * @param child the child
	 */
	public void addChildren(Object child) {
		this.children.add(child);
	}
}