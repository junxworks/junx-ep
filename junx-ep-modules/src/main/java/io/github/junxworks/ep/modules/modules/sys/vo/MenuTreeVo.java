package io.github.junxworks.ep.modules.modules.sys.vo;

import java.util.List;

/**
 * @Description: 菜单树信息
 * @Author: FengYun
 * @Date: 2019/7/1 10:46
 */
public class MenuTreeVo {

    private Long id;

    private Long parentId;

    private String title;

    private String href;

    private Byte sort;

    private boolean spread;

    private boolean checked;

    private boolean disabled;

    private Byte isParent;

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
