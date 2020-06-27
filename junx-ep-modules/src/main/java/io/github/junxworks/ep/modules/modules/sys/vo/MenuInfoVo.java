package io.github.junxworks.ep.modules.modules.sys.vo;

import org.springframework.util.StringUtils;

import com.google.common.collect.Lists;

import java.util.Date;
import java.util.List;

/**
 * @Description: 菜单信息
 * @Author: FengYun
 * @Date: 2019/7/1 10:46
 */
public class MenuInfoVo {
	private Long id;

	private Long parentId;

	private String parentName;

	private String name;

	private Byte type;

	private String mark;

	private String url;

	private String icon;

	private Byte sort;

	private Byte status;

	private Long creatorId;

	private Date createDate;

	private Long modifierId;

	private Date modifyDate;

	private boolean checked;

	private List<MenuInfoVo> children = Lists.newArrayList();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getParentId() {
		return this.parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Byte getType() {
		return this.type;
	}

	public void setType(Byte type) {
		this.type = type;
	}

	public String getMark() {
		return this.mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Byte getSort() {
		return this.sort;
	}

	public void setSort(Byte sort) {
		this.sort = sort;
	}

	public Byte getStatus() {
		return this.status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public Long getCreatorId() {
		return this.creatorId;
	}

	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Long getModifierId() {
		return this.modifierId;
	}

	public void setModifierId(Long modifierId) {
		this.modifierId = modifierId;
	}

	public Date getModifyDate() {
		return this.modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getTypeDesc() {
		if (!StringUtils.isEmpty(this.type)) {
			if (this.type == 0) {
				return "菜单";
			} else if (this.type == 1) {
				return "权限";
			} else {
				return "目录";
			}
		}
		return "";
	}

	public List<MenuInfoVo> getChildren() {
		return children;
	}

	public void setChildren(List<MenuInfoVo> children) {
		this.children = children;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}
}
