package io.github.junxworks.ep.modules.modules.sys.dto;

import io.github.junxworks.ep.core.Pageable;

/**
 * @Description:
 * @Author: FengYun
 * @Date: 2019/7/1 10:54
 */
public class MenuPageable extends Pageable {
    private byte status;
    private String name;
    private String parentId;
    private String type;

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
