/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  MenuTreeVo.java   
 * @Package io.github.junxworks.ep.sys.vo   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2021-1-24 15:35:41   
 * @version V1.0 
 * @Copyright: 2021 Junxworks. All rights reserved. 
 * 注意：
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.sys.vo;

import java.util.List;

/**
 * {类的详细说明}.
 *
 * @ClassName:  MenuTreeVo
 * @author: Michael
 * @date:   2020-7-19 12:17:47
 * @since:  v1.0
 */
public class MenuTreeVo {

    /** id. */
    private Long id;

    /** parent id. */
    private Long parentId;

    /** title. */
    private String title;

    /** href. */
    private String href;

    /** sort. */
    private Byte sort;

    /** spread. */
    private boolean spread;

    /** checked. */
    private boolean checked;

    /** disabled. */
    private boolean disabled;

    /** is parent. */
    private Byte isParent;

    /** children. */
    private List<MenuTreeVo> children;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public Byte getSort() {
        return sort;
    }

    public void setSort(Byte sort) {
        this.sort = sort;
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

    public List<MenuTreeVo> getChildren() {
        return children;
    }

    public void setChildren(List<MenuTreeVo> children) {
        this.children = children;
    }

    public Byte getIsParent() {
        return isParent;
    }

    public void setIsParent(Byte isParent) {
        this.isParent = isParent;
    }
}
