package io.github.junxworks.ep.modules.modules.sys.dto;

import io.github.junxworks.ep.core.Pageable;

/**
 * @Description:
 * @Author: FengYun
 * @Date: 2019/7/2 16:54
 */
public class DictionaryPageable extends Pageable {
    private byte status;
    private String parentCode;
    private String dataValue;
    private String dataCode;

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getDataValue() {
        return dataValue;
    }

    public void setDataValue(String dataValue) {
        this.dataValue = dataValue;
    }

    public String getDataCode() {
        return dataCode;
    }

    public void setDataCode(String dataCode) {
        this.dataCode = dataCode;
    }
}
