/*
 ***************************************************************************************
 * All rights Reserved, Designed By www.cqhyrc.com.cn
 * @Title:  TreeNodeVo.java   
 * @Package io.github.junxworks.ep.modules.modules.sys.vo   
 * @Description: (用一句话描述该文件做什么)   
 * @author: AOC
 * @date:   2019-12-13 18:51:53   
 * @version V1.0 
 * @Copyright: 2019 重庆华宇集团. All rights reserved. 
 * 注意：本内容仅限于公司内部使用，禁止外泄以及用于其他的商业目
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.modules.modules.sys.vo;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * {类的详细说明}.
 *
 * @ClassName:  TreeNodeVo
 * @author: 王兴
 * @date:   2019-12-13 18:51:53
 * @since:  v1.0
 */
public class TreeNodeVo {

	/** 节点唯一索引值，用于对指定节点进行各类操作. */
	private String id;

	/** 父节点ID. */
	private String parentId;

	/** 节点标题. */
	private String title;

	/** 节点字段名. */
	private String field;

	/** 子节点。支持设定选项同父节点. */
	private List<Object> children = Lists.newArrayList();

	/** 点击节点弹出新窗口对应的 url。需开启 isJump 参数. */
	private String href;

	/** 节点是否初始展开，默认 false. */
	private boolean spread = false;

	/** 节点是否初始为选中状态（如果开启复选框的话），默认 false. */
	private boolean checked = false;

	/** 节点是否为禁用状态。默认 false. */
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

	public void addChildren(Object child) {
		this.children.add(child);
	}
}